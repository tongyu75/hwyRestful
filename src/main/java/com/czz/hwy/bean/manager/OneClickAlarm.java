package com.czz.hwy.bean.manager;

import java.io.Serializable;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 一键报警
  * @author 张咏雪 2016-5-17
  */
public class OneClickAlarm extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -815841541341918444L;

    private String id;//主键ID
    private String employeeId;//员工ID
    private String lat;//报警纬度
    private String lng;//报警纬度
    private String address;//报警地址
    private String alarmAt;//报警时间
    private String injurySituation;//受伤情况描述
    private String alarmStatus;//报警通知情况，1已发通知
    private String result;//处理结果
    private String updateId;//修改人ID
    private String updateAt;//修改时间
    
    //以下不是表字段
    private String employeeName;//员工名称
    private String beginDateStr;//报警开始日期
    private String endDateStr;//报警结束日期
    private String updateName;//修改人名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlarmAt() {
		return alarmAt;
	}

	public void setAlarmAt(String alarmAt) {
		this.alarmAt = alarmAt;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getInjurySituation() {
		return injurySituation;
	}

	public void setInjurySituation(String injurySituation) {
		this.injurySituation = injurySituation;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}