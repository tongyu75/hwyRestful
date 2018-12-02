package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class WorkInfo extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -932729141516828849L;

    // ID
    private int id;
    // 考核类型ID
    private int evalType;
    // 检查者ID
    private int employeeId;
    // 检查地址
    private String checkAddress;
    // 检查时间
    private Date checkTime;
    // 检查纬度
    private double checkLat;
    // 检查经度
    private double checkLng;
    // 检查值
    private double checkValue;
    // 发布时间
    private Date publishTime;
    // 考核状态1：合格2：不合格
    private int checkStatus;
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
	  * 设定考核类型ID
	  * @param evalType
      */
	public void setEvalType(int evalType) {
	   this.evalType = evalType;
	}
	/**
	  * 获取考核类型ID
	  * @return 考核类型ID
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
	  * 设定检查值
	  * @param checkValue
      */
	public void setCheckValue(double checkValue) {
	   this.checkValue = checkValue;
	}
	/**
	  * 获取检查值
	  * @return 检查值
      */
	public double getCheckValue() {
	   return checkValue;
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