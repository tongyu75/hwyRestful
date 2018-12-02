package com.czz.hwy.action.users.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.app.CoverWorkApp;
import com.czz.hwy.bean.manager.app.LeaveApp;
import com.czz.hwy.bean.mission.app.TaskInformationApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.bean.user.app.UsersApp;
import com.czz.hwy.service.manager.app.CoverWorkAppService;
import com.czz.hwy.service.manager.app.LeaveAppService;
import com.czz.hwy.service.mission.app.TaskInformationAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService;
import com.czz.hwy.service.usermanagement.app.UsersAppService;
import com.czz.hwy.utils.CalendarUntil;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.DutyTimeConfigApp;
import com.czz.hwy.utils.JsonDateValueHMSProcessor;

/***
 * 考勤相关接口，用于app接口
 * @author 张咏雪
 * @Date 2016-11-10
 */
@Controller
@RequestMapping(value = "/attendanceForPlansAppController")
public class AttendanceForPlansAppController {

	@Autowired
	private WorkPlansAppService workPlansAppService;//新的排班计划业务层
	
    @Autowired
    private UsersAppService usersAppService;
	
    @Autowired
    private AttendanceForPlansAppService attService;
    
	@Autowired
	private CoverWorkAppService coverWorkAppService;//代班信息业务层
	
	@Autowired
	private UsersService usersService;//用户信息业务层
	
	@Autowired
	private LeaveAppService leaveAppService;//请假业务层
	
	@Autowired
	private TaskInformationAppService taskInformationAppService;//任务业务层
	
	// 上下班时间参数配置类 
	@Autowired  
    private DutyTimeConfigApp dutyTimeConfig; 
	
	private SimpleDateFormat hsm = new SimpleDateFormat("HH:ss:mm");
	
    /**
	 * 获取明天的排班计划，便于用户查看明天几点上班，2016-11-10
	 * 检测员，环卫工都走这个方法.
	 * 注意：检测员，环卫工查看明天排班时，要注意判断当天是否有换班计划，若是有换班计划，则显示换班之后的排班计划
	 * 2016-12-07:注意内容：
	 * （1）考虑明天是否有代班，若有代班，获取请假人明日排班，并考虑是否上自己的排班。
	 * （2）考虑明日是否有请假，若有，显示上下班状态为请假
	 * （3）考虑明日是否有任务，若有，显示上下班状态为任务
	 * （4）考虑是否无设备，若无，显示上下班状态为无设备
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/workPlans/{employeeIdStr}/{attendanceDateStr}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTomorrowWorkPlansList(@PathVariable(value="employeeIdStr")String employeeIdStr,@PathVariable(value="attendanceDateStr")String attendanceDateStr, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			Date attendanceDate = sdf.parse(attendanceDateStr);
			int week = CalendarUntil.week(attendanceDate);//获取当前日期是周几,1是周一 ，7是周天
			int day = CalendarUntil.day(attendanceDate);//获取当前日期是几号， 1是1号
			
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("nowDate", attendanceDate);
			selectMap.put("week", week);
			selectMap.put("day", day);
			selectMap.put("employeeId", Integer.parseInt(employeeIdStr));
			
			//1.首先获取员工的角色，考核员没有明日排班计划
			Users users = new Users();
			users.setEmployeeId(Integer.parseInt(employeeIdStr));
			List<Users> usersList = usersService.getUserList(users);
			users = usersList.get(0);
			if(users.getRoleId() != 1 && users.getRoleId() != 2 && users.getRoleId() != 3){//如果角色是督察员，督察员没有考勤，故没有明日排班计划
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
				map.put("total", 0);
				map.put("rows", new ArrayList<Map<String, Object>>());
				JSONObject jsonobject = JSONObject.fromObject(map);
				return jsonobject.toString();
			}
			
			//2.根据员工ID和日期获取员工自己的明日排班
			List<Map<String, Object>> selfWorkPlansList = getTomorrowWorkPlansBySelectMap(selectMap);
			
			//设置明日日期
			Date tomorrowDate = new Date();
			tomorrowDate.setTime((attendanceDate.getTime() + 24 * 60 * 60 * 1000 ));//获取明天日期
			selectMap.put("tomorrowDate", tomorrowDate);
			
			//3.获取代班排班计划
			//注意事项：
			//	(1)若是环卫工是代班人员： 环卫工上请假人的上班时间
			//  (2)若是检测员是代班人员：检测员不上请假人的上班时间，而是上自己的排班时间。也就是说，检测员即使代别的责任区的班，上班时间与自己管辖的责任区上班时间保持一致
			List<Map<String, Object>> coverWorkWorkPlansList = new ArrayList<Map<String,Object>>();//用于接收明日代班计划
			if(users.getRoleId() == 1){//如果是环卫工，则获取明日代班计划
				
				//3.依据明日日期和员工ID获取代班记录
				List<CoverWorkApp> coverWorkAppList = coverWorkAppService.getCoverWorkListByMapApp(selectMap);//根据日期和代班人Id获取代班记录，2016-12-08
				
				Map<String, List<Map<String, Object>>> plansMap = getCoverWorkDutyPlansList(coverWorkAppList, selectMap, tomorrowDate, selfWorkPlansList);//获取某个员工明日代班排班计划集合
				coverWorkWorkPlansList = plansMap.get("coverwork");//获取某个员工明日代班排班计划集合
				selfWorkPlansList = plansMap.get("self");//获取某个员工明日自己的排班计划集合
			}
			
			//4.将自己的明日排班计划与代班排班计划合并，并去重
			selfWorkPlansList = getDistinctWorkPlansList(selfWorkPlansList, coverWorkWorkPlansList);//将自己的明日排班计划与代班排班计划合并，并去重，最后返回不重复的排班计划
			
			//5.判断该员工明日是否请假，若是请假，则对上下班状态做相应处理
			selectMap.put("employeeId", Integer.parseInt(employeeIdStr));
 			selfWorkPlansList = setLeaveStatus(selfWorkPlansList, selectMap);
			
			//6.判断该员工明日是否出任务，若是出任务，则对上下班状态做相应处理,注意：排除掉上下班状态为请假的排班计划。
			selfWorkPlansList = setTaskStatus(selfWorkPlansList, selectMap);
			
			//7.依据员工ID获取该员工是否有手机设备，若无手机设备，则对上下班状态做相应处理，注意：排除掉请假和任务的排班计划
			selfWorkPlansList = setNoMobileStatus(users, selfWorkPlansList);
			
			//8.对明日排班计划按上班时间进行排班
			selfWorkPlansList = getTomorrowWorkPlansByOnTimeOrder(selfWorkPlansList);
			
			//9.格式化明日排班计划
			selfWorkPlansList = setTomorrowDutyPlansStatus(selfWorkPlansList);
			
			//10.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", selfWorkPlansList.size());
			map.put("rows", selfWorkPlansList);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		JSONObject jsonobject = JSONObject.fromObject(map);
		return jsonobject.toString();
	} 
	
	/**
	 * 对明日排班计划按上班时间进行排班,2016-12-12
	 * @param selfWorkPlansList
	 * @return
	 */
	private List<Map<String, Object>> getTomorrowWorkPlansByOnTimeOrder(List<Map<String, Object>> selfWorkPlansList) {
		List<Date> onTimeList = new ArrayList<Date>();//用于保存上班时间集合
		//循环排班计划，获取上班时间
		for(Map<String, Object> map : selfWorkPlansList){
			onTimeList.add((Date)map.get("dutyOnTime"));
		}
		Collections.sort(onTimeList);
		
		//循环排班计划，进行排序
		List<Map<String, Object>> newSelfWorkPlansList  = new ArrayList<Map<String,Object>>();//用于保存排序后的排班计划
		for(Date onTime : onTimeList){
			for(Map<String, Object> map : selfWorkPlansList){
				if(onTime.compareTo((Date)map.get("dutyOnTime")) == 0){//如果上班时间一致
					newSelfWorkPlansList.add(map);//添加排班计划到新的集合中
					selfWorkPlansList.remove(map);//将排班计划从旧的集合中移除
					break;
				}
			}
		}
		return newSelfWorkPlansList;
	}

