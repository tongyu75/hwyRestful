package com.czz.hwy.service.usermanagement.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.app.UsersApp;

@Service
/**
 * 获取用户信息业务层接口
 * 
 * @author 佟士儒
 * @version V1.0
 */
public interface UsersAppService
{
    /**
     * 根据员工ID查询员工信息
     */
	public UsersApp getUserInfoByEmployeeId(int employeeId);
	
	/**
     * 获取员工登陆信息
     */
    public UsersApp login(HttpServletRequest request, int employeeId, String password, String imei);

    /**
     * 根据bean 获取用户集合，207-03-08
     * @param usersApp
     * @return
     */
	public List<UsersApp> getUsersAppListByUsersApp(UsersApp usersApp);

	/**
	 * 根据员工ID和员工名称，修改员工手机标识和手机号，2017-03-08
	 * @param usersApp
	 * @return
	 */
	public int updateUsersAppByBeanApp(UsersApp usersApp);
}
