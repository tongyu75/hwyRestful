package com.czz.hwy.service.usermanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.EmployeeSelect;
import com.czz.hwy.dao.user.EmployeeSelectDao;
import com.czz.hwy.service.usermanagement.EmployeeSelectService;

/**
 * 获取员工信息业务层实现类
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161012
 * @version V1.0
 */
@Service
public class EmployeeSelectServiceImpl implements EmployeeSelectService {
    
    @Autowired
    private EmployeeSelectDao employeeSelectDao;
    
    /**
     * 添加员工信息
     */
    public int insertEmployeeSelect(EmployeeSelect bean) {
        return employeeSelectDao.insertInfo("insertEmployeeSelect", bean);
    }

    /**
     * 根据查询条件查询员工
     */
    public EmployeeSelect getEmployeeSelectByBean(EmployeeSelect bean) {
        return employeeSelectDao.getInfoByBean("getEmployeeSelectByBean", bean);
    }

    /**
     * 根据查询条件查询员工列表
     */
    public List<EmployeeSelect> getEmployeeSelectListByBean(EmployeeSelect bean) {
        return employeeSelectDao.getInfoListByBean("getEmployeeSelectByBean", bean);
    }

    /**
     * 修改员工信息
     */
    public int updateEmployeeSelectByBean(EmployeeSelect bean) {
        return employeeSelectDao.insertInfo("updateEmployeeSelect", bean);
    }

    /**
     * 删除员工信息
     */
    public int deleteEmployeeSelectById(int id) {
        return employeeSelectDao.deleteInfoByPk("deleteEmployeeSelectById", id);
    }
    
    /**
     * 删除员工信息
     */
    public int resetSelectEmployee() {
        return employeeSelectDao.deleteAllInfo("resetSelectEmployee");
    }
    
}
