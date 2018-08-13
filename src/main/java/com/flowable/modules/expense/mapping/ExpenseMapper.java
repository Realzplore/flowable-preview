package com.flowable.modules.expense.mapping;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.flowable.modules.expense.domain.Expense;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/10
 */
public interface ExpenseMapper extends BaseMapper<Expense> {
    Expense selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);
}
