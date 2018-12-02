package com.czz.hwy.service.usermanagement.watch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.watch.DutyPlans;
import com.czz.hwy.bean.user.watch.AttendanceForPlans;

/**
 * 生成考勤结果的service
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
public interface AttendanceForPlansWatchService {

    /**
     * 根据员工号和当前日期查询出环卫工所有的考勤结果
     * @param employeeId
     * @param currDate
     * @return
     */
    public List<AttendanceForPlans> getAttForPlans(int employeeId,Date currDate);
    
    /**
     * 根据员工号和当前日期查询出检测员所有的考勤结果
     * @param employeeId
     * @param currDate
     * @return
     */
    public List<AttendanceForPlans> getAttForPlansToCCY(int parseInt, Date c);

    /**
     * 根据员工号和当前日期和计划上下班时间查询出环卫工（排了多个责任点的）所有的考勤结果
     * @param parseInt
     * @param c
     * @param dutyplans
     * @return
     */
    public List<AttendanceForPlans> getAttForPlansToHWG(int parseInt, Date c, DutyPlans dutyplans);
    
    /**
     * 根据员工号查询当前员工的考勤状态
     * @param employeeId
     * @param currDate
     * @return
     */
    public Map<String, Object> getCurrentAttendanceStatus(int employeeId);
    
    /**
     * 获取当前员工上下班状态考勤记录 
     * @param timeMap
     * @return
     */
    public List<AttendanceForPlans> selectAllAttendances(Map<String, Object> timeMap);

    /**
     * 考勤上班时间设置为【上班时间前60分钟，上班时间】时间段内，第一次在责任点的时间，2016-10-26
     * @param timeMap
     * @return
     */
    public List<AttendanceForPlans> selectAtPointAttenadanceList(Map<String, Object> timeMap);

    /**
     * 批量更新考勤记录的上班状态，2016-10-26
     * @param beforList
     * @return
     */
    public int batchUpdateOnStatusByList(List<AttendanceForPlans> beforList);

    /**
     * 批量更新上下班状态 2016-08-31
     * @param timeMap
     * @return
     */
    public int updateOnOrOffStatusByMap(Map<String, Object> timeMap);
    
    /**
     * 获取时间在某一个区间内，且在责任点的考勤记录Id集合 2016-09-02
     * @param timeMap
     * @return
     */
    public List<Integer> selectAtPointAttenadance(Map<String, Object> timeMap);
    
    /**
     * 根据员工ID查询昨日和今日考勤记录，用于手表端的考勤记录 2016-11-29
     * @return
     */
    public List<Map<String, Object>> getYesAndTomAttendanceForWatch(Map<String, Object> selectMap);

    /**
     * 根据Ids获取人、上下班时间、日期唯一的考勤记录列表，2017-05-05
     * @param selectMap
     * @return
     */
	public List<AttendanceForPlans> getAttendanceForPlansListByIdsForWatch(Map<String, Object> selectMap);

	/**
	 * 批量更新考勤记录中上下班时间内移动的距离，2017-05-05
	 * @param attPlansList
	 */
	public int batchUpdateMobileDistanceByListForWatch(List<AttendanceForPlans> attPlansList);
}
