package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class UsersForRedis implements Serializable {

    private static final long serialVersionUID = 1597023096156371609L;

    // ID
    private int token;
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
    // 性别
    private int gener;
    
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
    
    
    
    public String getGenerName() {
		return generName;
	}
	public void setGenerName(String generName) {
		this.generName = generName;
	}
	public String getPictureCheckCode() {
		return pictureCheckCode;
	}
	public void setPictureCheckCode(String pictureCheckCode) {
		this.pictureCheckCode = pictureCheckCode;
	}
	// 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    private String pictureCheckCode;
    
	public int getToken() {
        return token;
    }
    public void setToken(int token) {
        this.token = token;
    }
    /**
	  * 设定ID
	  * @param id
      */
	public void setId(int id) {
	   this.id = id;
	}
	/**
	  * 获取ID
	  * @return ID
      */
	public int getId() {
	   return id;
	}
	/**
	  * 设定显示名字
	  * @param showname
      */
	public void setShowname(String showname) {
	   this.showname = showname;
	}
	/**
	  * 获取显示名字
	  * @return 显示名字
      */
	public String getShowname() {
	   return showname;
	}
	/**
	  * 设定员工ID
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取员工ID
	  * @return 员工ID
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定密码
	  * @param password
      */
	public void setPassword(String password) {
	   this.password = password;
	}
	/**
	  * 获取密码
	  * @return 密码
      */
	public String getPassword() {
	   return password;
	}
	/**
	  * 设定邮件
	  * @param email
      */
	public void setEmail(String email) {
	   this.email = email;
	}
	/**
	  * 获取邮件
	  * @return 邮件
      */
	public String getEmail() {
	   return email;
	}
	/**
	  * 设定手机
	  * @param tel
      */
	public void setTel(String tel) {
	   this.tel = tel;
	}
	/**
	  * 获取手机
	  * @return 手机
      */
	public String getTel() {
	   return tel;
	}
	/**
	  * 设定性别
	  * @param gener
      */
	public void setGener(int gener) {
	   this.gener = gener;
	}
	/**
	  * 获取性别
	  * @return 性别
      */
	public int getGener() {
	   return gener;
	}
	/**
	  * 设定地址
	  * @param address
      */
	public void setAddress(String address) {
	   this.address = address;
	}
	/**
	  * 获取地址
	  * @return 地址
      */
	public String getAddress() {
	   return address;
	}
	/**
	  * 设定身份证
	  * @param idCard
      */
	public void setIdCard(String idCard) {
	   this.idCard = idCard;
	}
	/**
	  * 获取身份证
	  * @return 身份证
      */
	public String getIdCard() {
	   return idCard;
	}
	/**
	  * 设定部门ID
	  * @param deptId
      */
	public void setDeptId(int deptId) {
	   this.deptId = deptId;
	}
	/**
	  * 获取部门ID
	  * @return 部门ID
      */
	public int getDeptId() {
	   return deptId;
	}
	/**
	  * 设定角色ID
	  * @param roleId
      */
	public void setRoleId(int roleId) {
	   this.roleId = roleId;
	}
	/**
	  * 获取角色ID
	  * @return 角色ID
      */
	public int getRoleId() {
	   return roleId;
	}
	/**
	  * 设定状态( 1：在职2：离职3：试用)
	  * @param status
      */
	public void setStatus(int status) {
	   this.status = status;
	}
	/**
	  * 获取状态( 1：在职2：离职3：试用)
	  * @return 状态( 1：在职2：离职3：试用)
      */
	public int getStatus() {
	   return status;
	}
	/**
	  * 设定是否签到（1：签到2：未签到）
	  * @param flag
      */
	public void setFlag(int flag) {
	   this.flag = flag;
	}
	/**
	  * 获取是否签到（1：签到2：未签到）
	  * @return 是否签到（1：签到2：未签到）
      */
	public int getFlag() {
	   return flag;
	}
	/**
	  * 设定职位
	  * @param position
      */
	public void setPosition(String position) {
	   this.position = position;
	}
	/**
	  * 获取职位
	  * @return 职位
      */
	public String getPosition() {
	   return position;
	}
	/**
	  * 设定备注
	  * @param remark
      */
	public void setRemark(String remark) {
	   this.remark = remark;
	}
	/**
	  * 获取备注
	  * @return 备注
      */
	public String getRemark() {
	   return remark;
	}
	/**
	  * 设定创建时间
	  * @param createAt
      */
	public void setCreateAt(Date createAt) {
	   this.createAt = createAt;
	}
	/**
	  * 获取创建时间
	  * @return 创建时间
      */
	public Date getCreateAt() {
	   return createAt;
	}
	/**
	  * 设定修改时间
	  * @param updateAt
      */
	public void setUpdateAt(Date updateAt) {
	   this.updateAt = updateAt;
	}
	/**
	  * 获取修改时间
	  * @return 修改时间
      */
	public Date getUpdateAt() {
	   return updateAt;
	}
	private String deptName;
	private String name;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}