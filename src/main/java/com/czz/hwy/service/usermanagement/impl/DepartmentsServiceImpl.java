package com.czz.hwy.service.usermanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Departments;
import com.czz.hwy.dao.user.DepartmentsDao;
import com.czz.hwy.service.usermanagement.DepartmentsService;
@Service
public class DepartmentsServiceImpl implements DepartmentsService {

    @Autowired
    private DepartmentsDao departmentsDao;

    /**
     * 根据查询条件查询部门列表（带有分页信息）
     */
    public List<Departments> getAllDeptByBean(Departments bean) {
        return departmentsDao.getInfoListByBean("getAllDeptByBean", bean);
    }
    
    /**
     * 根据查询条件获取部门信息的数量
     */
    public int getDeptCount(Departments bean) {
        return departmentsDao.getInfoCount("getDeptCount", bean);
    }
    
    /**
     * 根据查询条件查询部门
     */
    public Departments getDepartmentByBean(Departments bean) {
        return departmentsDao.getInfoByBean("getDepartmentsByBean", bean);
        
    }
    
    /**
     * 插入部门
     */    
    public int insertDepartments(Departments bean) {
        return departmentsDao.insertInfo("insertDepartments", bean);
    }

    /**
     * 删除部门
     */ 
    public int deleteDepartments(Departments bean) {
        return departmentsDao.updateInfoByBean("updateDepartments", bean);
    }
    
    /**
     * 更新部门信息
     */     
    public int updateDepartments(Departments bean) {
        return departmentsDao.updateInfoByBean("updateDepartments", bean);
    }
}
