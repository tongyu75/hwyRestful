package com.czz.hwy.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.AdminLogs;
import com.czz.hwy.dao.syslogs.AdminLogsDao;
import com.czz.hwy.service.manager.AdminLogsService;

/**
 * 系统日志servie接口的实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-7-6
 */
@Service
public class AdminLogsServiceImpl implements AdminLogsService {

	@Autowired
	private AdminLogsDao adminLogsDao;

	/**
	 * 插入一条系统日志
	 */
	public int insertAdminLogs(AdminLogs adminLogs) {
		return adminLogsDao.insertInfo("insertAdminLogs", adminLogs);
	}
	
	
}
