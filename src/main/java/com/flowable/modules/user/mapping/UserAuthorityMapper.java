package com.flowable.modules.user.mapping;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.flowable.modules.user.domain.UserAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/1
 */
public interface UserAuthorityMapper extends BaseMapper<UserAuthority> {
    List<UserAuthority> getAuthoritesByUserId(@Param("userId") Long id);
}
