package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class CheckDuty implements Serializable {

    private static final long serialVersionUID = -4241673187405885385L;

    // ID
    private int id;
    // 检查ID
    private int checkId;
    // 考核类型ID
    private int evalType;
    // 责任人ID
    private int employeeId;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    //  关系类型
    private int workType;
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