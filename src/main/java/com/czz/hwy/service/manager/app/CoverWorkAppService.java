package com.czz.hwy.service.manager.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.manager.app.CoverWorkApp;

/**
 * 代班记录查询功能业务层接口
 * 
 * @author 张咏雪 2016-10-21
 * @version V1.0
 */
public interface CoverWorkAppService {

	/**
	 * 查看自己某一段时间内的代班记录总条数，2016-10-21
	 * @param coverWork
	 * @return
	 */
	int getCoverWorkCountByBean(CoverWorkApp coverWorkApp);

	/**
	 * 查看自己某一段时间内的代班记录集合，2016-10-21，不分页
	 * @param coverWork
	 * @return
	 */
	List<CoverWorkApp> getCoverWorkListByBean(CoverWorkApp coverWorkApp);

	/**
	 * 查询某个责任区某个时间段内的请假记录对应的代班详情总条数，2016-10-21
	 * @param coverWork
	 * @return
	 */
	int getAllCoverWorkCountByBean(CoverWorkApp coverWorkApp);

	/**
	 * 查询某个责任区某个时间段内的请假记录对应的代班详情集合，2016-10-21，不分页
	 * @param coverWork
	 * @return
	 */
	List<CoverWorkApp> getAllCoverWorkListByBean(CoverWorkApp coverWorkApp);

	/**
	 * 查询出检测员负责的责任区,2016-10-21
	 * @param coverWork
	 * @return
	 */
	int getAreaIdByBean(CoverWorkApp coverWorkApp);

    /**
     * 根据当日时间，查询代班人信息用于定时生成考勤状态,2016-12-09
     * @return 代班信息
     */
    public List<CoverWorkApp> getCoverWorkForAttendace();

	/**
	 * 根据日期和代班人Id获取代班记录，2016-12-08
	 * @param selectMap
	 * @return
	 */
	List<CoverWorkApp> getCoverWorkListByMapApp(Map<String, Object> selectMap);

}
