package com.czz.hwy.dao.manager.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.manager.app.LeaveApp;
import com.czz.hwy.dao.manager.app.LeavekAppDao;

/**
 *  请假功能dao层接口实现类，用于app接口
 * @author 张咏雪 2016-11-08
 * @version V1.0
 */
@Repository
public class LeaveAppDaoImpl extends BaseDaoImpl<LeaveApp> implements LeavekAppDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

    
}