package com.czz.hwy.service.usermanagement.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Attendances;
import com.czz.hwy.bean.user.DutyRecode;
import com.czz.hwy.dao.user.AttendancesDao;
import com.czz.hwy.dao.user.DutyRecodeDao;
import com.czz.hwy.service.usermanagement.AttendancesService;
@Service
public class AttendancesServiceImpl implements AttendancesService {

	@Autowired
	private AttendancesDao attendancesDao;
	
	@Autowired
	private DutyRecodeDao dutyRecodeDao ;
	
	public List<String> getAttendancesDate(String employeeId,int row)
	{
		Attendances attendances=new Attendances();
		attendances.setEmployeeId(Integer.parseInt(employeeId));
		List<String> dates=attendancesDao.getInfoListStringByBean("getInfoListStringByBean",attendances);
		return dates;
	}
	
	public List<Attendances> getAttendancesByDate(String employeeId,
			Date attendanceDate) {
	    Attendances attendances = new Attendances();
        attendances.setEmployeeId(Integer.parseInt(employeeId));
        attendances.setRecordTime(attendanceDate);
		List<Attendances> attendancesList = attendancesDao.getInfoListByBean("getInfoByDate", attendances);
		return attendancesList;
	}
	
	public void insertAttendances(Attendances attendances) {
		attendancesDao.insertInfo("insertAttendances", attendances);
	}
	
	/**
	 * 查岗
	 */
	public List<DutyRecode> getDutyRecode(DutyRecode dRecode) {
		return dutyRecodeDao.getInfoListByBean("selectDutyRecode", dRecode);
	}
	
	public List<Attendances> getAllAttendanceByBean(Attendances bean) {
		return attendancesDao.getInfoListByBean("getAllAttendanceByBean", bean);
	}
	
	/**
	 * 获取指定员工的今日考勤数据
	 */
	public List<Attendances> getAttendanceByEmployeeIdCurdate(Map<String,Object> param) {
		return attendancesDao.getInfoListTByMap("getAttendanceByEmployeeIdCurdate", param);
	}
	
	public int updateIsValidateStatus(String ids) {
		return attendancesDao.updateInfoByString("sqlSessionTemplate", ids);
	}
	
	/**
	 * 获取某责任区，当前时间正在上班的员工的最新坐标。2016-09-14
	 */
	public List<Map<String, Object>> getNewCoorList(Map<String, Object> selectMap) {
		return attendancesDao.getInfoListMapByMap("getNewCoorList", selectMap);
	}
	
    /**
     * 获取某个员工所有的坐标点：某日期，某时间前所有的坐标点，2016-09-15
     */
    public List<Map<String, Object>> getEmployeeOfCoorList(Map<String, Object> selectMap) {
        return attendancesDao.getInfoListMapByMap("getEmployeeOfCoorList", selectMap);
    }

    /**
     * 获取某责任区，当前时间正在上班的员工的最新的两个坐标。2016-09-26
     */
	public List<Map<String, Object>> getNewTwoCoorList(Map<String, Object> selectMap) {
		 return attendancesDao.getInfoListMapByMap("getNewTwoCoorList", selectMap);
	}

    /**
     * 获取出勤信息时，获取坐标内容。2016-11-16
     * @param selectMap
     * @return
     */
    public List<Map<String, Object>> getAttendancesCoordinate(Map<String, Object> selectMap) {
        return attendancesDao.getInfoListMapByMap("getAttendancesCoordinate", selectMap);
    }
    
    /**
     * 获取经纬度信息，用于当前用户轨迹的查看。2016-11-21
     * @param selectMap
     * @return
     */
    public List<Map<String, Object>> getCoordinateForUsrePath(Map<String, Object> selectMap) {
        return attendancesDao.getInfoListMapByMap("getCoordinateForUsrePath", selectMap);
    }
}
