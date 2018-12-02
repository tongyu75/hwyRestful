/**
 * 
 */
package com.czz.hwy.service.area.watch;

import java.util.List;

import com.czz.hwy.bean.area.watch.DutyPointGather;

/**
 * 责任点考核点采集信息
 */
public interface DutyPointGatherWatchService {
    
    /**
     * 根据责任点ID查询边界点
     * @param dutyPointId
     * @param dutyAreaId
     * @return
     */
    public List<DutyPointGather> getDutyPointInfoById(int dutyPointId, int dutyAreaId);
}
