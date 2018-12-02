/**
 * 
 */
package com.czz.hwy.action.users.watch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.watch.SubTopics;
import com.czz.hwy.bean.user.watch.BindingInformation;
import com.czz.hwy.bean.user.watch.Users;
import com.czz.hwy.service.manager.watch.SubTopicsWatchService;
import com.czz.hwy.service.usermanagement.watch.BindingInformationWatchService;
import com.czz.hwy.service.usermanagement.watch.UsersWatchService;
import com.czz.hwy.utils.ConstantUtil;


/**
 * 手表用户信息
 * @author 佟士儒
 *
 */
@Controller
@RequestMapping(value = "/userLoginWatchController")
public class UserLoginWatchController{
    
    @Autowired
    private BindingInformationWatchService bindWatchService;
    
    @Autowired
    private UsersWatchService usersWatchService;
    
    @Autowired
    private SubTopicsWatchService subTopicsWatchService;
    
    /**
     * 登录手表
     * @param employeeId 员工ID
     * @param deviceId 设备ID    
     */
    @RequestMapping(value = "/loginWatch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginWatch(String deviceId) {
        // 定义返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        if(deviceId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", 1);
            return map;
        }
        // 通过设备ID获得绑定用户信息
        Users users = usersWatchService.getLoginUser(deviceId);
        if (users != null) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", users);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", 2);
        }
        
        return map;
    }
    
    /**
     * 对手表进行注册
     * @param employeeId 员工ID
     * @param deviceId 设备ID    
     */
    @RequestMapping(value = "/registerWatch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerWatch(Integer employeeId, String deviceId) {
        // 定义返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        if(employeeId == null || deviceId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
            return map;
        }
        // 获得当前用户信息
        Users currentUsers = usersWatchService.getUserInfoByEmployeeId(employeeId.intValue());
        if (currentUsers != null) {
            // 通过员工ID获得绑定用户信息，一个员工同一时刻只能绑定一个设备
            Users users = usersWatchService.getBindingUserByEmployeeId(employeeId.intValue());
            // 当前用户没有绑定手机
            if (users == null) {
                // 更新用户信息中的绑定手机
                Users user = new Users();
                // 员工ID
                user.setEmployeeId(employeeId);
                // 绑定标识（1绑定2未绑定）
                user.setHasWatch(1);
                int option = usersWatchService.updateUserInfoByBean(user);
                
                // 插入绑定信息
                BindingInformation bean = new BindingInformation();
                // 员工ID
                bean.setEmployeeId(employeeId);
                // 设备
                bean.setDeviceId(deviceId);
                bean.setCreateId(employeeId);
                bean.setCreateAt(new Date());
                bean.setStatus(1);
                int flag = bindWatchService.insertBindingInfo(bean);
                
                // 订阅信息
                SubTopics subTopics = new SubTopics();
                subTopics.setEmployeeId(employeeId);
                SubTopics oldSubTopics = subTopicsWatchService.getSubTopicsByBean(employeeId);
                //根据员工号获取订阅信息，判断是否存在订阅信息，不存在则新增订阅信息
                boolean topicFlag = true;;
                if (oldSubTopics != null) {
                    //判断从前台获取的imei是否与后台获取的订阅信息匹配，不匹配则更新数据
                    if (!deviceId.equals(oldSubTopics.getDeviceId())) {
                       int topic = subTopicsWatchService.updateSubTopicsByBean(deviceId, oldSubTopics);
                        if (topic > 0) {
                            topicFlag = true;
                        } else {
                            topicFlag = false;
                        }
                    }
                } else {
                   int topic = subTopicsWatchService.insertSubTopics(deviceId, employeeId);
                    if (topic > 0) {
                        topicFlag = true;
                    } else {
                        topicFlag = false;
                    }
                }
                
                // 绑定成功
                if(option > 0 && flag > 0 && topicFlag == true){
                    map.put("result", ConstantUtil.SUCCESS);
                    map.put("information", currentUsers);
                }else{
                    map.put("result", ConstantUtil.FAIL);
                    map.put("information", "绑定失败！");
                }
            } else {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", "此用户已经被其它设备绑定");
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "此用户不存在,请添加用户信息");
        }
        return map;
    }
    
    /**
     * 注销用户
     * @param employeeId 员工ID
     * @param deviceId 设备ID    
     */
    @RequestMapping(value = "/logoutWatch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logoutWatch(Integer employeeId) {
        // 定义返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        if(employeeId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
            return map;
        }
        // 更新用户信息中的绑定手机状态（未绑定）
        Users user = new Users();
        // 员工ID
        user.setEmployeeId(employeeId);
        // 绑定标识（1绑定2未绑定）
        user.setHasWatch(2);
        int option = usersWatchService.updateUserInfoByBean(user);
        
        // 删除绑定信息
        BindingInformation bean = new BindingInformation();
        // 员工ID
        bean.setEmployeeId(employeeId);
        bean.setCreateId(employeeId);
        bean.setStatus(2);
        int flag = bindWatchService.updateBindingInfo(bean);
        
        // 绑定成功
        if(option > 0 && flag > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "注销成功！");
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "注销失败！");
        }
        return map;
    }
    
}
