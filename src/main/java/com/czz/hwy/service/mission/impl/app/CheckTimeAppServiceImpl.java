package com.czz.hwy.service.mission.impl.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.mission.app.CheckDutyApp;
import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.CheckTimeForSelectApp;
import com.czz.hwy.bean.mission.app.QualifiedNumberApp;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.dao.mission.app.CheckTimeAppDao;
import com.czz.hwy.service.mission.app.CheckTimeAppService;
import com.czz.hwy.utils.ConstantUtil;

/**
 * 五分钟考核项目的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-08
 */
@Service
@Transactional
public class CheckTimeAppServiceImpl implements CheckTimeAppService {
	
	@Autowired
	private CheckTimeAppDao checkTimeAppDao;

	/**
	 * 新增五分钟考核任务，并将新增数据的主键ID返回
	 */
	public int insertCheckTimeByBeanApp(CheckTimeApp checkTimeApp) {
		return checkTimeAppDao.insertInfo("insertCheckTimeByBeanApp", checkTimeApp);
	}

	/**
	 * 根据主键ID和考核类型ID获取五分钟考核信息，2016-11-09
	 */
	public CheckTimeApp getCheckTimeByBeanApp(CheckTimeApp checkTimeApp) {
		return checkTimeAppDao.getInfoByBean("getCheckTimeByBeanApp", checkTimeApp);
	}

	/**
	 * 根据主键ID和考核类型ID，删除五分钟考核记录，2016-11-09
	 */
	public int deleteCheckTimeByBeanApp(CheckTimeApp checkTimeApp) {
		return checkTimeAppDao.deleteInfoByBean("deleteCheckTimeByBeanApp", checkTimeApp);
	}

	/**
	 * 查询某个责任区某个时间段内的五分钟考核总条数，2016-11-09
	 */
	public int getCheckTimeCountByMapApp(Map<String, Object> selectMap) {
		return checkTimeAppDao.getInfoCountByMap("getCheckTimeCountByMapApp", selectMap);
	}

	/**
	 * 查询某个责任区某个时间段内的五分钟考核集合，2016-11-09，不分页
	 */
	public List<CheckTimeForSelectApp> getCheckTimeListByMapApp(Map<String, Object> selectMap) {
		return checkTimeAppDao.getCheckTimeListByMapApp(selectMap);
	}

}
