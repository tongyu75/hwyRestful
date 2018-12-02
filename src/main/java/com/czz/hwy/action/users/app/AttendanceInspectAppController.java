package com.czz.hwy.action.users.app;

import java.io.IOException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.bean.user.app.DutyRecodeApp;
import com.czz.hwy.service.area.app.DutyAreaAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService;
import com.czz.hwy.utils.CalendarUntil;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * APP端 考勤查岗
 * @author 佟士儒
 *
 */
@Controller
@RequestMapping(value = "/attInspectAppController")
public class AttendanceInspectAppController {

    @Autowired
    private AttendanceForPlansAppService attService;//考勤记录业务层
    
    @Autowired
    private DutyAreaAppService areaService;//责任区业务层
    
    @Autowired
    private WorkPlansAppService workPlansAppService;//排班计划业务层
    
    /**
     * 考核员考勤查岗
     */
    @RequestMapping(value="/attInspectKApp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAttInspectKApp(Integer employeeId, HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 考核员负责区域员工考勤状态
        List<Map<String, Object>> lstWorkAtt = attService.getAllCityWorkAttForApp(employeeId);
        // 查询考核员的考勤查岗的责任区
        List<Map<String, Object>> lstArea = areaService.selectAttendanceInspectArea(employeeId);
        
        // 取得第一个责任区ID
        int areaId = 0;
        if (lstWorkAtt.size() > 0) {
            Map<String, Object> mp = lstWorkAtt.get(0);
            areaId = (Integer) mp.get("areaId");
        }
        // 初始化考核参数
        List<Map<Object, Object>> lstMp = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> mpParm = this.getInitParam();
        for (Map<String, Object> mp : lstWorkAtt) {
            // 当责任区不同时，建立新的返回值
            if (areaId == (Integer) mp.get("areaId")) {
                for (Map<String, Object> area : lstArea) {
                    if ((Integer) mp.get("areaId") == area.get("areaId")) {
                        this.setParam(mpParm, mp);
                    }
                }
                mpParm.put("areaName", (String) mp.get("areaName"));
                mpParm.put("areaId", (Integer) mp.get("areaId"));
            } else {
                lstMp.add(mpParm);
                mpParm = this.getInitParam();
                for (Map<String, Object> area : lstArea) {
                    if ((Integer) mp.get("areaId") == area.get("areaId")) {
                        this.setParam(mpParm, mp);
                    }
                }
                areaId = (Integer) mp.get("areaId");
                // 设置责任区名称
                mpParm.put("areaName", (String) mp.get("areaName"));
                // 设置责任区ID
                mpParm.put("areaId", areaId);
            }
        }
        lstMp.add(mpParm);
        map.put("cityWorkAtt", lstMp);

        // 当前时间是否是一天中最晚下班之后的时间
        AttendanceForPlansApp att = attService.getAllCityWorkAttLastTimeForApp(employeeId);
        // 当前时间查岗
        if (att == null) {
            map.put("type", 1);
        // 下班之后查岗
        } else {
            map.put("type", 2);
        }
        
        return map;
    }
    
    /**
     * 督查员考勤查岗
     */
    @RequestMapping(value="/attInspectDApp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAttInspectDApp(Integer employeeId, HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 督查员负责区域员工考勤状态
        List<Map<String, Object>> lstWorkAtt = attService.getAllCityWorkAttForApp(employeeId);
        // 查询督察员员的考勤查岗的责任区
        List<Map<String, Object>> lstArea = areaService.selectAttendanceInspectArea(null);
        
        // 取得第一个责任区ID
        int areaId = 0;
        if (lstWorkAtt.size() > 0) {
            Map<String, Object> mp = lstWorkAtt.get(0);
            areaId = (Integer) mp.get("areaId");
        }
        // 初始化考核参数
        List<Map<Object, Object>> lstMp = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> mpParm = this.getInitParam();
        for (Map<String, Object> mp : lstWorkAtt) {
            // 当责任区不同时，建立新的返回值
            if (areaId == (Integer) mp.get("areaId")) {
                for (Map<String, Object> area : lstArea) {
                    if ((Integer) mp.get("areaId") == area.get("areaId")) {
                        this.setParam(mpParm, mp);
                    }
                }
                mpParm.put("areaName", (String) mp.get("areaName"));
                mpParm.put("areaId", (Integer) mp.get("areaId"));
            } else {
                lstMp.add(mpParm);
                mpParm = this.getInitParam();
                for (Map<String, Object> area : lstArea) {
                    if ((Integer) mp.get("areaId") == area.get("areaId")) {
                        this.setParam(mpParm, mp);
                    }
                }
                areaId = (Integer) mp.get("areaId");
                // 设置责任区名称
                mpParm.put("areaName", (String) mp.get("areaName"));
                // 设置责任区ID
                mpParm.put("areaId", areaId);
            }
        }
        lstMp.add(mpParm);
        map.put("cityWorkAtt", lstMp);
        
        // 当前时间是否是一天中最晚下班之后的时间
        AttendanceForPlansApp att = attService.getAllCityWorkAttLastTimeForApp(null);
        // 当前时间查岗
        if (att == null) {
            map.put("type", 1);
        // 下班之后查岗
        } else {
            map.put("type", 2);
        }
        return map;
    }
    
