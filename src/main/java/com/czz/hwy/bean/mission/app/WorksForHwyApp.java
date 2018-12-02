package com.czz.hwy.bean.mission.app;

/**
 * 用于配置环卫云app考核中使用的参数,用于app接口
 * @author 张咏雪
 * @createDate 2016-11-09
 */
public class WorksForHwyApp {

	/**
	 * 用于判定某个员工是否出任务的缓冲时间，用于任务开始时间的缓冲时间，暂定为30分钟，意思就是如果当前时间在任务开始时间的前半个小时内，也认为是在出任务
	 */
	private int taskStartBufferTime;
	
	/**
	 * 用于判定某个员工是否出任务的缓冲时间，用于任务结束时间的缓冲时间，暂定为30分钟，意思就是如果当前时间在任务结束时间的后半个小时内，也认为是在出任务
	 */
	private int taskEndBufferTime;

	public int getTaskStartBufferTime() {
		return taskStartBufferTime;
	}

	public void setTaskStartBufferTime(int taskStartBufferTime) {
		this.taskStartBufferTime = taskStartBufferTime;
	}

	public int getTaskEndBufferTime() {
		return taskEndBufferTime;
	}

	public void setTaskEndBufferTime(int taskEndBufferTime) {
		this.taskEndBufferTime = taskEndBufferTime;
	}

}
