/**
 * 
 */
package com.czz.hwy.service.area;

import java.util.List;

import com.czz.hwy.bean.area.DutyPointGather;

/**
 * 责任点考核点采集信息
 */
public interface DutyPointGatherService {
    
    /**
     * 获取责任点考核点的采集信息
     * @param dutyPointGathers
     */
    public DutyPointGather getGatherPoint(DutyPointGather dutyPointGathers);
    
    /**
     * 获取责任点考核点的采集列表信息
     * @param dutyPointGathers
     */
    public List<DutyPointGather> getGatherPointList(DutyPointGather dutyPointGathers);
    
    /**
     * 新增责任点的采集点
     * @param dutyPointGathers
     */
    public int insertGatherPoint(List<DutyPointGather> dutyPointGathers);

    /**
     * 更新采集的边界点经纬度信息
     * @param dutyPointGather
     */
    public int updateGatherPoint(DutyPointGather dutyPointGather);
}
