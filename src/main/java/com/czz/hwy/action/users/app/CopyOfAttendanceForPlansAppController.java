package com.czz.hwy.action.users.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.utils.CalendarUntil;
import com.czz.hwy.utils.ConstantUtil;

/***
 * 考勤相关接口，用于app接口
 * @author 张咏雪
 * @Date 2016-11-10
 */
@Controller
@RequestMapping(value = "/copyOfAttendanceForPlansAppController")
public class CopyOfAttendanceForPlansAppController {

	@Autowired
	private WorkPlansAppService workPlansAppService;//新的排班计划业务层
	
    /**
	 * 获取明天的排班计划，便于用户查看明天几点上班，2016-11-10
	 * 检测员，环卫工都走这个方法.
	 * 注意：检测员，环卫工查看明天排班时，要注意判断当天是否有换班计划，若是有换班计划，则显示换班之后的排班计划
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/workPlans/{employeeIdStr}/{attendanceDateStr}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String getTomorrowWorkPlansList(@PathVariable(value="employeeIdStr")String employeeIdStr,@PathVariable(value="attendanceDateStr")String attendanceDateStr, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			//1.若是请求参数为空，则返回fail
			/*if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			String employeeIdStr = json.getString("employeeId");
			String attendanceDateStr = json.getString("attendanceDate");//年月日，时分秒
*/			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			Date attendanceDate = sdf.parse(attendanceDateStr);
			int week = CalendarUntil.week(attendanceDate);//获取当前日期是周几,1是周一 ，7是周天
			int day = CalendarUntil.day(attendanceDate);//获取当前日期是几号， 1是1号
			
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("nowDate", attendanceDate);
			selectMap.put("week", week);
			selectMap.put("day", day);
			selectMap.put("employeeId", Integer.parseInt(employeeIdStr));
			
			//3. 判定[当前时间,当天24点之前]，是否有换班计划，若无，则返回排班计划，若有，则返回换班后的排班计划
			List<WorkPlansApp> changeWorkPlansList = workPlansAppService.getWorkPlansListByMapApp(selectMap); // 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划，2016-11-11
			
			List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();//要返回的结果集
			int total = 0;
			
			/*3.1 如果没有换班计划,则获取该员工的排班计划，
				3.1.1若是没有排班计划,则判断该员工在[当前时间,当天24点之前]，是否作为换班人存在
					3.1.1.1 若是作为换班人存在，则返回其作为换班人对应的值班人的排班计划，
					3.1.1.2若不是作为换班人存在，则返回空
				3.1.2若是有排班计划，则返回原有排班计划*/
			
			//3.1 如果没有换班计划,则获取该员工的排班计划
			if(changeWorkPlansList.size() == 0){
				
				//3.1 如果没有换班计划,则获取该员工的排班计划
				List<Map<String, Object>> workPlansList = workPlansAppService.getWorkPlansListByEmployeeIdApp(employeeIdStr);//获取某员工上下班时间不重复的排班计划集合，2016-11-11 
				
				//该员工没有排班计划
				if(workPlansList.size() == 0){
					//3.1.1若是没有排班计划,则判断该员工在[当前时间,当天24点之前]，是否作为换班人存在，并进行换班
					List<WorkPlansApp> tradeWorkPlansList = workPlansAppService.getTradeWorkPlansListByMapApp(selectMap); //获取某个员工在【当前时间，当天24点之前】时间段内，作为换班人存在且进行换班的排班计划，2016-11-11
					
					//3.1.1.1 若是作为换班人存在，则返回其作为换班人对应的值班人的排班计划，
					if(tradeWorkPlansList.size() > 0){
						String employeeIds = "";
						for(WorkPlansApp workPlansApp : tradeWorkPlansList){
							employeeIds += workPlansApp.getEmployeeId() + ",";
						}
						employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
						workPlansList = workPlansAppService.getWorkPlansListByEmployeeIdApp(employeeIds);//获取某些员工上下班时间不重复的排班计划集合，2016-11-11 
						workPlansList = setTomorrowDutyPlansStatus(workPlansList);
						
						rows = workPlansList;
						total = workPlansList.size();
					}else{//3.1.1.2若不是作为换班人存在，则返回空
						
					}
				}else{//3.1.2若是有排班计划，则返回原有排班计划
					workPlansList = setTomorrowDutyPlansStatus(workPlansList);
					
					rows = workPlansList;
					total = workPlansList.size();
				}
			}else{//4.获取换班人的排班计划
				String employeeIds = "";
				for(WorkPlansApp workPlansApp : changeWorkPlansList){
					employeeIds += workPlansApp.getTradeEmployeeId() + ",";
				}
				employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
				List<Map<String, Object>> workPlansList  = workPlansAppService.getWorkPlansListByEmployeeIdApp(employeeIds);//获取某些员工上下班时间不重复的排班计划集合，2016-11-11 
				workPlansList = setTomorrowDutyPlansStatus(workPlansList);
				
				rows = workPlansList;
				total = workPlansList.size();
				
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
		JSONObject jsonobject = JSONObject.fromObject(map);
		return jsonobject.toString();
	} 
	
	/**
	 * 根据排班计划，整理返回的排班计划数据格式，2016-09-06
	 */
	public List<Map<String, Object>> setTomorrowDutyPlansStatus(List<Map<String, Object>> workPlansList){
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		
		//循环排班计划列表，为每一条排班计划设置上下班状态，上下班时间以及出任务状态
		for(Map<String, Object> map : workPlansList){
			map.put("taskStatus", map.get("taskStatus") == null || "".equals((String)map.get("taskStatus")) ? "--" : (String)map.get("taskStatus"));
			map.put("planOnTime", fmt.format(map.get("dutyOnTime")));
			map.put("planOffTime", fmt.format(map.get("dutyOffTime")));
			map.put("onTime", "--" );
			map.put("offTime", "--" );
			map.put("onStatus", "--" );
			map.put("offStatus", "--" );
			map.remove("dutyOnTime");
			map.remove("dutyOffTime");
		}
		return workPlansList;
	}
	
}