	/**
	 * 判断该员工是否有设备，若是无设备，则对上下班状态做相应处理，2016-12-08
	 * 注意：排除掉请假和任务的排班计划
	 * @param users              该员工信息
	 * @param selfWorkPlansList  明日排班计划
	 * @return
	 */
	private List<Map<String, Object>> setNoMobileStatus(Users users, List<Map<String, Object>> selfWorkPlansList) {
		//依据员工ID获取该员工是否有手机设备，若有手机设置，明日排班计划的上下班状态设置为无设备，实际上下班时间保持为空。
		//注意：排除掉请假和任务的排班计划。
		if(users.getHasMobile() == 2){//如果无设备
			for(Map<String, Object> map : selfWorkPlansList){
				Object onStatus = map.get("onStatus");
				if(onStatus == null || "".equals((String)map.get("onStatus"))){//排除掉请假和任务的排班计划。
					map.put("onStatus", ConstantUtil.WUSEBEI);
	        		map.put("offStatus", ConstantUtil.WUSEBEI);
				}
			}
		}
		
		return selfWorkPlansList;
	}

	/**
	 *  判断该员工明日是否出任务，若是出任务，则对上下班状态做相应处理，2016-12-08
	 *  注意：排除掉上下班状态为请假的排班计划。
	 * @param selfWorkPlansList   明日排班计划
	 * @param selectMap           查询某员工明日请假情况
	 * @return
	 */
	private List<Map<String, Object>> setTaskStatus(List<Map<String, Object>> selfWorkPlansList,Map<String, Object> selectMap) {
		//6. 依据员工ID以及明日日期，查询出明日任务记录，并明日排班计划，进行比较。注意：排除掉上下班状态为请假的排班计划。
		// 比较规则：
		//    (1) 如果任务开始时间 <= 明日计划上班时间 <= 任务结束时间，那么整个上班时间段的上下班状态设置为任务，实际上下班时间保持为空。
		//    (2) 如果任务开始时间 <= 明日计划下班时间 <= 任务结束时间，那么整个上班时间段的上下班状态设置为任务，实际上下班时间保持为空。
		//    (3) 如果任务开始时间 <= 明日计划上班时间，且 明日计划下班时间 <= 任务结束时间，那么整个上班时间段的上下班状态设置为任务，实际上下班时间保持为空。
		//    (4) 如果明日计划上班时间 <= 任务开始时间，且 任务结束时间 <= 明日计划下班时间，那么整个上班时间段的上下班状态设置为任务，实际上下班时间保持为空。

		List<TaskInformationApp> taskAppList = taskInformationAppService.getTaskInformationListByMapApp(selectMap);//依据员工ID以及明日日期，查询出明日出任务记录。2016-12-09
		for(Map<String, Object> map : selfWorkPlansList){
			Date dutyOnTime = CalendarUntil.dateTimeFormat((Date)selectMap.get("tomorrowDate"), (Date)map.get("dutyOnTime"));
        	Date dutyOffTime = CalendarUntil.dateTimeFormat((Date)selectMap.get("tomorrowDate"), (Date)map.get("dutyOffTime"));
        	Object onStatus = map.get("onStatus");
        	if(onStatus == null || "".equals((String)map.get("onStatus"))){//排除掉上下班状态为请假的排班计划。
        		//循环明日出任务记录
            	for(TaskInformationApp taskInformationApp : taskAppList){
            		Date taskStart = taskInformationApp.getTaskStart();
            		Date taskEnd = taskInformationApp.getTaskEnd();
            		boolean flag = false;
    	        	if(taskStart.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(taskEnd) <= 0){//如果任务开始时间 <= 明日计划上班时间 <= 任务结束时间
    	        		flag = true;
    	        	}else if(taskStart.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(taskEnd) <= 0){//如果任务开始时间 <= 明日计划下班时间 <= 任务结束时间
    	        		flag = true;
    	        	}else if(taskStart.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(taskEnd) <= 0){//如果任务开始时间 <= 明日计划上班时间，且 明日计划下班时间 <= 任务结束时间
    	        		flag = true;
    	        	}else if(dutyOnTime.compareTo(taskStart) <= 0 && taskEnd.compareTo(dutyOffTime) <= 0){//如果明日计划上班时间 <= 任务开始时间，且 任务结束时间 <= 明日计划下班时间
    	        		flag = true;
    	        	}
    	        	if(flag){//若是符合条件，则将上下班状态设置为任务。并退出循环进入下一次循环
    	        		map.put("onStatus", ConstantUtil.RENWU);
    	        		map.put("offStatus", ConstantUtil.RENWU);
    	        		break;
    	        	}
            	}
        	}
		}

		return selfWorkPlansList;
	}

