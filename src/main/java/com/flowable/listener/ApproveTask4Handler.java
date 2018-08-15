package com.flowable.listener;

import com.flowable.modules.user.domain.User;
import com.flowable.modules.user.service.UserService;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/25
 */
@Component("approveTask4Handler")
public class ApproveTask4Handler implements TaskListener {
    private static final Logger log = LoggerFactory.getLogger(ApproveTask4Handler.class);

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> params = delegateTask.getVariables();
        Long userId = (Long) params.get("userId");
        Double decisionLevel = (Double) params.get("decisionLevel");

        //根据申请人Id和决策级别来获取审批人Id
        try {
            User assignee = userService.getAssignee(userId, decisionLevel);
            delegateTask.setAssignee(String.valueOf(assignee.getId()));
        } catch (ObjectNotFoundException e) {
            log.error("申请人 Id : {} 不存在，请检查", userId);
        }
    }
}
