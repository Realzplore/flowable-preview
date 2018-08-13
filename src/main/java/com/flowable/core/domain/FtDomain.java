package com.flowable.core.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/27
 */
@Data
public abstract class FtDomain implements Serializable {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
