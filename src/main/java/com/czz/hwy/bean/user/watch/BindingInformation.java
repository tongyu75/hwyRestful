package com.czz.hwy.bean.user.watch;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 佟士儒 16-七月-2015
  * @version V0.1
  */
public class BindingInformation extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 1597023096156371609L;

    // ID
    private int id;
    // 员工ID
    private int employeeId;
    // 绑定手机号
    private String tel;
    // 手机标识
    private String deviceId;
    // 状态
    private int status;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 创建者
    private int createId;
    // 修改者
    private int updateId;
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
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
}