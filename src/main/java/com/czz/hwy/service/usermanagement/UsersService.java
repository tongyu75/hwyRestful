package com.czz.hwy.service.usermanagement;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.czz.hwy.bean.user.Users;
import com.czz.hwy.bean.user.UsersForRedis;

@Service
/**
 * 获取用户信息业务层接口
 * 
 * @author 以克论净环卫管理系统    佟士儒 20161011
 * @version V1.0
 */
public interface UsersService {
    
    /**
     * 根据员工ID查询员工信息
     */
    public Users getUserInfoByEmployeeId(int employeeId);
    
    /**
     * 根据查询条件查询员工数量
     */
    public int getUserCount(Users bean);
    
    /**
     * 根据查询条件查询员工
     */
    public Users getUserInfoByBean(Users users);
    
    /**
     * 根据查询条件查询员工列表
     */
    public List<Users> getAllUserByBean(Users bean);
    
    /**
     * 根据查询条件查询员工列表
     */
    public List<Users> getUserInfoListByBean(Users bean);
    
    /**
     * 添加员工信息
     */
    public int insertUsers(Users bean);
    
    /**
     * 修改员工信息
     */
    public int updateUsersByBean(Users bean); 
    
    /**
     * 根据查询条件查询消息员工列表
     */
    public List<Users> getMessageUsersInfoListByBean(Users bean);
    
    /**
     * 从Excel导入员工信息
     */
    public int insertLeadUserInfo(MultipartFile path);
    
    /**
     * 向缓存服务器添加用户信息
     */
	public boolean addUsersForRedis(String key, UsersForRedis bean);
	
    /**
     * 根据key从缓存服务器获取用户信息
     */
	public UsersForRedis getUsersForRedis(String token);
	
    /**
     * 向缓存服务器添加验证码 
     */
	public boolean addValidateCodeForRedis(String token, String code);
	    
    /**
     * 根据key从缓存服务器获取验证码 
     */
	public String getValidateCodeForRedis(String key);

	/**
	 * 查询员工记录集合，2016-11-15，不分页
	 * @param users
	 * @return
	 */
	public List<Users> getUserList(Users users);
	
    /**
     * 根据责任区ID获取该责任区中的所有用户  2016-11-21
     * @param workPlans
     * @return
     */
    List<Map<String, Object>> getUserIdByAreaId(Users users);    	
}
