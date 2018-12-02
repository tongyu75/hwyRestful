package com.czz.hwy.service.usermanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Employee;
import com.czz.hwy.dao.user.EmployeeDao;
import com.czz.hwy.service.usermanagement.EmployeeService;

/**
 * 获取员工信息业务层实现类
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161012
 * @version V1.0
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeDao employeeDao;
    
    /**
     * 添加员工信息
     */
    public int insertEmployee(Employee bean) {
        return employeeDao.insertInfo("insertEmployee", bean);
    }

    /**
     * 根据查询条件查询员工
     */
    public Employee getEmployeeByBean(Employee bean) {
        return employeeDao.getInfoByBean("getEmployeeByBean", bean);
    }

    /**
     * 根据查询条件查询员工列表
     */
    public List<Employee> getEmployeeListByBean(Employee bean) {
        return employeeDao.getInfoListByBean("getEmployeeByBean", bean);
    }

    /**
     * 修改员工信息
     */
    public int updateEmployeeByBean(Employee bean) {
        return employeeDao.insertInfo("updateEmployee", bean);
    }

    /**
     * 删除员工信息
     */
    public int deleteEmployeeById(int id) {
        return employeeDao.deleteInfoByPk("deleteEmployeeById", id);
    }
}
