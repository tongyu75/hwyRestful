package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.EvalTypeApp;
import com.czz.hwy.dao.mission.app.EvalTypeAppDao;

/**
 * 考核分类的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Repository
public class EvalTypeAppDaoImpl extends BaseDaoImpl<EvalTypeApp> implements EvalTypeAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

}
