package com.czz.hwy.dao.user.impl.app;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.user.app.AttendanceForPointApp;
import com.czz.hwy.dao.user.app.AttendanceForPointAppDao;

/**
 * 生成考勤记录对应的责任点的dao层接口
 * 功能描述
 * @author 张咏雪 2017-01-10
 * @company chnegzhongzhi
 */
@Repository
public class AttendanceForPointAppDaoImpl extends BaseDaoImpl<AttendanceForPointApp> implements AttendanceForPointAppDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

}
