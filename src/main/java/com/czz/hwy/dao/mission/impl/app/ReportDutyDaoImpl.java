package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.ReportDutyApp;
import com.czz.hwy.dao.mission.app.ReportDutyAppDao;

/**
 * 监察举报考核项目责任人的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-22
 */
@Repository
public class ReportDutyDaoImpl extends BaseDaoImpl<ReportDutyApp> implements ReportDutyAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	
	
}
