package com.czz.hwy.dao.mission.impl.app;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.CheckTimeForSelectApp;
import com.czz.hwy.dao.mission.app.CheckTimeAppDao;

/**
 * 五分钟考核项目的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
@Repository
public class CheckTimeAppDaoImpl extends BaseDaoImpl<CheckTimeApp> implements CheckTimeAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查询某个责任区某个时间段内的五分钟考核集合，2016-11-09，不分页
	 */
	public List<CheckTimeForSelectApp> getCheckTimeListByMapApp(Map<String, Object> selectMap) {
		return sqlSessionTemplate.selectList("getCheckTimeListByMapApp", selectMap);
	}
	
	
}
