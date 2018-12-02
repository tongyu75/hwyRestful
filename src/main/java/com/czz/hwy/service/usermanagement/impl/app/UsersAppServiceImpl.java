package com.czz.hwy.service.usermanagement.impl.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.SubTopicsApp;
import com.czz.hwy.bean.user.app.UsersApp;
import com.czz.hwy.dao.manager.app.SubTopicsAppDao;
import com.czz.hwy.dao.user.app.UsersAppDao;
import com.czz.hwy.service.usermanagement.app.UsersAppService;

/**
 * 获取员工信息业务层实现类
 * 
 * @author 佟士儒
 * @version V1.0
 */
@Service
public class UsersAppServiceImpl implements UsersAppService {

    @Autowired
	UsersAppDao userAppDao;
	
    @Autowired
    SubTopicsAppDao subTopicsAppDao;
	/**
	 * 根据员工ID查询员工信息
	 */
	public UsersApp getUserInfoByEmployeeId(int employeeId) {
		UsersApp resultUser = userAppDao.getInfoById("getUserInfoByEmployeeIdApp", employeeId);
		return resultUser;
	}

	/**
     * 获取员工信息对象
     */
    public UsersApp login(HttpServletRequest request, int employeeId, String password, String imei) {
        UsersApp user = new UsersApp();
        user.setEmployeeId(employeeId);
        user.setPassword(password);
        user.setStatus(1);
        UsersApp oldUser = userAppDao.getInfoByBean("getUsersByBeanApp", user);
        //判断如果获取到员工信息，则走验证imei以及获取头像的图片ID，否则返回空
        if (oldUser != null) {
            SubTopicsApp subTopicsApp = new SubTopicsApp();
            subTopicsApp.setEmployeeId(oldUser.getEmployeeId());
            SubTopicsApp oldSubTopics = subTopicsAppDao.getInfoByBean("getSubTopicsAppByBeanApp", subTopicsApp);
            //根据员工号获取订阅信息，判断是否存在订阅信息，不存在则新增订阅信息
            if (oldSubTopics != null) {
                //判断从前台获取的imei是否与后台获取的订阅信息匹配，不匹配则更新数据
                if (!imei.equals(oldSubTopics.getDeviceId())) {
                    SubTopicsApp updateSubTopics = new SubTopicsApp();
                    updateSubTopics.setId(oldSubTopics.getId());
                    updateSubTopics.setStatus(oldSubTopics.getStatus());
                    updateSubTopics.setEmployeeId(employeeId);
                    updateSubTopics.setDeviceId(imei);
                    updateSubTopics.setTopics(imei+employeeId);
                    updateSubTopics.setUpdateAt(new Date());
                    subTopicsAppDao.updateInfoByBean("updateSubTopicsApp", updateSubTopics);
                }
            } else {
                SubTopicsApp newSubTopics = new SubTopicsApp();
                newSubTopics.setDeviceId(imei);
                newSubTopics.setEmployeeId(employeeId);
                newSubTopics.setStatus(1);
                newSubTopics.setCreateAt(new Date());
                newSubTopics.setTopics(imei+employeeId);
                subTopicsAppDao.insertInfo("insertSubTopicsApp", newSubTopics);
            }
            
            // 获取头像的图片ID
            List<Long> nameList = new ArrayList<Long>();
            // 获取头像文件路径,本系统中的路径为服务器路径
            String realPath = request.getSession().getServletContext().getRealPath("/") + "public/photo/" + employeeId + "/";
            File photoPath = new File(realPath);
            String[] fileName = photoPath.list();
            if (fileName != null) {
                for (String name : fileName) {
                    String subName = name.substring(0, name.lastIndexOf(".")); // 获取当前文件的存放时间值
                    nameList.add(Long.parseLong(subName));
                }
                // 对nameList进行排序
                if (nameList.size() > 1) {
                    Collections.sort(nameList);
                }
                Long name = nameList.get(nameList.size() - 1);
                oldUser.setPhotoId(name + ".jpg");
            }
        }
        return oldUser;
    }

    /**
     * 根据bean 获取用户集合，207-03-08
     */
	public List<UsersApp> getUsersAppListByUsersApp(UsersApp usersApp) {
		return userAppDao.getInfoListByBean("getUsersAppListByUsers", usersApp);
	}

	/**
	 * 根据员工ID和员工名称，修改员工手机标识和手机号，2017-03-08
	 */
	public int updateUsersAppByBeanApp(UsersApp usersApp) {
		return userAppDao.updateInfoByBean("updateUsersAppByBeanApp", usersApp);
	}
}
