/**
 * 
 */
package com.czz.hwy.bean.area;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * @author 张纪才
 * 边界点及考核点采集上报文件管理bean
 *
 */
public class BoderPointFile extends PageReqParamDTO<BoderPointFile> implements Serializable {
	
	private static final long serialVersionUID = 3358857332697931928L;
	
	private int id;
	//责任区ID
	private int areaId;
	//责任区名称
	private String areaName;
	//责任点ID
	private int pointId;
	//责任点名称
	private String pointName;
	//责任点的子采集数据文件的版本, 文件名称_当前时间（yyMMddHHmiss）
	private String fileFileName;
	//责任点的子区域的文件保存路径
	private String pointFilepath;
	//审批状态0未审批1审批通过2审批未通过
	private int approveStatus;
	//创建时间
	private Date createAt;
	//条件起始时间
	transient private Date f_createAt;
	//条件截至时间
	transient private Date t_createAt;
	//修改时间
	private Date updateAt;
	//创建者
	private int createId;
	//修改者
	private int updateId;
	//状态（1:启用2：废止）
	private int status;
	//状态名称
	//上报文件类型 边界点文件或者考核点文件
	private int pointType;
	//文件传输类型
	private String fileContentType;
	//添加url链接
	private String url;
	// 责任点的边界点采集信息
	List<DutyPointGather> lstDutyPointGather;
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
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getPointFilepath() {
		return pointFilepath;
	}
	public void setPointFilepath(String pointFilepath) {
		this.pointFilepath = pointFilepath;
	}
	public int getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(int approveStatus) {
		this.approveStatus = approveStatus;
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
	public Date getF_createAt() {
		return f_createAt;
	}
	public void setF_createAt(Date f_createAt) {
		this.f_createAt = f_createAt;
	}
	public Date getT_createAt() {
		return t_createAt;
	}
	public void setT_createAt(Date t_createAt) {
		this.t_createAt = t_createAt;
	}
	public int getPointType() {
		return pointType;
	}
	public void setPointType(int pointType) {
		this.pointType = pointType;
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
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    public List<DutyPointGather> getLstDutyPointGather() {
        return lstDutyPointGather;
    }
    public void setLstDutyPointGather(List<DutyPointGather> lstDutyPointGather) {
        this.lstDutyPointGather = lstDutyPointGather;
    }
}
