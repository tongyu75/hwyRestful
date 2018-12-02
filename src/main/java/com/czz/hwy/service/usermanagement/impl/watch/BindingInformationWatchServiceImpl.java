package com.czz.hwy.service.usermanagement.impl.watch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.user.watch.BindingInformation;
import com.czz.hwy.dao.user.watch.BindingInformationWatchDao;
import com.czz.hwy.service.usermanagement.watch.BindingInformationWatchService;

/**
 * 生成考勤结果的service接口实现类
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
@Service
public class BindingInformationWatchServiceImpl implements BindingInformationWatchService {

	@Autowired
	private BindingInformationWatchDao bindingInfo;
	
    /**
     * 插入绑定信息
     * @param bean
     * @return
     */
    public int insertBindingInfo(BindingInformation bean) {
        return bindingInfo.insertInfo("insertBindingInfoWatch", bean);
    }

    /**
     * 更新绑定信息
     * @param bean
     * @return
     */
    public int updateBindingInfo(BindingInformation bean) {
        return bindingInfo.updateInfoByBean("updateBindingInfoWatch", bean);
    }
}
