package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 监察举报责任人，用于app接口
 * @author 张咏雪，2016-12-22
 * @version V0.1
 */
public class ReportDutyApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -7180922630395983879L;

    // ID
    private int id;
    private int supervisorId; // 监督举报ID(外键ID)
    private int areaId;//责任区ID
    private int pointId;//责任点ID
    private String pointName;//责任点名称
    private int employeeId;// 责任人ID
    private String employeeName;//责任人名称
    private double fines;//罚款金额
    private Date createAt; // 创建时间
    private Date updateAt;// 修改时间
    private int workType ;
    private int supervisorType; // 举报类型 
    
    //2017-04-17 
    private int days;//统计天数
    
    public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public double getFines() {
		return fines;
	}
	public void setFines(double fines) {
		this.fines = fines;
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
}