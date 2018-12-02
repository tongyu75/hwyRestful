package com.czz.hwy.service.usermanagement.watch;

import com.czz.hwy.bean.user.watch.BindingInformation;

/**
 * 绑定信息的service
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
public interface BindingInformationWatchService {

    /**
     * 插入绑定信息
     * @param bean
     * @return
     */
    public int insertBindingInfo(BindingInformation bean);

    /**
     * 更新绑定信息
     * @param bean
     * @return
     */
    public int updateBindingInfo(BindingInformation bean);
}
