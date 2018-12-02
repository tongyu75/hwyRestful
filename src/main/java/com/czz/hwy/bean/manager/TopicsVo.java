package com.czz.hwy.bean.manager;

import java.util.Date;

public class TopicsVo {
	
	private int employeeId;
	private int id;
	private int vsrsionCode ;
	private String title;
	private String content;
	private Date createA;
	private String type;
	private int appealStatus; //是否允许申诉
	private int appealResult;//申诉结果
	
	public int getAppealResult() {
		return appealResult;
	}
	public void setAppealResult(int appealResult) {
		this.appealResult = appealResult;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppealStatus() {
		return appealStatus;
	}
	public void setAppealStatus(int appealStatus) {
		this.appealStatus = appealStatus;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getVsrsionCode() {
		return vsrsionCode;
	}
	public void setVsrsionCode(int vsrsionCode) {
		this.vsrsionCode = vsrsionCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateA() {
		return createA;
	}
	public void setCreateA(Date createA) {
		this.createA = createA;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
