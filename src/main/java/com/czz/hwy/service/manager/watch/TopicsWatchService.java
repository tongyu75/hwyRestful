package com.czz.hwy.service.manager.watch;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.TopicsVo;
@Service
public interface TopicsWatchService {

    /**
     *  获取通知信息
     */
    public List<Map<String, Object>> getTopicsInfo(TopicsVo topicsVo);
}
