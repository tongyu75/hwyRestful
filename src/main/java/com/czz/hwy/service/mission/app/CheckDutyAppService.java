package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.CheckDutyApp;


/**
 * 考核项目对应责任人的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
public interface CheckDutyAppService{

	/**
	 * 根据list集合，批量新增考核责任人记录，2016-11-09
	 * @param checkDutyList
	 * @return
	 */
	int batchAddCheckDutyByListApp(List<CheckDutyApp> checkDutyList);

	/**
	 * 根据主键ID和考核类型ID，删除五分钟考核责任人记录，2016-11-09
	 * @param checkDutyApp
	 * @return
	 */
	int deleteCheckDutyByBeanApp(CheckDutyApp checkDutyApp);
	
}
