package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 考核分类bean对象,用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-07
 */
public class EvalTypeApp extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;//主键id
	
	private int evalValue;//考核分类值
	
	private String evalName;//考核分类名称
	
	private int type;//考核项目类别：1五克 2五分钟 3 举报 4监察
	
	private int role1;//举报标志，1是0否
	
	private int role2 ;//检测员监察标志，1是0否
	
	private int role3;//考核员监察标志，1是0否
	
	private int role4;//督察员监察标志，1是0否
	
	private Date createAt;//创建时间
	
	private Date updateAt;//更新时间
	
	private String remark;//备注

	private int isHwg;//是否是环卫工
	
	private int isJcy;//是否是检测员
	
	private int isKhy;//是否是考核员
	
	private String fine; //罚金

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRole1() {
		return role1;
	}

	public void setRole1(int role1) {
		this.role1 = role1;
	}

	public int getRole2() {
		return role2;
	}

	public void setRole2(int role2) {
		this.role2 = role2;
	}

	public int getRole3() {
		return role3;
	}

	public void setRole3(int role3) {
		this.role3 = role3;
	}

	public int getRole4() {
		return role4;
	}

	public void setRole4(int role4) {
		this.role4 = role4;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsHwg() {
		return isHwg;
	}

	public void setIsHwg(int isHwg) {
		this.isHwg = isHwg;
	}

	public int getIsJcy() {
		return isJcy;
	}

	public void setIsJcy(int isJcy) {
		this.isJcy = isJcy;
	}

	public int getIsKhy() {
		return isKhy;
	}

	public void setIsKhy(int isKhy) {
		this.isKhy = isKhy;
	}
}
