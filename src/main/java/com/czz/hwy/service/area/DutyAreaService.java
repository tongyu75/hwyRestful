package com.czz.hwy.service.area;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.area.DutyArea;

public interface DutyAreaService{
    /**
     * 根据查询条件获取责任区信息（带有分页信息）
     */
    public List<DutyArea> getDutyArea(DutyArea bean);

    /**
     * 根据查询条件获取责任区信息的数量
     */
    public int getAreaCount(DutyArea bean);
    
    /**
     * 插入责任区信息
     */    
    public int insertDutyArea(DutyArea bean);

    /**
     * 删除责任区信息
     */ 
    public int deleteDutyArea(DutyArea bean);
    
    /**
     * 根据查询条件获取责任区信息（没有分页信息）
     */
    public DutyArea getDutyAreaByBean(DutyArea dutyArea);

    /**
     * 根据查询条件获取责任区列表信息（没有分页信息）
     */    
    public List<DutyArea> getDutyAreaListByBean(DutyArea dutyArea);
    /**
     * 更新责任区信息
     */     
    public int updateDutyArea(DutyArea bean);

    /**
    * 责任区域模糊查询
    */
    public List<Map<String,Object>> getDutyAreaByLike(String areaName);
    
    /**
     * 获取责任区（领导APP端）
     */
    public List<DutyArea> getDutyAreaForLeaderApp();
    
    /**
     * 查询当前员工的所有责任区用于环卫工出勤率（当前环卫工出勤情况统计），2016-11-07
     * @param selectMap
     * @return
     */
    List <Map<String, Object>>  selectAttendanceInspectArea();
}
