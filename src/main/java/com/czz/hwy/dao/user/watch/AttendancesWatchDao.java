package com.czz.hwy.dao.user.watch;

import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.user.watch.AttendanceForPlans;
import com.czz.hwy.bean.user.watch.Attendances;

public interface AttendancesWatchDao extends BaseDao<Attendances> {

	/**
	 * 根据考勤记录中的员工ID，上班时间，下班时间，日期获取按采集时间升序排列的在责任点的轨迹集合，2017-05-05 
	 * @param att
	 * @return
	 */
	List<Attendances> getAttendancesListByAttPlansBeanForWatch(AttendanceForPlans att);

}
