package com.czz.hwy.service.usermanagement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.Attendances;
import com.czz.hwy.bean.user.DutyRecode;
/**
 *  员工考勤记录业务层
 * @author 刘新洲 2015/08/10
 * @version 1.0 换环卫云
 *
 */
@Service
public interface AttendancesService{
	
	public List<String> getAttendancesDate(String employeeId,int row);
	// 根据日期获取某日考勤信息
	public List<Attendances> getAttendancesByDate(String employeeId,
			Date attendanceDate);
	public void insertAttendances(Attendances attendances);
	// 查岗
	public List<DutyRecode> getDutyRecode(DutyRecode dRecode);
	
	public List<Attendances> getAllAttendanceByBean(Attendances bean);
	/**
	 * 根据employee_id及当前日期获取今日的考勤数据
	 * @param employeeId
	 * @return
	 */
	public List<Attendances> getAttendanceByEmployeeIdCurdate(Map<String,Object> param);
	/**
	 * 更新考勤表中的isvalidate状态为2
	 * @param string
	 * @return 
	 */
	public int updateIsValidateStatus(String string);
	
	/**
	 * 获取某责任区，当前时间正在上班的员工的最新坐标。2016-09-14
	 * @param selectMap
	 * @return
	 */
	public List<Map<String, Object>> getNewCoorList(Map<String, Object> selectMap);
	
	/**
	 * 获取某个员工所有的坐标点：某日期，某时间前所有的坐标点，2016-09-15
	 * @param selectMap
	 * @return
	 */
	public List<Map<String, Object>> getEmployeeOfCoorList(Map<String, Object> selectMap);
	
	/**
	 * 获取某责任区，当前时间正在上班的员工的最新的两个坐标。2016-09-26
	 * @param selectMap
	 * @return
	 */
	public List<Map<String, Object>> getNewTwoCoorList(Map<String, Object> selectMap);
	
    /**
     * 获取出勤信息时，获取坐标内容。2016-11-16
     * @param selectMap
     * @return
     */
    public List<Map<String, Object>> getAttendancesCoordinate(Map<String, Object> selectMap);
    
    /**
     * 获取经纬度信息，用于当前用户轨迹的查看。2016-11-21
     * @param selectMap
     * @return
     */
    public List<Map<String, Object>> getCoordinateForUsrePath(Map<String, Object> selectMap);    
}