	/**
	 * 判断该员工明日是否请假，若是请假，则对上下班状态做相应处理，2016-12-08
	 * @param selfWorkPlansList 明日排班计划
	 * @param selectMap         查询某员工明日请假情况
	 * @return
	 */
	private List<Map<String, Object>> setLeaveStatus(List<Map<String, Object>> selfWorkPlansList,Map<String, Object> selectMap) {
		//5.依据员工ID以及明日日期，查询出明日请假记录，并与明日排班计划进行比较。
		//比较规则：
			//(1) 如果请假开始时间 <= 明日计划上班时间 <= 请假结束时间，那么整个上班时间段的上下班状态设置为请假，实际上下班时间保持为空。
			//(2) 如果请假开始时间 <= 明日计划下班时间 <= 请假结束时间，那么整个上班时间段的上下班状态设置为请假，实际上下班时间保持为空。
			//(3) 如果请假开始时间 <= 明日计划上班时间，且 明日计划下班时间 <= 请假结束时间，那么整个上班时间段的上下班状态设置为请假，实际上下班时间保持为空。
			//(4) 如果明日计划上班时间 <= 请假开始时间，且 请假结束时间 <= 明日计划下班时间，那么整个上班时间段的上下班状态设置为请假，实际上下班时间保持为空。
		
		List<LeaveApp> leaveAppList = leaveAppService.getLeaveListByMapApp(selectMap);//依据员工ID以及明日日期，查询出明日请假记录。2016-12-09
		for(Map<String, Object> map : selfWorkPlansList){
			Date dutyOnTime = CalendarUntil.dateTimeFormat((Date)selectMap.get("tomorrowDate"), (Date)map.get("dutyOnTime"));
        	Date dutyOffTime = CalendarUntil.dateTimeFormat((Date)selectMap.get("tomorrowDate"), (Date)map.get("dutyOffTime"));
			
        	//循环明日请假记录
        	for(LeaveApp leaveApp : leaveAppList){
        		Date leaveFromTime = leaveApp.getLeaveFromTime();
        		Date leaveToTime = leaveApp.getLeaveToTime();
        		boolean flag = false;
	        	if(leaveFromTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(leaveToTime) <= 0){//如果请假开始时间 <= 明日计划上班时间 <= 请假结束时间
	        		flag = true;
	        	}else if(leaveFromTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(leaveToTime) <= 0){//如果请假开始时间 <= 明日计划下班时间 <= 请假结束时间
	        		flag = true;
	        	}else if(leaveFromTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(leaveToTime) <= 0){//如果请假开始时间 <= 明日计划上班时间，且 明日计划下班时间 <= 请假结束时间
	        		flag = true;
	        	}else if(dutyOnTime.compareTo(leaveFromTime) <= 0 && leaveToTime.compareTo(dutyOffTime) <= 0){//如果明日计划上班时间 <= 请假开始时间，且 请假结束时间 <= 明日计划下班时间
	        		flag = true;
	        	}
	        	if(flag){//若是符合条件，则将上下班状态设置为请假。并退出循环进入下一次循环
	        		map.put("onStatus", ConstantUtil.QINGJIA);
	        		map.put("offStatus", ConstantUtil.QINGJIA);
	        		break;
	        	}
        		
        	}
		}

		return selfWorkPlansList;
	}

	/**
	 * 将某员工的明日排班计划与代班排班计划合并，并去重，2016-12-08
	 * @param selfWorkPlansList       某员工原有明日排班计划
	 * @param coverWorkWorkPlansList  某员工明日代班排班计划 
	 * @return
	 */
	private List<Map<String, Object>> getDistinctWorkPlansList(List<Map<String, Object>> selfWorkPlansList, List<Map<String, Object>> coverWorkWorkPlansList) {
		
		//循环代班排班计划
		for(Map<String, Object> coverWorkMap : coverWorkWorkPlansList){
			boolean flag = false;//用于标识代班计划与排班计划是否一致,true表示排班计划中含有这个时间段，false表示排班计划中不含有这个时间段
			Date coverOnTime = (Date)coverWorkMap.get("dutyOnTime");
			Date coverOffTime = (Date)coverWorkMap.get("dutyOffTime");
			
			//循环原有排班计划
			for(Map<String, Object> workPlansMap : selfWorkPlansList){
				Date selfOnTime = (Date)workPlansMap.get("dutyOnTime");
				Date selfOffTime = (Date)workPlansMap.get("dutyOffTime");
				if(selfOnTime.compareTo(coverOnTime) == 0 && selfOffTime.compareTo(coverOffTime) == 0){//如果代班计划时间与排班计划时间一致，则排班计划中表示含有代班计划时间,退出该次循环，进入下次循环
					flag = true;
					break;
				}
			}
			
			if(!flag){//如果原有排班计划中不包含这个上班时间段，则添加进去
				selfWorkPlansList.add(coverWorkMap);
			}
		}
		return selfWorkPlansList;
	}

	/**
	 * 获取某个员工明日代班排班计划集合，2016-12-08
	 * 目前：只用于获取环卫工明日代班排班计划，因为检测员即使代班的话，上班时间也是和自己的排班计划保持一致
	 * @param coverWorkAppList   代班记录集合
	 * @param selectMap          查询代班记录中请假人明日排班计划的条件
	 * @param tomorrowDate       明日日期
	 * @param selfWorkPlansList  某员工明日排班计划
	 * @return
	 */
	public Map<String,  List<Map<String, Object>>> getCoverWorkDutyPlansList(List<CoverWorkApp> coverWorkAppList, Map<String, Object> selectMap, 
				Date tomorrowDate, List<Map<String, Object>> selfWorkPlansList){
		
		List<Map<String, Object>> coverWorkWorkPlansList = new ArrayList<Map<String, Object>>();//用于保存代班排班计划
		
		//3.循环代班记录，获取代班排班计划
		for(CoverWorkApp coverWorkApp : coverWorkAppList){
			selectMap.put("employeeId", coverWorkApp.getLeaveId());
			//3.1 取得代班记录中的请假人ID，依据请假人ID的排班计划以及轮班频率获取某员工明日代班排班计划。注意：是否会进行换班
			List<Map<String, Object>> leaveWorkPlansList = getTomorrowWorkPlansBySelectMap(selectMap);//获取请假人明日排班计划
			
			//3.2 将取得的请假人明日代班排班计划于代班记录进行比较，取得符合条件的排班计划。
			/* 
			 比较规则：
			 (1) 如果代班开始时间 <= 明日代班计划上班时间 <= 代班结束时间，那么整个上班时间段将由该员工进行代班。
			 (2) 如果代班开始时间 <= 明日代班计划下班时间 <= 代班结束时间，那么整个上班时间段将由该员工进行代班。
			 (3) 如果代班开始时间 <= 明日代班计划上班时间，且 明日代班计划下班时间 <= 代班结束时间，那个整个上班时间段将由该员工进行代班。
			 (4) 如果明日代班计划上班时间 <= 代班开始时间，且 代班结束时间 <=明日代班计划下班时间，那个整个上班时间段将由该员工进行代班。*/
			//循环排班计划进行比较
			Date coverFromTime = coverWorkApp.getCoverFromTime();
        	Date coverToTime = coverWorkApp.getCoverToTime();
			List<Map<String, Object>> rightLeaveWorkPlansList = new ArrayList<Map<String,Object>>();//该员工的代班排班计划，符合比较规则的请假人明日排班计划
			for(Map<String, Object> leaveMap : leaveWorkPlansList){
				Date dutyOnTime = CalendarUntil.dateTimeFormat(tomorrowDate, (Date)leaveMap.get("dutyOnTime"));
	        	Date dutyOffTime = CalendarUntil.dateTimeFormat(tomorrowDate, (Date)leaveMap.get("dutyOffTime"));
	        	boolean flag = false;
	        	if(coverFromTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(coverToTime) <= 0){//如果代班开始时间 <= 明日代班计划上班时间 <= 代班结束时间
	        		flag = true;
	        	}else if(coverFromTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(coverToTime) <= 0){//如果代班开始时间 <= 明日代班计划下班时间 <= 代班结束时间
	        		flag = true;
	        	}else if(coverFromTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(coverToTime) <= 0){//如果代班开始时间 <= 明日代班计划上班时间，且 明日代班计划下班时间 <= 代班结束时间
	        		flag = true;
	        	}else if(dutyOnTime.compareTo(coverFromTime) <= 0 && coverToTime.compareTo(dutyOffTime) <= 0){// 如果明日代班计划上班时间 <= 代班开始时间，且 代班结束时间 <=明日代班计划下班时间
	        		flag = true;
	        	}
	        	
	        	if(flag){
	        		leaveMap.put("employeeId", coverWorkApp.getCoverId());
	        		rightLeaveWorkPlansList.add(leaveMap);
	        	}
			}
			
			coverWorkWorkPlansList.addAll(rightLeaveWorkPlansList);//将代班排班计划保存起来，便于后面去重
			
			//3.3 如果代班记录中设置代班人不在上原有班次，去掉代班人原有排班计划中与代班排班计划相交或包含的排班计划
			if(coverWorkApp.getIsOldPlans() == 2){
				selfWorkPlansList = getSelfTomorrowPlansListForCW(selfWorkPlansList, rightLeaveWorkPlansList);//获取去除掉与代班排班相交或包含的原有排班计划。
			}
		}
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String,Object>>>();
		map.put("self", selfWorkPlansList);//自己的排班计划
		map.put("coverwork", coverWorkWorkPlansList);//代班计划
		return map;
	}
	
