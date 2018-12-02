package com.czz.hwy.service.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.CheckTimeForSelectApp;
import com.czz.hwy.bean.mission.app.WorkInfoApp;


/**
 * 五分钟考核项目的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
public interface CheckTimeAppService{

	/**
	 * 新增五分钟考核任务，并将新增数据的主键ID返回
	 * @return
	 */
	int insertCheckTimeByBeanApp(CheckTimeApp checkTimeApp);

	/**
	 * 根据主键ID和考核类型ID获取五分钟考核信息，2016-11-09
	 * @param checkTimeApp
	 * @return
	 */
	CheckTimeApp getCheckTimeByBeanApp(CheckTimeApp checkTimeApp);

	/**
	 * 根据主键ID和考核类型ID，删除五分钟考核记录，2016-11-09
	 * @param checkTimeApp
	 * @return
	 */
	int deleteCheckTimeByBeanApp(CheckTimeApp checkTimeApp);

	/**
	 * 查询某个责任区某个时间段内的五分钟考核总条数，2016-11-09
	 * @param selectMap
	 * @return
	 */
	int getCheckTimeCountByMapApp(Map<String, Object> selectMap);

	/**
	 * 查询某个责任区某个时间段内的五分钟考核集合，2016-11-09，不分页
	 * @param selectMap
	 * @return
	 */
	List<CheckTimeForSelectApp> getCheckTimeListByMapApp(Map<String, Object> selectMap);

}
