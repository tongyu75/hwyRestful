package com.czz.hwy.service.mission.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.NoQualifiedNumber;
import com.czz.hwy.dao.mission.NoQualifiedNumberDao;
import com.czz.hwy.service.mission.NoQualifiedNumberService;

/**
 * 五克五分钟不合格人员功能service的接口实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
@Service
public class NoQualifiedNumberServiceImpl implements NoQualifiedNumberService {

    @Autowired
    private NoQualifiedNumberDao noQuaNumberDao;

    /**
     * 管理人员尽职监督 获取七天内每人每天不合格次数用于排名（领导APP端）
     * @param type 类型（1.五克 2.五分钟）
     * @return
     */
    public List<NoQualifiedNumber> getNoFiveGramMinNumForApp(Integer type) {
        NoQualifiedNumber bean = new NoQualifiedNumber();
        bean.setType(type);
        return noQuaNumberDao.getInfoListByBean("getNoFiveGramMinNumForApp", bean);
    }
    
    /**
     * 五克检测合格情况（"以克论净"之检测力度）2016-11-16
     * @return
     */
    public List<Map<String, Object>> getFiveGramNum() {
        return noQuaNumberDao.getInfoListMap("getFiveGramNum");
    }
    
    /**
     * 道路清洁程度（"以克论净"之平均克数）2016-11-16
     * @return
     */
    public List<Map<String, Object>> getFiveGramEvg() {
        return noQuaNumberDao.getInfoListMap("getFiveGramEvg");
    }
}
