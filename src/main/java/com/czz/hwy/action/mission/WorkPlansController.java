package com.czz.hwy.action.mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.mission.WorkPlansService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 新的排班计划管理功能
 * @author 张咏雪
 * @Date 2016-09-29
 */
@Controller
@RequestMapping(value = "/workPlansController")
public class WorkPlansController {

	@Autowired
	private WorkPlansService workPlansService;//新的排班计划业务层
	
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
	 * 新增排班计划，2016-09-30
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/workPlans", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addWorkPlans(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//			String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
			
			String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码*************************************/
			
			Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
			
			if(users == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","用户信息过期，请重新登录系统！");
				return map;
			}
			
			// 设置允许跨域访问的路径
			CommonUtils.setAccessOrigin(request,response, accessOrigin);
			
			
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败，排班计划信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为排班计划
			JSONObject json = JSONObject.fromObject(acceptContent);
			String pointIds = json.getString("pointIds");
			String areaId = json.getString("areaId");
			String plansArr = json.getString("plansList");
			int roleId = Integer.parseInt(json.getString("roleId"));
			if(pointIds == null || pointIds == "" 
					|| areaId == null || areaId == "" ){//判断责任点和责任区是否为空
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败:责任点或责任区不能为空！");
				return map;
			}
			
			//3.删除原有责任区责任点的排班计划
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			deleteMap.put("pointIds", pointIds);
			deleteMap.put("areaId", areaId);
			deleteMap.put("roleId", roleId);
			int deleteNum = workPlansService.deleteWorkPlansByMap(deleteMap);//根据责任区和责任点删除排班计划。2016-09-30
			
			//4.获取要重新添加的排班计划集合
			JSONArray jsonArray = JSONArray.fromObject(plansArr);
			List<WorkPlans> plansList = new ArrayList<WorkPlans>();
			if(plansArr != null && !"".equals(plansArr)){//获取排班计划表
				plansList = jsonArray.toList(jsonArray, WorkPlans.class);
			}
			
			if(plansList.size() == 0 && deleteNum == 0){//如果要添加的排班计划集合为空，且删除旧的排班计划个数等于0,提示没有排班计划需要保存
				map.put("result", ConstantUtil.FAIL);
				map.put("information","没有排班计划需要保存！");
				return map;
			}else if(plansList.size() == 0 && deleteNum > 0){//如果要添加的排班计划集合为空，且删除旧的排班计划个数大于0,提示排班计划保存成功
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","排班计划保存成功！");
				return map;
			}
			
			//5.获取轮班频率和频率对应的月，星期，日，时分秒
			String tradeRate = json.getString("tradeRate");//换班频率:1 不循环,2 日循环，3周循环，4月循换，
			if(tradeRate == null || "".equals(tradeRate)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","请先选择轮班频率！");
				return map;
			}
			
			SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm:ss"); 
			String timeStr = json.getString("time");//时分秒，用于日，周，月循换
			Date time = null;
			if(timeStr != null && !"".equals(timeStr)){
				time = sdf.parse(timeStr);
			}
			String weekStr = json.getString("week");//周几，7表示7，用于周循环
			int week = 0;
			if(weekStr != null && !"".equals(weekStr)){
				week = Integer.parseInt(weekStr);
			}
			String dayStr = json.getString("day");//每月几号，用于月循换
			int day = 0;
			if(dayStr != null && !"".equals(dayStr)){
				day = Integer.parseInt(dayStr);
			}
			
			//TODO 判断轮班频率的月，星期，时分秒的判断
			//判断轮班频率的月，星期，时分秒的判断
			if("1".equals(tradeRate)){//不循环
				if(time != null || week != 0 || day != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率是不循环时，请保证【时分秒】，【星期】和【日】为空！");
					return map;
				}
			}else if("2".equals(tradeRate)){//日循环
				if(time == null){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为日循环时，请保证【时分秒】不能为空！");
					return map;
				}
				if(week != 0 || day != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为日循环时，请保证【星期】和【日】为空！");
					return map;
				}
			}else if("3".equals(tradeRate)){//周循环
				if(time == null || week == 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【时分秒】和【星期】不能为空！");
					return map;
				}
				if(day != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【日】为空！");
					return map;
				}
				
			}else if("4".equals(tradeRate)){//月循换
				if(time == null || day == 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【时分秒】和【日】不能为空！");
					return map;
				}
				if(week != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【星期】为空！");
					return map;
				}
			}
			
