package com.flowable.core.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.flowable.core.domain.FtDomain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/27
 */
public abstract class BaseService<M extends BaseMapper<T>, T extends FtDomain> {
    @Autowired
    protected M baseMapper;

    public T selectById(Long id) {
        T result = baseMapper.selectById(id);
        return Optional.ofNullable(result).orElse(null);
    }
    
    @SuppressWarnings("unchecked")
    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        wrapper = (Wrapper<T>) SqlHelper.fillWrapper(page, wrapper);
        wrapper.setSqlSelect("id");
        List<T> entities = baseMapper.selectPage(page, wrapper);
        List<Long> entityIds = entities.stream().map(T::getId).collect(Collectors.toList());
        List<T> result = new ArrayList<>();
        for (Long entityId : entityIds) {
            T et = this.selectById(entityId);
            result.add(et);
        }
        page.setRecords(result);
        return page;
    }

}
