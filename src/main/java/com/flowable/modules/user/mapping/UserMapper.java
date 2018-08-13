package com.flowable.modules.user.mapping;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.flowable.modules.user.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/27
 */
public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(@Param("username") String username);
}
