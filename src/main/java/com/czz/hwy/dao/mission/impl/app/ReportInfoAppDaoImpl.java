package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.ReportInfoApp;
import com.czz.hwy.dao.mission.app.ReportInfoAppDao;

/**
 * 新的举报功能的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2017-04-24
 */
@Repository
public class ReportInfoAppDaoImpl extends BaseDaoImpl<ReportInfoApp> implements ReportInfoAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	
	
}
