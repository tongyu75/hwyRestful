package com.czz.hwy.dao.user.impl.app;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.bean.user.app.DutyRecodeApp;
import com.czz.hwy.dao.user.app.AttendanceForPlansAppDao;

/**
 * 生成考勤记录的dao层接口
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
@Repository
public class AttendanceForPlansAppDaoImpl extends BaseDaoImpl<AttendanceForPlansApp> implements AttendanceForPlansAppDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合，2016-12-29
	 */
	public List<DutyRecodeApp> getLastBanciAttendanceForPlansByListApp(List<AttendanceForPlansApp> workPlansList) {
		return sqlSessionTemplate.selectList("getLastBanciAttendanceForPlansByListApp", workPlansList);
	}

	/**
	 * 根据获取的上一班次的员工以及其上下班时间获取其考勤记录集合(含移动距离)，2017-04-21
	 */
	public List<DutyRecodeApp> getLastBanciAttendanceForPlansByListAppForMOV(List<AttendanceForPlansApp> workPlansList) {
		return sqlSessionTemplate.selectList("getLastBanciAttendanceForPlansByListAppForMOV", workPlansList);
	}

}
