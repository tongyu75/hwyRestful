package com.czz.hwy.action.mission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.Relay;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.mission.RelayService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 替班人员功能
 * @author 张咏雪
 * @Date 2016-09-29
 */
@Controller
@RequestMapping(value = "/relayController")
public class RelayController {

	@Autowired
	private RelayService relayService;//班次业务层
	
//	@Autowired
//	private DutyForBanciService dutyForBanciService;//排班业务层
	
//	@Autowired
//	private DutyPlansService dutyPlansService;//排班计划业务层
	
	@Resource
    private AccessOrigin accessOrigin;//跨域

	@SuppressWarnings("rawtypes")
    @Autowired
	private RedisTemplate redisTemplate;//缓存服务器
	
	
	@SuppressWarnings("unchecked")
	public String getUserKey(){
		Users users  = new Users();
		users.setEmployeeId(2015101501);
		 //将用户信息储存到缓存服务器中
        String userKey = CommonUtils.getEncryptByPublicKey("2015101501");
        redisTemplate.opsForValue().set(userKey,users );
        redisTemplate.expire(userKey, 30, TimeUnit.MINUTES);
        return userKey;
	}
	
	/**
	 * 新增一条替班人员记录，2016-09-29
	 */
	@RequestMapping(value = "/relay", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addRelay(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//			String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
			
			String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码*************************************/
			
			Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
			
			// 设置允许跨域访问的路径
			CommonUtils.setAccessOrigin(request,response, accessOrigin);
			
			
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","新增失败，替班人员信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为班次实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			Relay relay = (Relay) JSONObject.toBean(json, Relay.class);
			
			//3.判断替班人员是否已经存在
			Relay isRelay = relayService.getRelayByBean(relay);//根据员工ID和责任区ID，获取替班人员信息。2016-09-29
			if(isRelay != null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","新增失败，替班人员在本责任区中已经存在！");
				return map;
			}
			
			//4.新增一条替班人员记录
			relay.setCreateId(users.getEmployeeId());
			relay.setCreateAt(new Date());
			int count = relayService.insertRelayByBean(relay);//新增一条替班人员记录,2016-09-29
			
			//5.根据新增结果，返回失败或成功的提示
			if(count > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.INSERT_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/**
	 * 删除一条替班人员记录，2016-09-29
	 */
	@RequestMapping(value = "/relay/{id}", method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> deleteRelay(@PathVariable(value="id") String id, HttpServletResponse response, HttpServletRequest request){
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try{	
			//1.若是请求参数为空，则返回fail
			if(id == null || "".equals(id)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.DELETE_FAIL_MSG);
				return map;
			}
			
			//2.根据ID获取替班人员信息
			Relay relay = relayService.getRelayById(id);//根据ID获取替班人员信息，2016-09-29
			if(relay == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","删除失败，该替班人员信息不存在！");
				return map;
			}
			
			//---------------加上判断是否有替班记录的判断--------------//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//TODO 加上判断是否有替班记录的判断，后期添加,是否有代班记录存在
			//---------------加上判断是否有替班记录的判断--------------//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			//3.删除一条替班人员信息
			int count = relayService.deleteRelayById(id);//物理删除一条替班人员记录，2016-09-29
			
			//4.根据删除结果，返回失败或成功的提示
			if(count > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.DELETE_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/**
	 * 查询所有的替班人员记录，2016-09-29
	 */
	@RequestMapping(value = "/relay", method = RequestMethod.GET)
    @ResponseBody
	public String selectRelayList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为替班人员对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Relay relay = (Relay) JSONObject.toBean(json, Relay.class);
			
			//3.查询替班人员记录总条数
			int total = relayService.getRelayCountByBean(relay);//查询替班人员记录总条数，2016-09-29
			
			//4.查询替班人员记录集合
			List<Relay> rows = relayService.getRelayListByBean(relay);//查询替班人员记录集合，2016-09-29，分页
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", total);
			map.put("rows", rows);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
	} 
	
	/**
	 * 查询所有的替班人员记录，不分页，2016-09-29
	 */
	@RequestMapping(value = "/allRelay", method = RequestMethod.GET)
    @ResponseBody
	public String selectAllRelayList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为替班人员对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Relay relay = (Relay) JSONObject.toBean(json, Relay.class);
			
			//3.查询替班人员记录总条数
			int total = relayService.getRelayCountByBean(relay);//查询替班人员记录总条数，2016-09-29
			
			//4.查询替班人员记录集合
			List<Relay> rows = relayService.getAllRelayListByBean(relay);//查询替班人员记录集合，2016-09-29，不分页
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", total);
			map.put("rows", rows);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
	} 
	
	/**
	 * 查询所有没有排班计划的,并且没有维护在替班人员表中人员，用于点击新增按钮，弹出选择员工的数据查询，2016-09-29
	 */
	@RequestMapping(value = "/relayForCom", method = RequestMethod.GET)
    @ResponseBody
	public String selectNotRelayList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为替班人员对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Relay relay = (Relay) JSONObject.toBean(json, Relay.class);
			
			//3.查询所有没有排班计划，且又不再替班表中的员工总条数
			int total = relayService.getNotRelayCountByBean(relay);//查询所有没有排班计划，且又不再替班表中的员工总条数，2016-09-30
			
			//4.查询所有没有排班计划，且又不再替班表中的员工信息集合
			List<Relay> rows = relayService.getAllNotRelayListByBean(relay);//查询所有没有排班计划，且又不再替班表中的员工信息集合，2016-09-30，不分页
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", total);
			map.put("rows", rows);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
	} 
	
}
