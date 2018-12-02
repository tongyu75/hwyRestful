package com.czz.hwy.bean.user.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 生成考勤记录的bean对象
 * 功能描述
 * @author 万仁义 wanrenyi@chengzhongzhi.com
 * @company chnegzhongzhi
 * @createDate 2016年5月4日 下午5:45:20
 */
public class AttendanceForPlansApp extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id; //主键ID
	private int employeeId; //当前用户的员工号
	private int dutyAreaId; //责任区的id
	private int dutyPointId; //责任点的id
	private Date lastRecordTime; //最后一次记录时间
	private double lat; //考勤记录的经度
	private double lng; //考勤记录的纬度
	private Date dutyOnTime; //计划上班时间
	private Date dutyOffTime; //计划下班时间
	private String dutyOnStatus; //上班状态
	private String dutyOffStatus; //下班状态
	private Date createDate; //创建时间
	private Date updateTime; //更新时间
	private Date recordDate; //记录的时间
	private int slowDownNum;//记录怠工次数
	private String reason;//修改迟到状态的原因
	private int isCoverwork; // 是否代班人考勤代班记录(1是2不是)
	private String offStatusReason;//修改下班状态的原因
	
	
	//以下不是表段
	private String pointName;
	private String employeeName;//员工名称
	private int roleId;//角色Id
	private String roleName;//角色名称
	private int exceptionValue;//异常值，1上班异常   2下班异常
	private String exceptionText;//异常文本,与异常值对应， 1上班  2下班

	
	private int hasMobile;//是否有设备，1表示有 2表示无设备
	private String status;//人员查岗时，迟到处理的状态,上一班次查岗时，下班状态的处理状态
	
	//用于判断下班状态是否为早退用到的字段
	private int realPointNum;//该条考勤记录上班期间实际上传的坐标点数量
	private int shouldPointMaxNum;//该条考勤记录上班期间内，应该上传的坐标点最大数量。最大坐标数量 = 【下班时间 - 上班时间】的分钟数
	private int shouldPointMinNum;//该条考勤记录上班期间，应该上传的坐标点最小数量。最小坐标数量 = 【下班时间 - 上班时间】的分钟数 * X%,
	private Date maxRecordTime;//该条考勤记录上班期间内，在责任点的坐标的最大采集时间。
	private int timeDiff;//该条考勤记录上班期间内，在责任点的坐标的最大采集时间与下班时间比较的时间差
	
	
	public String getOffStatusReason() {
		return offStatusReason;
	}

	public void setOffStatusReason(String offStatusReason) {
		this.offStatusReason = offStatusReason;
	}

	public int getHasMobile() {
		return hasMobile;
	}

	public void setHasMobile(int hasMobile) {
		this.hasMobile = hasMobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRealPointNum() {
		return realPointNum;
	}

	public void setRealPointNum(int realPointNum) {
		this.realPointNum = realPointNum;
	}

	public int getShouldPointMaxNum() {
		return shouldPointMaxNum;
	}

	public void setShouldPointMaxNum(int shouldPointMaxNum) {
		this.shouldPointMaxNum = shouldPointMaxNum;
	}

	public int getShouldPointMinNum() {
		return shouldPointMinNum;
	}

	public void setShouldPointMinNum(int shouldPointMinNum) {
		this.shouldPointMinNum = shouldPointMinNum;
	}

	public Date getMaxRecordTime() {
		return maxRecordTime;
	}

	public void setMaxRecordTime(Date maxRecordTime) {
		this.maxRecordTime = maxRecordTime;
	}

	public int getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}

	public AttendanceForPlansApp(){
		
	}
	
	public AttendanceForPlansApp(AttendanceForPlansApp attendanceForPlans) {
		this.id = attendanceForPlans.getId();
		this.employeeId = attendanceForPlans.getEmployeeId();
		this.dutyAreaId = attendanceForPlans.getDutyAreaId();
		this.dutyPointId = attendanceForPlans.getDutyPointId();
		this.lastRecordTime = attendanceForPlans.getLastRecordTime();
		this.lat = attendanceForPlans.getLat();
		this.lng = attendanceForPlans.getLng();
		this.dutyOnTime = attendanceForPlans.getDutyOnTime();
		this.dutyOffTime = attendanceForPlans.getDutyOffTime();
		this.dutyOnStatus = attendanceForPlans.getDutyOnStatus();
		this.dutyOffStatus = attendanceForPlans.getDutyOffStatus();
		this.createDate = attendanceForPlans.getCreateDate();
		this.updateTime = attendanceForPlans.getUpdateTime();
		this.recordDate = attendanceForPlans.getRecordDate();
		this.slowDownNum = attendanceForPlans.getSlowDownNum();
		this.pointName = attendanceForPlans.getPointName();
		this.employeeName = attendanceForPlans.getEmployeeName();
		this.roleId = attendanceForPlans.getRoleId();
		this.roleName = attendanceForPlans.getRoleName();
		this.exceptionValue = attendanceForPlans.getExceptionValue();
		this.exceptionText = attendanceForPlans.getExceptionText();
		this.reason = attendanceForPlans.getReason();
	}
	public int getExceptionValue() {
		return exceptionValue;
	}
	public void setExceptionValue(int exceptionValue) {
		this.exceptionValue = exceptionValue;
	}
	public String getExceptionText() {
		return exceptionText;
	}
	public void setExceptionText(String exceptionText) {
		this.exceptionText = exceptionText;
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getSlowDownNum() {
		return slowDownNum;
	}
	public void setSlowDownNum(int slowDownNum) {
		this.slowDownNum = slowDownNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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
	public Date getLastRecordTime() {
		return lastRecordTime;
	}
	public void setLastRecordTime(Date lastRecordTime) {
		this.lastRecordTime = lastRecordTime;
	}
	public Date getDutyOnTime() {
		return dutyOnTime;
	}
	public void setDutyOnTime(Date dutyOnTime) {
		this.dutyOnTime = dutyOnTime;
	}
	public Date getDutyOffTime() {
		return dutyOffTime;
	}
	public void setDutyOffTime(Date dutyOffTime) {
		this.dutyOffTime = dutyOffTime;
	}
	public String getDutyOnStatus() {
		return dutyOnStatus;
	}
	public void setDutyOnStatus(String dutyOnStatus) {
		this.dutyOnStatus = dutyOnStatus;
	}
	public String getDutyOffStatus() {
		return dutyOffStatus;
	}
	public void setDutyOffStatus(String dutyOffStatus) {
		this.dutyOffStatus = dutyOffStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

    public int getIsCoverwork() {
        return isCoverwork;
    }

    public void setIsCoverwork(int isCoverwork) {
        this.isCoverwork = isCoverwork;
    }
	
}
