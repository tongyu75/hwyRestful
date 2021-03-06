package com.czz.hwy.bean.manager.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 请假实体类，用于app接口
 * @Author 张咏雪
 * @createDate 2016-11-08
 */
public class LeaveApp extends PageReqParamDTO implements Serializable{

    private static final long serialVersionUID = -7913439826036004952L;

	private int id;  // ID

    private int applyId;// 申请人ID

    private String applyName;// 申请人名称   
	
    private String leaveContent;// 请假理由 

    private int leaveNumber;// 请假天数
    
    private int leaveTimeNumber;//请假小时（请假结束时间的时分 - 请假开始时间的时分），与请假天数字段拼成完整的请假天数
   
    private Date leaveFromTime; // 请假开始时间
   
    private Date leaveToTime; // 请假截止时间  

    private Date leaveApplTime; // 请假提交时间    
   
    private int leaveApprId; // 审核人ID  

    private String leaveApprName;// 审核人名称 

    private String leaveApprContent; // 审核理由

    private int applyRole;// 申请人角色  
   
    private int apprRole; // 审核人角色
   
    private String apprContent; // 审核意见

    private Date apprTime;// 审核时间
    
    private int apprStatus;// 审核状态

    private Date createAt; // 创建时间
   
    private Date updateAt;  // 修改时间   
    
    private String toTime; // 检索结果请假截止时间  
   
    private String fromTime; // 检索结果请假提交时间    
   
    private String applTime; // 检索结果申请时间  
    
    private int dutyAreaId; // 应出勤责任区ID
   
    private int dutyPointId; // 应出勤责任区ID
    
    private int apprTwoStatus; // 二级审批状态
    
    private int apprThreeStatus; // 三级审批状态
    
    private int apprFourStatus;// 四级审批状态
    
    private int flag;//1：请假信息查看 2：审批信息查看
    
    private String groupName;//用于记录 责任区名称（组长名称）

    
    private int jcyApprId;//检测员审批人ID
    
    private String jcyApprName;//检测员审批人
    
    private Date jcyApprDate;//检测员审批时间
    
    private String jcyApprMemo;//检测员审批理由（记录拒绝请假理由）
    
    private int jcyApprStatus;//检测员审批状态，1 审批通过 2 审批拒绝
    
    private int khyApprId;//考核员审批人ID
    
    private String khyApprName;//考核员审批人
    
    private Date khyApprDate;//考核员审批时间
    
    private String khyApprMemo;//考核员审批理由（记录拒绝请假理由）
    
    private int khyApprStatus;//考核员审批状态，1 审批通过 2 审批拒绝
    
    
    private int dcyApprId;//督察员审批人ID
    
    private String dcyApprName;//督察员审批人
    
    private Date dcyApprDate;//督察员审批时间
    
    private String dcyApprMemo;//督察员审批理由（记录拒绝请假理由）
    
    private int dcyApprStatus;//督察员审批状态，1 审批通过 2 审批拒绝
    
    private int apprNumber;//该条请假最后一步审批人的角色ID
    
    //2016-10-19
    private int relayId;//代班人ID
    private String relayName;//代班人名称
//    private String beginDate;//开始日期：用于过滤申请时间
//    private String endDate;//结束日期：用于过滤申请时间
    
    private Date timeForSelect;//用于过滤请假开始时间与请假接收时间

    
	public int getRelayId() {
		return relayId;
	}

	public void setRelayId(int relayId) {
		this.relayId = relayId;
	}

	public String getRelayName() {
		return relayName;
	}

	public void setRelayName(String relayName) {
		this.relayName = relayName;
	}

	public Date getTimeForSelect() {
		return timeForSelect;
	}

	public void setTimeForSelect(Date timeForSelect) {
		this.timeForSelect = timeForSelect;
	}

	public int getLeaveTimeNumber() {
		return leaveTimeNumber;
	}

	public void setLeaveTimeNumber(int leaveTimeNumber) {
		this.leaveTimeNumber = leaveTimeNumber;
	}

	public String getJcyApprName() {
		return jcyApprName;
	}

	public void setJcyApprName(String jcyApprName) {
		this.jcyApprName = jcyApprName;
	}

	public String getKhyApprName() {
		return khyApprName;
	}

	public void setKhyApprName(String khyApprName) {
		this.khyApprName = khyApprName;
	}

	public String getDcyApprName() {
		return dcyApprName;
	}

	public void setDcyApprName(String dcyApprName) {
		this.dcyApprName = dcyApprName;
	}

	public int getJcyApprId() {
		return jcyApprId;
	}

	public void setJcyApprId(int jcyApprId) {
		this.jcyApprId = jcyApprId;
	}

	public Date getJcyApprDate() {
		return jcyApprDate;
	}

	public void setJcyApprDate(Date jcyApprDate) {
		this.jcyApprDate = jcyApprDate;
	}

	public String getJcyApprMemo() {
		return jcyApprMemo;
	}

	public void setJcyApprMemo(String jcyApprMemo) {
		this.jcyApprMemo = jcyApprMemo;
	}

