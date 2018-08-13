package com.flowable.service;

import com.flowable.modules.expense.domain.Expense;
import com.flowable.modules.user.domain.User;
import com.flowable.modules.user.service.UserService;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.flowable.dmn.api.DmnDecisionTable;
import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.dmn.api.DmnRuleService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/10
 */
@Service
public class PreviewService {
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
