package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 监察举报实体类，用于app接口
 * @author 张咏雪，2016-12-22
 * @version V0.1
 */
public class ReportsApp extends PageReqParamDTO implements Serializable{

    private static final long serialVersionUID = -2828221651473895324L;

    //表结构
    // ID
    private int id;  //主键
    private int supervisorType; //举报类型
    private int supervisorUser; // 举报者ID
    private String supervisorName; // 举报者名称
    private String addressFrom; // 举报地址
    private Date checkTime; // 举报时间
    private double checkLat;// 举报纬度
    private double checkLng; // 举报经度
    private Date createAt;// 创建时间
    private Date updateAt;// 修改时间
    private int status;//状态：1合格2不合格
    private String otherReason;//考核分类为其他时的原因
    
    //不是表结构
    private double fines;//罚款金额
    
    private int checkStatus=2;
    private String fromAddress; //监察地址
    private int evalType; // 考核类型ID
    private String evalName;//考核类型名称
    private String pointName;//责任点ID
	private String checkUser;
    private String checkAddress; // 检查地址
    private String toAddress;
    private String checkTimeStr;// 字符串格式的举报时间
    
    
    private int employeeId;// 员工ID
    private Date startTimeDate;// 开始时间
    private Date endTimeDate; // 结束时间
    private String startTimeStr; // 开始时间
    private String endTimeStr; // 结束时间
    private int roleId; // 角色ID
    private int areaId;  // 责任区ID
    
    private String type;// 监察或监督举报
    private int imageFlag; // 是否有图片
    private int checkId; // ID
    private String dutyPeopleName;// 责任人名称
    
    //2017-04-17
    private String areaName;//责任区名称
    private int days;//统计天数
    
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public double getFines() {
		return fines;
	}
	public void setFines(double fines) {
		this.fines = fines;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSupervisorType() {
		return supervisorType;
	}
	public void setSupervisorType(int supervisorType) {
		this.supervisorType = supervisorType;
	}
	public int getSupervisorUser() {
		return supervisorUser;
	}
	public void setSupervisorUser(int supervisorUser) {
		this.supervisorUser = supervisorUser;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public int getEvalType() {
		return evalType;
	}
	public void setEvalType(int evalType) {
		this.evalType = evalType;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckAddress() {
		return checkAddress;
	}
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getCheckTimeStr() {
		return checkTimeStr;
	}
	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public Date getStartTimeDate() {
		return startTimeDate;
	}
	public void setStartTimeDate(Date startTimeDate) {
		this.startTimeDate = startTimeDate;
	}
	public Date getEndTimeDate() {
		return endTimeDate;
	}
	public void setEndTimeDate(Date endTimeDate) {
		this.endTimeDate = endTimeDate;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getImageFlag() {
		return imageFlag;
	}
	public void setImageFlag(int imageFlag) {
		this.imageFlag = imageFlag;
	}
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public String getDutyPeopleName() {
		return dutyPeopleName;
	}
	public void setDutyPeopleName(String dutyPeopleName) {
		this.dutyPeopleName = dutyPeopleName;
	}
  
}