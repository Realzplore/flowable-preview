package com.flowable.modules.group.service;

import com.flowable.modules.group.domain.Group;
import com.flowable.modules.group.mapping.GroupMapper;
import com.flowable.core.service.BaseService;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: liping.zheng
 * @Date: 2018/7/27
 */
@Service
public class GroupService extends BaseService<GroupMapper, Group> {

    public Group selectParentById(Long id) throws ObjectNotFoundException {
        Group group = baseMapper.selectById(id);
        if (group == null) {
            throw new ObjectNotFoundException("该组别不存在");
        }
        Group parent = baseMapper.selectById(group.getParentGroupId());
        return Optional.ofNullable(parent).orElse(null);
    }

}
