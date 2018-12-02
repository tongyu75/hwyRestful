package com.czz.hwy.service.area.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.area.app.DutyPointApp;
import com.czz.hwy.dao.area.app.DutyPointAppDao;
import com.czz.hwy.service.area.app.DutyPointAppService;

/**
 * 责任点业务层接口实现类，用于app接口
 * @author 张咏雪 2016-11-02
 */
@Service
public class DutyPointAppServiceImpl implements DutyPointAppService {

	@Autowired
	private DutyPointAppDao dutyPointAppDao;
	
	/**
	 * 根据责任区ID获取责任点列表，2016-11-02
	 */
	public List<DutyPointApp> getDutyPointListByBean(DutyPointApp bean) {
		return dutyPointAppDao.getInfoListByBean("getPointListByBeanForApp", bean);
	}
    
}
