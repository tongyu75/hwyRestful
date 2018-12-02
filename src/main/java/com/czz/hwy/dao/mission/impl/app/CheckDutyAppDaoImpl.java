package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.CheckDutyApp;
import com.czz.hwy.dao.mission.app.CheckDutyAppDao;

/**
 * 考核项目对应责任人的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
@Repository
public class CheckDutyAppDaoImpl extends BaseDaoImpl<CheckDutyApp> implements CheckDutyAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
}
