package com.czz.hwy.service.usermanagement.impl.watch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.watch.Users;
import com.czz.hwy.dao.user.watch.UsersWatchDao;
import com.czz.hwy.service.usermanagement.watch.UsersWatchService;

/**
 * 获取员工信息业务层实现类
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161012
 * @version V1.0
 */
@Service
public class UsersWatchServiceImpl implements UsersWatchService {
    
    @Autowired
    private UsersWatchDao usersWatchDao;
    
    /**
     * 登陆用户信息
     */
    public Users getLoginUser(String deviceId) {
        return usersWatchDao.getInfoById("getLoginUserWatch", deviceId);
    }
    
    /**
     * 根据员工ID查询员工信息
     */
    public Users getUserInfoByEmployeeId(int employeeId) {
        return usersWatchDao.getInfoById("getUserInfoByEmployeeIdWatch",employeeId);
    }

    /**
     * 更新用户信息
     */
    public int updateUserInfoByBean(Users bean) {
        return usersWatchDao.updateInfoByBean("updateUserInfoWatch", bean);
    }
    
    /**
     * 通过员工ID获得绑定用户信息，一个员工同一时刻只能绑定一个设备
     */
    public Users getBindingUserByEmployeeId(int employeeId) {
        return usersWatchDao.getInfoById("getBindingUserByEmployeeIdWatch", employeeId);
    }    
}
