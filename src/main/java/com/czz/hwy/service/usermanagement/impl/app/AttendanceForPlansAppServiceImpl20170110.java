package com.czz.hwy.service.usermanagement.impl.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.manager.AdminLogs;
import com.czz.hwy.bean.manager.app.CoverWorkApp;
import com.czz.hwy.bean.manager.app.LeaveApp;
import com.czz.hwy.bean.mission.app.TaskInformationApp;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.bean.user.app.DutyRecodeApp;
import com.czz.hwy.dao.user.app.AttendanceForPlansAppDao;
import com.czz.hwy.service.manager.AdminLogsService;
import com.czz.hwy.service.manager.app.CoverWorkAppService;
import com.czz.hwy.service.manager.app.LeaveAppService;
import com.czz.hwy.service.mission.app.TaskInformationAppService;
import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService;
import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService20170111;
import com.czz.hwy.utils.CalendarUntil;
import com.czz.hwy.utils.ConstantUtil;

/**
 * 生成考勤结果的service接口实现类
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
@Service
@Transactional
public class AttendanceForPlansAppServiceImpl20170110 implements AttendanceForPlansAppService20170111 {

	@Autowired
	private AttendanceForPlansAppDao attendanceForPlansDao;
    
    @Autowired
    private CoverWorkAppService coverWorkAppService;
    
    @Autowired
    private LeaveAppService leaveAppService;
    
    @Autowired
    private TaskInformationAppService taskInformationAppService;
    
    @Autowired
    private AdminLogsService adminLogsService;
	
    /**
     * 考勤查岗 考核员或督察员（APP端）
     */
    public List<Map<String, Object>> getAllCityWorkAttForApp(Integer employeeId) {
        
        // 当前时间是否是一天中最晚下班之后的时间，如果是就查询最晚下班时间之前的考勤状态，如果不是则查询当前时间的考勤状态
        AttendanceForPlansApp att = attendanceForPlansDao.getInfo("getAllCityWorkAttLastTimeForApp");
        // 是
        if (att != null) {
            return attendanceForPlansDao.getInfoListMapByInt("getAllCityWorkAttLastForApp", employeeId);
        // 不是    
        } else {
            return attendanceForPlansDao.getInfoListMapByInt("getAllCityWorkAttForApp", employeeId);
        }
        
    }
    
    /**
     * 根据员工ID查询昨日和今日考勤记录
     * @return
     */
    public List<Map<String, Object>> getYesAndTodAttendanceForApp(Map<String, Object> selectMap) {
        return attendanceForPlansDao.getInfoListMapByMap("getYesAndTodAttendanceForApp", selectMap);
    }
    
    /**
     * 自动生成考勤记录 2016-12-09
     */
    public int batchAddAttendanceForPlansForApp() {
        return attendanceForPlansDao.insertInfo("batchAddAttendanceForPlansForApp");
    }
    
    /**
     * 根据员工ID，查询员工的上下班时间用于定时生成考勤状态,2016-11-28
     * @param employeeId
     * @return
     */
    public List<AttendanceForPlansApp> getAttendanceForPlansByEmployeeId(Integer employeeId) {
        return attendanceForPlansDao.getInfoListByIntegerId("getAttendanceForPlansByEmployeeIdForApp", employeeId);
    }
    
    /**
     * 针对代代班人不再上与所代替的请假人的班次重叠的班次，删除代班人的考勤记录 2016-12-06
     * @return
     */
    public void deleteAttendanceForCoverWork(AttendanceForPlansApp bean) {
        attendanceForPlansDao.deleteInfoByBean("deleteAttendanceForCoverWorkForApp", bean);
    }
    
    /**
     * 代班人是检测员的时候,查询代班人的实际上下班时间，并且根据此上下班时间生成代班的时间,2016-12-09
     * @param employeeId
     * @return
     */
    public List<AttendanceForPlansApp> getAttendanceForPlansForCreateCoverWork(Integer employeeId) {
        return attendanceForPlansDao.getInfoListByIntegerId("getAttendanceForPlansForCreateCoverWorkForApp", employeeId);
    }
    
    /**
     * 当存在请假和任务的时候，更新考勤表的上下班状态为请假和任务 2016-11-28
     * @return
     */
    public int updateAttendancePlansForLeaveAndTask(AttendanceForPlansApp attendanceForPlans) {
        return attendanceForPlansDao.updateInfoByBean("updateAttendancePlansForLeaveAndTaskForApp", attendanceForPlans);
    }    
    
    /**
     * 更新当天的没有手机的环卫工的上下班状态为正常，2016-10-11
     */
    public int updateNoMobileStatus(String status) {
        return attendanceForPlansDao.updateInfoByString("updateNoMobileStatusApp", status);
    }    
    
    /**
     * 根据上下班时间段，查询当前在考勤表中代班人的记录是否存在,如果存在则将is_coverwork状态置为1，否则需要重新插入一条代班的考勤记录2016-11-28
     * @return
     */
    public int getExsitsCoverWorkAtt(AttendanceForPlansApp attendanceForPlans) {
        return attendanceForPlansDao.getInfoCount("getExsitsCoverWorkAttApp", attendanceForPlans);
    }
    
    /**
     * 考勤表中指定上下班时间段的代班人记录存在，更新is_coverwork状态置为1 2016-11-28
     * @return
     */
    public int updateAttendancePlansForCoverWork(AttendanceForPlansApp attendanceForPlans) {
        return attendanceForPlansDao.updateInfoByBean("updateAttendancePlansForCoverWorkApp", attendanceForPlans);
    }
    
    /**
     * 考勤表中指定上下班时间段的代班人记录不存在，重新插入一条代班的考勤记录 2016-11-28
     * @return
     */
    public int insertAttendancePlansForCoverWork(AttendanceForPlansApp attendanceForPlans) {
        return attendanceForPlansDao.insertInfo("insertAttendancePlansForCoverWorkApp", attendanceForPlans);
    }    
    
    /**
     * 根据员工ID，查询今日员工出勤的责任点,2016-12-10
     * @return
     */
    public List<Map<String, Object>> getTodayAttendancePointNameForApp(Map<String, Object> selectMap) {
        return attendanceForPlansDao.getInfoListMapByMap("getTodayAttendancePointNameForApp", selectMap);
        
    }
    
    /**
     * 每天凌晨零点一分自动生成考勤记录2016-12-09
     * @return
     */    
    public void autoGenAttendanceForPlans() {
        Date beforeDate = new Date();
        System.out.println("开始生成考勤记录时间：" + beforeDate);
        System.out.println("每天自动生成考勤记录");
        //1.自动生成考勤记录
        int count = this.batchAddAttendanceForPlansForApp();
        
        // 查询代班信息,如果存在代班人需要生成代表人的考勤记录
        List<CoverWorkApp> lstCoverWork = coverWorkAppService.getCoverWorkForAttendace();
        for (CoverWorkApp coverWork : lstCoverWork) {
            // 代班开始时间
            Date coverFromTime = coverWork.getCoverFromTime();
            // 代班结束时间
            Date coverToTime = coverWork.getCoverToTime();
            // 代班人员工ID
            int coverId = coverWork.getCoverId();
            // 标识代班人是否继续上原有班次
            int isOldPlans = coverWork.getIsOldPlans();
            // 根据代班信息中的请假人ID，查询出请假人对应的计划上下班时间
            List<AttendanceForPlansApp> lstAttForPlans = this.getAttendanceForPlansByEmployeeId(coverWork.getLeaveId());
            for (AttendanceForPlansApp att : lstAttForPlans) {
                // 请假人计划上班时间
                Date dutyOnTime = CalendarUntil.dateTimeFormat(att.getRecordDate(), att.getDutyOnTime());
                // 请假人计划上班时间
                Date dutyOffTime = CalendarUntil.dateTimeFormat(att.getRecordDate(), att.getDutyOffTime());
                // 请假人角色ID
                int roleId = att.getRoleId();
                // 请假人责任区ID
                int dutyAreaId = att.getDutyAreaId();
                
                // 如果代班开始时间 <= 请假人计划上班时间 <= 代班结束时间，那么整个上班时间段将由代班人进行代班
                if (coverFromTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(coverToTime) <= 0) {
                    // 生成代班人的考勤记录（排除掉与已经生成的正常考勤记录上班时间相同的集合）
                    createCoverWorkAtt(isOldPlans, coverId, dutyAreaId, dutyOnTime, dutyOffTime, roleId, att);
                // 如果代班开始时间 <= 请假人计划下班时间 <= 代班结束时间，那么整个上班时间段将由代班人进行代班。
                } else if (coverFromTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(coverToTime) <= 0) {
                    // 生成代班人的考勤记录（排除掉与已经生成的正常考勤记录上班时间相同的集合）
                    createCoverWorkAtt(isOldPlans, coverId, dutyAreaId, dutyOnTime, dutyOffTime, roleId, att);
                // 如果代班开始时间 <= 请假人计划上班时间，且 请假人计划下班时间 <= 代班结束时间，那个整个上班时间段将由代班人进行代班。
                } else if (coverFromTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(coverToTime) <= 0) {
                    // 生成代班人的考勤记录（排除掉与已经生成的正常考勤记录上班时间相同的集合）
                    createCoverWorkAtt(isOldPlans, coverId, dutyAreaId, dutyOnTime, dutyOffTime, roleId, att);
                // 如果请假人计划上班时间<=代班开始时间  ，且 代班结束时间<=请假人计划下班时间  ，那个整个上班时间段将由代班人进行代班。
                } else if (dutyOnTime.compareTo(coverFromTime) <= 0 && coverToTime.compareTo(dutyOffTime) <= 0) {
                    // 生成代班人的考勤记录（排除掉与已经生成的正常考勤记录上班时间相同的集合）
                    createCoverWorkAtt(isOldPlans, coverId, dutyAreaId, dutyOnTime, dutyOffTime, roleId, att);
                }
            }
        }
        
        // 查询请假信息,如果存在请假人更新考勤记录为的上下班状态为请假
        List<LeaveApp> lstLeave = leaveAppService.getLeaveForAttendance();
        for (LeaveApp leave : lstLeave) {
            // 请假开始时间
            Date leaveFromTime = leave.getLeaveFromTime();
            // 请假结束时间
            Date leaveToTime = leave.getLeaveToTime();
            // 请假人ID
            int applyId = leave.getApplyId();
            // 根据请假人ID，查询出请假人对应的计划上下班时间
            List<AttendanceForPlansApp> lstAttForPlans = this.getAttendanceForPlansByEmployeeId(applyId);
            for (AttendanceForPlansApp att : lstAttForPlans) {
                // 请假人计划上班时间
                Date dutyOnTime = CalendarUntil.dateTimeFormat(att.getRecordDate(), att.getDutyOnTime());
                // 请假人计划上班时间
                Date dutyOffTime = CalendarUntil.dateTimeFormat(att.getRecordDate(), att.getDutyOffTime());
                // 用于更新的bean
                AttendanceForPlansApp bean = new AttendanceForPlansApp();
                // ID
                bean.setId(att.getId());
                // 上班状态为请假
                bean.setDutyOnStatus(ConstantUtil.QINGJIA);
                // 下班状态为请假
                bean.setDutyOffStatus(ConstantUtil.QINGJIA);
                // 如果请假开始时间 <= 请假人计划上班时间 <= 请假结束时间，那么整个上班时间段的上下班状态设置为请假
                if (leaveFromTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(leaveToTime) <= 0) {
                    // 更新考勤的上下班状态为请假
                    this.updateAttendancePlansForLeaveAndTask(bean);
                // 如果请假开始时间 <= 请假人计划下班时间 <= 请假结束时间，那么整个上班时间段的上下班状态设置为请假
                } else if (leaveFromTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(leaveToTime) <= 0) {
                    // 更新考勤的上下班状态为请假
                    this.updateAttendancePlansForLeaveAndTask(bean);
                // 如果请假开始时间 <= 请假人计划上班时间，且 请假人计划下班时间 <= 请假结束时间，那么整个上班时间段的上下班状态设置为请假
                } else if (leaveFromTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(leaveToTime) <= 0) {
                    // 更新考勤的上下班状态为请假
                    this.updateAttendancePlansForLeaveAndTask(bean);
                // 如果请假人计划上班时间<=请假开始时间  ，且 请假结束时间<=请假人计划下班时间  ，那么整个上班时间段的上下班状态设置为请假
                } else if (dutyOnTime.compareTo(leaveFromTime) <= 0 && leaveToTime.compareTo(dutyOffTime) <= 0) {
                    // 更新考勤的上下班状态为请假
                    this.updateAttendancePlansForLeaveAndTask(bean);
                }
            }
        }
        
        // 查询任务信息,如果存在任务人更新考勤记录为的上下班状态为任务
        List<TaskInformationApp> lstTask = taskInformationAppService.getTaskForAttendance();
        for (TaskInformationApp task : lstTask) {
            // 任务开始时间
            Date taskStartTime = task.getTaskStart();
            // 任务结束时间
            Date taskEndTime = task.getTaskEnd();
            // 任务人ID列表
            String taskdutypeopleId = task.getTaskdutypeopleId();
            if (!StringUtils.isEmpty(taskdutypeopleId)) {
                // 用,分隔获取任务人列表
                String[] peopleId = taskdutypeopleId.split(",");
                for (String empId : peopleId) {
                    // 根据任务人ID，查询出任务人对应的计划上下班时间
                    List<AttendanceForPlansApp> lstAttForPlans = this.getAttendanceForPlansByEmployeeId(Integer.parseInt(empId));
                    for (AttendanceForPlansApp att : lstAttForPlans) {
                        // 如果当前考勤状态为空，说明不存在请假，可以更新任务状态，否则已经存在请假，不需要再更新为任务了
                        if(StringUtils.isEmpty(att.getDutyOnStatus())) {
                            // 任务人计划上班时间
                            Date dutyOnTime = CalendarUntil.dateTimeFormat(att.getRecordDate(), att.getDutyOnTime());
                            // 任务人计划上班时间
                            Date dutyOffTime = CalendarUntil.dateTimeFormat(att.getRecordDate(), att.getDutyOffTime());
                            // 用于更新的bean
                            AttendanceForPlansApp bean = new AttendanceForPlansApp();
                            // ID
                            bean.setId(att.getId());
                            // 上班状态为请假
                            bean.setDutyOnStatus(ConstantUtil.RENWU);
                            // 下班状态为请假
                            bean.setDutyOffStatus(ConstantUtil.RENWU);
                            // 如果任务开始时间 <= 任务人计划上班时间 <= 任务结束时间，那么整个上班时间段的上下班状态设置为任务
                            if (taskStartTime.compareTo(dutyOnTime) <= 0 && dutyOnTime.compareTo(taskEndTime) <= 0) {
                                // 更新考勤的上下班状态为请假
                                this.updateAttendancePlansForLeaveAndTask(bean);
                                // 如果任务开始时间 <= 任务人计划下班时间 <= 任务结束时间，那么整个上班时间段的上下班状态设置为任务
                            } else if (taskStartTime.compareTo(dutyOffTime) <= 0 && dutyOffTime.compareTo(taskEndTime) <= 0) {
                                // 更新考勤的上下班状态为请假
                                this.updateAttendancePlansForLeaveAndTask(bean);
                                // 如果任务开始时间 <= 任务人计划上班时间，且 任务人计划下班时间 <= 任务结束时间，那么整个上班时间段的上下班状态设置为任务
                            } else if (taskStartTime.compareTo(dutyOnTime) <= 0 && dutyOffTime.compareTo(taskEndTime) <= 0) {
                                // 更新考勤的上下班状态为请假
                                this.updateAttendancePlansForLeaveAndTask(bean);
                                // 如果任务人计划上班时间<=任务开始时间  ，且 任务结束时间<=任务人计划下班时间  ，那么整个上班时间段的上下班状态设置为任务
                            } else if (dutyOnTime.compareTo(taskStartTime) <= 0 && taskEndTime.compareTo(dutyOffTime) <= 0) {
                                // 更新考勤的上下班状态为请假
                                this.updateAttendancePlansForLeaveAndTask(bean);
                            }
                        }
                    }
                }
            }
        }
        
        AdminLogs adminlogs = new AdminLogs();
        adminlogs.setCreateAt(new Date());
        adminlogs.setEmployeeId(2015101501);
        adminlogs.setStatus("1");
        String content = "";
        
        if (count > 0) {//成功
            content = "每天凌晨零点一分执行自动生成考勤记录定时任务,定时任务执行成功。(218服务器)";
            //2.更新当天的没有手机的员工的上下班状态为正常
            this.updateNoMobileStatus(ConstantUtil.WUSEBEI);//更新当天的没有手机的员工的上下班状态为正常，2016-10-11
        }else {//失败
            content = "每天凌晨零点一分执行自动生成考勤记录定时任务,定时任务执行失败。(218服务器)";
        }
        //3. 记录系统日期
        adminlogs.setContent(content);
        adminLogsService.insertAdminLogs(adminlogs);//插入系统日志
        
        Date afterDate = new Date();
        System.out.println("结束生成考勤记录时间：" + afterDate);
        System.out.println("用时：" + (afterDate.getTime() - beforeDate.getTime()) + "毫秒");
    }
    
    
    
    /**
     * 生成代班人考勤信息
     * @param coverId 代班人ID
     * @param dutyOnTime 上班时间
     * @param dutyOffTime 下班时间
     * @param dutyAreaId 责任区ID 
     * @param roleId 角色ID
     */
    public void createCoverWorkAtt(int isOldPlans, int coverId, int dutyAreaId, Date dutyOnTime, Date dutyOffTime, 
            int roleId, AttendanceForPlansApp attendance){
        // 当值为2时，表示代班人不再上与所代替的请假人的班次重叠的班次,即删除代班人的考勤信息
        if (isOldPlans == 2) {
            // 删除代班人考勤信息
            AttendanceForPlansApp bean = new AttendanceForPlansApp();
            bean.setEmployeeId(coverId);
            bean.setDutyOnTime(dutyOnTime);
            bean.setDutyOffTime(dutyOffTime);
            bean.setDutyAreaId(dutyAreaId);
            bean.setRecordDate(attendance.getRecordDate());
            this.deleteAttendanceForCoverWork(bean);
        }
        
        // 如果请假人是检测员
        if (roleId == 2) {
            AttendanceForPlansApp att = new AttendanceForPlansApp();
            att.setEmployeeId(coverId);
            att.setDutyAreaId(dutyAreaId);
            // 根据责任区查询当前在考勤表中代班人的记录是否存在，如果存在则只需要更新更新is_coverwork状态置为1，
            // 如果不存在，则针对当前代班人（检测员）的考勤记录复制生成一条只有责任区不同的记录
            int lstAttForPlans = this.getExsitsCoverWorkAtt(att);
            // 存在
            if (lstAttForPlans != 0) {
                // 是否代班人考勤代班记录(1是 2不是)
                att.setIsCoverwork(1);
                this.updateAttendancePlansForCoverWork(att);
                // 不存在
            } else {
                // 代班人（检测员）的考勤信息
                List<AttendanceForPlansApp> lstAttForCover = this.getAttendanceForPlansForCreateCoverWork(coverId);
                for (AttendanceForPlansApp bean : lstAttForCover) {
                    // 上班时间
                    att.setDutyOnTime(bean.getDutyOnTime());
                    // 下班时间
                    att.setDutyOffTime(bean.getDutyOffTime());
                    // 是否代班人考勤代班记录(1是 2不是)
                    att.setIsCoverwork(1);
                    this.insertAttendancePlansForCoverWork(att);
                }
            }
            
        // 如果是环卫工
        } else {
            // 根据上下班时间段，查询当前在考勤表中代班人的记录是否存在,如果存在则将is_coverwork状态置为1，否则需要重新插入一条代班的考勤记录
            AttendanceForPlansApp att = new AttendanceForPlansApp();
            att.setEmployeeId(coverId);
            att.setDutyOnTime(dutyOnTime);
            att.setDutyOffTime(dutyOffTime);
            att.setDutyAreaId(dutyAreaId);
            int lstAttForPlans = this.getExsitsCoverWorkAtt(att);
            // 考勤表中指定上下班时间段的代表人记录存在，更新is_coverwork状态置为1
            if (lstAttForPlans != 0) {
                // 是否代班人考勤代班记录(1是 2不是)
                att.setIsCoverwork(1);
                this.updateAttendancePlansForCoverWork(att);
                // 考勤表中指定上下班时间段的代表人记录不存在，重新插入一条代班的考勤记录
            } else {
                // 是否代班人考勤代班记录(1是 2不是)
                att.setIsCoverwork(1);
                this.insertAttendancePlansForCoverWork(att);
            }
        }
    }

    /**
     * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合，2016-12-29
     */
	public List<DutyRecodeApp> getLastBanciAttendanceForPlansByListApp(List<AttendanceForPlansApp> workPlansList) {
		return attendanceForPlansDao.getLastBanciAttendanceForPlansByListApp(workPlansList);
	}

	/**
	 * 根据对下班状态的不同出来，对下班状态和下班时间进行相应处理。2016-12-30
	 */
	public int updateAttendancePlansForXBApp( AttendanceForPlansApp attendanceForPlansApp) {
		return attendanceForPlansDao.updateInfoByBean("updateAttendancePlansForXBApp", attendanceForPlansApp);
	}
	
    /**
     * 当前时间是否是一天中最晚下班之后的时间。2017-01-09
     * @param attendanceForPlansApp
     * @return
     */
    public AttendanceForPlansApp getAllCityWorkAttLastTimeForApp() {
        return attendanceForPlansDao.getInfo("getAllCityWorkAttLastTimeForApp");
    }
}
