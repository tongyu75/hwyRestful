package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.QualifiedNumberApp;
import com.czz.hwy.dao.mission.app.QualifiedNumberAppDao;

/**
 * 五克五分钟次数统计dao的接口实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Repository
public class QualifiedNumberAppDaoImpl extends BaseDaoImpl<QualifiedNumberApp> implements QualifiedNumberAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

}