    /**
     * 设置返回参数
     */
    public void setParam(Map<Object, Object> mpParm, Map<String, Object> mp) {
        if ("上班".equals(mp.get("curStatus")) || "休息".equals(mp.get("curStatus")) 
                || "无设备".equals(mp.get("curStatus")) || "下班".equals(mp.get("curStatus"))) {
            Long lNum = (Long) mpParm.get("workNum");
            mpParm.put("workNum", (Long) mp.get("num") + lNum);
        } else if ("迟到".equals(mp.get("curStatus")) || "早退".equals(mp.get("curStatus"))) {
            mpParm.put("lateNum", (Long) mp.get("num"));
        } else if ("未上班".equals(mp.get("curStatus"))) {
            mpParm.put("offNum", (Long) mp.get("num"));
        } else if ("任务".equals(mp.get("curStatus"))) {
            mpParm.put("taskNum", (Long) mp.get("num"));
        } else if ("请假".equals(mp.get("curStatus"))) {
            mpParm.put("leaveNum", (Long) mp.get("num"));
        }    
    }
    
    /**
     * 初始化考核参数
     */
    public Map<Object, Object> getInitParam() {
        // 初始化考核参数
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("areaName", "");
        map.put("areaId", "");
        map.put("leaveNum", 0l);
        map.put("workNum", 0l);
        map.put("offNum", 0l);
        map.put("lateNum", 0l);
        map.put("taskNum", 0l);
        return map;
    }
    
