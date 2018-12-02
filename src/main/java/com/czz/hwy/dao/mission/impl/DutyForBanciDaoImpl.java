package com.czz.hwy.dao.mission.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.DutyForBanci;
import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.bean.user.AttendanceForPlans;
import com.czz.hwy.dao.mission.DutyForBanciDao;

/**
 * 改造后的排班计划的dao层接口实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
@Repository
public class DutyForBanciDaoImpl extends BaseDaoImpl<DutyForBanci> implements DutyForBanciDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 根据bean删除多余的考勤记录
	 */
	public int deleteAttendanceForPlansByBean(AttendanceForPlans bean) {
		return sqlSessionTemplate.delete("deleteAttendanceForPlansByBean", bean);
	}
	
	/**
	 * 更新那条不一致的考勤记录
	 */
	public int updateAttendanceForPlansByBean(AttendanceForPlans bean) {
		return sqlSessionTemplate.update("updateAttendanceForPlansByBean", bean);
	}

	/**
	 * 根据员工ID，当前日期，责任点ID，责任区ID，删除考勤记录
	 */
	public int deleteAttendanceByBean(AttendanceForPlans bean) {
		return sqlSessionTemplate.delete("deleteAttendanceByBean", bean);
	}


	/**
	 * 若考勤记录不为空，根据员工ID,责任区ID,责任点ID和当前日期查询出环卫工的排班计划列表
	 */
	public List<DutyPlans> getDutyPlansListByBean(
			AttendanceForPlans bean) {
		return sqlSessionTemplate.selectList("getDutyPlansListByBean", bean);
	}
	
	/**
	 * 根据员工ID,责任区ID，责任点ID和日期查询出员工的考勤记录
	 */
	public List<AttendanceForPlans> getAttendanceForPlansByBean(DutyPlans bean) {
		return sqlSessionTemplate.selectList("getAttendanceForPlansByBean", bean);
	}
	
	/**
	 * 批量更新排班计划
	 */
	public int beatchUpdateForDuty(List<DutyForBanci> list) {
		return sqlSessionTemplate.update("beatchUpdateForDuty", list);
	}
	
	/**
	 * 根据seq获取排班计划
	 */
	public DutyPlans getDutyPlansByBean(DutyPlans bean) {
		return sqlSessionTemplate.selectOne("getDutyPlansBySeq", bean);
	}

	/**
	 * 批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 */
	public int beatchDeleteForDutyForBanci(List<DutyForBanci> dutyForBanciList) {
		return sqlSessionTemplate.delete("beatchDeleteForDutyForBanci", dutyForBanciList);
//		return sqlSessionTemplate.update("beatchDeleteForDutyForBanci", dutyForBanciList);
	}
}
