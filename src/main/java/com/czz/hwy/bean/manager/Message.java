/**
 * 
 */
package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 消息
 * @author 张纪才
 *
 */
public class Message extends PageReqParamDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -443449735101975078L;
	//主键
	private int id;
	//消息标题
	private String title;
	//消息内容
	private String content;
	//发布时间
	private Date publish_time;
	//消息发布起始时间
	private Date f_publishTime;
	//消息发布截至时间
	private Date t_publishTime;
	//创建人员工号
	private int create_id;
	//发布人姓名
	private String publishName;
	//接收类型0表示所有人1表示环卫工2表示检测员3表示考核员4表示督察员9表示指定人员
	private int receive_type;
	//当receive_kind值为9时表示指定人员发送,其他情况可以为空
	private String receive_ids;
	//数据入库时间
	private Date create_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(Date publish_time) {
		this.publish_time = publish_time;
	}
	public int getCreate_id() {
		return create_id;
	}
	public void setCreate_id(int create_id) {
		this.create_id = create_id;
	}
	public Date getF_publishTime() {
		return f_publishTime;
	}
	public void setF_publishTime(Date f_publishTime) {
		this.f_publishTime = f_publishTime;
	}
	public Date getT_publishTime() {
		return t_publishTime;
	}
	public void setT_publishTime(Date t_publishTime) {
		this.t_publishTime = t_publishTime;
	}
	public int getReceive_type() {
		return receive_type;
	}
	public void setReceive_type(int receive_type) {
		this.receive_type = receive_type;
	}
	public String getReceive_ids() {
		return receive_ids;
	}
	public void setReceive_ids(String receive_ids) {
		this.receive_ids = receive_ids;
	}
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
    public String getPublishName() {
        return publishName;
    }
    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }
	
}
