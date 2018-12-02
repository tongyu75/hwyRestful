/**
 * 
 */
package com.czz.hwy.bean.area;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 考核点bean
 * 
 * @author 张纪才
 *
 */
public class EvalPoint extends PageReqParamDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8069788863853502015L;

	private int id;
	
	private int area_id;// 责任区ID
	
	private int point_id;// 责任点ID
	
	private String checkpoint_name;// 考核点名称
	
	private String checkpoint_address;// 考核点地址
	
	private double checkpoint_lat;// 考核点纬度
	
	private double checkpoint_lng;// 考核点经度
	
	private double offset;// 偏移值
	
	private int checkpoint_order;// 考核点顺序
	
	private int isarray;// 是否必达
	
	private int ischeck;// 是否考核
	
	private double stoptime;// 停留时间
	
	private String remain;// 预留字段
	
	private Date create_at;// 创建时间
	
	private Date update_at;// 修改时间
	
	private int create_id;// 创建者
	
	private int update_id;// 修改者
	
	private int status;// 状态（1:启用2：废止）
	
	private int file_id;//导入文件的主键id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public int getPoint_id() {
		return point_id;
	}

	public void setPoint_id(int point_id) {
		this.point_id = point_id;
	}

	public String getCheckpoint_name() {
		return checkpoint_name;
	}

	public void setCheckpoint_name(String checkpoint_name) {
		this.checkpoint_name = checkpoint_name;
	}

	public String getCheckpoint_address() {
		return checkpoint_address;
	}

	public void setCheckpoint_address(String checkpoint_address) {
		this.checkpoint_address = checkpoint_address;
	}

	public double getCheckpoint_lat() {
		return checkpoint_lat;
	}

	public void setCheckpoint_lat(double checkpoint_lat) {
		this.checkpoint_lat = checkpoint_lat;
	}

	public double getCheckpoint_lng() {
		return checkpoint_lng;
	}

	public void setCheckpoint_lng(double checkpoint_lng) {
		this.checkpoint_lng = checkpoint_lng;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public int getCheckpoint_order() {
		return checkpoint_order;
	}

	public void setCheckpoint_order(int checkpoint_order) {
		this.checkpoint_order = checkpoint_order;
	}

	public int getIsarray() {
		return isarray;
	}

	public void setIsarray(int isarray) {
		this.isarray = isarray;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}

	public double getStoptime() {
		return stoptime;
	}

	public void setStoptime(double stoptime) {
		this.stoptime = stoptime;
	}

	public String getRemain() {
		return remain;
	}

	public void setRemain(String remain) {
		this.remain = remain;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Date getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}

	public int getCreate_id() {
		return create_id;
	}

	public void setCreate_id(int create_id) {
		this.create_id = create_id;
	}

	public int getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(int update_id) {
		this.update_id = update_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
	
}
