package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.CheckImageApp;
import com.czz.hwy.dao.mission.app.CheckImageAppDao;

/**
 * 考核任务上传图片功能的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
@Repository
public class CheckImageAppDaoImpl extends BaseDaoImpl<CheckImageApp> implements CheckImageAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
}
