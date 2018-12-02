package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 新的排班计划bean对象
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public class WorkPlans extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;//主键id
	
	private int areaId;//责任区ID（外键）
	
	private int pointId;//责任点ID（外键）
	
	private int employeeId;//值班人ID（外键）
	
	private int tradeEmployeeId;//换班人ID（外键）
	
	private int banzuId;//班组ID(外键)
	
	private int orderNum;//排序，用于指定同一个责任点排第几列
	
	private int tradeRate ;//换班频率:1 不循环,2 日循环，3周循环，4月循换，
	
	private Date time;//时分秒，用于日，周，月循换
	
	private int week;//周几，0表示7，用于周循环
	
	private int day;//每月几号，用于月循换
	
	private String oldplanIds;//用于保存旧版排班计划表的ID集合（作用是兼容旧系统）
	
	private int createId;//创建人Id
	
	private Date createAt;//创建时间
	
	private int updateId;//更新人Id
	
	private Date updateAt;//更新时间
	
	private int roleId;//角色ID，用于表示是哪个角色的排版计划。1 表示环卫工，2 表示检测员，8 办公人员，9 表示司机

	private String areaName;//责任区名称
	private String pointName;//责任点名称
	private String employeeName;//值班人名称
	private String tradeEmployeeName;//换班人名称
	private String banzuName;//班组名称
	private String createName;//创建人
	private String updateName;//更新人
	private String roleName;//责任区名称
	
	private String banciInfo;//班次信息
	
/*	// 检查时间
	private Date checkTime; 
	//计划上班时间
    private Date dutyOnTime;
    //计划下班时间
    private Date dutyOffTime;
    // 员工名称
	private String showName;
    // 电话号
    private String tel;*/
    
    private String areasName;//责任区名称集合，例如：第一责任区,第二责任区
    
    private String areasId;//责任区ID集合，例如：1,2
    
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getBanciInfo() {
		return banciInfo;
	}

	public void setBanciInfo(String banciInfo) {
		this.banciInfo = banciInfo;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public String getTradeEmployeeName() {
		return tradeEmployeeName;
	}

	public void setTradeEmployeeName(String tradeEmployeeName) {
		this.tradeEmployeeName = tradeEmployeeName;
	}

	public String getBanzuName() {
		return banzuName;
	}

	public void setBanzuName(String banzuName) {
		this.banzuName = banzuName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getTradeEmployeeId() {
		return tradeEmployeeId;
	}

	public void setTradeEmployeeId(int tradeEmployeeId) {
		this.tradeEmployeeId = tradeEmployeeId;
	}

	public int getBanzuId() {
		return banzuId;
	}

	public void setBanzuId(int banzuId) {
		this.banzuId = banzuId;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getTradeRate() {
		return tradeRate;
	}

	public void setTradeRate(int tradeRate) {
		this.tradeRate = tradeRate;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getOldplanIds() {
		return oldplanIds;
	}

	public void setOldplanIds(String oldplanIds) {
		this.oldplanIds = oldplanIds;
	}

	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getAreasName() {
		return areasName;
	}

	public void setAreasName(String areasName) {
		this.areasName = areasName;
	}

	public String getAreasId() {
		return areasId;
	}

	public void setAreasId(String areasId) {
		this.areasId = areasId;
	}
}
