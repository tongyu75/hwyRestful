/**
 * 
 */
package com.czz.hwy.service.area.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.area.DutyPointGather;
import com.czz.hwy.dao.area.DutyPointGatherDao;
import com.czz.hwy.service.area.DutyPointGatherService;

/**
 *  责任点考核点的采集信息Service接口
 */
@Service
@Transactional
public class DutyPointGatherServiceImpl implements DutyPointGatherService {

    @Autowired
    private DutyPointGatherDao dutyPointGatherDao;
    
    /**
     * 获取责任点考核点的采集信息
     * @param dutyPointGathers
     */
    public DutyPointGather getGatherPoint(DutyPointGather dutyPointGathers) {
        return dutyPointGatherDao.getInfoByBean("getDutyPointGatherByBean", dutyPointGathers);
    }
    
    /**
     * 获取责任点考核点的采集列表信息
     * @param dutyPointGathers
     */
    public List<DutyPointGather> getGatherPointList(DutyPointGather dutyPointGathers) {
        return dutyPointGatherDao.getInfoListByBean("getDutyPointGatherByBean", dutyPointGathers);
    }
    
    /**
     * 新增责任点考核点的采集信息
     * @param dutyPointGathers
     */
    public int insertGatherPoint(List<DutyPointGather> dutyPointGathers) {
        return dutyPointGatherDao.insertInfo("insertDutyPointGather", dutyPointGathers);
    }

    /**
     * 更新责任点考核点的采集信息
     * @param dutyPointGathers
     */
    public int updateGatherPoint(DutyPointGather dutyPointGather) {
        return dutyPointGatherDao.updateInfoByBean("updateDutyPointGather", dutyPointGather);
    }

}
