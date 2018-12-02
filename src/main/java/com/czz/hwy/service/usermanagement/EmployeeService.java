package com.czz.hwy.service.usermanagement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Employee;

@Service
/**
 * 获取用户信息业务层接口
 * 
 * @author 以克论净环卫管理系统    佟士儒 20161011
 * @version V1.0
 */
public interface EmployeeService {
    
    /**
     * 添加员工信息
     */
    public int insertEmployee(Employee bean);

    /**
     * 根据查询条件查询员工
     */
    public Employee getEmployeeByBean(Employee bean);
    /**
     * 根据查询条件查询员工列表
     */
    public List<Employee> getEmployeeListByBean(Employee bean);

    /**
     * 修改员工信息
     */
    public int updateEmployeeByBean(Employee bean);

    /**
     * 删除员工信息
     */
    public int deleteEmployeeById(int id);
}
