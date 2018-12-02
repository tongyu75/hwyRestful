package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class Reports extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -2828221651473895324L;

    // ID
    private int id;
    private String fromAddress;
    private String evalName;
    private String pointName;
    
    private String otherReason;//考核分类为其他时的原因
    
    public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	public String getCheckAddress() {
		return checkAddress;
	}
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	private String checkUser;
    // 检查地址
    private String checkAddress;
	// 考核类型ID
    private int evalType;
    public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	private String toAddress;
    public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	// 举报类型
    private int supervisorType;
    // 举报者
    private int supervisorUser;
    public int getSupervisorType() {
		return supervisorType;
	}
	public void setSupervisorType(int supervisorType) {
		this.supervisorType = supervisorType;
	}
	// 举报地址
    private String addressFrom;
    private int checkStatus=2;

    public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	// 举报时间
    private Date checkTime;
    // 举报纬度
    private double checkLat;
    // 举报经度
    private double checkLng;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 字符串格式的举报时间
    private String checkTimeStr;
    
    // 员工ID
    private int employeeId;
    // 开始时间
    private Date startTimeDate;
    // 结束时间
    private Date endTimeDate;
    // 开始时间
    private String startTimeStr;
    // 结束时间
    private String endTimeStr;
    // 角色ID
    private int roleId;
    // 责任区ID
    private int areaId;
    // 责任点ID
    private int pointId;
    // 监察或监督举报
    private String type;
    // 是否有图片
    private int imageFlag;
    // ID
    private int checkId;
    // 责任人
    private String dutyPeopleName;
    /**
	  * 设定字符串举报时间
	  * @param checkTimeStr
     */
	public String getCheckTimeStr() {
		return checkTimeStr;
	}
	/**
	  * 获取字符串举报时间
	  * @return checkTimeStr
     */
	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
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
	  * 设定举报者
	  * @param supervisorUser
      */
	public void setSupervisorUser(int supervisorUser) {
	   this.supervisorUser = supervisorUser;
	}
	/**
	  * 获取举报者
	  * @return 举报者
      */
	public int getSupervisorUser() {
	   return supervisorUser;
	}
	/**
	  * 设定举报地址
	  * @param addressFrom
      */
	public void setAddressFrom(String addressFrom) {
	   this.addressFrom = addressFrom;
	}
	/**
	  * 获取举报地址
	  * @return 举报地址
      */
	public String getAddressFrom() {
	   return addressFrom;
	}
	/**
	  * 设定举报时间
	  * @param checkTime
      */
	public void setCheckTime(Date checkTime) {
	   this.checkTime = checkTime;
	}
	/**
	  * 获取举报时间
	  * @return 举报时间
      */
	public Date getCheckTime() {
	   return checkTime;
	}
	/**
	  * 设定举报纬度
	  * @param checkLat
      */
	public void setCheckLat(double checkLat) {
	   this.checkLat = checkLat;
	}
	/**
	  * 获取举报纬度
	  * @return 举报纬度
      */
	public double getCheckLat() {
	   return checkLat;
	}
	/**
	  * 设定举报经度
	  * @param eheckLng
      */
	public void setCheckLng(double checkLng) {
	   this.checkLng = checkLng;
	}
	/**
	  * 获取举报经度
	  * @return 举报经度
      */
	public double getCheckLng() {
	   return checkLng;
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
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getStartTimeDate() {
        return startTimeDate;
    }
    public void setStartTimeDate(Date startTimeDate) {
        this.startTimeDate = startTimeDate;
    }
    public Date getEndTimeDate() {
        return endTimeDate;
    }
    public void setEndTimeDate(Date endTimeDate) {
        this.endTimeDate = endTimeDate;
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
    public int getEvalType() {
        return evalType;
    }
    public void setEvalType(int evalType) {
        this.evalType = evalType;
    }
    public int getImageFlag() {
        return imageFlag;
    }
    public void setImageFlag(int imageFlag) {
        this.imageFlag = imageFlag;
    }
    public int getCheckId() {
        return checkId;
    }
    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }
    public String getDutyPeopleName() {
        return dutyPeopleName;
    }
    public void setDutyPeopleName(String dutyPeopleName) {
        this.dutyPeopleName = dutyPeopleName;
    }
    public int getPointId() {
        return pointId;
    }
    public void setPointId(int pointId) {
        this.pointId = pointId;
    }
}