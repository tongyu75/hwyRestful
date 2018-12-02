package com.czz.hwy.dao.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.app.CheckGramApp;
import com.czz.hwy.bean.mission.app.CheckGramForSelectApp;

/**
 * 五克考核项目的dao接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-20
 */
public interface CheckGramAppDao extends BaseDao<CheckGramApp>{

	/**
	 * 查询某个责任区某个时间段内的五克考核集合，2016-12-22，不分页
	 * @param selectMap
	 * @return
	 */
	List<CheckGramForSelectApp> getCheckGramListByMapApp(Map<String, Object> selectMap);

	
}
