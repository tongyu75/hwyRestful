package com.czz.hwy.bean.area;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 
 * @author 佟士儒
 *
 */
// 责任点之边界点采集类
public class DutyPointGather extends PageReqParamDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7634432115326416004L;

	private int id;

	private int areaId;// 责任区ID

	private String areaName;// 责任区名称
	
	private int pointId;// 责任点ID

	private String pointName;// 责任点名称
	
	private String borderpointName;// 采集边界点名称

	private String borderpointAddress;// 边界点地址

	private double borderpointLat;// 边界点纬度

	private double borderpointLng;// 边界点经度

	private double offset;// 偏移值

	private int borderpointOrder;// 边界点顺序

	private Date createAt;// 创建时间

	private Date updateAt;// 修改时间

	private int createId;// 创建者

	private int updateId;// 修改者

	private int status;// 状态（1:启用2：废止）
	
	private int file_id;//导入的数据的相关上传文件的主键id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getBorderpointName() {
		return borderpointName;
	}

	public void setBorderpointName(String borderpointName) {
		this.borderpointName = borderpointName;
	}

	public String getBorderpointAddress() {
		return borderpointAddress;
	}

	public void setBorderpointAddress(String borderpointAddress) {
		this.borderpointAddress = borderpointAddress;
	}

	public double getBorderpointLat() {
		return borderpointLat;
	}

	public void setBorderpointLat(double borderpointLat) {
		this.borderpointLat = borderpointLat;
	}

	public double getBorderpointLng() {
		return borderpointLng;
	}

	public void setBorderpointLng(double borderpointLng) {
		this.borderpointLng = borderpointLng;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public int getBorderpointOrder() {
		return borderpointOrder;
	}

	public void setBorderpointOrder(int borderpointOrder) {
		this.borderpointOrder = borderpointOrder;
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

	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

}
