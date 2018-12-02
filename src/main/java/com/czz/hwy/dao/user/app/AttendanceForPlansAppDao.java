package com.czz.hwy.dao.user.app;

import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.bean.user.app.DutyRecodeApp;

/**
 * 生成考勤记录的dao层接口
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
public interface AttendanceForPlansAppDao extends BaseDao<AttendanceForPlansApp>{

	/**
	 * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合，2016-12-29
	 * @param workPlansList
	 * @return
	 */
	List<DutyRecodeApp> getLastBanciAttendanceForPlansByListApp(List<AttendanceForPlansApp> workPlansList);

	/**
	 * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合(含移动距离)，2017-04-21
	 * @param workPlansList
	 * @return
	 */
	List<DutyRecodeApp> getLastBanciAttendanceForPlansByListAppForMOV(List<AttendanceForPlansApp> workPlansList);

}
