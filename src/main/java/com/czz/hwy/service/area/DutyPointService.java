package com.czz.hwy.service.area;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.area.DutyPoint;

public interface DutyPointService {
    
    /**
     * 根据查询条件获取责任点信息（带有分页信息）
     */
    public List<DutyPoint> getAllDutyPoint(DutyPoint bean);

    /**
     * 根据查询条件获取责任点信息的数量
     */
    public int getAllDutyPointCount(DutyPoint bean);

    /**
     * 插入责任点信息
     */
    public int insertDutyPoint(DutyPoint dutyPoint);

    /**
     * 更新责任点信息
     */
    public int updateDutyPoint(DutyPoint dutyPoint);
    
    /**
     * 根据查询条件获取责任点信息（没有分页信息）
     */
    public DutyPoint getDutyPointByBean(DutyPoint dutyPoint);
    
    /**
     * 根据查询条件获取责任点列表信息（没有分页信息）
     */
    public List<DutyPoint> getDutyPointListByBean(DutyPoint dutyPoint);
    
    /**
     * 根据责任点模糊查询责任点信息
     * @return
     */
    public List<Map<String, Object>> getDutyPointByLike(String q);
}
