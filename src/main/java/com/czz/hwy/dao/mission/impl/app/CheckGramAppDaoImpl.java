package com.czz.hwy.dao.mission.impl.app;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.app.CheckGramApp;
import com.czz.hwy.bean.mission.app.CheckGramForSelectApp;
import com.czz.hwy.dao.mission.app.CheckGramAppDao;

/**
 * 五克考核项目的dao接口实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-20
 */
@Repository
public class CheckGramAppDaoImpl extends BaseDaoImpl<CheckGramApp> implements CheckGramAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查询某个责任区某个时间段内的五克考核集合，2016-12-22，不分页
	 */
	public List<CheckGramForSelectApp> getCheckGramListByMapApp(Map<String, Object> selectMap) {
		return sqlSessionTemplate.selectList("getCheckGramListByMapApp", selectMap);
	}

	
	
}
