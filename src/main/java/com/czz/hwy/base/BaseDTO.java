package com.czz.hwy.base;

import java.io.Serializable;

/**
 * DTO公共类，所有业务涉及的公共字段
 * BaseBusinessDTO 是调用SIP公共发送和返回报文头信息
 * @author 陈瑶
 *
 */
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键
    private int pageSize; //每页记录数
    private int pageNo;	//页码
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
    
}
