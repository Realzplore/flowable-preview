package com.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/25
 */
public class BusinessDelegate implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(BusinessDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("业务数据展示------------------------------");
        Map<String, Object> params = execution.getTransientVariables();
        log.info("params : {}", params.toString());
    }
}
