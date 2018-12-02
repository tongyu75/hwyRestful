package com.czz.hwy.service.mission;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.NoQualifiedNumber;

/**
 * 五克五分钟不合格人员功能service的接口实现类
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public interface NoQualifiedNumberService{

	/**
	 * 管理人员尽职监督 获取七天内每人每天次数用于排名（领导APP端）
	 * @param type 类型（1.五克 2.五分钟）
	 * @return
	 */
	public List<NoQualifiedNumber> getNoFiveGramMinNumForApp(Integer type);

	/**
     * 五克检测合格情况（"以克论净"之检测力度）2016-11-16
     * @return
     */
    public List<Map<String, Object>> getFiveGramNum();
    
    /**
     * 道路清洁程度（"以克论净"之平均克数）2016-11-16
     * @return
     */
    public List<Map<String, Object>> getFiveGramEvg();    
}
