package com.czz.hwy.service.mission.impl.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.CheckImageApp;
import com.czz.hwy.dao.mission.app.CheckImageAppDao;
import com.czz.hwy.service.mission.app.CheckImageAppService;

/**
 * 考核任务上传图片功能的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Service
public class CheckImageAppServiceImpl implements CheckImageAppService {
	
	@Autowired
	private CheckImageAppDao checkImageAppDao;

	/**
	 * 新增一条图片信息记录，2016-11-09
	 */
	public int insetCheckImageByBeanApp(CheckImageApp checkImage) {
		return checkImageAppDao.insertInfo("insetCheckImageByBeanApp", checkImage);
	}

	/**
	 * 根据外键ID和考核类型ID，删除图片信息。2016-11-09
	 */
	public int deleteCheckImageByBeanApp(CheckImageApp checkImageApp) {
		return checkImageAppDao.deleteInfoByBean("deleteCheckImageByBeanApp", checkImageApp);
	}

	
}
