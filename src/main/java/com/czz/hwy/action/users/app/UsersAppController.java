package com.czz.hwy.action.users.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.bean.user.app.UsersApp;
import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService;
import com.czz.hwy.service.usermanagement.app.UsersAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 通过此Action里的接口，获取用户信息
 * 
 * @author 以克论净环卫管理系统 佟士儒 20170223
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/usersAppController")
public class UsersAppController {
    
    @Autowired
    private UsersAppService usersAppService;
    
    @Autowired
    private AttendanceForPlansAppService attendanceForPlansAppService;
    
    /**
     * 根据员工ID获取用户信息
     */
    @RequestMapping(value="/usersApp/{employeeId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getUsersApp(@PathVariable(value="employeeId") Integer employeeId, HttpServletResponse response, HttpServletRequest request){
        
        Map<String,Object> map = new HashMap<String,Object> ();
        try {
            // 根据员工号获取当前员工的信息
            UsersApp users = usersAppService.getUserInfoByEmployeeId(employeeId.intValue());
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("users", users);
        } catch (Exception e) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.ERROR_MSG);
        }
        // 返回用户信息
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回用户信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    } 
    
    /**
	 * 用户注册：用于将用户信息中的有无手机标识改为有手机，2017-03-08
	 * 功能描述：在手机app系统设置中，加入注册功能，
	 * 			注册时（后台已经添加过用户，先登录再注册），填写环卫工工号（即登录用户名）、姓名、手机号，信息发送至后台服务，
	 * 			服务端判断工号和用户名， 如果后台用户表中工号和用户名同时匹配的数据有1条，
	 * 									则修改手机号码为注册时填写的号码，指定改员工是否有手机状态为“有”，注册成功；
	 * 									其他情况返回相关信息，不能注册。
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> register(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注册失败:注册信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为考勤信息实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			UsersApp usersApp = (UsersApp) JSONObject.toBean(json, UsersApp.class);
			
			
			//3.判定注册参数是否符合规范
			if(usersApp.getEmployeeId() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注册失败: 员工号不能为空！");
				return map;
			}
			
			if(usersApp.getShowname() == null || "".equals(usersApp.getShowname())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注册失败: 员工姓名不能为空！");
				return map;
			}
			
			if(usersApp.getTel() == null || "".equals(usersApp.getTel())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注册失败: 手机号不能为空！");
				return map;
			}
			
			int result = 0;
			List<UsersApp> usersAppList = usersAppService.getUsersAppListByUsersApp(usersApp);//根据bean 获取员工集合，207-03-08
			result = usersAppList.size();
			
			//如果只查询到一条员工信息，则修改员工手机标识为有手机
			if(result == 1){
				usersApp.setHasMobile(1);//设置手机标识为1
				result = usersAppService.updateUsersAppByBeanApp(usersApp);//根据员工ID和员工名称，修改员工手机标识和手机号，2017-03-08 
				AttendanceForPlansApp attendanceForPlansApp = new AttendanceForPlansApp();
				attendanceForPlansApp.setEmployeeId(usersApp.getEmployeeId());
				attendanceForPlansAppService.updateRegisterOrLogoutStatusApp(attendanceForPlansApp);//修改某个员工比当前时间晚的下一个班次的考勤记录，2017-03-16
			}
			
			//7.根据结果返回
			if(result == 1){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "注册成功！");
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "注册失败！");
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	
	/**
	 * 用户注销：用于将用户信息中的有无手机标识改为无手机，2017-03-08
	 * 功能描述：在手机app系统设置中，加入注销功能，
	 * 			注销时，手机端提供当前登录用户的工号和姓名，不需要输入，
	 * 			如果后台用户表中工号和用户名同时匹配的数据有1条，
	 * 			则修改改用户是否有手机的状态为“无”，注销成功；
	 * 			其他情况返回相关信息，不能注销。
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> logout(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注销失败:注册信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为考勤信息实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			UsersApp usersApp = (UsersApp) JSONObject.toBean(json, UsersApp.class);
			
			
			//3.判定注销参数是否符合规范
			if(usersApp.getEmployeeId() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注销失败: 员工号不能为空！");
				return map;
			}
			
			if(usersApp.getShowname() == null || "".equals(usersApp.getShowname())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","注销失败: 员工姓名不能为空！");
				return map;
			}
			
			int result = 0;
			List<UsersApp> usersAppList = usersAppService.getUsersAppListByUsersApp(usersApp);//根据bean 获取员工集合，207-03-08
			result = usersAppList.size();
			
			//如果只查询到一条员工信息，则修改员工手机标识为无手机
			if(result == 1){
				usersApp.setHasMobile(2);//设置手机标识为2
				result = usersAppService.updateUsersAppByBeanApp(usersApp);//根据员工ID和员工名称，修改员工手机标识和手机号，2017-03-08
				AttendanceForPlansApp attendanceForPlansApp = new AttendanceForPlansApp();
				attendanceForPlansApp.setEmployeeId(usersApp.getEmployeeId());
				attendanceForPlansApp.setDutyOnStatus(ConstantUtil.WUSEBEI);
				attendanceForPlansApp.setDutyOffStatus(ConstantUtil.WUSEBEI);
				attendanceForPlansAppService.updateRegisterOrLogoutStatusApp(attendanceForPlansApp);//修改某个员工比当前时间晚的下一个班次的考勤记录，2017-03-16
			}
			
			//7.根据结果返回
			if(result == 1){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "注销成功！");
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "注销失败！");
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	
}
