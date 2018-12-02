package com.czz.hwy.bean.user.watch;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class Users extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 1597023096156371609L;

    // ID
    private int id;
    // 显示名字
    private String showname;
    // 员工ID
    private int employeeId;
    // 密码
    private String password;
    // 邮件
    private String email;
    // 手机
    private String tel;
    // 性别ID
    private int gener;
    // 性别名称
    private String generName;
    // 地址
    private String address;
    // 身份证
    private String idCard;
    // 部门ID
    private int deptId;
    // 角色ID
    private int roleId;
    // 状态( 1：在职2：离职3：试用)
    private int status;
    // 是否签到（1：签到2：未签到）
    private int flag;
    // 职位
    private String position;
    // 备注
    private String remark;
    // 部门名称
    private String deptName;
    // 角色名称
    private String roleName;
	// 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 是否有手机
    private int hasMobile;
    // 是否有手表
    private int hasWatch;
    // 车牌号
    private String cardNum;
    private String pictureCheckCode;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getShowname() {
        return showname;
    }
    public void setShowname(String showname) {
        this.showname = showname;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public int getGener() {
        return gener;
    }
    public void setGener(int gener) {
        this.gener = gener;
    }
    public String getGenerName() {
        return generName;
    }
    public void setGenerName(String generName) {
        this.generName = generName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public int getDeptId() {
        return deptId;
    }
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
    public String getPictureCheckCode() {
        return pictureCheckCode;
    }
    public void setPictureCheckCode(String pictureCheckCode) {
        this.pictureCheckCode = pictureCheckCode;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public int getHasMobile() {
        return hasMobile;
    }
    public void setHasMobile(int hasMobile) {
        this.hasMobile = hasMobile;
    }
    public int getHasWatch() {
        return hasWatch;
    }
    public void setHasWatch(int hasWatch) {
        this.hasWatch = hasWatch;
    }
    public String getCardNum() {
        return cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}