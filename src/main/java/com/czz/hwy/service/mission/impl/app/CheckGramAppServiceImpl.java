package com.czz.hwy.service.mission.impl.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.CheckGramApp;
import com.czz.hwy.bean.mission.app.CheckGramForSelectApp;
import com.czz.hwy.dao.mission.app.CheckGramAppDao;
import com.czz.hwy.service.mission.app.CheckGramAppService;

/**
 * 五克考核项目的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-20
 */
@Service
public class CheckGramAppServiceImpl implements CheckGramAppService {
	
	@Autowired
	private CheckGramAppDao checkGramAppDao;//五克数据层
	
	/**
	 * 新增五克考核任务，并将新增数据的主键ID返回,2016-12-20
	 * @param checkGramApp
	 * @return
	 */
	public int insertCheckGramByBeanApp(CheckGramApp checkGramApp) {
		return checkGramAppDao.insertInfo("insertCheckGramByBeanApp", checkGramApp);
	}

	/**
	 * 查询某个责任区某个时间段内的五克考核总条数，2016-12-22
	 */
	public int getCheckGramCountByMapApp(Map<String, Object> selectMap) {
		return checkGramAppDao.getInfoCountByMap("getCheckGramCountByMapApp", selectMap);
	}

	/**
	 * 查询某个责任区某个时间段内的五克考核集合，2016-12-22，不分页
	 */
	public List<CheckGramForSelectApp> getCheckGramListByMapApp(Map<String, Object> selectMap) {
		return checkGramAppDao.getCheckGramListByMapApp(selectMap);
	}

}
