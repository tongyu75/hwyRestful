package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 排班计划管理的bean对象
 * 功能描述
 * @author 万仁义 wanrenyi@chengzhongzhi.com
 * @company chnegzhongzhi
 * @createDate 2016年5月19日 下午2:19:29
 */
public class DutyForBanci extends PageReqParamDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5018960766433726663L;

	//主键id
	private int id;
	
	//责任区的id
	private int  dutyAreaId;
	
	 // 应出勤责任区名称
    private String dutyAreaName;
	
	//责任点的id
	private int dutyPointId;
	
	 // 应出勤责任点名称
    private String dutyPointName;
	
	//工作方式 1 表示使用机械 2表示 人力
	private int dutyType;
	
	 // 任务类型名称
    private String dutyTypename;
	
	//计划出勤开始时间
	private Date dutyFromDate;
	
	//计划出勤结束时间
	private Date dutyToDate;
	
	//班次序号
	private int dutyNumber;
	
	//分配人的id
	private int distributorId;
	
	 // 分配者名字
    private String distributorName;
	
	//责任人的id
	private int employeeId;
	
	//责任人的姓名
	private String showname;
	
	//创建时间
	private Date createAt;
	
	//更新时间
	private Date updateAt;
	
	//出勤车辆
	private String plateNum;
	
	//角色类型 1表示环卫工 2表示检测员 3表示考核员
	private int rolesValue;
	
	//状态 1表示可用 2表示废弃 默认为1
	private int status;
	
	//数据唯一序列
	private String seq;
	
	//创建者id
	private int creatId;
	
	//更新者id
	private int updateId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDutyAreaId() {
		return dutyAreaId;
	}

	public void setDutyAreaId(int dutyAreaId) {
		this.dutyAreaId = dutyAreaId;
	}

	public int getDutyPointId() {
		return dutyPointId;
	}

	public void setDutyPointId(int dutyPointId) {
		this.dutyPointId = dutyPointId;
	}

	public int getDutyType() {
		return dutyType;
	}

	public void setDutyType(int dutyType) {
		this.dutyType = dutyType;
	}

	public Date getDutyFromDate() {
		return dutyFromDate;
	}

	public void setDutyFromDate(Date dutyFromDate) {
		this.dutyFromDate = dutyFromDate;
	}

	public Date getDutyToDate() {
		return dutyToDate;
	}

	public void setDutyToDate(Date dutyToDate) {
		this.dutyToDate = dutyToDate;
	}

	public int getDutyNumber() {
		return dutyNumber;
	}

	public void setDutyNumber(int dutyNumber) {
		this.dutyNumber = dutyNumber;
	}

	public int getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(int distributorId) {
		this.distributorId = distributorId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public String getPlateNum() {
		return plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	public int getRolesValue() {
		return rolesValue;
	}

	public void setRolesValue(int rolesValue) {
		this.rolesValue = rolesValue;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCreatId() {
		return creatId;
	}

	public void setCreatId(int creatId) {
		this.creatId = creatId;
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getDutyAreaName() {
		return dutyAreaName;
	}

	public void setDutyAreaName(String dutyAreaName) {
		this.dutyAreaName = dutyAreaName;
	}

	public String getDutyPointName() {
		return dutyPointName;
	}

	public void setDutyPointName(String dutyPointName) {
		this.dutyPointName = dutyPointName;
	}

	public String getDutyTypename() {
		return dutyTypename;
	}

	public void setDutyTypename(String dutyTypename) {
		this.dutyTypename = dutyTypename;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
