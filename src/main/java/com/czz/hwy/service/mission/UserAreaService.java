package com.czz.hwy.service.mission;

import java.util.List;

import com.czz.hwy.bean.mission.UserArea;

/**
 * 员工归属责任区管理功能service接口
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public interface UserAreaService{

    /**
     * 根据员工Id获取绑定关系记录。2016-09-29
     * @param userArea
     * @return
     */
    public UserArea getUserAreaByEmployeeId(UserArea userArea);

    /**
     * 新增一条员工归属责任区记录,2016-09-29
     * @param userArea
     * @return
     */
    public int insertUserAreaByBean(UserArea userArea);

    /**
     * 根据ID获取员工归属责任区关系信息，2016-09-29
     * @param id
     * @return
     */
    public UserArea getUserAreaById(String id);

    /**
     * 对一条关系记录解除绑定，2016-09-29
     * @param id
     * @return
     */
    public int deleteUserAreaById(String id);

    /**
     * 查询员工归属责任区记录总条数，2016-09-29
     * 2016-11-10 : 只查询环卫工
     * @param userArae
     * @return
     */
    public int getUserAreaCount(UserArea userArae);

    /**
     * 查询员工归属责任区记录集合，2016-09-29，分页
     * 2016-11-10 : 只查询环卫工
     * @param userArae
     * @return
     */
    public List<UserArea> getUserAreaList(UserArea userArae);

    /**
     * 查询员工归属责任区记录集合，2016-09-29，不分页
     * 2016-11-10 : 只查询环卫工
     * @param userArea
     * @return
     */
    public List<UserArea> getAllUserAreaListByBean(UserArea userArea);
    
    /**
     * 根据责任区Id获取绑定关系记录。2016-10-17
     * @param userArea
     * @return
     */
    public List<UserArea> getUserAreaByAreaId(UserArea userArea);
}
