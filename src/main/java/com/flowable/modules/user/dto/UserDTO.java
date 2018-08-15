package com.flowable.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Set;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    /**
     * 用户Id
     */
    private Long id;
    /**
     * 登录名
     */
    private String username;

    /**
     * 用户密码
     */
    @JsonIgnore
    private String password;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 人员架构组Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    /**
     * 职业信息Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long professionInfoId;

    /**
     * 上级Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentUserId;

    private Set<String> authorityList;

    /**
     * 是否已审批
     */
    private Boolean isProcessed = Boolean.FALSE;
}
