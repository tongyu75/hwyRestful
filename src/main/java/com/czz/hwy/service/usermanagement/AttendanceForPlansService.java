package com.czz.hwy.service.usermanagement;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.user.AttendanceForPlans;

/**
 * 生成考勤结果的service
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
public interface AttendanceForPlansService {

    /**
     * 工作抽查 全市当天工作员工考勤状态（领导APP端）
     */
	public List<Map<String, Object>> getAllCityWorkAttForLeaderApp();

    /**
     * 工作抽查 当前责任区的所有员工的工作状态（领导APP端）
     */
    public List<Map<String, Object>> getCurWorkAttForLeaderApp(int areaId, int page);

    /**
     * 自动生成每天的考勤记录(只生成环卫工和检测员的)，2016-10-28
     * @return
     */
	public int batchAddAttendanceForPlans();

	/**
	 * 更新当天的没有手机的员工的上下班状态为正常，2016-10-28
	 */
	public int updateNoMobileStatus();

    /**
     * 获取出勤信息记录条数。2016-11-15
     * @param reports
     * @return
     */
    public int getAttendanceHistoryCount(AttendanceForPlans bean);
    
    /**
     * 获取出勤信息记录。2016-11-15
     * @param reports
     * @return
     */
    public List<Map<String, Object>> getAttendanceHistory(AttendanceForPlans bean);   
    
    /**
     * 环卫工出勤率（当前环卫工出勤情况统计）
     */
    public List<Map<String, Object>> getAllCityWorkAtt();
    
    /**
     * 考勤统计信息
     */
    public List<Map<String, Object>> attendanceCountInfo(Map<String,Object> paramMap);
    
    /**
     * 考勤统计总数
     */
    public int attendanceCountTotal(Map<String,Object> paramMap);
    
    /**
     * 考勤详情
     */
    public List<Map<String, Object>> attendanceInfo(AttendanceForPlans bean);
    
    /**
     * 考勤详情导出用
     */
    public List<Map<String, Object>> attendanceInfoExport(AttendanceForPlans bean);
    
    /**
     * 通过日期和责任区id获取employeeId导出用
     */
    public List<Map<String, Object>> getEmployeeId(AttendanceForPlans bean);
    /**
     * 考勤统计信息导出用
     */
    public List<Map<String, Object>> attendanceCountInfoExport(Map<String,Object> paramMap);
    /**
     * 查询员工当月当天最多出勤次数导出用
     */
    public List<Map<String, Object>> attendanceMaxBanci(AttendanceForPlans bean);
}
