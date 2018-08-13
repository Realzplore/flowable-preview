package com.flowable.modules.expense.domain.enumeration;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/10
 */
public enum ExpenseStatus {

    EDITING(1001),//待提交
    APPROVING(1002),//待审批
    SUCCESS(1003),//审批通过
    REJECTED(1004)//审批驳回
    ;

    Integer value;

    ExpenseStatus(Integer value) {
        this.value = value;
    }
}
