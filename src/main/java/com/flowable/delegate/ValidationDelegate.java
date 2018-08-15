package com.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import javax.validation.ValidationException;
import java.util.Map;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/25
 */
public class ValidationDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> params = execution.getTransientVariables();
        Double money = (Double) params.get("money");
        if (money == null || money < 0) {
            throw new ValidationException("报销金额有误，请检查.");
        }
    }
}