	/**
	 * 获取某员工去除与代班计划相交或包含的排班计划，2016-12-08
	 * @param selfWorkPlansList   某员工原有排班计划
	 * @param workPlansList       某员工代班计划
	 * @return
	 */
	public List<Map<String, Object>> getSelfTomorrowPlansListForCW(List<Map<String, Object>> selfWorkPlansList, List<Map<String, Object>> rightLeaveWorkPlansList){
		List<Map<String, Object>> afterSelfWorkPlansList = new ArrayList<Map<String,Object>>();//用于保存去除与代班排班计划相交或包含的自己原有的排班计划
		//循环自己原有排班计划，去除与代班排班计划相交或包含的排班计划
		for(Map<String, Object> selfMap : selfWorkPlansList){
			Date selfDutyOnTime = (Date)selfMap.get("dutyOnTime");
        	Date selfDutyOffTime = (Date)selfMap.get("dutyOffTime");
        	boolean flag = true;//表示原有排班与代班排班，不想交，不包含
			
			//循环代班排班计划
 			for(Map<String, Object> coverWorkMap : rightLeaveWorkPlansList){
				Date cwDutyOnTime = (Date)coverWorkMap.get("dutyOnTime");
	        	Date cwDutyOffTime = (Date)coverWorkMap.get("dutyOffTime");
	        	
	        	if(selfDutyOnTime.compareTo(cwDutyOnTime) <= 0 && cwDutyOnTime.compareTo(selfDutyOffTime) <= 0){//如果原有明日排班计划上班时间 <= 代班明日计划上班时间 <= 原有排班计划下班时间
	        		flag = false;
	        	}else if(selfDutyOnTime.compareTo(cwDutyOffTime) <= 0 && cwDutyOffTime.compareTo(selfDutyOffTime) <= 0){//如果原有明日排班计划上班时间 <= 代班明日计划下班时间 <= 原有排班计划下班时间
	        		flag = false;
	        	}else if(selfDutyOnTime.compareTo(cwDutyOnTime) <= 0 && cwDutyOffTime.compareTo(selfDutyOffTime) <= 0){//如果原有明日排班计划上班时间 <= 代班明日计划上班时间 ，且 代班明日计划下班时间 <= 原有排班计划下班时间
	        		flag = false;
	        	}else if(cwDutyOnTime.compareTo(selfDutyOnTime) <= 0 && selfDutyOffTime.compareTo(cwDutyOffTime) <= 0){// 如果代班明日计划上班时间 <= 原有明日排班计划上班时间，且 原有排班计划下班时间 <= 代班明日计划下班时间
	        		flag = false;
	        	}
			}
 			if(flag){//不想交，不包含
        		afterSelfWorkPlansList.add(selfMap);
        	}
		}
		return afterSelfWorkPlansList;
	}
	