			//6.组合排班计划集合，并新增排班计划集合。
			Date createAt = new Date();
			for(WorkPlans workPlans : plansList){
				workPlans.setAreaId(Integer.parseInt(areaId));
				workPlans.setTradeRate(Integer.parseInt(tradeRate));
				workPlans.setTime(time);
				workPlans.setWeek(week);
				workPlans.setDay(day);
				workPlans.setCreateId(users.getEmployeeId());
				workPlans.setCreateAt(createAt);
				workPlans.setRoleId(roleId);
			}
			
			int result = workPlansService.batchAddWorkPlans(plansList);//批量新增排班计划，2016-09-30
			
			//批量更新该责任区环卫工的轮班频率，2016-10-13
			WorkPlans plans = plansList.get(0);
			plans.setUpdateId(users.getEmployeeId());
			plans.setUpdateAt(createAt);
			
			workPlansService.batchaUpdateTradeRate(plansList.get(0));//批量更新某责任区环卫工的轮班频率，2016-10-13
			
			//TODO 为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分
			//******************************为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分*****************************/
			//7.删除某责任区下，某些责任点的旧系统的排班计划表数据
			//7.1 根据责任区ID和责任点ID集合，删除旧系统排班计划表数据
			workPlansService.deleteDutyForBanciByMap(deleteMap);//根据责任区ID和责任点ID，删除旧系统排班计划表数据,2016-10-09
			
			//7.2根据责任区ID和责任点ID集合，删除旧系统排班计划表数据
			workPlansService.deleteDutyPlansByMap(deleteMap);//根据责任区ID和责任点ID，删除旧系统排班计划表数据,2016-10-09
			
			//7.3 根据排班计划，新增旧系统排班计划集合
			workPlansService.batchAddDutyForBanciByMap(deleteMap);//根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
			
			//7.4 根据排班计划，新增旧系统排班计划集合
			workPlansService.batchAddDutyPlansByMap(deleteMap);//根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
			//******************************为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分*****************************/
			
