package com.czz.hwy.action.mission.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.dao.manager.SubTopicsDao;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 新的排班计划管理功能
 * @author 张咏雪
 * @Date 2016-10-18
 */
@Controller
@RequestMapping(value = "/workPlansAppController")
public class WorkPlansAppController {

	@Autowired
	private WorkPlansAppService workPlansAppService;//新的排班计划业务层
	
    @Autowired
    private SubTopicsDao subTopicsDao;// 订阅通知信息表持久层接口
	
//    @Autowired
//    private MqttSendMessage mqttSendMessage;
    
    /**
	 * 查询本责任区且没有替班的所有员工信息，2016-10-18
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/employeeForPlans", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectEmployeeList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
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
			
			WorkPlansApp workPlansApp = (WorkPlansApp) JSONObject.toBean(json, WorkPlansApp.class);
			
			//3.查询某责任区且不是替班人员的用户信息集合总数
			int total = workPlansAppService.selectEmployeeCount(workPlansApp);//查询本责任区且不是替班人员的用户信息集合总数，2016-10-18
			
			//4.查询某责任区且不是替班人员的用户信息集合
			List<UserArea> rows = workPlansAppService.selectEmployeeList(workPlansApp);//查询本责任区且不是替班人员的用户信息集合，2016-10-18
			
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
	 * 查询某责任区某责任点的环卫工排班计划列表，2016-10-18
	 *  produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/workPlans", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectWorkPlansList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
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
			
			WorkPlansApp workPlansApp = (WorkPlansApp) JSONObject.toBean(json, WorkPlansApp.class);
			
			//3. 查询某责任区某责任点的排班计划记录总条数，
			int total = workPlansAppService.getWorkPlansCountBySel(workPlansApp);//查询某责任区某责任点某角色的排班计划记录总条数,2016-10-18
			
			//4.查询某责任区某责任点的排班计划记录
			List<WorkPlansApp> rows = workPlansAppService.getWorkPlansListBySel(workPlansApp);//查询某责任区某责任点某角色的排班计划记录，2016-10-18
			
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
	 * 新增排班计划，2016-10-20
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/workPlans", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addWorkPlans(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败，排班计划信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为排班计划
			JSONObject json = JSONObject.fromObject(acceptContent);
			String pointId = json.getString("pointId");
			String areaId = json.getString("areaId");
			String plansArr = json.getString("plansList");
			String employeeId = json.getString("employeeId");
			int roleId = Integer.parseInt(json.getString("roleId"));
			Date createAt = new Date();
			if(pointId == null || pointId == "" 
					|| areaId == null || areaId == "" ){//判断责任点和责任区是否为空
				map.put("result", ConstantUtil.FAIL);
				map.put("information","保存失败:责任点或责任区不能为空！");
				return map;
			}
			
			//3.删除原有责任区责任点的排班计划
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			deleteMap.put("pointId", pointId);
			deleteMap.put("areaId", areaId);
			deleteMap.put("roleId", roleId);
			deleteMap.put("employeeId", employeeId);
			deleteMap.put("curDate", createAt);
			int deleteNum = workPlansAppService.deleteWorkPlansByMap(deleteMap);//根据责任区和责任点删除排班计划。2016-10-18
			
			//4.获取要重新添加的排班计划集合
			JSONArray jsonArray = JSONArray.fromObject(plansArr);
			List<WorkPlansApp> plansList = new ArrayList<WorkPlansApp>();
			if(plansArr != null && !"".equals(plansArr)){//获取排班计划表
				plansList = jsonArray.toList(jsonArray, WorkPlansApp.class);
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
			
			//5.获取本责任区的轮班频率和频率对应的月，星期，日，时分秒、
			WorkPlansApp trade = workPlansAppService.getSelectRateByBean(deleteMap);//获取本责任区的轮班频率，2016-10-18
			if(trade == null){//若是本责任区没有轮班频率，则将轮班频率设置为不循环
				trade = new WorkPlansApp();
				trade.setTradeRate(1);
			}
			
			//6.组合排班计划集合，并新增排班计划集合。
			for(WorkPlansApp workPlansApp : plansList){
				workPlansApp.setAreaId(Integer.parseInt(areaId));
				workPlansApp.setPointId(Integer.parseInt(pointId));
				workPlansApp.setTradeRate(trade.getTradeRate());
				workPlansApp.setTime(trade.getTime());
				workPlansApp.setWeek(trade.getWeek());
				workPlansApp.setDay(trade.getDay());
				workPlansApp.setCreateId(Integer.parseInt(employeeId));
				workPlansApp.setCreateAt(createAt);
				workPlansApp.setRoleId(roleId);
			}
			
			int result = workPlansAppService.batchAddWorkPlans(plansList);//批量新增排班计划，2016-10-18
			
			//TODO 为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分
			//******************************为了兼容旧系统，新增新系统的排版计划的同时，需要修改旧系统的排版计划，新系统正式使用之后，去掉该部分*****************************/
			//7.删除某责任区下，某些责任点的旧系统的排班计划表数据
			//TODO 根据责任区ID和责任点ID和角色ID集合，删除旧系统排班计划表数据，现在规定为逻辑删除，之后改成物理删除
			//7.1 根据责任区ID和责任点ID和角色ID集合，删除旧系统排班计划表数据，现在规定为逻辑删除，之后改成物理删除
			workPlansAppService.deleteDutyForBanciByMap(deleteMap);//根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
			
			//TODO 根据责任区ID和责任点ID和角色ID集合，删除旧系统排班计划表数据，现在规定为逻辑删除，之后改成物理删除
			//7.2 根据责任区ID和责任点ID和角色ID集合，删除旧系统排班计划表数据，现在规定为逻辑删除，之后改成物理删除
			workPlansAppService.deleteDutyPlansByMap(deleteMap);//根据责任区ID和责任点ID和角色ID集合，删除旧系统排班计划表数据，2016-10-19,现在规定为逻辑删除，之后改成物理删除
			
			//7.3 根据排班计划，新增旧系统排班计划集合
			workPlansAppService.batchAddDutyForBanciByMap(deleteMap);//根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
			
			//7.4 根据排班计划，新增旧系统排班计划集合
			workPlansAppService.batchAddDutyPlansByMap(deleteMap);//根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
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
	
}
