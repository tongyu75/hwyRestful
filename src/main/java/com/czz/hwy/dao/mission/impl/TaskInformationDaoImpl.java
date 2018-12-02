package com.czz.hwy.dao.mission.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.TaskInformation;
import com.czz.hwy.dao.mission.TaskInformationDao;

/**
 * 任务管理功能dao的接口实现类,用于pc端
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-14
 */
@Repository
public class TaskInformationDaoImpl extends BaseDaoImpl<TaskInformation> implements TaskInformationDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
}
