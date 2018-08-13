package com.flowable.modules.expense.service;

import com.flowable.core.service.BaseService;
import com.flowable.modules.expense.domain.Expense;
import com.flowable.modules.expense.mapping.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/10
 */
@Service
public class ExpenseService extends BaseService<ExpenseMapper,Expense> {
    @Autowired
    private ExpenseMapper expenseMapper;

    public Expense insert(Expense expense) {
        if (expense == null) {
            return null;
        }
        if (expense.getId() != null && expenseMapper.selectById(expense.getId()) != null) {
            throw new RuntimeException("报销单已存在");
        }
        expenseMapper.insert(expense);
        return expense;
    }

    public Expense selectByProcessInstanceId(String processInstanceId) {
        return expenseMapper.selectByProcessInstanceId(processInstanceId);
    }
}
