package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.CheckDutyApp;
import com.czz.hwy.dao.mission.app.CheckDutyAppDao;
import com.czz.hwy.service.mission.app.CheckDutyAppService;

/**
 * 考核项目对应责任人的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
@Service
public class CheckDutyAppServiceImpl implements CheckDutyAppService {
	@Autowired
	private CheckDutyAppDao checkDutyAppDao;

	/**
	 * 根据list集合，批量新增考核责任人记录，2016-11-09
	 */
	public int batchAddCheckDutyByListApp(List<CheckDutyApp> checkDutyList) {
		return checkDutyAppDao.insertInfo("batchAddCheckDutyByListApp", checkDutyList);
	}

	/**
	 * 根据主键ID和考核类型ID，删除五分钟考核责任人记录，2016-11-09
	 */
	public int deleteCheckDutyByBeanApp(CheckDutyApp checkDutyApp) {
		return checkDutyAppDao.deleteInfoByBean("deleteCheckDutyByBeanApp", checkDutyApp);
	}
}
