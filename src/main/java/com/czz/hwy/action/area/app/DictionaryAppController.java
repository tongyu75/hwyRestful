package com.czz.hwy.action.area.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.czz.hwy.bean.area.app.DutyAreaApp;
import com.czz.hwy.bean.area.app.DutyPointApp;
import com.czz.hwy.bean.mission.app.EvaluationsApp;
import com.czz.hwy.service.area.app.DutyAreaAppService;
import com.czz.hwy.service.area.app.DutyPointAppService;
import com.czz.hwy.service.manager.app.LeaveAppService;
import com.czz.hwy.service.mission.app.EvaluationsAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.service.usermanagement.app.AttendanceForPointAppService;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 常用数据字典，用于app接口
 * @author 张咏雪
 * 2016-11-12
 */
@Controller
@RequestMapping(value = "/dictionarAppyController")
public class DictionaryAppController {
    
    Map<String,Object> map=null;
    
    @Autowired
    private DutyAreaAppService dutyAreaAppService;//责任区业务层
    
    @Autowired
    private DutyPointAppService dutyPointAppService;//责任点业务层
    
    @Autowired
    private WorkPlansAppService workPlansAppService;//排班计划业务层
    
    @Autowired
    private LeaveAppService leaveAppService;//请假业务层
    
    @Autowired
    private EvaluationsAppService evaluationsAppService;//考核标准业务层
    
    @Autowired
    private AttendanceForPointAppService attendanceForPointAppService;//考勤记录对应责任点业务层
  
