package com.czz.hwy.service.area.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.area.DutyPoint;
import com.czz.hwy.dao.area.DutyPointDao;
import com.czz.hwy.service.area.DutyPointService;
@Service
public class DutyPointServiceImpl implements DutyPointService {
    
    @Autowired
    private DutyPointDao dutyPointDao;
    
    /**
     * 根据查询条件获取责任点信息（带有分页信息）
     */
    public List<DutyPoint> getAllDutyPoint(DutyPoint bean) {
       return dutyPointDao.getInfoListByBean("getAllDutyPoint", bean);
    }

    /**
     * 根据查询条件获取责任点信息的数量
     */
    public int getAllDutyPointCount(DutyPoint bean) {
        return dutyPointDao.getInfoCount("getAllDutyPointCount", bean);
    }

    /**
     * 插入责任点信息
     */
    public int insertDutyPoint(DutyPoint dutyPoint) {
        int count =dutyPointDao.insertInfo("insertDutyPoint", dutyPoint);
        return count;
    }

    /**
     * 更新责任点信息
     */
    public int updateDutyPoint(DutyPoint dutyPoint) {
        int count = dutyPointDao.updateInfoByBean("updateDutyPoint", dutyPoint);
        return count;
    }
    
    /**
     * 根据查询条件获取责任点信息（没有分页信息）
     */
    public DutyPoint getDutyPointByBean(DutyPoint dutyPoint) {
        DutyPoint point=dutyPointDao.getInfoByBean("getDutyPointByBean", dutyPoint);
        return point;
    }
    
    /**
     * 根据查询条件获取责任点列表信息（没有分页信息）
     */
    public List<DutyPoint> getDutyPointListByBean(DutyPoint dutyPoint) {
        List<DutyPoint> pointList=dutyPointDao.getInfoListByBean("getDutyPointByBean", dutyPoint);
        return pointList;
    }
    
    /**
     * 根据责任点模糊查询责任点信息
     */
    public List<Map<String, Object>> getDutyPointByLike(String q) {
        return dutyPointDao.getInfoListMapByString("getDutyPointByLike", q);
    }
}
