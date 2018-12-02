package com.czz.hwy.service.usermanagement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Departments;

@Service
public interface DepartmentsService{

    /**
     * 根据查询条件查询部门列表（带有分页信息）
     */
    public List<Departments> getAllDeptByBean(Departments bean);
    
    /**
     * 根据查询条件获取部门信息的数量
     */
    public int getDeptCount(Departments bean);
    
    /**
     * 根据查询条件查询部门(没有分页信息)
     */
    public Departments getDepartmentByBean(Departments bean);
    
    /**
     * 插入部门
     */    
    public int insertDepartments(Departments bean);

    /**
     * 删除部门
     */ 
    public int deleteDepartments(Departments bean);
    
    /**
     * 更新部门信息
     */     
    public int updateDepartments(Departments bean);
}
