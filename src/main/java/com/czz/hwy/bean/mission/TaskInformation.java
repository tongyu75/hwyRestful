package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 任务信息bean 功能描述，用于pc端
 * @author 张咏雪
 * @createDate 2016-11-14
 */
public class TaskInformation extends PageReqParamDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id; // 当前任务的id
	private String taskName; //任务名称
	private Date taskStart; // 任务开始时间
	private Date taskEnd; // 任务结束时间
	private String taskAddress; // 任务地点
	private String taskDutyPeople; // 任务责任人
	private int taskCreatePeople; // 任务的发布人id
	private String taskComment; // 任务的详细信息
	private int isArrived; // 人员是否到位 0表示新建任务 1表示到位 2 表示没有到位
	private int taskResult; // 任务是否完成 0 表示创建新的任务 1表示完成 2 表示没有完成
	private int taskStatus; // 任务是否有效 1 表示有效 2表示无效任务,任务状态：1进行中 2已结束
	private Date createAt; // 任务创建时间
	private Date updateAt; // 任务更新时间
	private int peopleNumber;//出任务  人数
	
	//不是表字段
	private String taskTime;//出任务时长
	private String taskCreatePeopleName;//任务的发布人名称
	

	public String getTaskCreatePeopleName() {
		return taskCreatePeopleName;
	}

	public void setTaskCreatePeopleName(String taskCreatePeopleName) {
		this.taskCreatePeopleName = taskCreatePeopleName;
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

	public Date getTaskStart() {
		return taskStart;
	}

	public void setTaskStart(Date taskStart) {
		this.taskStart = taskStart;
	}

	public Date getTaskEnd() {
		return taskEnd;
	}

	public void setTaskEnd(Date taskEnd) {
		this.taskEnd = taskEnd;
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

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	public int getIsArrived() {
		return isArrived;
	}

	public void setIsArrived(int isArrived) {
		this.isArrived = isArrived;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public int getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
}
