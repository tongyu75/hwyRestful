package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 任务信息bean,用于app接口
 * @author 张咏雪
 * @createDate 2016-11-08
 */
public class TaskInformationApp extends PageReqParamDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id; // 当前任务的id
	private String taskName; //任务名称
	private Date taskStart; // 任务开始时间
	private Date taskEnd; // 任务结束时间
	private String taskAddress; // 任务地点
	private String taskDutyPeople; // 任务责任人的id
	private int taskCreatePeople; // 任务的发布人id
	private String taskCreatePeopleName; //任务发布人的姓名
	private String taskComment; // 任务的详细信息
	private int isArrived; // 人员是否到位 0表示新建任务 1表示到位 2 表示没有到位
	private int taskResult; // 任务是否完成 0 表示创建新的任务 1表示完成 2 表示没有完成
	private int taskStatus; // 任务状态  1 表示任务中 2表示任务完成
	private Date createAt; // 任务创建时间
	private Date updateAt; // 任务更新时间
	private int peopleNumber;//参加任务的人数
	
	private Date timeForSelect;//用于过滤任务开始日期与结束日期
	private int taskStartBufferTime;//用于判定某个员工是否出任务的缓冲时间，用于任务开始时间的缓冲时间，暂定为30分钟，意思就是如果当前时间在任务开始时间的前半个小时内，也认为是在出任务
	private int taskEndBufferTime;// 用于判定某个员工是否出任务的缓冲时间，用于任务结束时间的缓冲时间，暂定为30分钟，意思就是如果当前时间在任务结束时间的后半个小时内，也认为是在出任务
	
	private String taskdutypeopleId;//用于保存出任务ID，以逗号分隔
//	private int orderMarkTask; //是否允许结束任务 1表示允许 0表示不允许 默认不允许
	
//	private int roleId;//角色Id
	
	public int getId() {
		return id;
	}
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
	public Date getTimeForSelect() {
		return timeForSelect;
	}
	public void setTimeForSelect(Date timeForSelect) {
		this.timeForSelect = timeForSelect;
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
	public String getTaskCreatePeopleName() {
		return taskCreatePeopleName;
	}
	public void setTaskCreatePeopleName(String taskCreatePeopleName) {
		this.taskCreatePeopleName = taskCreatePeopleName;
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
    public String getTaskdutypeopleId() {
        return taskdutypeopleId;
    }
    public void setTaskdutypeopleId(String taskdutypeopleId) {
        this.taskdutypeopleId = taskdutypeopleId;
    }
	
}
