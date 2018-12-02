package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 劳资Bean
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/05/07
 */
public class ArticleInfo extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = -7933476723837240651L;

	// ID
	private int id;

	// 员工ID
    private int employeeId;

	// 员工名称   
    private String employeeName;

	// 领用申请日期  
    private Date applyTime;

	// 领用申请数量    
    private int applyNumber;

    // 领用物品名称
    private String articleName;

    // 领用审批日期   
    private Date approvalTime;

    // 审批状态    
    private int approvalStatus;

    // 领用申请理由    
    private String applyContent;

    // 报废日期
    private Date scrapTime;

    // 报废理由    
    private String scrapContent;

    // 报废物品名称    
    private String scrapName;

    // 报废物品数量   
    private int scrapNumber;

    // 报废状态    
    private int scrapStatus;

    // 劳资状态    
    private int articleStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public int getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(int applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName == null ? null : articleName.trim();
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent == null ? null : applyContent.trim();
    }

    public Date getScrapTime() {
        return scrapTime;
    }

    public void setScrapTime(Date scrapTime) {
        this.scrapTime = scrapTime;
    }

    public String getScrapContent() {
        return scrapContent;
    }

    public void setScrapContent(String scrapContent) {
        this.scrapContent = scrapContent == null ? null : scrapContent.trim();
    }

    public String getScrapName() {
        return scrapName;
    }

    public void setScrapName(String scrapName) {
        this.scrapName = scrapName == null ? null : scrapName.trim();
    }

    public int getScrapNumber() {
        return scrapNumber;
    }

    public void setScrapNumber(int scrapNumber) {
        this.scrapNumber = scrapNumber;
    }

    public int getScrapStatus() {
        return scrapStatus;
    }

    public void setScrapStatus(int scrapStatus) {
        this.scrapStatus = scrapStatus;
    }

    public int getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(int articleStatus) {
        this.articleStatus = articleStatus;
    }
}