	/**
	 * 获取某个人某个日期的明日排班计划,2016-12-07
	 * @return
	 */
	public List<Map<String, Object>> getTomorrowWorkPlansBySelectMap(Map<String, Object> selectMap){
		//1. 判定[当前时间,当天24点之前]，是否有换班计划，若无，则返回排班计划，若有，则返回换班后的排班计划
		List<WorkPlansApp> changeWorkPlansList = workPlansAppService.getWorkPlansListByMapApp(selectMap); // 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划，2016-11-11
		
		List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();//要返回的结果集
		
		/*2.1 如果没有换班计划,则获取该员工的排班计划，
			2.1.1若是没有排班计划,则判断该员工在[当前时间,当天24点之前]，是否作为换班人存在
				2.1.1.1 若是作为换班人存在，则返回其作为换班人对应的值班人的排班计划，
				2.1.1.2若不是作为换班人存在，则返回空
			2.1.2若是有排班计划，则返回原有排班计划*/
		
		//2.1 如果没有换班计划,则获取该员工的排班计划
		if(changeWorkPlansList.size() == 0){
			
			//3.1 如果没有换班计划,则获取该员工的排班计划
			List<Map<String, Object>> workPlansList = workPlansAppService.getDistinctWorkPlansListByEmployeeIdApp(selectMap.get("employeeId").toString());//获取某员工上下班时间不重复的排班计划集合，2016-11-11 
			
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
					workPlansList = workPlansAppService.getDistinctWorkPlansListByEmployeeIdApp(employeeIds);//获取某些员工上下班时间不重复的排班计划集合,只有时间集合，2016-12-12
					
					rows = workPlansList;
				}else{//3.1.1.2若不是作为换班人存在，则返回空
					
				}
			}else{//3.1.2若是有排班计划，则返回原有排班计划
				
				rows = workPlansList;
			}
		}else{//4.获取换班人的排班计划
			String employeeIds = "";
			for(WorkPlansApp workPlansApp : changeWorkPlansList){
				employeeIds += workPlansApp.getTradeEmployeeId() + ",";
			}
			employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
			List<Map<String, Object>> workPlansList  = workPlansAppService.getDistinctWorkPlansListByEmployeeIdApp(employeeIds);//获取某些员工上下班时间不重复的排班计划集合,只有时间集合，2016-12-12
			
			rows = workPlansList;
		}
		
		//循环排班计划，修改排班计划中员工ID为该员工自己的员工ID
		for(Map<String, Object> map : rows){
			map.put("employeeId", selectMap.get("employeeId").toString());
		}
		
		return rows;
	}
	
	/**
	 * 根据排班计划，整理返回的排班计划数据格式，2016-12-07
	 */
	public List<Map<String, Object>> setTomorrowPlansStatus(List<Map<String, Object>> workPlansList){
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		
		//循环排班计划列表，为每一条排班计划设置上下班状态，上下班时间以及出任务状态
		for(Map<String, Object> map : workPlansList){
			// map.put("taskStatus", map.get("taskStatus") == null || "".equals((String)map.get("taskStatus")) ? "--" : (String)map.get("taskStatus"));
			map.put("planOnTime", fmt.format(map.get("dutyOnTime")));
			map.put("planOffTime", fmt.format(map.get("dutyOffTime")));
			map.put("onTime", "--" );
			map.put("offTime", "--" );
			map.put("onStatus", map.get("onStatus") == null || "".equals( map.get("onStatus")) ? "--" : map.get("onStatus").toString() );
			map.put("offStatus", map.get("offStatus") == null || "".equals( map.get("offStatus")) ? "--" : map.get("offStatus").toString() );
			map.remove("dutyOnTime");
			map.remove("dutyOffTime");
		}
		return workPlansList;
	}
	
	/**
	 * 根据排班计划，整理返回的排班计划数据格式，2016-12-09
	 */
	public List<Map<String, Object>> setTomorrowDutyPlansStatus(List<Map<String, Object>> workPlansList){
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		
		//循环排班计划列表，为每一条排班计划设置上下班状态，上下班时间以及出任务状态
		boolean flag = true;
		for(Map<String, Object> map : workPlansList){
			// map.put("taskStatus", map.get("taskStatus") == null || "".equals((String)map.get("taskStatus")) ? "--" : (String)map.get("taskStatus"));
			map.put("planOnTime", fmt.format(map.get("dutyOnTime")));
			map.put("planOffTime", fmt.format(map.get("dutyOffTime")));
			map.put("onTime", "--" );
			map.put("offTime", "--" );
			flag = map.get("onStatus") == null || "".equals(map.get("onStatus").toString());
			map.put("onStatus", flag ? "--" : map.get("onStatus").toString() );
			flag = map.get("offStatus") == null || "".equals(map.get("offStatus").toString());
			map.put("offStatus", flag ? "--" : map.get("offStatus").toString());
			map.remove("dutyOnTime");
			map.remove("dutyOffTime");
		}
		return workPlansList;
	}
	
	/**
	 * 根据排班计划，整理返回的排班计划数据格式，2016-09-06
	 *//*
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
	}*/
  
	/*@RequestMapping(value = "/workPlans/{employeeIdStr}/{attendanceDateStr}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String getTomorrowWorkPlansList(@PathVariable(value="employeeIdStr")String employeeIdStr,@PathVariable(value="attendanceDateStr")String attendanceDateStr, HttpServletResponse response, HttpServletRequest request){
		
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
			String employeeIdStr = json.getString("employeeId");
			String attendanceDateStr = json.getString("attendanceDate");//年月日，时分秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
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
			
			3.1 如果没有换班计划,则获取该员工的排班计划，
				3.1.1若是没有排班计划,则判断该员工在[当前时间,当天24点之前]，是否作为换班人存在
					3.1.1.1 若是作为换班人存在，则返回其作为换班人对应的值班人的排班计划，
					3.1.1.2若不是作为换班人存在，则返回空
				3.1.2若是有排班计划，则返回原有排班计划
			
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
	} */
    /**
     * 获取昨日的考勤记录 2016/12/08
     */
    @RequestMapping(value="/yestodayAttendance/{employeeId}/{attendanceDate}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getYestodayAttendance(@PathVariable(value="employeeId")String employeeId, 
            @PathVariable(value="attendanceDate")String attendanceDate) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
/*        //根据员工号获取当前员工的角色
        UsersApp users = usersAppService.getUserInfoByEmployeeId(Integer.parseInt(employeeId));
        int roleId = users.getRoleId();
        if (roleId == 3) { //表示当前是考核员,不能查询考勤结果
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.KAOHEYUAN_KAOQIN_FAIL);
            JSONObject jsonobject = JSONObject.fromObject(map);
            return jsonobject.toString();
        }*/

        // 查询条件
        Map<String, Object> selectMap = new HashMap<String, Object>();
        // 员工ID
        selectMap.put("employeeId", employeeId);
        // 日期（昨日）
        selectMap.put("attendanceDate", attendanceDate);
        // 考勤记录
        List<Map<String, Object>> mapInfoList = attService.getYesAndTodAttendanceForApp(selectMap);
        if (mapInfoList.size() > 0) {
            // 对返回值里面包含日志字段进行json格式的过滤
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", mapInfoList.size());
            map.put("rows", mapInfoList);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.NOATTENDANCE_ERR);
        }
        JSONObject jsonobject = JSONObject.fromObject(map);
        return jsonobject.toString();
    }
    
    /**
     * 获取今日的考勤记录 2016/12/08
     */
    @RequestMapping(value="/todayAttendance/{employeeId}/{attendanceDate}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String geTtodayAttendance(@PathVariable(value="employeeId")String employeeId, 
            @PathVariable(value="attendanceDate")String attendanceDate) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        //根据员工号获取当前员工的角色
