package com.czz.hwy.action.users.watch;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.area.watch.DutyPointGather;
import com.czz.hwy.bean.mission.watch.DutyPlans;
import com.czz.hwy.bean.mission.watch.TaskInformation;
import com.czz.hwy.bean.mission.watch.TaskInformationPojo;
import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.bean.user.watch.AttendanceForPlans;
import com.czz.hwy.bean.user.watch.AttendanceVo;
import com.czz.hwy.bean.user.watch.Attendances;
import com.czz.hwy.bean.user.watch.Users;
import com.czz.hwy.service.area.watch.DutyPointGatherWatchService;
import com.czz.hwy.service.mission.BanciService;
import com.czz.hwy.service.mission.watch.DutyPlansWatchService;
import com.czz.hwy.service.mission.watch.TaskInformationWatchService;
import com.czz.hwy.service.mission.watch.WorkPlansWatchService;
import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService;
import com.czz.hwy.service.usermanagement.watch.AttendanceForPlansWatchService;
import com.czz.hwy.service.usermanagement.watch.AttendancesWatchService;
import com.czz.hwy.service.usermanagement.watch.UsersWatchService;
import com.czz.hwy.utils.CalendarUntil;
import com.czz.hwy.utils.CheckLatLng;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.DutyTimeConfigApp;
import com.czz.hwy.utils.GetDistance;

/**
 * 通过此Action里的接口，获取用户信息
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161026
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/usersWatchController")
public class UsersWatchController{
	// 定义返回结果
	Map<String, Object> map = null;
	// 定义查询出排班计划的map集合
	Map<Object, List<DutyPlans>> dutyPlanMap = null;
	// 用户信息类业务层
	@Autowired
	private UsersWatchService usersWatchService;
	// 获取出勤时间计划类业务层
	@Autowired
	private WorkPlansWatchService workPlansWatchService;
	// 获取出勤时间计划类业务层
    @Autowired
    private DutyPlansWatchService dutyPlansWatchService;
	// 任务管理的业务层
	@Autowired
	private TaskInformationWatchService taskInformationWatchService;
	// 考勤结果业务层
	@Autowired
	private AttendanceForPlansWatchService attendanceForPlansWatchService;
	
    // 责任点属性
    @Autowired
    private DutyPointGatherWatchService dutyPointGatherWatchService;
	// 班次业务层
	@Autowired
	private BanciService banciService;

    @Autowired
    private AttendanceForPlansAppService attService;
	
	// 上下班时间参数配置类 
    @Autowired  
    private DutyTimeConfigApp dutyTimeConfig; 
	
    // 考核员打卡时的经纬度
    @Autowired
    private CheckLatLng checkLatLng;    
    
	// 上下班地理位置异常业务层
//	@Autowired
//	private LocationExceptionRecordService locationExceptionRecordService;
	
	private final static String shangban = "上班";

	private final static String xiaban = "下班";

	private final static String chidao = "迟到";

	private final static String zaotui = "早退";

	private final static String weishangban = "未上班";

	private final static String CHURENWU = "出任务";
	
	private final static String WEIDAOSHIJIAN = "未到时间";

	/**上班前推时间*/
	public static int DUTY_ONTIME_BEFORE = 60;
	
	/** 上班推迟时间*/
	public static int DUTY_ON_TIME_AFTER = 0;

	/** 下班前推时间*/
	public static int DUTY_OFF_TIME_BEFORE = 0;
	
	/** 下班推迟时间*/
	public static int DUTY_OFF_TIME_AFTER = 60;
	
	/**判定上班活动距离*/
	public static int DUTY_ON_DISTANCE = 0;
	
	/**判定下班活动距离*/
	public static int DUTY_OFF_DISTANCE = 0;
	
	/**用于设置判定上班状态时，查询当前时间往前推？分钟的轨迹,查询当前时间到前推多少分钟的轨迹*/
	public static int CURRENT_TIME_BEFORE = 0;
	
	/**用于设置开始查询怠工时间，为上班时间后的？分钟，目前设为上班30分钟后*/
	public static int SLOW_DOWN_TIME_AFTER = 0;
	
	/**用于设置判定是否怠工时，查询当前时间往前推？分钟的轨迹，目前设置为【当前时间-30，当前时间】之间的轨迹*/
	public static int SLOW_DOWN_TIME_BEFORE = 0;
	
	/** 查询上班地理位置异常，查询【上班时间，上班后？分钟】的轨迹中是否有位置异常*/
	public static int ON_LOCATION_EXCEPTION_AFTER = 0;
	
	/** 查询下班地理位置异常，查询【下班前？分钟，下班时间】的轨迹中是否有位置异常*/
	public static int OFF_LOCATION_EXCEPTION_BEFORE = 0;
	
	/** 查询考勤记录的日期是当前日期，显示是否早退，把判断是否早退时间往下班时间后退10分钟*/
	public static int ZAO_TUI_AFTER_MINUTE = 0;
	
	/**用于设置判定上班状态时，判断上班前？分钟内，是否有在责任点的轨迹，判断逻辑是【上班时间前60分钟，上班时间】内是否有轨迹是在责任点内的*/
    public static int AT_POINT_TIME_FOR_SB = 60;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
	
	// 获取考勤信息
	@Autowired
	private AttendancesWatchService attendancesWatchService;

