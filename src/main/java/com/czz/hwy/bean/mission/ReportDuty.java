package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class ReportDuty extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -7180922630395983879L;

    // ID
    private int id;
    // 举报ID
    private int supervisorId;
    // 责任人ID
    private int employeeId;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    private int workType ;
    // 责任区id
    private int areaId;
    // 责任点id
    private int pointId;
    public int getWorkType() {
		return workType;
	}
	public void setWorkType(int workType) {
		this.workType = workType;
	}
	// 举报类型 
    private int supervisorType;
	public int getSupervisorType() {
		return supervisorType;
	}
	public void setSupervisorType(int supervisorType) {
		this.supervisorType = supervisorType;
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
	
}