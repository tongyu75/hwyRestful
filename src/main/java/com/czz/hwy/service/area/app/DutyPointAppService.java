package com.czz.hwy.service.area.app;

import java.util.List;

import com.czz.hwy.bean.area.app.DutyPointApp;

/**
 * 责任点业务层接口，用于app接口
 * @author 张咏雪 2016-11-02
 */
public interface DutyPointAppService {

	/**
	 * 根据责任区ID获取责任点列表，2016-11-02
	 * @param bean
	 * @return
	 */
	List<DutyPointApp> getDutyPointListByBean(DutyPointApp bean);
    
  
}