    /**
	 * 根据员工ID和角色ID查询出该员工所管辖的责任区列表，用于责任区下拉框，2016-11-02
	 * 检测员和考核员只能查看自己管辖的责任区，督察员查看所有的责任区
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
    @RequestMapping(value = "/areaList", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAreaList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并解析出请求参数
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			String employeeId = json.getString("employeeId");//员工ID
			String roleId = json.getString("roleId");//员工角色
			
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("employeeId", employeeId);
			selectMap.put("roleId", roleId);
			
			//3.如果是检测员和考核员，只查询自己管辖的责任区列表,如果是督察员，则查询所有责任区列表
			//3.1 获取责任区总数
			int total = dutyAreaAppService.selectAreaListCount(selectMap);//查询检测员或考核员或督察员所管辖的责任区总数，2016-11-02
			
			//3.2 获取责任区列表
			List<DutyAreaApp> rows = dutyAreaAppService.selectAreaList(selectMap);//查询检测员或考核员或督察员所管辖的责任区列表，2016-11-02
			
			//4.根据查询结果，返回相应数据
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
     * 根据责任区id获取责任点的集合信息
     * produces:用于解决springMVC返回json数据乱码问题
     */
    @RequestMapping(value="/pointByAreadId/{areaId}", method=RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getDutyPointByAreadId(@PathVariable(value="areaId") Integer areaId, HttpServletRequest request, 
            HttpServletResponse response){
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        //通过责任区域id获取相关责任点集合信息
        DutyPointApp bean = new DutyPointApp();
        bean.setAreaId(areaId);
        List<DutyPointApp> dutyPoints = dutyPointAppService.getDutyPointListByBean(bean);//根据责任区ID获取责任点列表，2016-11-02
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", dutyPoints.size());
        map.put("rows", dutyPoints);
        
        //处理时间格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        //返回责任点数据
        JSONObject json = JSONObject.fromObject(map,jsonConfig);
        return json.toString();
    }    

    /**
	 * 根据责任区ID和责任点ID和当前时间查询出责任人列表，用于责任人下拉框，责任人只能是环卫工，2016-12-14
	 * 注意点：不去除请假和出任务的人员
	 * 修改版本记录：2017-01-16 ：改为从考勤记录对应责任点表中获取责任人下拉框
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
    @RequestMapping(value = "/dutyPeopleList", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectDutyPeopleList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并解析出请求参数
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			String areaId = json.getString("areaId");//责任区ID
			String pointId = json.getString("pointId");//责任点ID
			String curDateStr = json.getString("curDate");//当前时间，yyyy-MM-dd HH:mm:ss格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = sdf.parse(curDateStr);
			
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("areaId", areaId);
			selectMap.put("pointId", pointId);
			selectMap.put("curDate", curDate);
			
			List<Map<String, Object>> dutyPeopleList = new ArrayList<Map<String,Object>>();//用于保存责任人集合
			
			//3.根据责任区ID和责任点Id,当前时间，获取责任人列表。
			dutyPeopleList = attendanceForPointAppService.getDutyPeopleListByPointMapApp(selectMap);//根据责任区ID和责任点Id,当前时间，获取责任人列表。责任人只能是环卫工，2017-01-16
			if(dutyPeopleList.size() == 0){//如果没有查询到责任点当前责任人，则走查询排班计划的分支，这样做的目的是防止考勤责任点没有定时生成，导致查询不出责任点责任人
				//3.根据责任区ID和责任点Id,当前时间，获取责任人列表。
				dutyPeopleList = workPlansAppService.getDutyPeopleListByMapApp(selectMap);//根据责任区ID和责任点Id,当前时间，获取责任人列表。责任人只能是环卫工，2016-12-14
				
				List<Map<String, Object>> coverWorkPeopleList = new ArrayList<Map<String,Object>>();//用于保存代班人集合
				//4.循环责任人列表,获取代班人列表
				for(Map<String, Object> peopleMap : dutyPeopleList){
					int employeeId = Integer.parseInt(peopleMap.get("employeeId").toString());
					selectMap.put("employeeId", employeeId);
					//4.1 根据责任人ID和当前时间获取请假记录,根据请假记录获取代班人集合
					List<Map<String, Object>> dbList = leaveAppService.getCoverWorkDutyPeopleListByMapApp(selectMap);//根据时间和请假人ID获取请假记录中的代班人集合，2016-12-14
					coverWorkPeopleList.addAll(dbList);
				}
				
				//5.将责任人列表和代班人列表，去重，合并
				//循环代班人列表
				for(Map<String, Object> coverWorkMap : coverWorkPeopleList){
					boolean flag = false;//用于标识代班人是否在责任人列表中
					String coverEmployeeId = coverWorkMap.get("employeeId").toString();
					String coverEmployeeName = coverWorkMap.get("employeeName").toString();
					
					//循环责任人列表
					for(Map<String, Object> dutyMap : dutyPeopleList){
						String dutyEmployeeId = dutyMap.get("employeeId").toString();
						String dutyEmployeeName = dutyMap.get("employeeName").toString();
						if(coverEmployeeId.equals(dutyEmployeeId) && coverEmployeeName.equals(dutyEmployeeName)){//如果代班人与责任人一致，则退出该次循环，进入下次循环
							flag = true;
							break;
						}
					}
					if(!flag){////如果责任人列表中不包含该代班人，则添加进去
						dutyPeopleList.add(coverWorkMap);
					}
				}
			}
			
			//6.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", dutyPeopleList.size());
			map.put("rows", dutyPeopleList);
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
   	 * 根据责任区ID、责任点ID和以及考核类型值查询出该责任点的该考核类型的罚款金额。2016-12-21
   	 * produces:用于解决springMVC返回json数据乱码问题
   	 */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/getFinesForCheck", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
   	public String getFinesForCheck(String acceptContent,HttpServletResponse response, HttpServletRequest request){
   		
   		Map<String,Object> map = new HashMap<String,Object> ();
   		
   		try {
   			//1.若是请求参数为空，则返回fail
   			if(acceptContent == null || "".equals(acceptContent)){
   				map.put("result", ConstantUtil.FAIL);
   				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
   				return map.toString();
   			}
   			
   			// 2.对请求参数解码并解析出请求参数
   			acceptContent = CommonUtils.getDecodeParam(acceptContent);
   			JSONObject json = JSONObject.fromObject(acceptContent);
			EvaluationsApp evaluationsApp = (EvaluationsApp) json.toBean(json, EvaluationsApp.class);
			
			//3.根据责任区ID，责任点ID以及考核类型值获取罚款金额。
			evaluationsApp = evaluationsAppService.getEvaluationsByBeanApp(evaluationsApp);
   			
   			//6.根据查询结果，返回相应数据
   			map.put("result", ConstantUtil.SUCCESS);
   			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
   			map.put("fines", evaluationsApp == null ? 0 : evaluationsApp.getFine());
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