/*	// 读取配置文件,初始化上班推迟时间以及下班推迟时间,上班推前时间，下班推前时间，判定上班活动距离，判定下班活动距离
	static {
		UsersWatchController uAction = new UsersWatchController();
		InputStream in = uAction.getClass().getResourceAsStream("/dutytimeconfig.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DUTY_ONTIME_BEFORE = Integer.parseInt(prop.getProperty("DUTY_ONTIME_BEFORE"));
		DUTY_ON_TIME_AFTER = Integer.parseInt(prop.getProperty("DUTY_ON_TIME_AFTER"));
		DUTY_OFF_TIME_BEFORE = Integer.parseInt(prop.getProperty("DUTY_OFF_TIME_BEFORE"));
		DUTY_OFF_TIME_AFTER = Integer.parseInt(prop.getProperty("DUTY_OFF_TIME_AFTER"));
		DUTY_ON_DISTANCE = Integer.parseInt(prop.getProperty("DUTY_ON_DISTANCE"));
		DUTY_OFF_DISTANCE = Integer.parseInt(prop.getProperty("DUTY_OFF_DISTANCE"));
		CURRENT_TIME_BEFORE = Integer.parseInt(prop.getProperty("CURRENT_TIME_BEFORE"));
		SLOW_DOWN_TIME_AFTER = Integer.parseInt(prop.getProperty("SLOW_DOWN_TIME_AFTER"));
		SLOW_DOWN_TIME_BEFORE = Integer.parseInt(prop.getProperty("SLOW_DOWN_TIME_BEFORE"));
		ON_LOCATION_EXCEPTION_AFTER = Integer.parseInt(prop.getProperty("ON_LOCATION_EXCEPTION_AFTER"));
		OFF_LOCATION_EXCEPTION_BEFORE = Integer.parseInt(prop.getProperty("OFF_LOCATION_EXCEPTION_BEFORE"));
		ZAO_TUI_AFTER_MINUTE = Integer.parseInt(prop.getProperty("ZAO_TUI_AFTER_MINUTE"));
	}*/


    /**
     * 获取当前考勤信息
     */
    @RequestMapping(value="/attendanceWatch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCurrentAttendanceWatch(Integer employeeId) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取APP端请求时间
         String attendanceDate = sdf.format(new Date());
        if (employeeId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.SELECT_FAIL_MSG);
            map.put("failType", 5);
            // 返回用户信息
            return map;
        }
