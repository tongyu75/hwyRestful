package com.czz.hwy.service.mission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.dao.mission.UserAreaDao;
import com.czz.hwy.service.mission.UserAreaService;

/**
 *员工归属责任区管理的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
@Service
public class UserAreaServiceImpl implements UserAreaService {

    @Autowired
    private UserAreaDao userAreaDao;

    /**
     * 根据员工Id获取绑定关系记录。2016-09-29
     */
    public UserArea getUserAreaByEmployeeId(UserArea userArea) {
        return userAreaDao.getInfoByBean("getUserAreaByEmployeeId", userArea);
    }

    /**
     * 新增一条员工归属责任区记录,2016-09-29
     */
    public int insertUserAreaByBean(UserArea userArea) {
        return userAreaDao.insertInfo("insertUserAreaByBean", userArea);
    }

    /**
     * 根据ID获取员工归属责任区关系信息，2016-09-29
     */
    public UserArea getUserAreaById(String id) {
        return userAreaDao.getInfoById("getUserAreaById", id);
    }

    /**
     * 对一条关系记录解除绑定，2016-09-29
     */
    public int deleteUserAreaById(String id) {
        return userAreaDao.deleteInfoByPk("deleteUserAreaById", id);
    }

    /**
     * 查询员工归属责任区记录总条数，2016-09-29
     * 2016-11-10 : 只查询环卫工
     */
    public int getUserAreaCount(UserArea userArae) {
        return userAreaDao.getInfoCount("getUserAreaCount", userArae);
    }

    /**
     * 查询员工归属责任区记录集合，2016-09-29，分页
     * 2016-11-10 : 只查询环卫工
     */
    public List<UserArea> getUserAreaList(UserArea userArae) {
        return userAreaDao.getInfoListByBean("getUserAreaList", userArae);
    }

    /**
     * 查询员工归属责任区记录集合，2016-09-29，不分页
     * 2016-11-10 : 只查询环卫工
     */
    public List<UserArea> getAllUserAreaListByBean(UserArea userArea) {
        return userAreaDao.getInfoListByBean("getAllUserAreaListByBean", userArea);
    }
    
    /**
     * 根据责任区Id获取绑定关系记录。2016-10-17
     * @param userArea
     * @return
     */
    public List<UserArea> getUserAreaByAreaId(UserArea userArea) {
        return userAreaDao.getInfoListByBean("getUserAreaByAreaId", userArea);
    }
}
