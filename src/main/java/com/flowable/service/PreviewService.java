package com.flowable.service;

import com.flowable.modules.expense.domain.Expense;
import com.flowable.modules.user.domain.User;
import com.flowable.modules.user.dto.UserDTO;
import com.flowable.modules.user.service.UserService;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.flowable.dmn.api.DmnDecisionTable;
import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.dmn.api.DmnRuleService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/10
 */
@Service
public class PreviewService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private DmnRuleService dmnRuleService;

    @Autowired
    private DmnRepositoryService dmnRepositoryService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private UserService userService;

    public List<User> getPreviewProcessByDmnKey(String processKey, Expense expense) throws ObjectNotFoundException {
        Long userId = expense.getUserId();
        //预览审批列表
        Map<String, Object> variables = new HashMap<>();
        variables.put("money", expense.getMoney());
        variables.put("count", 1);

        //获取决策表Key
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).singleResult();
        List<DmnDecisionTable> decisionTableList = repositoryService.getDecisionTablesForProcessDefinition(processDefinition.getId());
        if (decisionTableList == null) {
            return null;
        }
        //目前只存在一个决策表
        DmnDecisionTable dmnDecisionTable = decisionTableList.get(0);
        Boolean isExecute = Boolean.TRUE;
        List<User> userList = new ArrayList<>();
        do {
            Map<String, Object> result = dmnRuleService.executeDecisionByKeySingleResult(dmnDecisionTable.getKey(), variables);
            if (result != null && result.get("decided") == Boolean.TRUE) {
                userList.add(userService.getAssignee(userId, (Double) result.get("decisionLevel")));
                variables.put("count", ((BigInteger) variables.get("count")).intValue() + 1);
            } else {
                isExecute = Boolean.FALSE;
            }
        } while (isExecute);
        return userList;
    }

    public List<UserDTO> getRunningPreviewProcessByDmnKey(String processKey, Expense expense) throws ObjectNotFoundException {
        //进行中流程模拟 beg
        //预览审批列表
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", expense.getUserId());
        variables.put("money", expense.getMoney());
        variables.put("count", 1);
        //新建流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);

        //complete task
        for (int i = 0; i < 2; i++) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
            for (Task task : tasks) {
                Map<String, Object> params = taskService.getVariables(task.getId());
                params.put("count", (Integer) params.get("count") + 1);
                params.put("approved", Boolean.TRUE);
                taskService.setVariableLocal(task.getId(), "approved", Boolean.TRUE);
                taskService.setVariableLocal(task.getId(), "assignee", task.getAssignee());
                taskService.complete(task.getId(), params);
            }
        }
        //进行中流程模拟 end

        //获取模拟流程审批表
        List<User> userList = this.getPreviewProcessByDmnKey(processKey, expense);
        Set<String> taskIds = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstance.getId()).list().stream().map(TaskInfo::getId).collect(Collectors.toSet());
        Map<String,List<HistoricVariableInstance>> variableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).taskIds(taskIds).list()
                .stream().collect(Collectors.groupingBy(HistoricVariableInstance::getTaskId));
        Map<String, Boolean> approvedList = new HashMap<>();

        for (List<HistoricVariableInstance> historicTaskInstanceList : variableInstanceList.values()) {
            String key = "";
            Boolean value = Boolean.FALSE;
            for (HistoricVariableInstance historicVariableInstance : historicTaskInstanceList) {
                if (historicVariableInstance.getValue() instanceof String) {
                    key = (String) historicVariableInstance.getValue();
                } else {
                    value = (Boolean) historicVariableInstance.getValue();
                }
            }
            if (!StringUtils.isEmpty(key)) {
                approvedList.put(key, value);
            }
        }

        return userList.stream().map(f->{
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(f, userDTO);
            if (approvedList.get(String.valueOf(userDTO.getId())) != null) {
                userDTO.setIsProcessed(approvedList.get(String.valueOf(userDTO.getId())));
            }
            return userDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 检查dmn是否存在
     * @param dmnKey
     * @return
     */
    public Boolean isDecisionTableExists(String dmnKey) {
        if (StringUtils.isEmpty(dmnKey)) {
            return Boolean.FALSE;
        }
        DmnDecisionTable dmnDecisionTable = dmnRepositoryService.createDecisionTableQuery().decisionTableKey(dmnKey).singleResult();
        return dmnDecisionTable != null;
    }

}
