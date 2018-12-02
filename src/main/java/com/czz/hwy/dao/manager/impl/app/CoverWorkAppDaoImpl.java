package com.czz.hwy.dao.manager.impl.app;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.manager.app.CoverWorkApp;
import com.czz.hwy.dao.manager.app.CoverWorkAppDao;

/**
 * 代班记录查询功能dao层接口实现
 * 
 * @author 张咏雪 2016-10-21
 * @version V1.0
 */
@Repository
public class CoverWorkAppDaoImpl extends BaseDaoImpl<CoverWorkApp> implements CoverWorkAppDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查询出检测员负责的责任区,2016-10-21
	 */
	public int getAreaIdByBean(CoverWorkApp coverWorkApp) {
		return sqlSessionTemplate.selectOne("getAreaIdByBeanApp", coverWorkApp);
	}

	/**
	 * 根据日期和代班人Id获取代班记录，2016-12-08
	 */
	public List<CoverWorkApp> getCoverWorkListByMapApp(Map<String, Object> selectMap) {
		return sqlSessionTemplate.selectList("getCoverWorkListByMapApp", selectMap);
	}
    
}