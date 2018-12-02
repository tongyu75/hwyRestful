package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 五分钟考核项目，详细信息，用于接收提交的五分钟考核项目详细信息，用于app接口
  * @author 张咏雪 2016-09-12
  * @version V0.1
  */
public class CheckTimeForSelectApp  extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -4975251178243077962L;
    
    private int areaId;//责任区ID
    private String areaName;//责任区名称
    private int pointId;//责任点ID
    private String pointName;//责任点名称
    private int checkId;//五分钟考核记录ID（责任人关系表的外键）
    private int evalType;//考核类型ID
    private String evalName;//考核名称
    private int submitEmployeeId;//提交人ID
    private String submitEmployeeName;//提交人名称
    private int dutyEmployeeId;//责任人ID
    private String dutyEmployeeName;//责任人名称
    private String checkAddress;//提交地址
    private double checkLat;//提交纬度
    private double checkLng;//提交经度
    private String checkStatus;//五分钟检测结果，合格，不合格,未审核
    private Date checkTime;//提交时间
    private String remark;//五分钟备注,记录其他选项的原因
    private int imageCount;//图片数量，用于app获取图片路径
    
    private double fines;//罚款金额
    
    public double getFines() {
		return fines;
	}
	public void setFines(double fines) {
		this.fines = fines;
	}
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public int getImageCount() {
		return imageCount;
	}
	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	public int getEvalType() {
		return evalType;
	}
	public void setEvalType(int evalType) {
		this.evalType = evalType;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public int getSubmitEmployeeId() {
		return submitEmployeeId;
	}
	public void setSubmitEmployeeId(int submitEmployeeId) {
		this.submitEmployeeId = submitEmployeeId;
	}
	public String getSubmitEmployeeName() {
		return submitEmployeeName;
	}
	public void setSubmitEmployeeName(String submitEmployeeName) {
		this.submitEmployeeName = submitEmployeeName;
	}
	public int getDutyEmployeeId() {
		return dutyEmployeeId;
	}
	public void setDutyEmployeeId(int dutyEmployeeId) {
		this.dutyEmployeeId = dutyEmployeeId;
	}
	public String getDutyEmployeeName() {
		return dutyEmployeeName;
	}
	public void setDutyEmployeeName(String dutyEmployeeName) {
		this.dutyEmployeeName = dutyEmployeeName;
	}
	public String getCheckAddress() {
		return checkAddress;
	}
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	public double getCheckLat() {
		return checkLat;
	}
	public void setCheckLat(double checkLat) {
		this.checkLat = checkLat;
	}
	public double getCheckLng() {
		return checkLng;
	}
	public void setCheckLng(double checkLng) {
		this.checkLng = checkLng;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
    
}