/*        //根据员工号获取当前员工的角色
        Users users = usersWatchService.getUserInfoByEmployeeId(employeeId.intValue());
        int roleId = users.getRoleId();
        if (roleId == 3) { //表示当前是考核员,不能查询考勤结果
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.KAOHEYUAN_KAOQIN_FAIL);
            map.put("failType", 5);
            // 返回用户信息
            return map;
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
        // 返回用户信息
        return map;
    }
    
    /**
     * 判断该员工的考勤状态
     * 2016-09-06:增加排班下班状态逻辑：若是查询日期是当天，判断下班状态是否早退时，推迟到下班时间10分钟后
     * @param attendances
     */
    private List<Map<String, Object>> getAttendanceStatus(List<AttendanceForPlans> attendancesList, Date today, boolean isToday) {
        Date planOnTime = null; // 计划上班时间
        Date planOffTime = null; // 计划下班时间
        Date resultOnTime = null; // 实际上班时间
        Date resultOffTime = null; // 实际下班时间
        String taskStatus = null; // 是否出任务
        
        // 考勤下班时间
        String onStatus = ""; // 上班状态
        String offStatus = ""; // 下班状态
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        List<Map<String, Object>> mapInfoList = new ArrayList<Map<String, Object>>();
        // 定义任务对象
        TaskInformationPojo taskInformationPojo = new TaskInformationPojo();
        if (attendancesList != null && attendancesList.size() > 0) {
            for (AttendanceForPlans attForPlans : attendancesList) {
                Map<String, Object> mapInfo = new HashMap<String, Object>();
                // 设置计划上班下班时间
                planOnTime = attForPlans.getDutyOnTime();
                planOffTime = attForPlans.getDutyOffTime();
                if (attForPlans.getGetOnStatus() != null && !"".equals(attForPlans.getGetOnStatus())) { // 生成上班考勤结果
                    onStatus = attForPlans.getGetOnStatus();
                    if(WEIDAOSHIJIAN.equals(onStatus)){//如果当天还没有到上班时间
                        onStatus = null;
                    }else{
                        // 设置实际上班时间
                        if (attForPlans.getUpdateAt() != null) {
                            resultOnTime = timeFormat(attForPlans.getUpdateAt());
                        }
                    }
                } else {
                    // 2016-11-18 当自动考勤时（针对上班时间是06:00那么在06:00到06:05之间查询时会显示未上班状态），现在将未上班状态变为不显示上班状态即空白 
                    onStatus = null;
                    // 2016-11-18 修正前 
                    // onStatus = weishangban;
                }
                
                // 判断当前查询的考勤的时间是否是在下班之后,若查询时间小于计划下班时间,这不生成下班状态
                if (timeFormat(today).compareTo(planOffTime) > 0) { // 当前查询在计划下班之后
                    // 判断下班状态
                    if (attForPlans.getGetOffStatus() != null && !"".equals(attForPlans.getGetOffStatus())) {
                        offStatus = attForPlans.getGetOffStatus();
                        // 设置下班时间
                        resultOffTime = attForPlans.getLastRecordTime();
                    } else if (attForPlans.getLastRecordTime() != null) { // 最后一次记录时间不为空
                        if(!isToday || timeFormat(today).compareTo(timeFormat(afterHowManyMin(planOffTime, ZAO_TUI_AFTER_MINUTE))) > 0){//如果是历史查询 或 当前时间比下班时间后10分钟大，判断下班状态是否早退
                            // 取出最后一次记录的时间
                            Date lastRecorDate = attForPlans.getLastRecordTime();
                            // 判断最后一次记录的时间是否是早退
                            if (timeFormat(lastRecorDate).compareTo(planOffTime) < 0) { // 在计划下班之前已经离开,标记为早退
                                offStatus = zaotui;
                                // 设置下班时间
                                resultOffTime = timeFormat(lastRecorDate);
                            }
                        }
                    } else {
                        offStatus = weishangban;
                    }
                // 针对垃圾清运人员（垃圾清运人员是指下班时间是23:59:00）随时可以下班，设置下班状态
                } else if (fmt.format(planOffTime).equals("23:59:00")) {
                    offStatus = attForPlans.getGetOffStatus();
                    resultOffTime = attForPlans.getLastRecordTime();
                }
                
                
                // 判断当前人员是否出任务
                taskInformationPojo.setCurronTime(today);
                taskInformationPojo.setTaskDutyPeople("工号:" + attForPlans.getEmployeeId() + ",");
                // 根据时间查询出当前时间的任务列表
                List<TaskInformation> informationByPojos = taskInformationWatchService.getTaskInformationByPojo(taskInformationPojo);
                if (informationByPojos != null && informationByPojos.size() > 0) { // 表示当前时间该责任人是出任务的
                    taskStatus = CHURENWU;
                    /*2016-08-04 修改逻辑：如果某员工出任务，则其当天不进行考勤，只显示任务状态*/
                    resultOnTime = null;
                    resultOffTime = null;
                    onStatus = null;
                    offStatus = null;
                    /*2016-08-04 修改逻辑：如果某员工出任务，则其当天不进行考勤，只显示任务状态*/
                }
                // 将信息存入map中
                mapInfo.put("taskStatus", taskStatus == null || "".equals(taskStatus) ? "--" : taskStatus);
                mapInfo.put("planOnTime", fmt.format(planOnTime));
                mapInfo.put("planOffTime", fmt.format(planOffTime));
                mapInfo.put("onTime", resultOnTime == null ? "--" : fmt.format(resultOnTime));
                mapInfo.put("offTime", resultOffTime == null ? "--" : fmt.format(resultOffTime));
                mapInfo.put("onStatus", onStatus == null || "".equals(onStatus) ? "--" : onStatus);
                mapInfo.put("offStatus", offStatus == null || "".equals(offStatus) ? "--" : offStatus);
                mapInfoList.add(mapInfo);
                resultOnTime = null;
                resultOffTime = null;
                if (onStatus != null) {
                    onStatus = null;
                }
                if (offStatus != null) {
                    offStatus = null;
                }
                if (taskStatus != null && taskStatus != "") {
                    taskStatus = null;
                }
            }
        }
        return mapInfoList;
    }
    
    /** 
     * 签到签退，环卫工，检测员考勤，批量写入考勤记录 2016-08-30
     * 2016-08-31，只做考勤轨迹的记录，不做上下班状态的过来，上下班状态的过滤放到定时任务中去判断
    */
    @RequestMapping(value = "/insertAttendanceWatch", method = RequestMethod.POST)
    @ResponseBody    
    public Map<String, Object> insertAttendancesByList(String acceptContent){
        System.out.println("毫秒" + new Date().getTime());
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断请求信息是否为空
        if (StringUtils.isEmpty(acceptContent)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
            return map;
        }
        
        // 1.从请求信息中获取请求字段
        JSONArray jsonArray = JSONArray.fromObject(acceptContent);
        if (jsonArray == null || jsonArray.size() == 0) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
            return map;
        }
        
        List<AttendanceVo> attendancesVoList = new ArrayList<AttendanceVo>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i = 0; i < jsonArray.size(); i++){//1.循环取出考勤对象
            JSONObject object = (JSONObject) jsonArray.get(i);
            AttendanceVo att = (AttendanceVo) JSONObject.toBean(object, AttendanceVo.class);
            String attendanceDateStr = object.getString("attendanceDate");
            String recordTimeStr = object.getString("recordTime");
            try {
                att.setAttendanceDate(sdf.parse(attendanceDateStr));
                att.setRecordTime(sdf.parse(recordTimeStr));
                attendancesVoList.add(att);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    
        // 员工编号
        AttendanceVo attendancesVo = attendancesVoList.get(0);
        
        // 定义返回值的用户信息
        Users users = null;
        // 判断提取的字段不为空
        if (attendancesVo != null && !"".equals(attendancesVo.getEmployeeId())) {
            // 查询用户信息
            users = usersWatchService.getUserInfoByEmployeeId(attendancesVo.getEmployeeId());
        }
        // 2.判断如果查询user对象结果不为空的话返回对象信息，空则返回错误信息
        if (users == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.USER_INFO_ERR);
            return map;
        }
        if (users.getRoleId() == 4) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.USER_ROLE);
            return map;
        }
        
        //3.先返回数据，表示接收数据成功
        //map.put("result", ConstantUtil.SUCCESS);
        //map.put("information", ConstantUtil.INSERT_SUCCESS);
        // 返回用户信息
        //return map;
        
        
        // 4.判断该用户是否是在出勤计划内
        WorkPlans workPlans  = new WorkPlans();
        workPlans.setEmployeeId(attendancesVo.getEmployeeId());
        workPlans.setRoleId(users.getRoleId());
        List<WorkPlans> workPlansList = dutyPlansWatchService.getAreaPointListByBeanWatch(workPlans);
        
        List<WorkPlans> workPlansListForDB = dutyPlansWatchService.getAreaPointListByBeanForDBWatch(workPlans);//根据代班人ID和代班人角色，获取对应请假人排班计划中责任区责任点不重复列表，2016-12-05
        //循环排除掉重复的责任区责任点
        for(WorkPlans plansDB: workPlansListForDB){
            boolean flag = true;
            for(WorkPlans plans : workPlansList){//如果有相同的，退出循环
                if(plansDB.getAreaId() == plans.getAreaId() && plansDB.getPointId() == plans.getPointId()){
                    flag = false;
                    break;
                }
            }
            if(flag){//若是不一样，添加到排班计划责任区责任点列表中
                workPlansList.add(plansDB);
            }
        }
        
        //循环attendancesVoList集合，转换为Attendances集合
        List<Attendances> attendancesList = new ArrayList<Attendances>();
        for(AttendanceVo attVo : attendancesVoList){
            Attendances attendances = new Attendances();
            attendances.setAddress(attVo.getAddress());
            attendances.setAttendanceDate(attVo.getAttendanceDate());
            attendances.setEmployeeId(attVo.getEmployeeId());
            attendances.setLat("".equals(attVo.getLat()) ? 0.0 : Double.parseDouble(attVo.getLat()));
            attendances.setLng("".equals(attVo.getLng()) ? 0.0 : Double.parseDouble(attVo.getLng()));
            attendances.setRecordTime(attVo.getRecordTime());
            attendances.setCreateAt(new Date());
            
            if (attendances.getAttendanceDate() == null || attendances.getRecordTime() == null) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
                return map;
            }
            attendancesList.add(attendances);
        }
        
        //5.添加轨迹表记录
        attendancesList = batchAddAttendancePath(workPlansList, attendancesList, users);
        
        //6.循环考勤信息
        /*for(Attendances attendances : attendancesList){
            //6.根据排班计划，判定员工考勤记录的上下班状态
            updateOnOrOffStatus(dutyPlansList, attendances, attendances.getAtPoint() == 1 ? true : false);
        }*/
        
        // 根据员工号查询当前员工的考勤状态
        return this.getCurrentAttendanceStatus(attendancesVo.getEmployeeId());
   }
    
   /** 批量新增考勤轨迹记录 2016-08-30
    * @param dutyPlansList
    * @param attendances
    * @return
    */
   public List<Attendances> batchAddAttendancePath(List<WorkPlans> dutyPlansList, List<Attendances> attendancesList, Users users){
       
       //1.循环每条考勤轨迹，判断每条轨迹在是否在责任点
       for(Attendances attendances : attendancesList){
           List<Map<String, Double>> pointListByRound = new ArrayList<Map<String,Double>>();//保存以环卫工当前位置为圆点，每五米取圆上360个点，一直取到100米
           
           // 存放该责任点采集点的纬度和经度
           List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
           // 判断员工当前位置是否在责任点的范围
           boolean isAtPoint = false;//表示是否在责任点范围
           for (WorkPlans dutyPlans : dutyPlansList) {
               // 判断该人是否出勤范围
               List<DutyPointGather> dutyPointinfo = dutyPointGatherWatchService.getDutyPointInfoById(dutyPlans.getPointId(), dutyPlans.getAreaId());
               boolean b = false;//标识是否在责任点的范围内
               for (DutyPointGather dutyPointGather : dutyPointinfo) {//循环判断该环卫工所在位置与责任点采集点的经纬度的距离是否小于100米
                   double distance = GetDistance.getLongDistance(attendances.getLng(), attendances.getLat(), dutyPointGather.getBorderpointLng(), dutyPointGather.getBorderpointLat());
                   if (distance < 10) {//若小于100米表示该员工在责任点范围内
                       b = true;
                       break;
                   } else {//否则将责任点采集点的经纬度分别存入对应的集合，用于后面构建多边形
                       Point2D.Double dutyPoint = new Point2D.Double(dutyPointGather.getBorderpointLng(), dutyPointGather.getBorderpointLat());
                       pts.add(dutyPoint);
                   }
               }
               
               if (b) {
                   isAtPoint = true;
                   break;
               }
               if(pts.size() == 0){
                   isAtPoint = false;
                   break;
               }
               
               //根据环卫工当前的经纬度，获取距离环卫工位置为100米的经纬度集合，这里取360个 + 再加环卫工当前位置
               List<Map<String, Double>> pointList = new ArrayList<Map<String,Double>>();
               Map<String, Double> map = new HashMap<String, Double>();
               map.put("lat", attendances.getLat());
               map.put("lng", attendances.getLng());
               pointList.add(map);//添加环卫工当前位置
               if(pointListByRound.size() == 0){//若是第一次取圆上的点
                   for(int i = 1;i <= 2; i++){
                       pointListByRound.addAll(GetDistance.getPointListByPoint(attendances.getLat(), attendances.getLng(), i*5, 360));//添加距离环卫工位置为100米的360个经纬度
                       
                   }
               }
               pointList.addAll(pointListByRound);
               
               boolean isPointInPolygon = false;
               //循环纬度集合，判断是否有在责任点范围内的点
               for(Map<String, Double> m : pointList){
                   Point2D.Double point = new Point2D.Double(m.get("lng"), m.get("lat"));
                   isPointInPolygon = GetDistance.isPointInPolygon(point, pts);//GetDistance.isPointInPolygon(m.get("lat"), m.get("lng"), polygonXA, polygonYA);
                   if (isPointInPolygon) {
                       // 如果该环卫工所在位置在责任点范围内
                       break;
                   }
               }
               if(isPointInPolygon){
                   isAtPoint = true;
                   break;
               }
           }
           attendances.setAtPoint(isAtPoint ? 1 : 0);//记录该坐标是否在责任点范围内
       }
       
       //2.插入轨迹数据，并记录该员工当前位置，是否在责任点范围内,2016-08-30
       attendancesWatchService.batchAddAttendances(attendancesList); // 插入轨迹记录
       //3.将当天轨迹数据插入到用于地图查询的轨迹表中去,2016-09-20
       attendancesWatchService.batchAddAttendancesForMap(attendancesList); 
       return attendancesList;//返回是否在责任点
   }
   
   /** 根据员工号查询当前员工的考勤状态
    * @param dutyPlansList
    * @param attendances
    * @return
    */
   public Map<String, Object> getCurrentAttendanceStatus(int employeeId) {
       Map<String, Object> map = new HashMap<String, Object>();
       // 当前时间
       String currentDate = hms.format(new Date());
       // 当前的考勤记录
       Map<String, Object> mp = attendanceForPlansWatchService.getCurrentAttendanceStatus(employeeId);
       if (mp != null) {
           if (currentDate.compareTo(hms.format((Date)mp.get("dutyOnTime"))) >= 0 
                   && (currentDate.compareTo(hms.format((Date) mp.get("dutyOffTime"))) <= 0)) {
               // 在考勤时间范围内的上班（手表显示绿色）
               if (mp.get("getOnStatus").equals("上班")) {
                   map.put("result", ConstantUtil.SUCCESS);
                   map.put("information", 2);
               // 在考勤时间范围内的迟到或未上班（手表显示红色）
               } else if (mp.get("getOnStatus").equals("迟到") || mp.get("getOnStatus").equals("未上班")) {
                   map.put("result", ConstantUtil.SUCCESS);
                   map.put("information", 3);
               // 任务中（手表显示白色）
               } else if (mp.get("getOnStatus").equals("任务")) {
                   map.put("result", ConstantUtil.SUCCESS);
                   map.put("information", 1);
               // 请假中（手表显示白色）
               } else if (mp.get("getOnStatus").equals("请假")) {
                   map.put("result", ConstantUtil.SUCCESS);
                   map.put("information", 1);
               }
           } else {
               // 不在考勤时间范围内是正常（手表显示绿色）
               map.put("result", ConstantUtil.SUCCESS);
               map.put("information", 2);
           }
       } else {
           map.put("result", ConstantUtil.SUCCESS);
           map.put("information", 1);
       }
       return map;
   }  
   
   
   /** 
    * 手动考勤 2016-11-01
    */
   @RequestMapping(value = "/manualAttendanceWatch", method = RequestMethod.POST)
   @ResponseBody    
   public Map<String, Object> manualAttendance(String acceptContent) throws ParseException{
       // 初始化返回值信息
       Map<String, Object> map = new HashMap<String, Object>();
       // 判断请求信息是否为空
       if (StringUtils.isEmpty(acceptContent)) {
           map.put("result", ConstantUtil.FAIL);
           map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
           map.put("failType", 1);
           return map;
       }
       
       // 1.从请求信息中获取请求字段
       JSONObject object = JSONObject.fromObject(acceptContent);
       AttendanceVo att = (AttendanceVo) JSONObject.toBean(object, AttendanceVo.class);
   
       // 定义返回值的用户信息
       Users users = null;
       // 判断提取的字段不为空
       if (att != null && !"".equals(att.getEmployeeId())) {
           // 查询用户信息
           users = usersWatchService.getUserInfoByEmployeeId(att.getEmployeeId());
       }
       // 2.判断如果查询user对象结果不为空的话返回对象信息，空则返回错误信息
       if (users == null) {
           map.put("result", ConstantUtil.FAIL);
           map.put("information", ConstantUtil.USER_INFO_ERR);
           return map;
       }
       if (users.getRoleId() == 4) {
           map.put("result", ConstantUtil.FAIL);
           map.put("information", ConstantUtil.USER_ROLE);
           return map;
       }
       
       // 4.判断该用户是否是在出勤计划内
       WorkPlans workPlans  = new WorkPlans();
       workPlans.setEmployeeId(att.getEmployeeId());
       workPlans.setRoleId(users.getRoleId());
       List<WorkPlans> workPlansList = dutyPlansWatchService.getAreaPointListByBeanWatch(workPlans);
       
       List<WorkPlans> workPlansListForDB = dutyPlansWatchService.getAreaPointListByBeanForDBWatch(workPlans);//根据代班人ID和代班人角色，获取对应请假人排班计划中责任区责任点不重复列表，2016-12-05
       //循环排除掉重复的责任区责任点
       for(WorkPlans plansDB: workPlansListForDB){
           boolean flag = true;
           for(WorkPlans plans : workPlansList){//如果有相同的，退出循环
               if(plansDB.getAreaId() == plans.getAreaId() && plansDB.getPointId() == plans.getPointId()){
                   flag = false;
                   break;
               }
           }
           if(flag){//若是不一样，添加到排班计划责任区责任点列表中
               workPlansList.add(plansDB);
           }
       }
       
       // 下班时间与下次上班时间间隔的分钟数
       long minute = 0;
       // 判断手动考勤时间段是否在对应的范围之内
       int timeFlag = object.getInt("timeFlag");
       // 考勤时间
       String timePlan = object.getString("timePlan");
       // 当前时间
       String curStr = hms.format(new Date());
       // 1表示上班时间段
       if (timeFlag == 1) {
           long from = hms.parse(timePlan).getTime();
           long to = hms.parse(curStr).getTime();
           // 考勤时间减去当前时间的分钟数
           int minutes = (int) ((from - to)/(1000 * 60));  
           // 不在一小时内不允许打卡
           if (minutes > 60 || minutes < 0) {
               map.put("result", ConstantUtil.FAIL);
               map.put("information", "不在一小时内不允许打上班卡");
               map.put("failType", 2);
               return map;
           }
       // 2表示下班时间段
       } else {
           // 获取当前所有的排班计划
           List<WorkPlans> work = workPlansWatchService.getWorkPlansByIdTimeList(att.getEmployeeId());
           // 由于垃圾清运人员（垃圾清运人员是指下班时间是23:59:00）随时可以下班，而不是垃圾清运人员则需要通过上下班时间进行打卡
           if (!timePlan.equals("23:59:00")) {
               // 对应当前的下班时间，获取下一次的上班时间
               Date dutyOffTime = null;
               int index = 0;
               for (int i = 0; i < work.size(); i++) {
                   dutyOffTime = work.get(i).getDutyOffTime();
                   if (timePlan.equals(hms.format(dutyOffTime))) {
                       index = i + 1;
                       break;
                   }
               }
               // 存在下次上班时间
               Date dutyOnTime = null;
               if (work.size() > index) {
                   dutyOnTime = work.get(index).getDutyOnTime();
               }
               if (dutyOnTime != null) {
                   // 下班打卡时间小于等于下班时间或者大于等于下一次的上班时间，都不允许打卡
                   if (curStr.compareTo(hms.format(dutyOffTime)) <= 0 
                           || curStr.compareTo(hms.format(dutyOnTime)) >= 0) {
                       map.put("result", ConstantUtil.FAIL);
                       map.put("information", "超出下班打卡时间不允许打卡");
                       map.put("failType", 3);
                       return map;
                   }
                   // 下班时间与下次上班时间间隔的分钟数
                   long time = dutyOnTime.getTime() - hms.parse(timePlan).getTime();
                   minute = time / (1000*60);
               }
           } else {
               // 如果打卡时下班时间是23:59:00,那么需要获取对应当前下班时间的上班时间，如果打卡的时间在此上班时间之前，不允许打卡
               // 获取对应的上班时间
               Date onTimePlan = null;
               for (int i = 0; i < work.size(); i++) {
                  Date dutyOffTime = work.get(i).getDutyOffTime();
                   if (timePlan.equals(hms.format(dutyOffTime))) {
                       onTimePlan = work.get(i).getDutyOnTime();
                       break;
                   }
               }
               // 比较打下班卡时间是否在上班之前，如果是,提示"请在上班区间内打卡"
               long from = onTimePlan.getTime();
               long to = hms.parse(curStr).getTime();
               if (to < from) {
                   map.put("result", ConstantUtil.FAIL);
                   map.put("information", "请在上班区间内打卡");
                   map.put("failType", 3);
                   return map;
               }
           }
       }
       
       //循环attendancesVoList集合，转换为Attendances集合
       Attendances attendances = new Attendances();
       attendances.setAddress(att.getAddress());
       attendances.setAttendanceDate(new Date());
       attendances.setEmployeeId(att.getEmployeeId());
       attendances.setLat("".equals(att.getLat()) ? 0.0 : Double.parseDouble(att.getLat()));
       attendances.setLng("".equals(att.getLng()) ? 0.0 : Double.parseDouble(att.getLng()));
       attendances.setRecordTime(new Date());
       attendances.setCreateAt(new Date());
       // 当前是否是手动考勤：0否1是
       attendances.setIsExcetion(1);
       
       if (attendances.getAttendanceDate() == null || attendances.getRecordTime() == null) {
           map.put("result", ConstantUtil.SUCCESS);
           map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
           return map;
       }
       
       // 判断打卡是否在在责任范围内, 如果是考核员打卡，需要按照配置文件指定的经纬度范围进行，如果是环卫工和检测员根据责任区责任点的经纬度来进行
       // 考核员
       boolean isAt = false;
       if (users.getRoleId() == 3) {
           isAt = isAtPointForCheck(checkLatLng, attendances);
       // 环卫工和检测员
       } else {
           isAt = isAtPoint(workPlansList, attendances, users);
       }
       if (isAt == false) {
           List<Attendances> lstAtt = new ArrayList<Attendances>();
           lstAtt.add(attendances);
           //2.插入轨迹数据，并记录该员工当前位置2016-11-17
           attendancesWatchService.batchAddAttendances(lstAtt); // 插入轨迹记录
           //3.将当天轨迹数据插入到用于地图查询的轨迹表中去,2016-11-17
           attendancesWatchService.batchAddAttendancesForMap(lstAtt); 
           map.put("result", ConstantUtil.FAIL);
           map.put("information", "当前位置不在责任范围内，不允许打卡");
           map.put("failType", 4);
           return map;
       } else {
           attendances.setAtPoint(1);//记录该坐标是否在责任点范围内
           List<Attendances> lstAtt = new ArrayList<Attendances>();
           lstAtt.add(attendances);
           //2.插入轨迹数据，并记录该员工当前位置，是否在责任点范围内,2016-08-30
           attendancesWatchService.batchAddAttendances(lstAtt); // 插入轨迹记录
           //3.将当天轨迹数据插入到用于地图查询的轨迹表中去,2016-09-20
           attendancesWatchService.batchAddAttendancesForMap(lstAtt); 
       }
       
       // 更新考勤表状态
       boolean flag = this.updateWorkStatus(att.getEmployeeId(), timeFlag, timePlan, minute);
       if (flag == false) {
           map.put("result", ConstantUtil.FAIL);
           map.put("information", "打卡失败");
           map.put("failType", 5);
           return map;
       }
       
       // 获取当前的考勤信息
       return this.getCurrentAttendanceWatch(att.getEmployeeId());
   }
   
   /** 
    * 更新考勤表状态 2016-11-01
    * @param employeeId 员工ID
    * @param timeFlag 时间段标识（1.上班 2.下班）
    * @param timePlan 具体时间段（如：06:00:00）
    * @param minute 相差分钟数（针对当前的下班时间如果存在下次的上班时间，这两个相差分钟数）
    * @param onTimePlan 针对下班时间是23:59:00的员工，获取对应的上班时间
    */
    public boolean updateWorkStatus(int employeeId, int timeFlag, String timePlan, long minute){
       
       //当前时间（年月日 时分秒）
       Date nowDate = new Date();
       //当前比较时间 （时分秒）
       Date compareDate = CalendarUntil.timeFormat(nowDate);
       
       //1.根据【上班前推时间】和【 下班推迟时间】查询出应该判断考勤上下班状态的排班计划开始与结束时间
       Map<String,Object> timeMap = new HashMap<String, Object>();
       timeMap.put("at_point_time_for_sb", AT_POINT_TIME_FOR_SB);
       timeMap.put("compareDate", compareDate);
       timeMap.put("nowDate", nowDate);
       timeMap.put("employeeId", employeeId);
       timeMap.put("timeFlag", timeFlag);
       timeMap.put("timePlan", timePlan);
       // 由于垃圾清运人员（垃圾清运人员是指下班时间是23:59:00）随时可以下班，所以要单独取出考勤记录
       if (timeFlag == 2 && timePlan.equals("23:59:00")) {
           timeMap.put("duty_ontime_before", 0);
           timeMap.put("duty_off_time_after", 0);
       // 其它人员的考勤记录取得
       } else {
           timeMap.put("duty_ontime_before", DUTY_ONTIME_BEFORE);
           // 如果是下班时间，并且存在下班时间与下次上班时间，设置在此范围内都可以手动打卡
           timeMap.put("duty_off_time_after", minute);  
       }
       /*
        * 1.获取所有的应该改变上下班状态考勤记录 
        */
       List<AttendanceForPlans> lstAttendance = attendanceForPlansWatchService.selectAllAttendances(timeMap);
       if(lstAttendance.size() == 0){//如果考勤记录不存在，退出该方法
           return false;
       }
       
       /*
        * 2.将考勤记录分类：当前时间在上班前，下班后；
        */
       List<Integer> beforeIds = new ArrayList<Integer>();//记录当前时间在【上班时间前30分钟，上班时间）段内的考勤记录Id
       List<Integer> afterIdsOfSB = new ArrayList<Integer>();//记录当前时间在【下班时间,下班后30分钟 段内的考勤记录Id，下班状态应该更新为下班的
       
       //循环排班计划，将排版计划分类处理
       for(AttendanceForPlans attendancePlans : lstAttendance){
           Date firstDpOn = attendancePlans.getDutyOnTime();   // 获取排班计划上班时间
           Date firstDpOff = attendancePlans.getDutyOffTime();// 获取排班计划下班时间 
           int id = attendancePlans.getId();//主键ID
           // 由于垃圾清运人员（垃圾清运人员是指下班时间是23:59:00）随时可以下班，所以要单独取出考勤记录
           if (timeFlag == 2 && timePlan.equals("23:59:00")) {
               Date firstDpOff30Temp = CalendarUntil.afterHowManyMin(firstDpOff, 0);//获取排班计划下班时间后推30分钟时间
               if (compareDate.compareTo(firstDpOff30Temp) <= 0) {
                   afterIdsOfSB.add(id);
               }
           } else {
               Date firstDpOn30Temp = CalendarUntil.beforeHowManyMin(firstDpOn, DUTY_ONTIME_BEFORE);//获取排班计划上班时间前推60分钟时间
               Date firstDpOff30Temp = CalendarUntil.afterHowManyMin(firstDpOff, DUTY_OFF_TIME_AFTER);//获取排班计划下班时间后推30分钟时间
               if(firstDpOn30Temp.compareTo(compareDate) <= 0 && compareDate.compareTo(firstDpOn) <= 0 ){//在【上班时间前60分钟，上班时间】
                   if (attendancePlans.getDutyOnStatus() == null || "".equals(attendancePlans.getDutyOnStatus())){//如果上班状态为空，将在责任点出现过，或者移动距离大于200米的考勤记录设置为上班
                       beforeIds.add(id);
                   }
               } else if (firstDpOff.compareTo(compareDate) < 0 && compareDate.compareTo(firstDpOff30Temp) <= 0){//在（下班时间，下班后30】
                   afterIdsOfSB.add(id);
               }
           }
       }
       
       /*
        * 3.更新上下班状态
        */
       /*3.1更新上班状态为上班状态的考勤记录:考勤上班时间设置为【上班时间前60分钟，上班时间】时间段内，第一次在责任点的时间，2016-10-26*/
       /*3.1.1 获得在【上班前60分钟，当前时间】时间内，且在责任点的考勤记录*/
       if(beforeIds.size() > 0){
           timeMap.put("flag", 1);//表示查询【上班前60分钟，当前时间】内的
           timeMap.put("ids", beforeIds.toString().substring(1, beforeIds.toString().length()-1));
           List<AttendanceForPlans> beforList = attendanceForPlansWatchService.selectAtPointAttenadanceList(timeMap);//获取在责任点的考勤记录以及在责任点的最早时间,2016-10-26
           
           // 将在责任点的考勤记录的上班状态更新为上班
           if(beforList.size() > 0){
               for(AttendanceForPlans attendanceForPlans : beforList){
                   attendanceForPlans.setUpdateTime(new Date());
                   attendanceForPlans.setLastRecordTime(null);
                   attendanceForPlans.setDutyOnStatus(shangban);
                   attendanceForPlans.setDutyOffStatus(null);
               }
               attendanceForPlansWatchService.batchUpdateOnStatusByList(beforList);//批量更新考勤记录的上班状态，2016-10-26
           }
       }
       
       // 更新下班状态为下班状态的考勤记录
       if(afterIdsOfSB.size() > 0){
           timeMap.put("ids", afterIdsOfSB.toString().substring(1, afterIdsOfSB.toString().length()-1));
           timeMap.put("getOnStatus", null);
           timeMap.put("getOffStatus", xiaban);
           attendanceForPlansWatchService.updateOnOrOffStatusByMap(timeMap);
           //计算考勤下班状态为下班状态的员工，在工作期间内的移动距离
           setAttendanceForPlansForMOV(afterIdsOfSB);
       }
       
       return true;
   }   
   
    /**
	 * 计算考勤下班状态为下班状态的员工，在工作期间内的移动距离并保存到考勤记录的字段中，2017-05-05
	 * @param afterIdsOfSB   应该计算上班期间移动距离的考勤ID集合
	 */
	public void setAttendanceForPlansForMOV(List<Integer> afterIdsOfSB) {
		//1.获取应该更新上班期间移动距离的考勤记录集合
		Map<String, Object> selectMap = new HashMap<String, Object>();
		selectMap.put("ids", afterIdsOfSB.toString().substring(1, afterIdsOfSB.toString().length()-1));
		List<AttendanceForPlans> attPlansList = attendanceForPlansWatchService.getAttendanceForPlansListByIdsForWatch(selectMap);//根据Ids获取人、上下班时间、日期唯一的考勤记录列表，2017-05-05
		
		//2.循环集合，计算每条考勤记录对应的移动记录
		for(AttendanceForPlans att : attPlansList){
			//2.1 根据考勤记录中的员工ID，上班时间，下班时间，日期获取按采集时间升序排列的在责任点的轨迹集合，2017-05-05 
			List<Attendances> attendancesList = attendancesWatchService.getAttendancesListByAttPlansBeanForWatch(att);//根据考勤记录中的员工ID，上班时间，下班时间，日期获取按采集时间升序排列的在责任点的轨迹集合，2017-05-05 
			double distance = getMobileDistance(attendancesList);//获取上班期间移动距离
			att.setMobileDistance(distance);
		}
		
		//3.批量更新考勤记录中的移动距离值
		if(attPlansList.size() > 0){
			attendanceForPlansWatchService.batchUpdateMobileDistanceByListForWatch(attPlansList);//批量更新考勤记录中上下班时间内移动的距离，2017-05-05
		}
	}
	
	/**
	 * 每个班次时间段之内，在责任区出现的点按时间顺序进行连线，所有连线距离加和形成本班次总的移动距离，已千米为单位，保留两位小数,2017-05-05
	 * @param attendancesList
	 * @return
	 */
	public double getMobileDistance(List<Attendances> attendancesList) {
		double distance = 0;
		double unit = 1000;//1千米 = 1000米
		int scale = 2;//保留两位小数
		for(int i = 0; i < attendancesList.size() - 2; i++){
			distance +=  GetDistance.getLongDistance(attendancesList.get(i).getLng(), attendancesList.get(i).getLat(), attendancesList.get(i + 1).getLng(),attendancesList.get(i + 1).getLat());
		}
		BigDecimal b1 = new BigDecimal(distance);  
		BigDecimal b2 = new BigDecimal(unit);  
		distance = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		return distance;
	}
    
   /** 判断打卡是否在在责任范围内(环卫工和检测员)
    * @param dutyPlansList
    * @param attendances
    * @return
    */
   public boolean isAtPoint(List<WorkPlans> dutyPlansList, Attendances attendances, Users users){
     //1.循环每条考勤轨迹，判断每条轨迹在是否在责任点
       List<Map<String, Double>> pointListByRound = new ArrayList<Map<String,Double>>();//保存以环卫工当前位置为圆点，每五米取圆上360个点，一直取到100米
       
       // 判断员工当前位置是否在责任点的范围
       boolean isAtPoint = false;//表示是否在责任点范围
       for (WorkPlans dutyPlans : dutyPlansList) {
           // 判断该人是否出勤范围
           List<DutyPointGather> dutyPointinfo = dutyPointGatherWatchService.getDutyPointInfoById(dutyPlans.getPointId(), dutyPlans.getAreaId());
           // 存放该责任点采集点的纬度和经度
           List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
           boolean b = false;//标识是否在责任点的范围内
           for (DutyPointGather dutyPointGather : dutyPointinfo) {//循环判断该环卫工所在位置与责任点采集点的经纬度的距离是否小于100米
               double distance = GetDistance.getLongDistance(attendances.getLng(), attendances.getLat(), dutyPointGather.getBorderpointLng(), dutyPointGather.getBorderpointLat());
               if (distance < 10) {//若小于100米表示该员工在责任点范围内
                   b = true;
                   break;
               } else {//否则将责任点采集点的经纬度分别存入对应的集合，用于后面构建多边形
                   Point2D.Double dutyPoint = new Point2D.Double(dutyPointGather.getBorderpointLng(), dutyPointGather.getBorderpointLat());
                   pts.add(dutyPoint);
               }
           }
           
           if (b) {
               isAtPoint = true;
               break;
           }
           if(pts.size() == 0){
               isAtPoint = false;
               break;
           }
           
           //根据环卫工当前的经纬度，获取距离环卫工位置为100米的经纬度集合，这里取360个 + 再加环卫工当前位置
           List<Map<String, Double>> pointList = new ArrayList<Map<String,Double>>();
           Map<String, Double> map = new HashMap<String, Double>();
           map.put("lat", attendances.getLat());
           map.put("lng", attendances.getLng());
           pointList.add(map);//添加环卫工当前位置
           if(pointListByRound.size() == 0){//若是第一次取圆上的点
               for(int i = 1;i <= 2; i++){
                   pointListByRound.addAll(GetDistance.getPointListByPoint(attendances.getLat(), attendances.getLng(), i*5, 360));//添加距离环卫工位置为100米的360个经纬度
                   
               }
           }
           pointList.addAll(pointListByRound);
           
           boolean isPointInPolygon = false;
           //循环纬度集合，判断是否有在责任点范围内的点
           for(Map<String, Double> m : pointList){
               Point2D.Double point = new Point2D.Double(m.get("lng"), m.get("lat"));
               isPointInPolygon = GetDistance.isPointInPolygon(point, pts);//GetDistance.isPointInPolygon(m.get("lat"), m.get("lng"), polygonXA, polygonYA);
               if (isPointInPolygon) {
                   // 如果该环卫工所在位置在责任点范围内
                   break;
               }
           }
           if(isPointInPolygon){
               isAtPoint = true;
               break;
           }
       }
       return isAtPoint;
   }
   
   /** 判断打卡是否在在责任范围内(考核员)
    * @param dutyPlansList
    * @param attendances
    * @return
    */
   public boolean isAtPointForCheck(CheckLatLng checkLatLng, Attendances attendances){
       // 读取配置文件中的经纬度坐标
       List<DutyPointGather> dutyPointInfo = new ArrayList<DutyPointGather>();
       DutyPointGather point1 = new DutyPointGather();
       point1.setBorderpointLat(Double.parseDouble(checkLatLng.getLat1()));
       point1.setBorderpointLng(Double.parseDouble(checkLatLng.getLng1()));
       DutyPointGather point2 = new DutyPointGather();
       point2.setBorderpointLat(Double.parseDouble(checkLatLng.getLat2()));
       point2.setBorderpointLng(Double.parseDouble(checkLatLng.getLng2()));
       DutyPointGather point3 = new DutyPointGather();
       point3.setBorderpointLat(Double.parseDouble(checkLatLng.getLat3()));
       point3.setBorderpointLng(Double.parseDouble(checkLatLng.getLng3()));
       DutyPointGather point4 = new DutyPointGather();
       point4.setBorderpointLat(Double.parseDouble(checkLatLng.getLat4()));
       point4.setBorderpointLng(Double.parseDouble(checkLatLng.getLng4()));
       dutyPointInfo.add(point1);
       dutyPointInfo.add(point2);
       dutyPointInfo.add(point3);
       dutyPointInfo.add(point4);
       
       // 1.循环每条考勤轨迹，判断每条轨迹在是否在责任点
       List<Map<String, Double>> pointListByRound = new ArrayList<Map<String,Double>>();//保存以环卫工当前位置为圆点，每五米取圆上360个点，一直取到100米
       // 表示是否在责任点范围
       boolean isAtPoint = false;
       // 存放该责任点采集点的纬度和经度
       List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
       boolean b = false;//标识是否在责任点的范围内
       for (DutyPointGather dutyPointGather : dutyPointInfo) {//循环判断该环卫工所在位置与责任点采集点的经纬度的距离是否小于100米
           double distance = GetDistance.getLongDistance(attendances.getLng(), attendances.getLat(), dutyPointGather.getBorderpointLng(), dutyPointGather.getBorderpointLat());
           if (distance < 10) {//若小于100米表示该员工在责任点范围内
               b = true;
               break;
           } else {//否则将责任点采集点的经纬度分别存入对应的集合，用于后面构建多边形
               Point2D.Double dutyPoint = new Point2D.Double(dutyPointGather.getBorderpointLng(), dutyPointGather.getBorderpointLat());
               pts.add(dutyPoint);
           }
       }
       
       if (b) {
           return true;
       }
       if(pts.size() == 0){
           return false;
       }
       
       //根据环卫工当前的经纬度，获取距离环卫工位置为100米的经纬度集合，这里取360个 + 再加环卫工当前位置
       List<Map<String, Double>> pointList = new ArrayList<Map<String,Double>>();
       Map<String, Double> map = new HashMap<String, Double>();
       map.put("lat", attendances.getLat());
       map.put("lng", attendances.getLng());
       pointList.add(map);//添加环卫工当前位置
       if(pointListByRound.size() == 0){//若是第一次取圆上的点
           for(int i = 1;i <= 2; i++){
               pointListByRound.addAll(GetDistance.getPointListByPoint(attendances.getLat(), attendances.getLng(), i*5, 360));//添加距离环卫工位置为100米的360个经纬度
           }
       }
       pointList.addAll(pointListByRound);
       
       boolean isPointInPolygon = false;
       //循环纬度集合，判断是否有在责任点范围内的点
       for(Map<String, Double> m : pointList){
           Point2D.Double point = new Point2D.Double(m.get("lng"), m.get("lat"));
           isPointInPolygon = GetDistance.isPointInPolygon(point, pts);//GetDistance.isPointInPolygon(m.get("lat"), m.get("lng"), polygonXA, polygonYA);
           if (isPointInPolygon) {
               // 如果该环卫工所在位置在责任点范围内
               break;
           }
       }
       if(isPointInPolygon){
           isAtPoint = true;
       }
       return isAtPoint;
       
   }    
    // 时间格式化
    public Date timeFormat(Date currDate) {
        String d = currDate.getHours() + ":" + currDate.getMinutes() + ":" + currDate.getSeconds();
        if (d != null) {
            Date date = null;
            try {
                date = hms.parse(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }
    
    /**
     * 将时间往后推？分钟
     * @param d  要推后的时间
     * @param minute 推后的分钟
     * @return
     */
    public Date afterHowManyMin(Date d, int minute) {

        if (d != null) {

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(d);

            calendar.add(Calendar.MINUTE, minute);

            return calendar.getTime();
        }

        return d;
    }
}
