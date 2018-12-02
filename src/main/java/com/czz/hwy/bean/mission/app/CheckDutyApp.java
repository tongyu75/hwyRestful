package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 提交的考核分类任务记录对应责任人列表，用于app接口
  * @author 张咏雪  2016-11-07
  */
public class CheckDutyApp extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -4241673187405885385L;

    
    private int id;// ID
    
    private int areaId;//责任区ID
    
    private int pointId;//责任点ID
    
    private int checkId;//提交考核记录ID（外键，对应着checktime,checkgram表中的id主键）
   
    private int evalType; // 考核类型ID
    
    private int employeeId; // 责任人ID
   
    private Date createAt; // 创建时间
    
    private Date updateAt;// 修改时间
    
    private int workType; //  关系类型，暂时不知道干什么的
    
    private String employeeName;//责任人名称
    
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
	public int getWorkType() {
		return workType;
	}
	public void setWorkType(int workType) {
		this.workType = workType;
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
	  * 设定检查ID
	  * @param checkId
      */
	public void setCheckId(int checkId) {
	   this.checkId = checkId;
	}
	/**
	  * 获取检查ID
	  * @return 检查ID
      */
	public int getCheckId() {
	   return checkId;
	}
	/**
	  * 设定考核类型
	  * @param evalType
      */
	public void setEvalType(int evalType) {
	   this.evalType = evalType;
	}
	/**
	  * 获取考核类型
	  * @return 考核类型
      */
	public int getEvalType() {
	   return evalType;
	}
	/**
	  * 设定责任人ID
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取责任人ID
	  * @return 责任人ID
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定创建时间
	  * @param createAt
      */
	public void setCreateAt(Date createAt) {
	   this.createAt = createAt;
	}
	/**
	  * 获取创建时间
	  * @return 创建时间
      */
	public Date getCreateAt() {
	   return createAt;
	}
	/**
	  * 设定修改时间
	  * @param updateAt
      */
	public void setUpdateAt(Date updateAt) {
	   this.updateAt = updateAt;
	}
	/**
	  * 获取修改时间
	  * @return 修改时间
      */
	public Date getUpdateAt() {
	   return updateAt;
	}
}