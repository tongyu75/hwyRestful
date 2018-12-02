package com.czz.hwy.service.manager.impl.watch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.TopicsVo;
import com.czz.hwy.dao.manager.watch.TopicsWatchDao;
import com.czz.hwy.service.manager.watch.TopicsWatchService;
/**
 * 系统通知业务层实现类
 * 
 * @author 以克论净环卫管理系统    佟士儒
 * @version V1.0
 */
@Service
public class TopicsWatchServiceImpl implements TopicsWatchService {
    // 系统通知dao层接口
    @Autowired
    private TopicsWatchDao topicsWatchDao;
    
    /**
     *  获取通知信息
     */
    public List<Map<String, Object>> getTopicsInfo(TopicsVo topicsVo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("employeeId", topicsVo.getEmployeeId());
        param.put("vsrsionCode", topicsVo.getVsrsionCode());
        return topicsWatchDao.getInfoListMapByMap("getTopicsInfoWatch", param);
    }

}
