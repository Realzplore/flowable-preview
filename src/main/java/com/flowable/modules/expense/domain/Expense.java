package com.flowable.modules.expense.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flowable.core.domain.FtDomain;
import com.flowable.modules.expense.domain.enumeration.ExpenseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/10
 */
@TableName("ft_expense")
@Data
@EqualsAndHashCode(callSuper = true)
public class Expense extends FtDomain {
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @TableField
    @NotBlank
    private Double money;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private ExpenseStatus status;

    @TableField
    private String processInstanceId;
}