    /**
	 * 督察员，考核员，检测员，查询某个责任区上一个班次的员工的考勤记录列表，用于显示上一班次员工的下班状态，便于对不正常的下班状态做处理，2016-12-29,返回内容包含员工岗位
	 * 关键点：（1）如何准确的判定那些考勤记录是属于上一班次的。
	 * 		      （2）需要有字段去标识是否已处理过不正常的下班状态。
	 *        （3）不正常的下班状态分为两种：早退和未上班。
	 *        （4）上一班次员工的责任点字符串为自己排班和代班的责任点，暂时不考虑去重和是否上原有班次的情况。
	 * 如何准确的判定那些考勤记录是属于上一班次的解决方法：
	 * 		（1）用当前时间去查询排班计划表，取出某责任区下每个责任点的下班状态小于当前时间的上班时间段集合，并从集合中取出每个责任点下班时间最晚的时间段集合
	 * 		（2）根据责任点和最晚上班段，去查询每个责任点上最晚时间段的员工。
	 * 		（2）将（2）中，员工和时间段去重，就得到上一个班次有哪些员工以及每个人上一班次的时间。
	 * 		（3）用这些员工和时间段集合去查询考勤表中，并显示出上一班次的下班状态。
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/lastBanciAttendanceList", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectLastBanciAttendanceList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 3.根据当前时间查询出某个责任区下每个责任点上一班次有哪些员工，这些员工的上班时间是多少？
			List<AttendanceForPlansApp> workPlansList = workPlansAppService.selectLastBanciWorkPlansList(workPlansApp);//查询出某个责任区下每个责任点针对当前时间的上一个班次有哪些员工，以及这些员工的上班时间集合。2016-12-29
			if(workPlansList.size() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
				return map.toString();
			}
			
			// 4.根据获取的上一班次的员工以及其上下班时间获取其考勤记录
			List<DutyRecodeApp> dutyAreaAppList = attService.getLastBanciAttendanceForPlansByListApp(workPlansList);//根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合，2016-12-29
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", dutyAreaAppList.size());
			map.put("rows", dutyAreaAppList);
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
	 * 督察员，考核员，检测员，查询某个责任区上一个班次的员工的考勤记录列表，用于显示上一班次员工的下班状态，便于对不正常的下班状态做处理，2017-04-20,返回内容包含上班期间移动距离
	 * 关键点：（1）如何准确的判定那些考勤记录是属于上一班次的。
	 * 		      （2）需要有字段去标识是否已处理过不正常的下班状态。
	 *        （3）不正常的下班状态分为两种：早退和未上班。
	 *        （4）上一班次员工的责任点字符串为自己排班和代班的责任点，暂时不考虑去重和是否上原有班次的情况。
	 * 如何准确的判定那些考勤记录是属于上一班次的解决方法：
	 * 		（1）用当前时间去查询排班计划表，取出某责任区下每个责任点的下班状态小于当前时间的上班时间段集合，并从集合中取出每个责任点下班时间最晚的时间段集合
	 * 		（2）根据责任点和最晚上班段，去查询每个责任点上最晚时间段的员工。
	 * 		（2）将（2）中，员工和时间段去重，就得到上一个班次有哪些员工以及每个人上一班次的时间。
	 * 		（3）用这些员工和时间段集合去查询考勤表中，并显示出上一班次的下班状态。
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/lastBanciAttendanceListForMOV", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String selectLastBanciAttendanceListForMOV(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 3.根据当前时间查询出某个责任区下每个责任点上一班次有哪些员工，这些员工的上班时间是多少？
			List<AttendanceForPlansApp> workPlansList = workPlansAppService.selectLastBanciWorkPlansList(workPlansApp);//查询出某个责任区下每个责任点针对当前时间的上一个班次有哪些员工，以及这些员工的上班时间集合。2016-12-29
			if(workPlansList.size() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
				return map.toString();
			}
			
			// 4.根据获取的上一班次的员工以及其上下班时间获取其考勤记录(含移动距离)
			List<DutyRecodeApp> dutyAreaAppList = attService.getLastBanciAttendanceForPlansByListAppForMOV(workPlansList);//根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合(含移动距离)，2017-04-21
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", dutyAreaAppList.size());
			map.put("rows", dutyAreaAppList);
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
	 * 修改下班状态，2016-12-30
	 * 下班状态处理状态有三种：1 维持原有下班状态（早退或未上班）
	 * 						  2将早退改为下班
	 *                       3 将未上班改为上班
	 *  注意：不管哪一种处理方式都要提供原因。
	 */
	@RequestMapping(value = "/offStatus", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> updateOffStatus(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","修改失败:修改考勤下班状态的条件不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为考勤信息实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			AttendanceForPlansApp attendanceForPlansApp = (AttendanceForPlansApp) JSONObject.toBean(json, AttendanceForPlansApp.class);
			
			
			//3.判定修改参数是否符合规范
			if(attendanceForPlansApp.getEmployeeId() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","修改失败: 要修改下班状态的员工ID不能为空！");
				return map;
			}
			
			if(json.getString("dutyOnTime") == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","修改失败: 请提供员工的上班时间！");
				return map;
			}
			
			if(json.getString("dutyOffTime") == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","修改失败: 请提供员工的下班时间！");
				return map;
			}
			
			if(attendanceForPlansApp.getStatus() == null ||  "".equals(attendanceForPlansApp.getStatus())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","修改失败: 修改下班状态的处理状态不能为空！");
				return map;
			}
			
			if(attendanceForPlansApp.getOffStatusReason() == null || "".equals(attendanceForPlansApp.getOffStatusReason())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","修改失败: 下班状态的修改原因不能为空！");
				return map;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				attendanceForPlansApp.setDutyOnTime(sdf.parse(json.getString("dutyOnTime")));
				attendanceForPlansApp.setDutyOffTime(sdf.parse(json.getString("dutyOffTime")));
			} catch (ParseException e) {
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "提交失败:考核时间格式不正确！");
				return map;
			}
			
			Date nowDate = new Date();
			attendanceForPlansApp.setRecordDate(nowDate);
			
			int result = 0;
			if("1".equals(attendanceForPlansApp.getStatus())){//1 表示确认保持早退或未上班,只需要保存原因即可
				
			}else if("2".equals(attendanceForPlansApp.getStatus())){//2表示，将早退改为下班，
				attendanceForPlansApp.setDutyOffStatus(ConstantUtil.XIABAN);
				attendanceForPlansApp.setLastRecordTime(CalendarUntil.dateTimeFormat(nowDate, attendanceForPlansApp.getDutyOffTime()));
			}else if("3".equals(attendanceForPlansApp.getStatus())){//3表示将未上班修改为上班，下班
				attendanceForPlansApp.setDutyOnStatus(ConstantUtil.SHANBAN);
				attendanceForPlansApp.setDutyOffStatus(ConstantUtil.XIABAN);
				attendanceForPlansApp.setUpdateTime(CalendarUntil.dateTimeFormat(nowDate, attendanceForPlansApp.getDutyOnTime()));
				attendanceForPlansApp.setLastRecordTime(CalendarUntil.dateTimeFormat(nowDate, attendanceForPlansApp.getDutyOffTime()));
			}
			
			result = attService.updateAttendancePlansForXBApp(attendanceForPlansApp);//根据对下班状态的不同出来，对下班状态和下班时间进行相应处理。2016-12-30
			
			//7.根据结果返回
			if(result > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "修改成功！");
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "修改失败！");
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
}
