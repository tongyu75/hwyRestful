package com.czz.hwy.dao.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.CheckTimeForSelectApp;

/**
 * 五分钟考核项目的dao接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
public interface CheckTimeAppDao extends BaseDao<CheckTimeApp>{

	/**
	 * 查询某个责任区某个时间段内的五分钟考核集合，2016-11-09，不分页
	 * @param selectMap
	 * @return
	 */
	List<CheckTimeForSelectApp> getCheckTimeListByMapApp(Map<String, Object> selectMap);
	
}
