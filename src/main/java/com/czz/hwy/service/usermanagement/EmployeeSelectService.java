package com.czz.hwy.service.usermanagement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.EmployeeSelect;

@Service
/**
 * 获取用户信息业务层接口
 * 
 * @author 以克论净环卫管理系统    佟士儒 20161011
 * @version V1.0
 */
public interface EmployeeSelectService {
    
	/**
     * 添加员工信息
     */
    public int insertEmployeeSelect(EmployeeSelect bean);

    /**
     * 根据查询条件查询员工
     */
    public EmployeeSelect getEmployeeSelectByBean(EmployeeSelect bean);
    /**
     * 根据查询条件查询员工列表
     */
    public List<EmployeeSelect> getEmployeeSelectListByBean(EmployeeSelect bean);

    /**
     * 修改员工信息
     */
    public int updateEmployeeSelectByBean(EmployeeSelect bean);

    /**
     * 删除员工信息
     */
    public int deleteEmployeeSelectById(int id);
    
    /**
     * 清楚选中的员工表信息
     */
    public int resetSelectEmployee();
}
