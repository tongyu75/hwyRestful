package com.czz.hwy.bean.mission;

import java.util.Date;

/**
 * 任务管理对象的扩展对象
 * 功能描述
 * @author 万仁义 wanrenyi@chengzhongzhi.com
 * @company chnegzhongzhi
 * @createDate 2016年5月13日 上午11:36:48
 */
public class TaskInformationPojo extends TaskInformation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//当前时间
	private Date curronTime;

	public Date getCurronTime() {
		return curronTime;
	}

	public void setCurronTime(Date curronTime) {
		this.curronTime = curronTime;
	}
	
}
