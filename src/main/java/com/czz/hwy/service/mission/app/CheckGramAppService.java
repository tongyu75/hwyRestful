package com.czz.hwy.service.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.app.CheckGramApp;
import com.czz.hwy.bean.mission.app.CheckGramForSelectApp;



/**
 * 五克考核项目的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-20
 */
public interface CheckGramAppService{
	/**
	 * 新增五克考核任务，并将新增数据的主键ID返回,2016-12-20
	 * @param checkGramApp
	 * @return
	 */
	int insertCheckGramByBeanApp(CheckGramApp checkGramApp);

	/**
	 * 查询某个责任区某个时间段内的五克考核总条数，2016-12-22
	 * @param selectMap
	 * @return
	 */
	int getCheckGramCountByMapApp(Map<String, Object> selectMap);

	/**
	 * 查询某个责任区某个时间段内的五克考核集合，2016-12-22，不分页
	 * @param selectMap
	 * @return
	 */
	List<CheckGramForSelectApp> getCheckGramListByMapApp(Map<String, Object> selectMap);
}
