package com.flowable.modules.group.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flowable.core.domain.FtDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ft_group")
public class Group extends FtDomain {
    /**
     * 人员架构组名称
     */
    @TableField
    private String groupName;

    /**
     * 父架构组id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentGroupId;
}
