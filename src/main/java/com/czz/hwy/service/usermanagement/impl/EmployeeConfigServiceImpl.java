package com.czz.hwy.service.usermanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.EmployeeConfig;
import com.czz.hwy.dao.user.EmployeeConfigDao;
import com.czz.hwy.service.usermanagement.EmployeeConfigService;

/**
 * 获取员工信息业务层实现类
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161012
 * @version V1.0
 */
@Service
public class EmployeeConfigServiceImpl implements EmployeeConfigService {
    
    @Autowired
    private EmployeeConfigDao employeeConfigDao;
    
    /**
     * 添加员工信息
     */
    public int insertEmployeeConfig(EmployeeConfig bean) {
        return employeeConfigDao.insertInfo("insertEmployeeConfig", bean);
    }

    /**
     * 根据查询条件查询员工
     */
    public EmployeeConfig getEmployeeConfigByBean(EmployeeConfig bean) {
        return employeeConfigDao.getInfoByBean("getEmployeeConfigByBean", bean);
    }

    /**
     * 根据查询条件查询员工列表
     */
    public List<EmployeeConfig> getEmployeeConfigListByBean(EmployeeConfig bean) {
        return employeeConfigDao.getInfoListByBean("getEmployeeConfigByBean", bean);
    }

    /**
     * 修改员工信息
     */
    public int updateEmployeeConfigByBean(EmployeeConfig bean) {
        return employeeConfigDao.insertInfo("updateEmployeeConfig", bean);
    }

    /**
     * 删除员工信息
     */
    public int deleteEmployeeConfigById(int id) {
        return employeeConfigDao.deleteInfoByPk("deleteEmployeeConfigById", id);
    }

}
