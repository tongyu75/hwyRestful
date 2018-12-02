package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.ReportsApp;
import com.czz.hwy.dao.mission.app.ReportsAppDao;

/**
 * 监察举报考核项目的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-22
 */
@Repository
public class ReportsAppDaoImpl extends BaseDaoImpl<ReportsApp> implements ReportsAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	
	
}
