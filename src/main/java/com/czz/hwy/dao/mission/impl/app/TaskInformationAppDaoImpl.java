package com.czz.hwy.dao.mission.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.TaskInformationApp;
import com.czz.hwy.dao.mission.app.TaskInformationAppDao;

/**
 * 工作调度功能的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Repository
public class TaskInformationAppDaoImpl extends BaseDaoImpl<TaskInformationApp> implements TaskInformationAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
}
