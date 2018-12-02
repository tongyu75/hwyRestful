package com.czz.hwy.bean.mission;

public class KindInfo {

	private int evalType;
	private String evalName;
	private int testNum;
	private int successNum;
	private int failNum;
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getTestNum() {
		return testNum;
	}
	public void setTestNum(int testNum) {
		this.testNum = testNum;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
}
