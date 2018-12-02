package com.czz.hwy.service.mission.app;

import com.czz.hwy.bean.mission.app.CheckImageApp;


/**
 * 考核任务上传图片功能的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
public interface CheckImageAppService{

	/**
	 * 新增一条图片信息记录，2016-11-09
	 * @param checkImage
	 * @return
	 */
	int insetCheckImageByBeanApp(CheckImageApp checkImage);

	/**
	 * 根据外键ID和考核类型ID，删除图片信息。2016-11-09
	 * @param checkImageApp
	 * @return
	 */
	int deleteCheckImageByBeanApp(CheckImageApp checkImageApp);

	
	
	
}
