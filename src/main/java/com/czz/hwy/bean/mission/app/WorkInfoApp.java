package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 用于接收用户提交的考核分类详细信息实体类，用于app接口
  * @author 张咏雪   2016-11-08
  */
public class WorkInfoApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -932729141516828849L;
    
    private int id;// ID
    
    private int areaId ;//责任区ID
    
    private int pointId;//责任点ID 
    
    private int evalType;// 考核类型ID
   
    private int employeeId; // 检查者ID
    
    private int roleId;//检查者角色ID
    
    private String checkAddress;// 检查地址
    
    private Date checkTime;// 检查时间
   
    private String checkLat; // 检查纬度
    
    private String checkLng;// 检查经度
   
    private String checkValue; // 检查值
    
    private Date publishTime;// 发布时间
  
    private int checkStatus;  // 考核状态1：合格2：不合格
  
    private String otherReason;//考核为其他选项时的原因
    
    private List<Map<String, Object>> dutyPeopleList;//用于保存责任人列表
    
    private String employeeName;//检查人名称
    
    private double fines;//罚款金额
    
	public double getFines() {
		return fines;
	}
	public void setFines(double fines) {
		this.fines = fines;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public List<Map<String, Object>> getDutyPeopleList() {
		return dutyPeopleList;
	}
	public void setDutyPeopleList(List<Map<String, Object>> dutyPeopleList) {
		this.dutyPeopleList = dutyPeopleList;
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
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
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
	
	public String getCheckLat() {
		return checkLat;
	}
	public void setCheckLat(String checkLat) {
		this.checkLat = checkLat;
	}
	public String getCheckLng() {
		return checkLng;
	}
	public void setCheckLng(String checkLng) {
		this.checkLng = checkLng;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
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
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
}