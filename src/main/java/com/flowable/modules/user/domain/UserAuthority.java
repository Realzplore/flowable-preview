package com.flowable.modules.user.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flowable.core.domain.FtDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ft_user_authority")
public class UserAuthority extends FtDomain {
    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @TableField("authority_name")
    private String authorityName;
}
