package com.czz.hwy.service.usermanagement.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.AttendanceForPlans;
import com.czz.hwy.dao.user.AttendanceForPlansDao;
import com.czz.hwy.service.usermanagement.AttendanceForPlansService;

/**
 * 生成考勤结果的service接口实现类
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
@Service
public class AttendanceForPlansServiceImpl implements AttendanceForPlansService {

	@Autowired
	private AttendanceForPlansDao attendanceForPlansDao;
	
    /**
     * 工作抽查 全市当天工作员工考勤状态（领导APP端）
     */
    public List<Map<String, Object>> getAllCityWorkAttForLeaderApp() {
        return attendanceForPlansDao.getInfoListMap("getAllCityWorkAttForLeaderApp");
    }

    /**
     * 工作抽查 当前责任区的所有员工的工作状态（领导APP端）
     */
    public List<Map<String, Object>> getCurWorkAttForLeaderApp(int areaId, int page) {
        // 检索条件
        AttendanceForPlans bean = new AttendanceForPlans();
        bean.setPage(page);
        bean.setDutyAreaId(areaId);
        return attendanceForPlansDao.getInfoListMapByBean("getCurWorkAttForLeaderAppApp", bean);
    }

    /**
     * 自动生成每天的考勤记录(只生成环卫工和检测员的)，2016-10-28
     */
	public int batchAddAttendanceForPlans() {
		return attendanceForPlansDao.insertInfo("batchAddAttendanceForPlans");
	}

	/**
	 * 更新当天的没有手机的员工的上下班状态为正常，2016-10-28
	 */
	public int updateNoMobileStatus() {
		return attendanceForPlansDao.updateInfo("updateNoMobileStatus");
	}
	
    /**
     * 获取出勤信息记录条数。2016-11-15
     * @return
     */
    public int getAttendanceHistoryCount(AttendanceForPlans bean) {
        return attendanceForPlansDao.getInfoCount("getAttendanceHistoryCount", bean);
    }
    
    /**
     * 获取出勤信息记录。2016-11-15
     * @return
     */
    public List<Map<String, Object>> getAttendanceHistory(AttendanceForPlans bean){
        return attendanceForPlansDao.getInfoListMapByBean("getAttendanceHistory", bean);
    }
    
    /**
     * 环卫工出勤率（当前环卫工出勤情况统计）
     */
    public List<Map<String, Object>> getAllCityWorkAtt() {
        return attendanceForPlansDao.getInfoListMap("getAllCityWorkAtt");
    }
    
    /**
     * 考勤统计信息
     */
    public List<Map<String, Object>> attendanceCountInfo(Map<String,Object> paramMap) {
        return attendanceForPlansDao.getInfoListMapByMap("attendanceCountInfo", paramMap);
    }
    
    /**
     * 考勤统计总数
     */
    public int attendanceCountTotal(Map<String,Object> paramMap) {
        return attendanceForPlansDao.getInfoCountByMap("attendanceCountTotal", paramMap);
    } 
    
    /**
     * 考勤详情
     */
    public List<Map<String, Object>> attendanceInfo(AttendanceForPlans bean) {
        return attendanceForPlansDao.getInfoListMapByBean("attendanceInfo", bean);
    }
    
    /**
     * 考勤详情导出用
     */
    public List<Map<String, Object>> attendanceInfoExport(AttendanceForPlans bean) {
        return attendanceForPlansDao.getInfoListMapByBean("attendanceInfoExport", bean);
    } 
    
    /**
     * 通过日期和责任区id获取employeeId
     */
    public List<Map<String, Object>> getEmployeeId(AttendanceForPlans bean) {
        return attendanceForPlansDao.getInfoListMapByBean("getEmployeeId", bean);
    }

	public List<Map<String, Object>> attendanceCountInfoExport(Map<String, Object> paramMap) {
		return attendanceForPlansDao.getInfoListMapByMap("attendanceCountInfoExport", paramMap);
	}
	/**
     * 查询员工当月当天最多出勤次数导出用
     */
	public List<Map<String, Object>> attendanceMaxBanci(AttendanceForPlans bean) {
		return attendanceForPlansDao.getInfoListMapByBean("attendanceMaxBanci", bean);
	} 
}
