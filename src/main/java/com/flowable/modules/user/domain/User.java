package com.flowable.modules.user.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flowable.core.domain.FtDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ft_user")
public class User extends FtDomain {
    /**
     * 登录名
     */
    @TableField
    private String username;

    /**
     * 用户密码
     */
    @TableField
    @JsonIgnore
    private String password;

    /**
     * 用户名称
     */
    @TableField
    private String name;

    /**
     * 人员架构组Id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    /**
     * 职业信息Id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long professionInfoId;

    /**
     * 上级Id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentUserId;

    @TableField(exist = false)
    @JsonIgnore
    private Set<String> authorityList;

    @Override
    public String toString() {
        return "User(" +
                "id="+ this.getId()+
                ",username="+this.getUsername()+
                ",name="+this.getName()+
                ",groupId="+this.getGroupId()+
                ",authorityList="+this.getAuthorityList()
                +")";
    }

}
