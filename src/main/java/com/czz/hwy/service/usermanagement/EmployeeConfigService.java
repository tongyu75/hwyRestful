package com.czz.hwy.service.usermanagement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.EmployeeConfig;

@Service
/**
 * 获取用户信息业务层接口
 * 
 * @author 以克论净环卫管理系统    佟士儒 20161011
 * @version V1.0
 */
public interface EmployeeConfigService {
    
    /**
     * 添加员工信息
     */
    public int insertEmployeeConfig(EmployeeConfig bean);

    /**
     * 根据查询条件查询员工
     */
    public EmployeeConfig getEmployeeConfigByBean(EmployeeConfig bean);
    /**
     * 根据查询条件查询员工列表
     */
    public List<EmployeeConfig> getEmployeeConfigListByBean(EmployeeConfig bean);

    /**
     * 修改员工信息
     */
    public int updateEmployeeConfigByBean(EmployeeConfig bean);

    /**
     * 删除员工信息
     */
    public int deleteEmployeeConfigById(int id);
}
