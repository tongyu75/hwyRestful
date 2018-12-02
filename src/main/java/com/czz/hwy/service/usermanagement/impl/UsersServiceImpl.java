package com.czz.hwy.service.usermanagement.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.czz.hwy.bean.user.Departments;
import com.czz.hwy.bean.user.Roles;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.bean.user.UsersForRedis;
import com.czz.hwy.dao.user.DepartmentsDao;
import com.czz.hwy.dao.user.RedisUsersDao;
import com.czz.hwy.dao.user.RedisValidateCodeDao;
import com.czz.hwy.dao.user.RolesDao;
import com.czz.hwy.dao.user.UsersDao;
import com.czz.hwy.service.usermanagement.UsersService;

/**
 * 获取员工信息业务层实现类
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161012
 * @version V1.0
 */
@Service
public class UsersServiceImpl implements UsersService {
    
    @Autowired
    private UsersDao usersDao;
    
    @Autowired
    private DepartmentsDao departmentsDao;
    
    @Autowired
    private RolesDao rolesDao;
    
    @Autowired
    private RedisUsersDao redisUsersDao;
    
    @Autowired
    private RedisValidateCodeDao redisValidateCodeDao;
    
    /**
     * 根据员工ID查询员工信息
     */
    public Users getUserInfoByEmployeeId(int employeeId) {
        return usersDao.getInfoById("getUserInfoByEmployeeId",employeeId);
    }
    
    /**
     * 根据查询条件查询员工数量
     */
    public int getUserCount(Users bean) {
        return usersDao.getInfoCount("getUserCount", bean);
    }
    
    /**
     * 根据查询条件查询员工列表(带分页)
     */
    public List<Users> getAllUserByBean(Users bean) {
        return usersDao.getInfoListByBean("getAllUsersByBean", bean);
    }
    
    /**
     * 根据查询条件查询员工
     */
    public Users getUserInfoByBean(Users bean) {
        return usersDao.getInfoByBean("getUsersByBean", bean);
    }
    
    /**
     * 根据查询条件查询员工列表
     */    
    public List<Users> getUserInfoListByBean(Users bean) {
        return usersDao.getInfoListByBean("getUsersByBean", bean);
    }
    
    /**
     * 根据查询条件查询消息员工列表
     */
    public List<Users> getMessageUsersInfoListByBean(Users bean) {
        return usersDao.getInfoListByBean("getMessageUsersByBean", bean);
    }    
    
    
    /**
     * 添加员工信息
     */
    public int insertUsers(Users bean) {
        return usersDao.insertInfo("insertUsers", bean);
    }
    
    /**
     * 修改员工信息
     */
    public int updateUsersByBean(Users bean) {
        return usersDao.insertInfo("updateUsers", bean);
    }
    
    /**
     * 从Excel导入员工信息
     */
    public int insertLeadUserInfo(MultipartFile path) {
        List<Users> usersInfo=null;
        try {
            // 读取Excel的员工信息
            usersInfo = readXls(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersDao.insertInfo("insertAllUsersInfo", usersInfo);
    }
    
    /**
     * 向缓存服务器添加登录用户信息 
     */
    public boolean addUsersForRedis(String key, UsersForRedis bean) {
        return redisUsersDao.add(key, bean);
    }

    /**
     * 根据key从缓存服务器获取登录用户信息 
     */
    public UsersForRedis getUsersForRedis(String token) {
        return redisUsersDao.get(token);
    }
    
    /**
     * 向缓存服务器添加验证码 
     */
    public boolean addValidateCodeForRedis(String token, String code) {
        return redisValidateCodeDao.add(token, code);
    }
        
    /**
     * 根据key从缓存服务器获取验证码 
     */
    public String getValidateCodeForRedis(String key) {
        return redisValidateCodeDao.get(key);
    }
    
    /**
     * 读取Excel中的用户信息
     */
    @SuppressWarnings("resource")
    public List<Users> readXls(MultipartFile path) throws IOException {
        List<Users> usersInfo=new ArrayList<Users>();
        
        InputStream is = path.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook( is);
        // 循环工作表Sheet
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt( numSheet);
            if(hssfSheet == null){
                continue;
            }

            // 循环行Row
            for(int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                Users user=new Users();
                HSSFRow hssfRow = hssfSheet.getRow( rowNum);
                if(hssfRow == null){
                    break;
                    // continue;
                }
                if(rowNum>0)
                {
                    // 循环列Cell
                    for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
                        HSSFCell hssfCell = hssfRow.getCell( cellNum);
                        //if(hssfCell == null){
                        //    continue;
                        //}
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("姓名"))
                        {
                            user.setShowname(getValue( hssfCell));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("员工号"))
                        {
                            user.setEmployeeId(Integer.parseInt(getValue( hssfCell)));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("email"))
                        {
                            user.setEmail(getValue( hssfCell));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("联系电话"))
                        {
                            user.setTel(getValue( hssfCell));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("性别"))
                        {
                            if(getValue( hssfCell).equals("男"))user.setGener(1);
                            else user.setGener(0);
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("地址"))
                        {
                            user.setAddress(getValue( hssfCell));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("身份证号"))
                        {
                            user.setIdCard(getValue( hssfCell));
                            user.setPassword(getValue( hssfCell).substring(getValue( hssfCell).length()-6,getValue( hssfCell).length()));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("部门"))
                        {
                            if(getValue( hssfCell)==null || getValue( hssfCell).equals(""))
                            {
                                user.setDeptId(0);
                            }
                            else
                            {   // 根据部门名称来查询对应的ID
                                Departments dep = new Departments();
                                dep.setDeptName(getValue( hssfCell));
                                Departments departments=departmentsDao.getInfoByBean("getDepartmentsByBean", dep);
                                user.setDeptId(departments.getDeptId());
                            }
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("角色"))
                        {
                            if(getValue( hssfCell).equals(""))
                            {
                                user.setRoleId(0);
                            }
                            else
                            {   // 根据角色名称来查询对应的ID
                                Roles setRole=new Roles();
                                setRole.setName(getValue( hssfCell));
                                setRole.setStatus(1);
                                Roles role=rolesDao.getInfoByBean("getRolesByBean", setRole);
                                user.setRoleId(role.getValue());
                            }
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("岗位"))
                        {
                            user.setPosition(getValue( hssfCell));
                        }
                        if(getValue(hssfSheet.getRow(0).getCell( cellNum)).equals("备注"))
                        {
                            user.setRemark(getValue( hssfCell));
                        }
                        user.setCreateAt(new Date());
                        user.setFlag(0);
                        user.setStatus(1);
                        // 绑定手表标识(2.未绑定)
                        user.setHasWatch(2);
                        // 绑定手机标识(2.未绑定)
                        user.setHasMobile(2);
                    }
                    usersInfo.add(user);
                }
            }
        }
        return usersInfo;
    }
    
    /**
     * 读取Excel中Cell的值
     */
    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell){
        if(hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN){
            return String.valueOf( hssfCell.getBooleanCellValue());
        }else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC){
            return String.valueOf( hssfCell.getNumericCellValue());
        }else{
            return String.valueOf( hssfCell.getStringCellValue());
        }
    }

    /**
     * 查询员工记录集合，2016-11-15，不分页
     */
	public List<Users> getUserList(Users users) {
		return usersDao.getInfoListByBean("getUserList", users);
	}

    /**
     * 根据责任区ID获取该责任区中的所有用户  2016-11-21
     * @param users
     * @return
     */
    public List<Map<String, Object>> getUserIdByAreaId(Users users) {
        return usersDao.getInfoListMapByBean("getUserIdByAreaId", users);
    }
}
