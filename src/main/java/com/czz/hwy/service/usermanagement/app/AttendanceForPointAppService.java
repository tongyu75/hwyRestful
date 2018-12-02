package com.czz.hwy.service.usermanagement.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.user.app.AttendanceForPointApp;


/**
 * 生成考勤记录对应的责任点的service
 * 功能描述
 * @author 张咏雪 2017-01-10
 * @company chnegzhongzhi
 */
public interface AttendanceForPointAppService {

	/**
	 * 自动生成考记录对应的责任点记录，2017-01-10
	 * @return
	 */
	int batchAddAttendanceForPointApp();

	/**
	 * 根据bean删除代班人考勤记录对应的责任点记录。2017-01-10
	 * @param attendanceForPointApp
	 * @return
	 */
	int deleteAttendanceForPointAppByBeanApp(AttendanceForPointApp attendanceForPointApp);

	/**
	 * 根据bean更新代班人考勤记录对应的责任点记录。2017-01-10
	 * @param attendanceForPointApp
	 * @return
	 */
	int updateAttendanceForPointAppByBeanApp(AttendanceForPointApp attendanceForPointApp);

	/**
	 * 新增考勤记录对应的代班责任点记录,2017-01-10
	 * @param attendanceForPointApp
	 * @return
	 */
	int insertAttendanceForPointByBeanApp(AttendanceForPointApp attendanceForPointApp);

	/**
	 * 将请假人某个时间段的责任点集合添加给代班人，2017-01-10
	 * @param attendanceForPointApp
	 * @return
	 */
	int insertAttendanceForPointByLeaveApp(AttendanceForPointApp attendanceForPointApp);

	/**
	 * 根据责任区ID和责任点Id,当前时间，获取责任人列表。责任人只能是环卫工，2017-01-16
	 * @param selectMap
	 * @return
	 */
	List<Map<String, Object>> getDutyPeopleListByPointMapApp(Map<String, Object> selectMap);

   
}
