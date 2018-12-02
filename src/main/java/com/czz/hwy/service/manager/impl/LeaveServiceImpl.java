package com.czz.hwy.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.Leave;
import com.czz.hwy.dao.manager.LeaveDao;
import com.czz.hwy.service.manager.LeaveService;

/**
 * 请假管理功能service接口实现类，用于pc端接口
 * @Author 张咏雪
 * @createDate 2016-11-17
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    // 请假管理dao层接口
    @Autowired
    private LeaveDao leaveDao;
    
    /**
     * 查询请假信息记录总条数，2016-11-14
     */
	public int getLeaveCount(Leave leave) {
		return leaveDao.getInfoCount("getLeaveCount", leave);
	}

	/**
	 * 查询请假信息记录集合，2016-11-14，分页
	 */
	public List<Leave> getLeaveList(Leave leave) {
		return leaveDao.getInfoListByBean("getLeaveList", leave);
	}

}
