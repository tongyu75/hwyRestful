package com.czz.hwy.service.area.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.dao.area.DutyAreaDao;
import com.czz.hwy.service.area.DutyAreaService;
@Service
public class DutyAreaServiceImpl implements DutyAreaService {
    
    /**
    * 责任区域持久层接口
    */
    @Autowired
    private DutyAreaDao dutyAreaDao;
    
    /**
     * 根据查询条件获取责任区信息（带有分页信息）
     */
    public List<DutyArea> getDutyArea(DutyArea bean) {
       return dutyAreaDao.getInfoListByBean("getAllDutyArea", bean);
    }

    /**
     * 根据查询条件获取责任区信息的数量
     */
    public int getAreaCount(DutyArea bean) {
        return dutyAreaDao.getInfoCount("getAllDutyAreaCount", bean);
    }
    
    /**
     * 插入责任区信息
     */    
    public int insertDutyArea(DutyArea bean) {
        return dutyAreaDao.insertInfo("insertDutyArea", bean);
    }

    /**
     * 删除责任区信息
     */ 
    public int deleteDutyArea(DutyArea bean) {
        return dutyAreaDao.updateInfoByBean("updateDutyArea", bean);
    }
    
    /**
     * 根据查询条件获取责任区信息（没有分页信息）
     */
    public DutyArea getDutyAreaByBean(DutyArea dutyArea) {
        return dutyAreaDao.getInfoByBean("getDutyAreaByBean", dutyArea);
    }

    /**
     * 根据查询条件获取责任区列表信息（没有分页信息）
     */    
    public List<DutyArea> getDutyAreaListByBean(DutyArea dutyArea) {
        return dutyAreaDao.getInfoListByBean("getDutyAreaByBean", dutyArea);
    }    
    
    /**
     * 更新责任区信息
     */     
    public int updateDutyArea(DutyArea bean) {
        return dutyAreaDao.updateInfoByBean("updateDutyArea", bean);
    }

    /**
    * 责任区域模糊查询
    */
    public List<Map<String,Object>> getDutyAreaByLike(String areaName) {
        return dutyAreaDao.getInfoListMapByString("getDutyAreaByLike", areaName);
    }
    
    /**
     * 获取责任区（领导APP端）
     */
    public List<DutyArea> getDutyAreaForLeaderApp() {
       return dutyAreaDao.getAllInfo("getAllDutyAreaForLeaApp");
    }
    
    /**
     * 查询当前员工的所有责任区用于环卫工出勤率（当前环卫工出勤情况统计），2016-11-16
     * @return
     */
    public List <Map<String, Object>> selectAttendanceInspectArea() {
        return dutyAreaDao.getInfoListMap("selectAttendanceInspectArea");
    }
}
