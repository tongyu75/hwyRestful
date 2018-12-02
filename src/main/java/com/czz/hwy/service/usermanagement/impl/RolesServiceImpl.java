package com.czz.hwy.service.usermanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Roles;
import com.czz.hwy.dao.user.RolesDao;
import com.czz.hwy.service.usermanagement.RolesService;
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesDao rolesDao;

    /**
     * 根据查询条件查询角色列表
     */
    public List<Roles> getAllRoles(Roles bean) {
          return rolesDao.getInfoListByBean("getAllRolesByBean",bean);
    }
    
    /**
     * 根据查询条件查询角色
     */
    public Roles getRolesByBean(Roles bean) {
        return rolesDao.getInfoByBean("getRolesByBean", bean);
        
    }
}
