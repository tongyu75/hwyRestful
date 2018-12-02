package com.czz.hwy.service.manager.impl.app;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.CoverWorkApp;
import com.czz.hwy.dao.manager.app.CoverWorkAppDao;
import com.czz.hwy.service.manager.app.CoverWorkAppService;

/**
 * 代班记录查询功能业务层接口实现
 * 
 @ author 张咏雪 2016-10-21
 * @version V1.0
 */
@Service
public class CoverWorkAppServiceImpl implements CoverWorkAppService {

    @Autowired
    private CoverWorkAppDao coverWorkZuDao;

    /**
     * 查看自己某一段时间内的代班记录总条数，2016-10-21
     */
	public int getCoverWorkCountByBean(CoverWorkApp coverWorkApp) {
		return coverWorkZuDao.getInfoCount("getCoverWorkCountByBeanApp", coverWorkApp);
	}

	/**
	 * 查看自己某一段时间内的代班记录集合，2016-10-21，不分页
	 */
	public List<CoverWorkApp> getCoverWorkListByBean(CoverWorkApp coverWorkApp) {
		return coverWorkZuDao.getInfoListByBean("getCoverWorkListByBeanApp", coverWorkApp);
	}

	/**
	 * 查询某个责任区某个时间段内的请假记录对应的代班详情总条数，2016-10-21
	 */
	public int getAllCoverWorkCountByBean(CoverWorkApp coverWorkApp) {
		return coverWorkZuDao.getInfoCount("getAllCoverWorkCountByBeanApp", coverWorkApp);
	}

	/**
	 * 查询某个责任区某个时间段内的请假记录对应的代班详情集合，2016-10-21，不分页
	 */
	public List<CoverWorkApp> getAllCoverWorkListByBean(CoverWorkApp coverWorkApp) {
		return coverWorkZuDao.getInfoListByBean("getAllCoverWorkListByBeanApp", coverWorkApp);
	}

	/**
	 * 查询出检测员负责的责任区,2016-10-21
	 */
	public int getAreaIdByBean(CoverWorkApp coverWorkApp) {
		return coverWorkZuDao.getAreaIdByBean(coverWorkApp);
	}

    /**
     * 根据当日时间，查询代班人信息用于定时生成考勤状态,2016-12-09
     * @return 代班信息
     */
    public List<CoverWorkApp> getCoverWorkForAttendace() {
        return coverWorkZuDao.getInfoList("getCoverWorkForAttendanceApp");
    }

	/**
	 * 根据日期和代班人Id获取代班记录，2016-12-08
	 */
	public List<CoverWorkApp> getCoverWorkListByMapApp(Map<String, Object> selectMap) {
		return coverWorkZuDao.getCoverWorkListByMapApp(selectMap);
	}
}