/*        UsersApp users = usersAppService.getUserInfoByEmployeeId(Integer.parseInt(employeeId));
        int roleId = users.getRoleId();
        if (roleId == 3) { //表示当前是考核员,不能查询考勤结果
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.KAOHEYUAN_KAOQIN_FAIL);
            JSONObject jsonobject = JSONObject.fromObject(map);
            return jsonobject.toString();
        }*/
        // 用于存放最终生成的考勤记录
        List<Map<String, Object>> mapInfoList = new ArrayList<Map<String, Object>>();
        // 查询条件
        Map<String, Object> selectMap = new HashMap<String, Object>();
        // 员工ID
        selectMap.put("employeeId", employeeId);
        // 日期（今日）
        selectMap.put("attendanceDate", attendanceDate);
        // 考勤记录
        List<Map<String, Object>> lstAttendance = attService.getYesAndTodAttendanceForApp(selectMap);
        for (Map<String, Object> mp : lstAttendance) {
            Map<String, Object> mapInfo = new HashMap<String, Object>();
            // 上班状态
            String onStatus = (String) mp.get("onStatus"); 
            // 下班状态
            String offStatus = (String) mp.get("offStatus"); 
            // 实际上班时间
            String resultOnTime = (String) mp.get("onTime");; 
            // 实际下班时间
            String resultOffTime = (String) mp.get("offTime"); 
            // 计划上班时间
            String planOnTime= (String) mp.get("planOnTime");
            // 计划下班时间
            String planOffTime = (String) mp.get("planOffTime");
            
            // 将信息存入map中
            mapInfo.put("planOnTime", planOnTime);
            mapInfo.put("planOffTime", planOffTime);
            mapInfo.put("onTime", resultOnTime == null || "".equals(resultOnTime)  ? "--" : resultOnTime);
            mapInfo.put("offTime", resultOffTime == null || "".equals(resultOffTime)  ? "--" : resultOffTime);
            mapInfo.put("onStatus", onStatus == null || "".equals(onStatus) ? "--" : onStatus);
            mapInfo.put("offStatus", offStatus == null || "".equals(offStatus)  ? "--" : offStatus);
            mapInfoList.add(mapInfo);
        }
        
        if (mapInfoList.size() > 0) {
            // 对返回值里面包含日志字段进行json格式的过滤
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", mapInfoList.size());
            map.put("rows", mapInfoList);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.NOATTENDANCE_ERR);
        }
        JSONObject jsonobject = JSONObject.fromObject(map);
        return jsonobject.toString();
    }
    
    /**
     * 获取昨日或今日的考勤的责任点 2016/12/08
     */
    @RequestMapping(value="/todayAttendancePoint/{employeeId}/{roleId}/{attendanceDateStr}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTodayAttendancePoint(@PathVariable(value="employeeId")String employeeId, 
            @PathVariable(value="roleId")String roleId, @PathVariable(value="attendanceDateStr")String attendanceDateStr) {
        
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件
        Map<String, Object> selMap = new HashMap<String, Object>();
        selMap.put("employeeId", employeeId);
        selMap.put("roleId", Integer.parseInt(roleId));
        selMap.put("attendanceDateStr", attendanceDateStr);
        // 获取昨日或今日的考勤的责任点
        List<Map<String, Object>> resultMap = attService.getTodayAttendancePointNameForApp(selMap);
        if (resultMap.size() > 0) {
            // 对返回值里面包含日志字段进行json格式的过滤
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", resultMap.size());
            map.put("rows", getTomorrowWorkPlansByOnTimeOrder(resultMap));
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.NOATTENDANCE_ERR);
        }
        // 返回用户信息
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.sql.Time.class, new JsonDateValueHMSProcessor());
        // 返回用户信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }    
    
    /**
     * 获取明日的考勤的责任点 2016/12/08
     */
    @RequestMapping(value="/tomorrowAttendancePoint/{employeeId}/{roleId}/{attendanceDateStr}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTomorrowAttendancePoint(@PathVariable(value="employeeId")String employeeId, 
            @PathVariable(value="roleId")String roleId, @PathVariable(value="attendanceDateStr")String attendanceDateStr) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取当前员工的明日排班
        List<Map<String, Object>> lstWorkPlans = getTomorrowWorkPlansByOnTimeOrder(getTomorrowWorkPlans(employeeId, roleId, attendanceDateStr));
        // 用于返回最终结果的Map
        // List<Map<String, Object>> resultMap = lstWorkPlans;
        List<Map<String, Object>> resultMap = getTomorrowWorkPlansByOnTimeOrder(getTomorrowWorkPlans(employeeId, roleId, attendanceDateStr));
        // 当前日期
        Date date = CalendarUntil.getTomorrowDate();
        // 明日代班信息查询条件
        Map<String, Object> coverMap = new HashMap<String, Object>();
        coverMap.put("employeeId", Integer.parseInt(employeeId));
        coverMap.put("tomorrowDate", attendanceDateStr);
        // 查询当前用户明日是否有代班信息
        List<CoverWorkApp> lstCoverWork = coverWorkAppService.getCoverWorkListByMapApp(coverMap);
        for (CoverWorkApp cover : lstCoverWork) {
            // 代班开始时间
            Date coverFromTime = cover.getCoverFromTime();
            // 代班结束时间
            Date coverToTime = cover.getCoverToTime();
            // 标识代班人是否继续上原有班次(1.上 2.不上)
            int isOldPlans = cover.getIsOldPlans();
            // 请假人明日的排班
            List<Map<String, Object>> lstLeaveWorkPlans = getTomorrowWorkPlansByOnTimeOrder(getTomorrowWorkPlans(String.valueOf(cover.getLeaveId()), roleId, attendanceDateStr));
            for (Map<String, Object> mp : lstLeaveWorkPlans) {
                // 请假人明日上班时间
                Date dutyOnTime = CalendarUntil.dateTimeFormat(CalendarUntil.getTomorrowDate(), (Date) mp.get("dutyOnTime"));
                // 请假人明日下班时间
                Date dutyOffTime = CalendarUntil.dateTimeFormat(CalendarUntil.getTomorrowDate(), (Date) mp.get("dutyOffTime"));
 
                // 如果代班开始时间 <= 请假人计划上班时间 <= 代班结束时间，那么整个上班时间段将由代班人进行代班
                if (coverFromTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(coverToTime) <= 0) {
                    // 获取明日的排班计划的责任点名称
                    getPointName(isOldPlans, lstWorkPlans, dutyOnTime, dutyOffTime, resultMap, mp, roleId, date);
                // 如果代班开始时间 <= 请假人计划下班时间 <= 代班结束时间，那么整个上班时间段将由代班人进行代班。
                } else if (coverFromTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(coverToTime) <= 0) {
                    // 获取明日的排班计划的责任点名称
                    getPointName(isOldPlans, lstWorkPlans, dutyOnTime, dutyOffTime, resultMap, mp, roleId, date);
                // 如果代班开始时间 <= 请假人计划上班时间，且 请假人计划下班时间 <= 代班结束时间，那个整个上班时间段将由代班人进行代班。
                } else if (coverFromTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(coverToTime) <= 0) {
                    // 获取明日的排班计划的责任点名称
                    getPointName(isOldPlans, lstWorkPlans, dutyOnTime, dutyOffTime, resultMap, mp, roleId, date);
                // 如果请假人计划上班时间<=代班开始时间  ，且 代班结束时间<=请假人计划下班时间  ，那个整个上班时间段将由代班人进行代班。
                } else if (dutyOnTime.compareTo(coverFromTime) <= 0 && coverToTime.compareTo(dutyOffTime) <= 0) {
                    // 获取明日的排班计划的责任点名称
                    getPointName(isOldPlans, lstWorkPlans, dutyOnTime, dutyOffTime, resultMap, mp, roleId, date);
                }
            }
        }
        if (resultMap.size() > 0) {
            // 对返回值里面包含日志字段进行json格式的过滤
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", resultMap.size());
            map.put("rows", getTomorrowWorkPlansByOnTimeOrder(resultMap));
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.NOATTENDANCE_ERR);
        }
        // 返回用户信息
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.sql.Time.class, new JsonDateValueHMSProcessor());
        // 返回用户信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }    
    
    
    /**
     * 获取今日或明日的排班计划的责任点名称 2016/12/12
     * @param isOldPlans 标识代班人是否继续上原有班次(1.上 2.不上)
     * @param lstWorkPlans 代班人的今日或明日排班计划list
     * @param dutyOnTime 请假人上班时间
     * @param dutyOffTime 请假人下班时间
     * @param resultMap 最终结果Map
     * @param mp 请假人的排班计划
     * @param roleId 角色ID
     */
    public void getPointName(int isOldPlans, List<Map<String, Object>> lstWorkPlans, Date dutyOnTime, 
            Date dutyOffTime, List<Map<String, Object>> resultMap, Map<String, Object> mp, String roleId, Date currentDate) {
        // 如果代班人正常的上班时间与请假人的上下班时间有重叠，删除代班人的责任点名称
        if (isOldPlans == 2) {
            for (Map<String, Object> mpWork : lstWorkPlans) {
                // 代班人的上班时间
                Date coverDutyOnTime = CalendarUntil.dateTimeFormat(currentDate, (Date) mpWork.get("dutyOnTime"));
                // 代班人的下班时间
                Date coverDutyOffTime = CalendarUntil.dateTimeFormat(currentDate, (Date) mpWork.get("dutyOffTime"));
                // 如果代班人的上班时间 <= 请假人计划上班时间 <= 代班人的下班时间，删除代班人此时段的责任点名称
                if (coverDutyOnTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(dutyOffTime) <= 0) {
                    // 责任点名称拼接处理
                    pointNameConcat(mpWork, resultMap, coverDutyOnTime, coverDutyOffTime);
                // 如果代班人的上班时间 <= 请假人计划下班时间 <= 代班人的下班时间，删除代班人此时段的责任点名称
                } else if (coverDutyOnTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(coverDutyOffTime) <= 0) {
                    // 责任点名称拼接处理
                    pointNameConcat(mpWork, resultMap, coverDutyOnTime, coverDutyOffTime);
                // 如果代班人的上班时间 <= 请假人计划上班时间，且 请假人计划下班时间 <= 代班人的下班时间，删除代班人此时段的责任点名称
                } else if (coverDutyOnTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(coverDutyOffTime) <= 0) {
                    // 责任点名称拼接处理
                    pointNameConcat(mpWork, resultMap, coverDutyOnTime, coverDutyOffTime);
                // 如果请假人计划上班时间<=代班人的上班时间  ，且 代班人的下班时间<=请假人计划下班时间  ，删除代班人此时段的责任点名称
                } else if (dutyOnTime.compareTo(coverDutyOnTime) <= 0 && coverDutyOffTime.compareTo(dutyOffTime) <= 0) {
                    // 责任点名称拼接处理
                    pointNameConcat(mpWork, resultMap, coverDutyOnTime, coverDutyOffTime);
                }
            }
            // 代班人的上班时间
            Date coverDutyOnTime = CalendarUntil.dateTimeFormat(currentDate, (Date) mp.get("dutyOnTime"));
            // 代班人的下班时间
            Date coverDutyOffTime = CalendarUntil.dateTimeFormat(currentDate, (Date) mp.get("dutyOffTime"));
            // 请假人的责任点名称
            String pointName = (String) mp.get("pointName");
            // 如果为true,添加请假人的上下班时间以及责任点名称，如果为false,只需要在原来的结果中拼接请假人的责任点名称即可
            boolean flag = true;
            // 添加请假人的责任点信息
            for (Map<String, Object> resMap : resultMap) {
                // 最终结果Map中的上班时间
                Date coverDutyOnTime1 = (Date) resMap.get("dutyOnTime");
                // 最终结果Map中的下班时间
                Date coverDutyOffTime1 = (Date) resMap.get("dutyOffTime");
                // 如果请假人的上下班时间，在最终结果Map中存在，则只需要拼接责任点名称即可
                if (hsm.format(coverDutyOnTime1).compareTo(hsm.format(coverDutyOnTime)) == 0 && hsm.format(coverDutyOffTime1).compareTo( hsm.format(coverDutyOffTime)) == 0) {
                    StringBuilder sb = new StringBuilder();
                    // 用于防止当出现责任点名称是""的时候，存放结果会出多余的","
                    if (!StringUtils.isEmpty((String) resMap.get("pointName"))) {
                        sb.append((String)resMap.get("pointName"));
                        sb.append(",");
                    }
                    sb.append(pointName);
                    resMap.put("pointName", sb.toString());
                    flag = false;
                }
            }
            if (flag) {
                resultMap.add(mp);
            }
        } else {
            // 环卫工
            if ("1".equals(roleId)) {
                boolean flag = false;
                // 遍历返回结果Map，进行责任点名称的拼接
                for (Map<String, Object> resMp : resultMap) {
                    // 代班人明日上班时间
                    String coverDutyOnTimeStr = hsm.format((Date) resMp.get("dutyOnTime"));
                    // 代班人明日下班时间
                    String coverDutyOffTimeStr = hsm.format((Date) resMp.get("dutyOffTime"));
                    // 请假人明日上班时间
                    String dutyOnTimeStr = hsm.format(dutyOnTime);
                    // 请假人明日上班时间
                    String dutyOffTimeStr = hsm.format(dutyOffTime);
                    // 代班人和请假人的明日上下班时间相等，则将责任点名称进行合并成一个责任点，如果不相等，则新添加此条不相等的责任点名称
                    if (dutyOnTimeStr.compareTo(coverDutyOnTimeStr) == 0 && dutyOffTimeStr.compareTo(coverDutyOffTimeStr) == 0) {
                        StringBuilder sb = new StringBuilder();
                        if (!StringUtils.isEmpty((String)resMp.get("pointName"))) {
                            sb.append((String)resMp.get("pointName"));
                            sb.append(",");
                        }
                        sb.append((String)mp.get("pointName"));
                        resMp.put("pointName", sb.toString());
                        flag = true;
                        break;
                    }
                }
                // 不相等，则添加此条不相等的责任点名称
                if (flag == false) {
                    resultMap.add(mp);
                }            
            // 检测员    
            } else {
                // 遍历返回结果Map，进行责任点名称的拼接,去掉重复的责任点名称
                StringBuilder sb = new StringBuilder();
                for (Map<String, Object> resMp : resultMap) {
                    // 代班人的责任点名称
                    String coverPointName = (String)resMp.get("pointName");
                    sb.append(coverPointName);
                    String[] coverArray = coverPointName.split(",");
                    List<String> lstCover = Arrays.asList(coverArray);
                    // 请假人的责任点名称
                    String leavePointName = (String)mp.get("pointName");
                    String[] leaveArray = leavePointName.split(",");
                    // 以代班人为基础去掉重复的责任点名称
                    for (String leaveStr : leaveArray) {
                        if (!lstCover.contains(leaveStr)) {
                            sb.append(",");
                            sb.append(leaveStr);
                            resMp.put("pointName", sb.toString());
                        }
                    }
                    // 将检测员的责任点名称进行排序（即第一责任区，第二责任区，第三责任区），然后重新放到结果MAP中
/*                    StringBuilder sbSort = new StringBuilder();
                    String sortPointName = (String)resMp.get("pointName");
                    List<String> lstSort = Arrays.asList(sortPointName.split(","));
                    Collections.sort(lstSort); 
                    for (String strSort : lstSort) {
                        sbSort.append(strSort);
                        sbSort.append(",");
                    }
                    resMp.put("pointName", sbSort.substring(0, sbSort.toString().length() - 1));*/
                }
            }
        }
    }
    
    /**
     * 责任点名称拼接处理
     * @param mpWork 代班人的今日或明日排班计划
     * @param resultMap 存放最终结果Map
     * @param coverDutyOnTime 代班人的上班时间
     * @param coverDutyOffTime 代班人的下班时间
     */
    public void pointNameConcat(Map<String, Object> mpWork, List<Map<String, Object>> resultMap, Date coverDutyOnTime, 
            Date coverDutyOffTime) {
        // 代班人的责任点名称
        String coverPointName = (String) mpWork.get("pointName");
        for (Map<String, Object> mp : resultMap) {
            // 存放最终结果代班人的上班时间
            Date dutyOnTime = (Date) mp.get("dutyOnTime");
            // 存放最终结果代班人的下班时间
            Date dutyOffTime = (Date) mp.get("dutyOffTime");
            // 如果当前代班人的上下班时间与存放最终结果代班人的上下班时间相等，删除存放最终结果中的代班人的责任点名称
            if (hsm.format(dutyOnTime).compareTo(hsm.format(coverDutyOnTime)) == 0 && hsm.format(dutyOffTime).compareTo( hsm.format(coverDutyOffTime)) == 0) {
                StringBuilder sb = new StringBuilder();
                // 当前时间段，最终结果Map中的责任点名称
                String pointName = (String) mp.get("pointName");
                String[] coverArray = pointName.split(",");
                List<String> lstCover = Arrays.asList(coverArray);
                // 删除存放最终结果中的代班人的责任点名称
                for (String str: lstCover) {
                    if (!str.equals(coverPointName)) {
                        sb.append(str);
                        sb.append(",");
                    }
                }
                // 以代班人为基础去掉重复的责任点名称
                if (sb.length() > 0) {
                    mp.put("pointName", sb.toString().substring(0, sb.toString().length() - 1));
                } else {
                    mp.put("pointName", "");
                }
            }
        }
    }
    
    /**
     * 针对责任点名称的获取明日的排班计划 2016/12/10
     */
    public List<Map<String, Object>> getTomorrowWorkPlans(String employeeId, String roleId, String attendanceDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        Date attendanceDate = null;
        int week = 0;
        int day = 0;
        try {
            attendanceDate = sdf.parse(sdf.format(new Date()));
            week = CalendarUntil.week(attendanceDate);//获取当前日期是周几,1是周一 ，7是周天
            day = CalendarUntil.day(attendanceDate);//获取当前日期是几号， 1是1号
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, Object> selectMap = new HashMap<String, Object>();
        selectMap.put("nowDate", attendanceDate);
        selectMap.put("week", week);
        selectMap.put("day", day);
        selectMap.put("employeeId", Integer.parseInt(employeeId));
        //1. 判定[当前时间,当天24点之前]，是否有换班计划，若无，则返回排班计划，若有，则返回换班后的排班计划
        List<WorkPlansApp> changeWorkPlansList = workPlansAppService.getWorkPlansAndPointNameListByMapApp(selectMap); // 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划，2016-11-11
        
        List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();//要返回的结果集
        
        /*2.1 如果没有换班计划,则获取该员工的排班计划，
            2.1.1若是没有排班计划,则判断该员工在[当前时间,当天24点之前]，是否作为换班人存在
                2.1.1.1 若是作为换班人存在，则返回其作为换班人对应的值班人的排班计划，
                2.1.1.2若不是作为换班人存在，则返回空
            2.1.2若是有排班计划，则返回原有排班计划*/
        
        //2.1 如果没有换班计划,则获取该员工的排班计划
        if(changeWorkPlansList.size() == 0){
            
            //3.1 如果没有换班计划,则获取该员工的排班计划
            Map<String, Object> mp = new HashMap<String, Object>();
            mp.put("employeeId", employeeId);
            mp.put("roleId", Integer.parseInt(roleId));
            List<Map<String, Object>> workPlansList = workPlansAppService.getWorkPlansAndPointNameListByEmployeeIdApp(mp);//获取某员工上下班时间不重复的排班计划集合，2016-11-11 
            
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
                    Map<String, Object> mp1 = new HashMap<String, Object>();
                    mp1.put("employeeId", employeeIds);
                    mp1.put("roleId", Integer.parseInt(roleId));
                    workPlansList = workPlansAppService.getWorkPlansAndPointNameListByEmployeeIdApp(mp1);//获取某些员工上下班时间不重复的排班计划集合,只有时间集合，2016-12-12
                    
                    rows = workPlansList;
                }else{//3.1.1.2若不是作为换班人存在，则返回空
                    
                }
            }else{//3.1.2若是有排班计划，则返回原有排班计划
                
                rows = workPlansList;
            }
        }else{//4.获取换班人的排班计划
            String employeeIds = "";
            for(WorkPlansApp workPlansApp : changeWorkPlansList){
                employeeIds += workPlansApp.getTradeEmployeeId() + ",";
            }
            employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
            Map<String, Object> mp2 = new HashMap<String, Object>();
            mp2.put("employeeId", employeeIds);
            mp2.put("roleId", Integer.parseInt(roleId));
            List<Map<String, Object>> workPlansList  = workPlansAppService.getWorkPlansAndPointNameListByEmployeeIdApp(mp2);//获取某些员工上下班时间不重复的排班计划集合,只有时间集合，2016-12-12
            
            rows = workPlansList;
        }
        
        //循环排班计划，修改排班计划中员工ID为该员工自己的员工ID
        for(Map<String, Object> map : rows){
            map.put("employeeId", selectMap.get("employeeId").toString());
        }
        
        return rows;
    }    
}
