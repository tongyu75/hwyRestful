package com.czz.hwy.dao.manager.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.manager.app.CoverWorkApp;

/**
 * 代班记录查询功能功能dao层接口
 * 
 * @author 张咏雪 2016-10-21
 * @version V1.0
 */
public interface CoverWorkAppDao extends BaseDao<CoverWorkApp>{

	/**
	 * 查询出检测员负责的责任区,2016-10-21
	 * @param coverWork
	 * @return
	 */
	int getAreaIdByBean(CoverWorkApp coverWorkApp);

	/**
	 * 根据日期和代班人Id获取代班记录，2016-12-08
	 * @param selectMap
	 * @return
	 */
	List<CoverWorkApp> getCoverWorkListByMapApp(Map<String, Object> selectMap);
  
     
}