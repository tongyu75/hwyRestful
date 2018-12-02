package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 考勤统计
 * @author 张咏雪  2016-5-5
 *
 */
public class AttendanceForPlans extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = -1594099608032805013L;
	
	private int id;//主键
	private int employeeId;//当前考勤员工的ID
	private int dutyAreaId;//责任区ID
	private int dutyPointId;//责任点ID
	private Date lastRecordTime;//最后一次采集考勤数据的时间
	private Date dutyOnTime;//计划上班时间
	private Date dutyOffTime;//计划下班时间
	private String getOnStatus;//上班状态
	private String getOffStatus;//下班状态
	private Date createAt;//创建时间
	private Date updateAt;//修改时间
	private Date recordDate;//记录日期
	private double lat; //考勤记录的经度
	private double lng; //考勤记录的纬度
	private int slowDownNum;//记录怠工次数
	
	//以下不是表字段
	private String employeeName;//当前考勤员工名称
	private String dutyAreaName;//责任区名称
	private String dutyPointName;//责任点名称
	private Date beginDate;//搜索开始日期
	private Date endDate;//搜索截止日期
	private String recoreDateS;//记录日期字符串类型
	
    public String getRecoreDateS() {
		return recoreDateS;
	}
	public void setRecoreDateS(String recoreDateS) {
		this.recoreDateS = recoreDateS;
	}
	// 责任区ID
    private int areaId;
    // 责任点ID
    private int pointId;
    // 开始时间
    private String startTimeStr;
    // 结束时间
    private String endTimeStr;
    // 责任人
    private String dutyPeopleName;
    // 角色ID
    private int roleId;
    
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
	public String getGetOnStatus() {
		return getOnStatus;
	}
	public void setGetOnStatus(String getOnStatus) {
		this.getOnStatus = getOnStatus;
	}
	public String getGetOffStatus() {
		return getOffStatus;
	}
	public void setGetOffStatus(String getOffStatus) {
		this.getOffStatus = getOffStatus;
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
    public int getSlowDownNum() {
        return slowDownNum;
    }
    public void setSlowDownNum(int slowDownNum) {
        this.slowDownNum = slowDownNum;
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
    public String getStartTimeStr() {
        return startTimeStr;
    }
    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
    public String getEndTimeStr() {
        return endTimeStr;
    }
    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
    public String getDutyPeopleName() {
        return dutyPeopleName;
    }
    public void setDutyPeopleName(String dutyPeopleName) {
        this.dutyPeopleName = dutyPeopleName;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
