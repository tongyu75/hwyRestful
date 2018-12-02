package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 用于接收提交的举报信息中处理前与处理之后的图片，用于APP接口
  * @author 张咏雪   2017-04-24
  */
public class ReportImageApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -932729141516828849L;
    
    private int id;//主键ID
    private int checkId;//外键ID，reportinfo的主键ID
    private int evalValue;//举报类型ID
    private String imageFileName;//图片文件名
    private String imageContentType;//图片类型
    private String imageFileSize;//图片文件大小
    private Date createAt;//记录时间
    private Date updateAt;//更新时间
    private int status;//图片类型：1 处理前图片 2 处理后图片
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public int getEvalValue() {
		return evalValue;
	}
	public void setEvalValue(int evalValue) {
		this.evalValue = evalValue;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public String getImageFileSize() {
		return imageFileSize;
	}
	public void setImageFileSize(String imageFileSize) {
		this.imageFileSize = imageFileSize;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
    
}