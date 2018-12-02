package com.czz.hwy.bean.mission.app;

import java.io.Serializable;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 监察举报统计实体类，用于app接口
 * @author 张咏雪，2017-04-14
 * @version V0.1
 */
public class ReportsCountApp extends PageReqParamDTO implements Serializable{

    private static final long serialVersionUID = -2828221651473895324L;
    
    private int evalValue; // 举报或监察类型ID
    private String evalName;//举报或监察类型名称
    private int number;//举报或监察次数
    
    private int areaId;//责任区ID
    private String areaName;//责任区名称
    
    private int days;//统计天数
    
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
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
	public int getEvalValue() {
		return evalValue;
	}
	public void setEvalValue(int evalValue) {
		this.evalValue = evalValue;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}