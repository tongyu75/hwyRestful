package com.czz.hwy.service.usermanagement.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.bean.user.app.DutyRecodeApp;

/**
 * 生成考勤结果的service
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
public interface AttendanceForPlansAppService {

    /**
     * 考勤查岗 考核员（APP端）
     */
	public List<Map<String, Object>> getAllCityWorkAttForApp(Integer employeeId);

    /**
     * 根据员工ID查询昨日和今日考勤记录
     * @return
     */
    public List<Map<String, Object>> getYesAndTodAttendanceForApp(Map<String, Object> selectMap);
    
    /**
     * 自动生成考勤记录 2016-12-09
     */
    public int batchAddAttendanceForPlansForApp();
    
    /**
     * 根据员工ID，查询员工的上下班时间用于定时生成考勤状态,2016-11-28
     * @param employeeId
     * @return
     */
    public List<AttendanceForPlansApp> getAttendanceForPlansByEmployeeId(Integer employeeId);  
    
    /**
     * 针对代代班人不再上与所代替的请假人的班次重叠的班次，删除代班人的考勤记录 2016-12-06
     * @return
     */
    public void deleteAttendanceForCoverWork(AttendanceForPlansApp bean);
    
    /**
     * 代班人是检测员的时候,查询代班人的实际上下班时间，并且根据此上下班时间生成代班的时间,2016-12-09
     * @param employeeId
     * @return
     */
    public List<AttendanceForPlansApp> getAttendanceForPlansForCreateCoverWork(Integer employeeId);
    
    /**
     * 当存在请假和任务的时候，更新考勤表的上下班状态为请假和任务 2016-11-28
     * @return
     */
    public int updateAttendancePlansForLeaveAndTask(AttendanceForPlansApp attendanceForPlans);
    
    /**
     * 更新当天的没有手机的员工的上下班状态为正常，2016-10-11
     */
    public int updateNoMobileStatus(String status);
    
    /**
     * 根据上下班时间段，查询当前在考勤表中代班人的记录是否存在,如果存在则将is_coverwork状态置为1，否则需要重新插入一条代班的考勤记录2016-11-28
     * @param employeeId
     * @return
     */
    public int getExsitsCoverWorkAtt(AttendanceForPlansApp attendanceForPlans);      
    
    /**
     * 考勤表中指定上下班时间段的代班人记录存在，更新is_coverwork状态置为1 2016-11-28
     * @return
     */
    public int updateAttendancePlansForCoverWork(AttendanceForPlansApp attendanceForPlans);
    
    /**
     * 考勤表中指定上下班时间段的代班人记录不存在，重新插入一条代班的考勤记录 2016-11-28
     * @return
     */
    public int insertAttendancePlansForCoverWork(AttendanceForPlansApp attendanceForPlans);        

    /**
     * 每天凌晨零点一分自动生成考勤记录2016-12-09
     * @return
     */    
    public void autoGenAttendanceForPlans();
    
    /**
     * 根据员工ID，查询今日员工出勤的责任点,2016-12-10
     * @return
     */
    public List<Map<String, Object>> getTodayAttendancePointNameForApp(Map<String, Object> selectMap);

    /**
     * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合，2016-12-29
     * @param workPlansList
     * @return
     */
	public List<DutyRecodeApp> getLastBanciAttendanceForPlansByListApp(List<AttendanceForPlansApp> workPlansList);

	/**
	 * 根据对下班状态的不同出来，对下班状态和下班时间进行相应处理。2016-12-30
	 * @param attendanceForPlansApp
	 * @return
	 */
	public int updateAttendancePlansForXBApp(AttendanceForPlansApp attendanceForPlansApp);
	
    /**
     * 当前时间是否是一天中最晚下班之后的时间。2017-01-09
     * @param attendanceForPlansApp
     * @return
     */
    public AttendanceForPlansApp getAllCityWorkAttLastTimeForApp(Integer employeeId);

    /**
     * 修改某个员工比当前时间晚的下一个班次的考勤记录，2017-03-16
     * @param attendanceForPlansApp
     */
	public int updateRegisterOrLogoutStatusApp(AttendanceForPlansApp attendanceForPlansApp);

	/**
	 * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合(含移动距离)，2017-04-21
	 * @param workPlansList
	 * @return
	 */
	public List<DutyRecodeApp> getLastBanciAttendanceForPlansByListAppForMOV(List<AttendanceForPlansApp> workPlansList);
}
