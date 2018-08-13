package com.flowable.modules.profession.domain;

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
@TableName("ft_profession_info")
public class ProfessionInfo extends FtDomain {
    /**
     * 所属职业Id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long professionId;

    /**
     * 职业信息名称
     */
    @TableField
    private String professionInfoName;

    /**
     * 职业等级
     */
    @TableField
    private Integer professionLevel;
}
