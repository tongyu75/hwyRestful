package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 用于接收环卫工、检测员、考核员提交的举报信息，用于APP接口
  * @author 张咏雪   2017-04-24
  */
public class ReportInfoApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -932729141516828849L;
    
    private int id;//主键ID
    private int supervisorId;//举报人ID
    private String supervisorName;//举报者名称
    private int supervisorRole;//举报人角色
    private String areaIds;//举报人责任区ID集合，以逗号分隔
    private int evalValue;//举报类型
    private String addressFrom;//举报地址
    private Date checkTime;//举报时间
    private double checkLat;//举报纬度
    private double checkLng;//举报经度
    private String memo;//举报说明
   
    private int status;//处理状态：1已处理 2未处理;
    private int manageRole;//当前处理人角色
    private String manageMemo;//处理说明
    
    private Date createAt;//记录时间
    private Date updateAt;//更新时间
    
    private int jcyManageId;//检测员处理人ID
    private String jcyManageName;//检测员处理人
    private int jcyManageStatus;//检测员处理状态，1 已处理 2 未处理 3已上报
    private Date jcyManageDate;//检测员处理时间
    private String jcyManageMemo;//检测员上报或处理说明
    
    private int khyManageId;//考核员处理人ID
    private String khyManageName;//考核员处理人
    private int khyManageStatus;//考核员处理状态，1 已处理 2 未处理 3已上报
    private Date khyManageDate;//考核员处理时间
    private String khyManageMemo;//考核员上报或处理说明
    
    private int dcyManageId;//督察员处理人ID
    private String dcyManageName;//督察员处理人
    private int dcyManageStatus;//督察员处理状态，1 已处理 2 未处理 3已上报
    private Date dcyManageDate;//督察员处理时间
    private String dcyManageMemo;//督察员上报或处理说明
    
    //不是表字段
    private String evalName;//举报类型名称
    private int dealBeforImageCount;//处理之前上传的图片数量
    private int dealAfterImageCount;//处理之后上传的图片数量
    
    //2017-04-26
    //用作查询举报记录的条件
    private String startDateStr;//查询开始日期，用于过滤举报时间
    private String endDateStr;//查询结束日期，用于过滤举报时间
    private int employeeId;//查询举报记录的人的员工ID
    private int roleId;//查询举报记录人的角色ID
   //用作查询举报记录的条件
    
    //2017-04-26
    //上报或处理所用字段
    private int manageId;//上报人或处理人ID
    private String manageName;//上报人或处理人名称
    
    //2017-04-27 举报统计所用参数
    private int days;//统计天数
    
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getManageId() {
		return manageId;
	}
	public void setManageId(int manageId) {
		this.manageId = manageId;
	}
	public String getManageName() {
		return manageName;
	}
	public void setManageName(String manageName) {
		this.manageName = manageName;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public int getDealBeforImageCount() {
		return dealBeforImageCount;
	}
	public void setDealBeforImageCount(int dealBeforImageCount) {
		this.dealBeforImageCount = dealBeforImageCount;
	}
	public int getDealAfterImageCount() {
		return dealAfterImageCount;
	}
	public void setDealAfterImageCount(int dealAfterImageCount) {
		this.dealAfterImageCount = dealAfterImageCount;
	}
	public String getManageMemo() {
		return manageMemo;
	}
	public void setManageMemo(String manageMemo) {
		this.manageMemo = manageMemo;
	}
	public int getEvalValue() {
		return evalValue;
	}
	public void setEvalValue(int evalValue) {
		this.evalValue = evalValue;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public int getSupervisorRole() {
		return supervisorRole;
	}
	public void setSupervisorRole(int supervisorRole) {
		this.supervisorRole = supervisorRole;
	}
	public String getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	
	public String getAddressFrom() {
		return addressFrom;
	}
	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public double getCheckLat() {
		return checkLat;
	}
	public void setCheckLat(double checkLat) {
		this.checkLat = checkLat;
	}
	public double getCheckLng() {
		return checkLng;
	}
	public void setCheckLng(double checkLng) {
		this.checkLng = checkLng;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getManageRole() {
		return manageRole;
	}
	public void setManageRole(int manageRole) {
		this.manageRole = manageRole;
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
	public int getJcyManageId() {
		return jcyManageId;
	}
	public void setJcyManageId(int jcyManageId) {
		this.jcyManageId = jcyManageId;
	}
	public String getJcyManageName() {
		return jcyManageName;
	}
	public void setJcyManageName(String jcyManageName) {
		this.jcyManageName = jcyManageName;
	}
	public int getJcyManageStatus() {
		return jcyManageStatus;
	}
	public void setJcyManageStatus(int jcyManageStatus) {
		this.jcyManageStatus = jcyManageStatus;
	}
	public Date getJcyManageDate() {
		return jcyManageDate;
	}
	public void setJcyManageDate(Date jcyManageDate) {
		this.jcyManageDate = jcyManageDate;
	}
	public String getJcyManageMemo() {
		return jcyManageMemo;
	}
	public void setJcyManageMemo(String jcyManageMemo) {
		this.jcyManageMemo = jcyManageMemo;
	}
	public int getKhyManageId() {
		return khyManageId;
	}
	public void setKhyManageId(int khyManageId) {
		this.khyManageId = khyManageId;
	}
	public String getKhyManageName() {
		return khyManageName;
	}
	public void setKhyManageName(String khyManageName) {
		this.khyManageName = khyManageName;
	}
	public int getKhyManageStatus() {
		return khyManageStatus;
	}
	public void setKhyManageStatus(int khyManageStatus) {
		this.khyManageStatus = khyManageStatus;
	}
	public Date getKhyManageDate() {
		return khyManageDate;
	}
	public void setKhyManageDate(Date khyManageDate) {
		this.khyManageDate = khyManageDate;
	}
	public String getKhyManageMemo() {
		return khyManageMemo;
	}
	public void setKhyManageMemo(String khyManageMemo) {
		this.khyManageMemo = khyManageMemo;
	}
	public int getDcyManageId() {
		return dcyManageId;
	}
	public void setDcyManageId(int dcyManageId) {
		this.dcyManageId = dcyManageId;
	}
	public String getDcyManageName() {
		return dcyManageName;
	}
	public void setDcyManageName(String dcyManageName) {
		this.dcyManageName = dcyManageName;
	}
	public int getDcyManageStatus() {
		return dcyManageStatus;
	}
	public void setDcyManageStatus(int dcyManageStatus) {
		this.dcyManageStatus = dcyManageStatus;
	}
	public Date getDcyManageDate() {
		return dcyManageDate;
	}
	public void setDcyManageDate(Date dcyManageDate) {
		this.dcyManageDate = dcyManageDate;
	}
	public String getDcyManageMemo() {
		return dcyManageMemo;
	}
	public void setDcyManageMemo(String dcyManageMemo) {
		this.dcyManageMemo = dcyManageMemo;
	}
    
}