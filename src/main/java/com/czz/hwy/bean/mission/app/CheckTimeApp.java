package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 五分钟考核实体类，用于app接口
  * @author 张咏雪  2016-11-07
  */
public class CheckTimeApp extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -2182588850631651803L;

    private int id;//主键ID
    
    private int evalType; // 考核类型ID
    
    private String evalName; //考核类型名称
   
    private int employeeId; // 检查者ID
   
    private int supervisorId; // 举报ID（检查者ID）
   
    private String checkAddress; // 检查地址
   
    private Date checkTime; // 检查时间
   
    private Date publishTime; // 发布时间
    
    private double checkLat;// 检查纬度
   
    private double checkLng; // 检查经度
    
    private int checkStatus;// 考核状态1：合格2：不合格，现在提交的所有五克考核项目都是默认为2不合格
    
    private String remark;//五克检测说明
    
    private String employeeName;//检查人姓名
    
    
//    private double overtime;
//    private String fromAddress;
//    private String toAddress;
//    private String pointName;
//    private String checkUser;
   
	public String getEvalName() {
		return evalName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
    
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	  * 设定ID
	  * @param id
      */
	public void setId(int id) {
	   this.id = id;
	}
	/**
	  * 获取ID
	  * @return ID
      */
	public int getId() {
	   return id;
	}
	/**
	  * 设定考核类型Type
	  * @param evalType
      */
	public void setEvalType(int evalType) {
	   this.evalType = evalType;
	}
	/**
	  * 获取考核类型Type
	  * @return 考核类型Type
      */
	public int getEvalType() {
	   return evalType;
	}
	/**
	  * 设定检查者ID
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取检查者ID
	  * @return 检查者ID
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定举报ID
	  * @param supervisorId
      */
	public void setSupervisorId(int supervisorId) {
	   this.supervisorId = supervisorId;
	}
	/**
	  * 获取举报ID
	  * @return 举报ID
      */
	public int getSupervisorId() {
	   return supervisorId;
	}
	/**
	  * 设定检查地址
	  * @param checkAddress
      */
	public void setCheckAddress(String checkAddress) {
	   this.checkAddress = checkAddress;
	}
	/**
	  * 获取检查地址
	  * @return 检查地址
      */
	public String getCheckAddress() {
	   return checkAddress;
	}
	/**
	  * 设定检查时间
	  * @param checkTime
      */
	public void setCheckTime(Date checkTime) {
	   this.checkTime = checkTime;
	}
	/**
	  * 获取检查时间
	  * @return 检查时间
      */
	public Date getCheckTime() {
	   return checkTime;
	}
	/**
	  * 设定发布时间
	  * @param publishTime
      */
	public void setPublishTime(Date publishTime) {
	   this.publishTime = publishTime;
	}
	/**
	  * 获取发布时间
	  * @return 发布时间
      */
	public Date getPublishTime() {
	   return publishTime;
	}
	/**
	  * 设定检查纬度
	  * @param checkLat
      */
	public void setCheckLat(double checkLat) {
	   this.checkLat = checkLat;
	}
	/**
	  * 获取检查纬度
	  * @return 检查纬度
      */
	public double getCheckLat() {
	   return checkLat;
	}
	/**
	  * 设定检查经度
	  * @param checkLng
      */
	public void setCheckLng(double checkLng) {
	   this.checkLng = checkLng;
	}
	/**
	  * 获取检查经度
	  * @return 检查经度
      */
	public double getCheckLng() {
	   return checkLng;
	}
	/**
	  * 设定考核状态1：合格2：不合格
	  * @param checkStatus
      */
	public void setCheckStatus(int checkStatus) {
	   this.checkStatus = checkStatus;
	}
	/**
	  * 获取考核状态1：合格2：不合格
	  * @return 考核状态1：合格2：不合格
      */
	public int getCheckStatus() {
	   return checkStatus;
	}
}