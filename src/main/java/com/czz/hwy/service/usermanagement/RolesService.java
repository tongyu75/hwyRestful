package com.czz.hwy.service.usermanagement;

import java.util.List;

import com.czz.hwy.bean.user.Roles;

public interface RolesService {

    /**
     * 根据查询条件查询角色列表
     */
    public List<Roles> getAllRoles(Roles bean);
    
    /**
     * 根据查询条件查询角色
     */
    public Roles getRolesByBean(Roles bean);
}
