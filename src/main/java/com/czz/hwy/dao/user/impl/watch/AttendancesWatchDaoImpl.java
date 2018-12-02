package com.czz.hwy.dao.user.impl.watch;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.user.watch.AttendanceForPlans;
import com.czz.hwy.bean.user.watch.Attendances;
import com.czz.hwy.dao.user.watch.AttendancesWatchDao;
@Repository
public class AttendancesWatchDaoImpl extends BaseDaoImpl<Attendances> implements AttendancesWatchDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 根据考勤记录中的员工ID，上班时间，下班时间，日期获取按采集时间升序排列的在责任点的轨迹集合，2017-05-05 
	 */
	public List<Attendances> getAttendancesListByAttPlansBeanForWatch(AttendanceForPlans att) {
		return sqlSessionTemplate.selectList("getAttendancesListByAttPlansBeanForWatch", att);
	}
}
