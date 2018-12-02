package com.czz.hwy.service.manager;

import java.util.List;

import com.czz.hwy.bean.manager.Leave;


/**
 * 请假管理功能service接口，用于pc端接口
 * @Author 张咏雪
 * @createDate 2016-11-17
 */
public interface LeaveService {

	/**
	 * 查询请假信息记录总条数，2016-11-14
	 * @param leave
	 * @return
	 */
	int getLeaveCount(Leave leave);

	/**
	 * 查询请假信息记录集合，2016-11-14，分页
	 * @param leave
	 * @return
	 */
	List<Leave> getLeaveList(Leave leave);
    
}