	public int getJcyApprStatus() {
		return jcyApprStatus;
	}

	public void setJcyApprStatus(int jcyApprStatus) {
		this.jcyApprStatus = jcyApprStatus;
	}

	public int getKhyApprId() {
		return khyApprId;
	}

	public void setKhyApprId(int khyApprId) {
		this.khyApprId = khyApprId;
	}

	public Date getKhyApprDate() {
		return khyApprDate;
	}

	public void setKhyApprDate(Date khyApprDate) {
		this.khyApprDate = khyApprDate;
	}

	public String getKhyApprMemo() {
		return khyApprMemo;
	}

	public void setKhyApprMemo(String khyApprMemo) {
		this.khyApprMemo = khyApprMemo;
	}

	public int getKhyApprStatus() {
		return khyApprStatus;
	}

	public void setKhyApprStatus(int khyApprStatus) {
		this.khyApprStatus = khyApprStatus;
	}

	public int getDcyApprId() {
		return dcyApprId;
	}

	public void setDcyApprId(int dcyApprId) {
		this.dcyApprId = dcyApprId;
	}

	public Date getDcyApprDate() {
		return dcyApprDate;
	}

	public void setDcyApprDate(Date dcyApprDate) {
		this.dcyApprDate = dcyApprDate;
	}

	public String getDcyApprMemo() {
		return dcyApprMemo;
	}

	public void setDcyApprMemo(String dcyApprMemo) {
		this.dcyApprMemo = dcyApprMemo;
	}

	public int getDcyApprStatus() {
		return dcyApprStatus;
	}

	public void setDcyApprStatus(int dcyApprStatus) {
		this.dcyApprStatus = dcyApprStatus;
	}

	public int getApprNumber() {
		return apprNumber;
	}

	public void setApprNumber(int apprNumber) {
		this.apprNumber = apprNumber;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getLeaveContent() {
        return leaveContent;
    }

    public void setLeaveContent(String leaveContent) {
        this.leaveContent = leaveContent;
    }

    public int getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(int leaveNumber) {
        this.leaveNumber = leaveNumber;
    }

    public Date getLeaveFromTime() {
        return leaveFromTime;
    }

    public void setLeaveFromTime(Date leaveFromTime) {
        this.leaveFromTime = leaveFromTime;
    }

    public Date getLeaveToTime() {
        return leaveToTime;
    }

    public void setLeaveToTime(Date leaveToTime) {
        this.leaveToTime = leaveToTime;
    }

    public Date getLeaveApplTime() {
        return leaveApplTime;
    }

    public void setLeaveApplTime(Date leaveApplTime) {
        this.leaveApplTime = leaveApplTime;
    }

    public int getLeaveApprId() {
        return leaveApprId;
    }

    public void setLeaveApprId(int leaveApprId) {
        this.leaveApprId = leaveApprId;
    }

    public String getLeaveApprName() {
        return leaveApprName;
    }

    public void setLeaveApprName(String leaveApprName) {
        this.leaveApprName = leaveApprName;
    }

    public String getLeaveApprContent() {
        return leaveApprContent;
    }

    public void setLeaveApprContent(String leaveApprContent) {
        this.leaveApprContent = leaveApprContent;
    }

    public int getApplyRole() {
        return applyRole;
    }

    public void setApplyRole(int applyRole) {
        this.applyRole = applyRole;
    }

    public int getApprRole() {
        return apprRole;
    }

    public void setApprRole(int apprRole) {
        this.apprRole = apprRole;
    }

    public String getApprContent() {
        return apprContent;
    }

    public void setApprContent(String apprContent) {
        this.apprContent = apprContent;
    }

    public Date getApprTime() {
        return apprTime;
    }

    public void setApprTime(Date apprTime) {
        this.apprTime = apprTime;
    }

    public int getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(int apprStatus) {
        this.apprStatus = apprStatus;
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

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getApplTime() {
        return applTime;
    }

    public void setApplTime(String applTime) {
        this.applTime = applTime;
    }

    public int getDutyAreaId() {
        return dutyAreaId;
    }

    public void setDutyAreaId(int dutyAreaId) {
        this.dutyAreaId = dutyAreaId;
    }

    public int getDutyPointId() {
        return dutyPointId;
    }

    public void setDutyPointId(int dutyPointId) {
        this.dutyPointId = dutyPointId;
    }

    public int getApprTwoStatus() {
        return apprTwoStatus;
    }

    public void setApprTwoStatus(int apprTwoStatus) {
        this.apprTwoStatus = apprTwoStatus;
    }

    public int getApprThreeStatus() {
        return apprThreeStatus;
    }

    public void setApprThreeStatus(int apprThreeStatus) {
        this.apprThreeStatus = apprThreeStatus;
    }

    public int getApprFourStatus() {
        return apprFourStatus;
    }

    public void setApprFourStatus(int apprFourStatus) {
        this.apprFourStatus = apprFourStatus;
    }    
    
}