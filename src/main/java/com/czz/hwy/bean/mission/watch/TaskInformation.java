package com.czz.hwy.bean.mission.watch;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 任务信息bean 功能描述
 * 
 * @author 万仁义 wanrenyi@chengzhongzhi.com
 * @company chnegzhongzhi
 * @createDate 2016年5月10日 下午1:56:20
 */
public class TaskInformation extends PageReqParamDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id; // 当前任务的id
	private String taskName; //任务名称
	private Date taskStartTime; // 任务开始时间
	private Date taskEndTime; // 任务结束时间
	private String taskAddress; // 任务地点
	private String taskDutyPeople; // 任务责任人
	private int taskCreatePeople; // 任务的发布人id
	private String taskContent; // 任务的详细信息
	private int isArrive; // 人员是否到位 0表示新建任务 1表示到位 2 表示没有到位
	private int taskResult; // 任务是否完成 0 表示创建新的任务 1表示完成 2 表示没有完成
	private int taskStatus; // 任务是否有效 1 表示有效 2表示无效任务,任务状态：1进行中 2已结束
	private Date createDate; // 任务创建时间
	private Date updateDate; // 任务更新时间
	private int peopleNumber;//出任务  人数
	
	//不是表字段
	private String taskTime;//出任务时长
	
	
	public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public String getTaskAddress() {
		return taskAddress;
	}
	public void setTaskAddress(String taskAddress) {
		this.taskAddress = taskAddress;
	}
	public String getTaskDutyPeople() {
		return taskDutyPeople;
	}
	public void setTaskDutyPeople(String taskDutyPeople) {
		this.taskDutyPeople = taskDutyPeople;
	}
	public int getTaskCreatePeople() {
		return taskCreatePeople;
	}
	public void setTaskCreatePeople(int taskCreatePeople) {
		this.taskCreatePeople = taskCreatePeople;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public int getIsArrive() {
		return isArrive;
	}
	public void setIsArrive(int isArrive) {
		this.isArrive = isArrive;
	}
	public int getTaskResult() {
		return taskResult;
	}
	public void setTaskResult(int taskResult) {
		this.taskResult = taskResult;
	}
	public int getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	

}
