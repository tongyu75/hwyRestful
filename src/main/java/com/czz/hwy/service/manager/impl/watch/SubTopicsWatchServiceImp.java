package com.czz.hwy.service.manager.impl.watch;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.watch.SubTopics;
import com.czz.hwy.dao.manager.watch.SubTopicsWatchDao;
import com.czz.hwy.service.manager.watch.SubTopicsWatchService;
/**
 * 获取订阅信息业务层实现类
 * 
 * @author 以克论净环卫管理系统    佟士儒
 * @version V1.0
 */
@Service
public class SubTopicsWatchServiceImp implements SubTopicsWatchService {
	//订阅信息持久层接口
	@Autowired
	private SubTopicsWatchDao subTopicsDao;
	/*
	 * 添加订阅信息
	 */
	public int insertSubTopics(String imei,int employeeId) {
		SubTopics subTopics = new SubTopics();
		subTopics.setDeviceId(imei);
		subTopics.setEmployeeId(employeeId);
		subTopics.setStatus(1);
		subTopics.setCreateAt(new Date());
		subTopics.setTopics(imei+employeeId);
		return subTopicsDao.insertInfo("insertSubTopicsWatch", subTopics);
	}
	/*
	 * 通过userId获得该订阅信息
	 */
	public SubTopics getSubTopicsByBean(int employeeId) {
		SubTopics subTopics = new SubTopics();
		subTopics.setEmployeeId(employeeId);
		SubTopics resultSubTopics = subTopicsDao.getInfoByBean("getSubTopicsByBeanWatch", subTopics);
		return resultSubTopics;
	}
	/*
	 * 更新订阅信息
	 */
	public int updateSubTopicsByBean(String imei,SubTopics subTopics) {
		subTopics.setDeviceId(imei);
		subTopics.setTopics(imei+subTopics.getEmployeeId());
		subTopics.setUpdateAt(new Date());
		int opinion=subTopicsDao.updateInfoByBean("updateSubTopicsWatch", subTopics);
		return opinion;
	}

}
