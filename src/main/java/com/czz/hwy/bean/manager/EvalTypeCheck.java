package com.czz.hwy.bean.manager;

import java.util.Date;

public class EvalTypeCheck {
	// 主键
	private Integer id ;
	// 考核值
	private Integer evalValue;
	// 考核名称
	private String evalName;
	// 创建时间
	private Date createAt;
	// 修改时间
	private Date updateAt;
	
	private Integer type;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEvalValue() {
		return evalValue;
	}

	public void setEvalValue(Integer evalValue) {
		this.evalValue = evalValue;
	}

	public String getEvalName() {
		return evalName;
	}

	public void setEvalName(String evalName) {
		this.evalName = evalName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