			//7.根据结果返回
			if(result > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "排班计划保存成功！");
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "排班计划保存失败！");
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/*@RequestMapping(value = "/delworkPlans", method = RequestMethod.GET)
	  @ResponseBody
	public void testDelete(){
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("pointIds", "1,2");
		deleteMap.put("areaId", "1");
		deleteMap.put("roleId", "1");
		
//		int deleteNum = workPlansService.deleteWorkPlansByMap(deleteMap);//根据责任区和责任点删除排班计划。2016-09-30
		
		int deleterow1 = workPlansService.deleteDutyForBanciByMap(deleteMap);//根据责任区ID和责任点ID集合，删除旧系统排班计划表数据,2016-10-09
		
		//7.2根据责任区ID和责任点ID集合，删除旧系统排班计划表数据
		int deleterow2 = workPlansService.deleteDutyPlansByMap(deleteMap);//根据责任区ID和责任点ID集合，删除旧系统排班计划表数据,2016-10-09
		
		//7.3 根据排班计划，新增旧系统排班计划集合
		int addrow1 = workPlansService.batchAddDutyForBanciByMap(deleteMap);//根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
		
		//7.4 根据排班计划，新增旧系统排班计划集合
		int addrow2 = workPlansService.batchAddDutyPlansByMap(deleteMap);//根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
		
		List<WorkPlans> workPlansList = workPlansService.selectWorkPlansListByBanZuId("1");//根据班组ID，查询出排班计划集合，2016-10-10
		
	}*/
	
	/**
	 * 查询排班计划，2016-09-30
	 */
	@RequestMapping(value = "/workPlans", method = RequestMethod.GET)
    @ResponseBody
	public String selectWorkPlansList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			
			WorkPlans workPlans = (WorkPlans) JSONObject.toBean(json, WorkPlans.class);
			
			//3.查询排班计划记录总条数，即该责任区的责任点总条数
			int total = workPlansService.getWorkPlansCount(workPlans);//查询排班计划记录总条数，2016-10-01
			
			//4.查询班组记录集合，对责任点进行分页，不是对排班计划表进行分页
			List<WorkPlans> planList = workPlansService.getWorkPlansList(workPlans);//查询排班计划记录集合，2016-10-01，分页
			
			Map<String, Object> rows = new HashMap<String,Object>();
			int tradeRate = 0;
			Date time = null;
			int week = 0;
			int day = 0;
			
			rows.put("areaId", planList.get(0).getAreaId());
			Map<String, List<WorkPlans>> plansMap = new HashMap<String, List<WorkPlans>>();//保存排班计划
			Map<Integer, Object> pointIdMap = new HashMap<Integer, Object>();//保存责任区
			for(WorkPlans plans : planList){
				String pointName =  pointIdMap.containsKey(plans.getPointId()) ? pointIdMap.get(plans.getPointId()).toString() : null;
				List<WorkPlans> list = null;
				if(pointName == null || "".equals(pointName)){//如果这个责任点的排班计划还没有保存到plansMap中
					pointName = plans.getPointName();
					pointIdMap.put(plans.getPointId(), plans.getPointName());
					list = new ArrayList<WorkPlans>();
				}else{
					list =  plansMap.get(pointName);
				}
				list.add(plans);
				plansMap.put(pointName, list);
				if(plans.getTradeRate() != 0 && tradeRate == 0){
					tradeRate = plans.getTradeRate();
					time = plans.getTime();
					week = plans.getWeek();
					day = plans.getDay();
				}
			}
			
			rows.put("tradeRate", tradeRate);
			rows.put("time", time);
			rows.put("week", week);
			rows.put("day", day);
			rows.put("plansList", plansMap);
			
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
	 * 根据责任区查询责任点列表，2016-10-01
	 */
	@RequestMapping(value = "/workPlans/{areaId}", method = RequestMethod.GET)
    @ResponseBody
	public String selectPointList(@PathVariable(value="areaId") String areaId,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(areaId == null || "".equals(areaId)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			//2.查询排班计划记录总条数，即该责任区的责任点总条数
			WorkPlans workPlans = new WorkPlans();
			workPlans.setAreaId(Integer.parseInt(areaId));
			int total = workPlansService.getWorkPlansCount(workPlans);//查询责任区下责任点的个数总条数，2016-10-01
			
			//4.查询某个责任区下的所有责任点
			List<Map<String, Object>> rows = workPlansService.getPointListByAreaId(areaId);//获取某个责任区下的所有责任点列表，2016-10-01
			
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
	 * 查询本责任区且没有替班的所有员工信息，2016-10-12
	 * 2016-11-10：若是环卫工排班，从员工归属地管理功能中获取数据
	 * 			       若是检测员排班，从员工表中获取检测员列表
	 */
	@RequestMapping(value = "/employeeForPlans", method = RequestMethod.GET)
    @ResponseBody
	public String selectEmployeeList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			
			WorkPlans workPlans = (WorkPlans) JSONObject.toBean(json, WorkPlans.class);
			
			int total = 0;
			List<UserArea> rows = null;
			if(workPlans.getRoleId() == 1){//若是环卫工排班
				//3.查询某责任区且不是替班人员的用户信息集合总数
				total = workPlansService.selectEmployeeCount(workPlans);//查询本责任区且不是替班人员的用户信息集合总数，2016-10-13
				
				//4.查询某责任区且不是替班人员的用户信息集合
				rows = workPlansService.selectEmployeeList(workPlans);//查询本责任区且不是替班人员的用户信息集合，2016-10-13
			}else if(workPlans.getRoleId() == 2) {//若是检测员排班
				
				//3.查询检测员总数
				total = workPlansService.selectJCEmployeeCount(workPlans);//查询检测员总数，2016-11-10
				//4.查询检测员集合
				rows = workPlansService.selectJCEmployeeList(workPlans);//查询检测员集合，2016-11-10
			}
			
			
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
	 * 查询检测员排班计划，2016-10-14
	 */
	@RequestMapping(value = "/workPlansForJC", method = RequestMethod.GET)
    @ResponseBody
	public String selectJCWorkPlansList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			
			WorkPlans workPlans = (WorkPlans) JSONObject.toBean(json, WorkPlans.class);
			
			//3.查询检测员排班计划记录总条数，即责任区的总条数
			int total = workPlansService.getJCWorkPlansCount(workPlans);//查询检测员排班计划记录总条数,即责任区的总条数，2016-10-14
			
			//4.查询检测员排班计划记录集合，对责任区进行分页，不是对排班计划表进行分页
			List<WorkPlans> planList = workPlansService.getJCWorkPlansList(workPlans);//查询检测员排班计划记录集合，2016-10-14，分页
			
			Map<String, Object> rows = new HashMap<String,Object>();
			int tradeRate = 0;
			Date time = null;
			int week = 0;
			int day = 0;
			
			List<Map<String, List<WorkPlans>>> plansList = new ArrayList<Map<String, List<WorkPlans>>>();//保存排班计划
			Map<String, List<WorkPlans>> plansMap = new LinkedHashMap<String, List<WorkPlans>>();//保存排班计划
			Map<Integer, Object> areaIdMap = new HashMap<Integer, Object>();//保存责任区ID
			for(WorkPlans plans : planList){
				String areaName =  areaIdMap.containsKey(plans.getAreaId()) ? areaIdMap.get(plans.getAreaId()).toString() : null;
				List<WorkPlans> list = null;
				if(areaName == null || "".equals(areaName)){//如果这个责任区的排班计划还没有保存到plansMap中
					areaName = plans.getAreaName();
					areaIdMap.put(plans.getAreaId(), plans.getAreaName());
					list = new ArrayList<WorkPlans>();
				}else{
					list =  plansMap.get(areaName);
				}
				list.add(plans);
				plansMap.put(areaName, list);
				if(plans.getTradeRate() != 0 && tradeRate == 0){
					tradeRate = plans.getTradeRate();
					time = plans.getTime();
					week = plans.getWeek();
					day = plans.getDay();
				}
			}
			
			rows.put("tradeRate", tradeRate);
			rows.put("time", time);
			rows.put("week", week);
			rows.put("day", day);
			
			//为了解决乱序的问
			Map<String, List<WorkPlans>> mapEntry = null;
			List<WorkPlans> plans = null;
			String value = null;
			for(Map.Entry<Integer, Object> entry : areaIdMap.entrySet()){
				value = entry.getValue().toString();
				mapEntry = new HashMap<String, List<WorkPlans>>();
				plans = (List<WorkPlans>) plansMap.get(value);
				mapEntry.put(value, plans);
				plansList.add(mapEntry);
			}
			rows.put("plansList", plansList);
			
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
	 * 新增检测员排班计划，2016-10-14
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/workPlansForJC", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addJCWorkPlans(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//			String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
			
			String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码*************************************/
			
			Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
			
			if(users == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","用户信息过期，请重新登录系统！");
				return map;
			}
			
			// 设置允许跨域访问的路径
			CommonUtils.setAccessOrigin(request,response, accessOrigin);
			
			
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败，排班计划信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为排班计划
			JSONObject json = JSONObject.fromObject(acceptContent);
			String areaIds = json.getString("areaIds");
			String plansArr = json.getString("plansList");
			int roleId = Integer.parseInt(json.getString("roleId"));
			if(areaIds == null || areaIds == ""){//判断责任点和责任区是否为空
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败:责任区不能为空！");
				return map;
			}
			
			//3.删除原有责任区,且为检测员的排班计划
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			deleteMap.put("areaIds", areaIds);
			deleteMap.put("roleId", roleId);
			int deleteNum = workPlansService.deleteJCWorkPlansByMap(deleteMap);//根据责任区删除检测员排班计划。2016-10-14
			
			//4.获取要重新添加的排班计划集合
			JSONArray jsonArray = JSONArray.fromObject(plansArr);
			List<WorkPlans> plansList = new ArrayList<WorkPlans>();
			if(plansArr != null && !"".equals(plansArr)){//获取排班计划表
				plansList = jsonArray.toList(jsonArray, WorkPlans.class);
			}
			
			if(plansList.size() == 0 && deleteNum == 0){//如果要添加的排班计划集合为空，且删除旧的排班计划个数等于0,提示没有排班计划需要保存
				map.put("result", ConstantUtil.FAIL);
				map.put("information","没有排班计划需要保存！");
				return map;
			}else if(plansList.size() == 0 && deleteNum > 0){//如果要添加的排班计划集合为空，且删除旧的排班计划个数大于0,提示排班计划保存成功
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","排班计划保存成功！");
				return map;
			}
			
			//5.获取轮班频率和频率对应的月，星期，日，时分秒
			String tradeRate = json.getString("tradeRate");//换班频率:1 不循环,2 日循环，3周循环，4月循换，
			if(tradeRate == null || "".equals(tradeRate)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","请先选择轮班频率！");
				return map;
			}
			
			SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm:ss"); 
			String timeStr = json.getString("time");//时分秒，用于日，周，月循换
			Date time = null;
			if(timeStr != null && !"".equals(timeStr)){
				time = sdf.parse(timeStr);
			}
			String weekStr = json.getString("week");//周几，7表示7，用于周循环
			int week = 0;
			if(weekStr != null && !"".equals(weekStr)){
				week = Integer.parseInt(weekStr);
			}
			String dayStr = json.getString("day");//每月几号，用于月循换
			int day = 0;
			if(dayStr != null && !"".equals(dayStr)){
				day = Integer.parseInt(dayStr);
			}
			
			//TODO 判断轮班频率的月，星期，时分秒的判断
			//判断轮班频率的月，星期，时分秒的判断
			if("1".equals(tradeRate)){//不循环
				if(time != null || week != 0 || day != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率是不循环时，请保证【时分秒】，【星期】和【日】为空！");
					return map;
				}
			}else if("2".equals(tradeRate)){//日循环
				if(time == null){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为日循环时，请保证【时分秒】不能为空！");
					return map;
				}
				if(week != 0 || day != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为日循环时，请保证【星期】和【日】为空！");
					return map;
				}
			}else if("3".equals(tradeRate)){//周循环
				if(time == null || week == 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【时分秒】和【星期】不能为空！");
					return map;
				}
				if(day != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【日】为空！");
					return map;
				}
				
			}else if("4".equals(tradeRate)){//月循换
				if(time == null || day == 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【时分秒】和【日】不能为空！");
					return map;
				}
				if(week != 0){
					map.put("result", ConstantUtil.FAIL);
					map.put("information","当轮班频率为周循环时，请保证【星期】为空！");
					return map;
				}
			}
			
			//6.组合排班计划集合，并新增排班计划集合。
			Date createAt = new Date();
			for(WorkPlans workPlans : plansList){
				workPlans.setTradeRate(Integer.parseInt(tradeRate));
				workPlans.setTime(time);
				workPlans.setWeek(week);
				workPlans.setDay(day);
				workPlans.setCreateId(users.getEmployeeId());
				workPlans.setCreateAt(createAt);
				workPlans.setRoleId(roleId);
			}
			//批量新增检测员排班计划，2016-10-14
			int result = workPlansService.batchAddWorkPlans(plansList);
			
			//批量更新检测员的轮班频率，2016-10-13
			WorkPlans plans = plansList.get(0);
			plans.setUpdateId(users.getEmployeeId());
			plans.setUpdateAt(createAt);
			
			workPlansService.batchaUpdateJCTradeRate(plansList.get(0));//批量更新检测员的轮班频率，2016-10-14
			
			//TODO 为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分
			//******************************为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分*****************************/
			//7.删除旧系统的检测员的排班计划表数据
			//7.1 根据责任区ID集合，删除旧系统排班计划表数据
			workPlansService.deleteJCDutyForBanciByMap(deleteMap);//根据责任区ID集合，删除旧系统排班计划表数据，2016-10-14
			
			//7.2根据责任区ID集合，删除旧系统排班计划表数据
			workPlansService.deleteJCDutyPlansByMap(deleteMap);//根据责任区ID集合，删除旧系统排班计划表数据，2016-10-14
			
			//7.3 根据排班计划，新增旧系统检测员排班计划集合
			workPlansService.batchAddJCDutyForBanciByMap(deleteMap);//根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
			
			//7.4 根据排班计划，新增旧系统检测员排班计划集合
			workPlansService.batchAddJCDutyPlansByMap(deleteMap);//根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
			//******************************为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分*****************************/
			
			//7.根据结果返回
			if(result > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "排班计划保存成功！");
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "排班计划保存失败！");
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/**
	 * 查询排班计划，分页，用于排班计划查看功能，2016-10-17
	 */
	@RequestMapping(value = "/workPlansForAll", method = RequestMethod.GET)
    @ResponseBody
	public String selectAllWorkPlansList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			
			WorkPlans workPlans = (WorkPlans) JSONObject.toBean(json, WorkPlans.class);
			
			//3.查询排班计划记录总条数，
			int total = workPlansService.getAllWorkPlansCount(workPlans);//根据查询条件，查询排班计划记录总条数,2016-10-17
			
			//4.查询排班计划记录集合，分页
			List<WorkPlans> rows = workPlansService.getAllWorkPlansList(workPlans);//根据查询条件，查询排班计划记录集合，2016-10-17，分页
			
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
	 * 新增考核员排班计划，2016-10-31
	 */
	@RequestMapping(value = "/workPlansForKH", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addKHWorkPlans(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//			String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
			
			String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码*************************************/
			
			Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
			
			if(users == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","用户信息过期，请重新登录系统！");
				return map;
			}
			
			// 设置允许跨域访问的路径
			CommonUtils.setAccessOrigin(request,response, accessOrigin);
			
			
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败，排班计划信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为排班计划
			JSONObject json = JSONObject.fromObject(acceptContent);
			String employeeId = json.getString("employeeId");
			int roleId = Integer.parseInt(json.getString("roleId"));
			String banzuId  = json.getString("banzuId");
			String areaId = json.getString("areasId");
			Date createAt = new Date();
			
			//3.根据考核员ID删除该考核员的排班计划
			int delNum = workPlansService.deleteWorPlansByEmployeeIds(employeeId);//删除某个考核员所有的排班计划，2016-11-01
			
			//传递过来的责任区ID集合或班组ID为空，则表示清空该考核员的排班计划
			if(areaId == null || areaId == "" || banzuId == null || "".equals(banzuId)){
				if(delNum > 0){//且删除条数大于0，表示该考核员原来是有排班计划的
					map.put("result", ConstantUtil.SUCCESS);
					map.put("information","排班计划保存成功！");
					return map;
				}else{//且删除条数等于0，表示该考核员原来是没有排班计划的
					map.put("result", ConstantUtil.FAIL);
					map.put("information","没有排班计划需要保存！");
					return map;
				}
				
			}
			
			//4.新增该考核员的排班计划
			String[] areaArr = areaId.split(",");
			List<WorkPlans> workPlansList = new ArrayList<WorkPlans>();//用于保存需要新增的排班计划
			int tradeRate = 1;//考核员换班频率默认为不循环：1 不循环,2 日循环，3周循环，4月循换，
			for(int i = 0; i < areaArr.length; i++){
				WorkPlans workPlans = new WorkPlans();
				workPlans.setTradeRate(tradeRate);
				workPlans.setCreateId(users.getEmployeeId());
				workPlans.setCreateAt(createAt);
				workPlans.setRoleId(roleId);
				workPlans.setAreaId(Integer.parseInt(areaArr[i]));
				workPlans.setEmployeeId(Integer.parseInt(employeeId));
				workPlans.setBanzuId(Integer.parseInt(banzuId));
				workPlansList.add(workPlans);
			}
			
			//5.批量新增考核员排班计划，2016-11-01
			int result = workPlansService.batchAddWorkPlans(workPlansList);
			
			//7.根据结果返回
			if(result > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "排班计划保存成功！");
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "排班计划保存失败！");
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/**
	 * 查询考核员排班计划，2016-11-01
	 */
	@RequestMapping(value = "/workPlansForKH", method = RequestMethod.GET)
    @ResponseBody
	public String selectKHWorkPlansList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			
			WorkPlans workPlans = (WorkPlans) JSONObject.toBean(json, WorkPlans.class);
			
			//3.查询考核员排班计划记录总条数
			int total = workPlansService.getKHWorkPlansCount(workPlans);//查询考核员排班计划记录总条数,即考核员的总条数，2016-11-01
			
			//4.查询考核员排班计划记录集合，对考核员进行分页，不是对排班计划表进行分页
			List<WorkPlans> rows = workPlansService.getKHWorkPlansList(workPlans);//查询检测员排班计划记录集合，2016-11-01，分页
			
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
     * 获得员工在排班计划中的排班时间
     * @param acceptContent 查询条件     
     */
    @RequestMapping(value="/workPlansByEmpId", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPointListByAreadId(Integer employeeId, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        // 获得员工在排班计划中的排班时间 
        WorkPlans bean = new WorkPlans();
        bean.setEmployeeId(employeeId);
        List<Map<String, Object>> workPlans = workPlansService.getWorkPlansByEmployeeId(bean);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", workPlans.size());
        map.put("rows", workPlans);

        return map;
    }    
}
