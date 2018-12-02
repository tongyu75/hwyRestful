package com.czz.hwy.service.usermanagement.watch;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.watch.Users;

@Service
/**
 * 获取用户信息业务层接口
 * 
 * @author 以克论净环卫管理系统    佟士儒 20161011
 * @version V1.0
 */
public interface UsersWatchService {

    /**
     * 登陆用户信息
     */
    public Users getLoginUser(String deviceId);
    
    /**
     * 根据员工ID查询员工信息
     */
    public Users getUserInfoByEmployeeId(int employeeId);

    /**
     * 更新用户信息
     */
    public int updateUserInfoByBean(Users bean);
    
    /**
     * 通过员工ID获得绑定用户信息，一个员工同一时刻只能绑定一个设备
     */
    public Users getBindingUserByEmployeeId(int employeeId);
}
