package com.czz.hwy.service.usermanagement.impl.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.user.app.AttendanceForPointApp;
import com.czz.hwy.dao.user.app.AttendanceForPointAppDao;
import com.czz.hwy.service.usermanagement.app.AttendanceForPointAppService;

/**
 * 生成考勤记录对应的责任点的service接口实现类
 * 功能描述
 * @author 张咏雪 2017-01-10
 * @company chnegzhongzhi
 */
@Service
@Transactional
public class AttendanceForPointAppServiceImpl implements AttendanceForPointAppService {

	@Autowired
	private AttendanceForPointAppDao attendanceForPointAppDao;

	/**
	 * 自动生成考记录对应的责任点记录，2017-01-10 
	 */
	public int batchAddAttendanceForPointApp() {
		return attendanceForPointAppDao.insertInfo("batchAddAttendanceForPointApp");
	}

	/**
	 * 根据bean删除代班人考勤记录对应的责任点记录。2017-01-10
	 */
	public int deleteAttendanceForPointAppByBeanApp(AttendanceForPointApp attendanceForPointApp) {
		return attendanceForPointAppDao.insertInfo("deleteAttendanceForPointAppByBeanApp", attendanceForPointApp);
	}

	/**
	 * 根据bean更新代班人考勤记录对应的责任点记录。2017-01-10
	 */
	public int updateAttendanceForPointAppByBeanApp(AttendanceForPointApp attendanceForPointApp) {
		return attendanceForPointAppDao.updateInfoByBean("updateAttendanceForPointAppByBeanApp", attendanceForPointApp);
	}

	/**
	 * 新增考勤记录对应的代班责任点记录,2017-01-10
	 */
	public int insertAttendanceForPointByBeanApp(AttendanceForPointApp attendanceForPointApp) {
		return attendanceForPointAppDao.insertInfo("insertAttendanceForPointByBeanApp", attendanceForPointApp);
	}

	/**
	 * 将请假人某个时间段的责任点集合添加给代班人，2017-01-10
	 */
	public int insertAttendanceForPointByLeaveApp(AttendanceForPointApp attendanceForPointApp) {
		return attendanceForPointAppDao.insertInfo("insertAttendanceForPointByLeaveApp", attendanceForPointApp);
	}

	/**
	 * 根据责任区ID和责任点Id,当前时间，获取责任人列表。责任人只能是环卫工，2017-01-16
	 */
	public List<Map<String, Object>> getDutyPeopleListByPointMapApp(Map<String, Object> selectMap) {
		return attendanceForPointAppDao.getInfoListMapByMap("getDutyPeopleListByPointMapApp", selectMap);
	}
    
	
	
   
}
