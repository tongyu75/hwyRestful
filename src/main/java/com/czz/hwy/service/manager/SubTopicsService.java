package com.czz.hwy.service.manager;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.SubTopics;
@Service
/**
 * 订阅信息业务层接口
 * 
 * @author 以克论净环卫管理系统  佟士儒
 * @version V1.0
 */
public interface SubTopicsService {
	/*
	 * 添加订阅信息
	 */
	public int insertSubTopics(String imei,int userId);
	/*
	 * 通过userId获得当前订阅信息
	 */
	public SubTopics getSubTopicsByBean(int userId);
	/*
	 * 更新订阅信息
	 */
	public int updateSubTopicsByBean(String imei,SubTopics subTopics);

}
