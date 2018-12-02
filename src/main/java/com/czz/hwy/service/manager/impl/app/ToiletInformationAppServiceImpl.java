package com.czz.hwy.service.manager.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.ToiletInformationApp;
import com.czz.hwy.dao.manager.app.ToiletInformationAppDao;
import com.czz.hwy.service.manager.app.ToiletInformationAppService;
/**
 * 系统通知业务层实现类
 * 
 * @author 以克论净环卫管理系统    佟士儒 20161102
 * @version V1.0
 */
@Service
public class ToiletInformationAppServiceImpl implements ToiletInformationAppService {
    
    @Autowired
    private ToiletInformationAppDao toiletAppDao;
    
    /*
     * 获取公测的所有信息，2017-03-02
     */
    public List<ToiletInformationApp> listToiletInformation() {
        return toiletAppDao.getInfoList("getToiletInformationApp");
    }
}
