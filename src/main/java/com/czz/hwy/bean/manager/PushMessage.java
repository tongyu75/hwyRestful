package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 消息推送Bean
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/4/28
 */
public class PushMessage extends PageReqParamDTO implements Serializable {

	private static final long serialVersionUID = -380218364351103984L;

	// ID
    private int id;
    
    // 推送者ID
    private int pushId;
    
    // 推送者名字
    private String pushName;
    
    // 推送消息内容
    private String pushContent;
    
    // 推送接收者ID
    private int pushReceiveId;

    // 推送接收者名字
    private String pushReceiveName;
    
    // 推送接收者角色
    private int pushReceiveRole;
    
    // 推送状态
    private int pushStatus;
    
    // 推送状态名字
    private String pushStatusName;
    
    // 推送时间
    private Date pushTime;

	// 推送起始时间(参数)
	private Date inPushFromTime;

	// 推送截至时间(参数)
	private Date inPushToTime;
    
    // 推送者ID(参数)
    private int inPushId;
	
    // 推送消息内容(参数)
    private String inPushContent;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPushId() {
		return pushId;
	}
	public void setPushId(int pushId) {
		this.pushId = pushId;
	}
	public String getPushContent() {
		return pushContent;
	}
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}
	public int getPushReceiveId() {
		return pushReceiveId;
	}
	public void setPushReceiveId(int pushReceiveId) {
		this.pushReceiveId = pushReceiveId;
	}
	public int getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(int pushStatus) {
		this.pushStatus = pushStatus;
	}
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	public String getPushName() {
		return pushName;
	}
	public void setPushName(String pushName) {
		this.pushName = pushName;
	}
	public String getPushReceiveName() {
		return pushReceiveName;
	}
	public void setPushReceiveName(String pushReceiveName) {
		this.pushReceiveName = pushReceiveName;
	}
	public int getPushReceiveRole() {
		return pushReceiveRole;
	}
	public void setPushReceiveRole(int pushReceiveRole) {
		this.pushReceiveRole = pushReceiveRole;
	}
	public String getPushStatusName() {
		return pushStatusName;
	}
	public void setPushStatusName(String pushStatusName) {
		this.pushStatusName = pushStatusName;
	}
	public Date getInPushFromTime() {
		return inPushFromTime;
	}
	public void setInPushFromTime(Date inPushFromTime) {
		this.inPushFromTime = inPushFromTime;
	}
	public Date getInPushToTime() {
		return inPushToTime;
	}
	public void setInPushToTime(Date inPushToTime) {
		this.inPushToTime = inPushToTime;
	}
	public int getInPushId() {
		return inPushId;
	}
	public void setInPushId(int inPushId) {
		this.inPushId = inPushId;
	}
	public String getInPushContent() {
		return inPushContent;
	}
	public void setInPushContent(String inPushContent) {
		this.inPushContent = inPushContent;
	}  
}