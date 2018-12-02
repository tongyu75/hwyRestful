package com.czz.hwy.action.users.watch;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.version.App;
import com.czz.hwy.service.usermanagement.watch.BindingInformationWatchService;
import com.czz.hwy.service.usermanagement.watch.UsersWatchService;
import com.czz.hwy.service.version.watch.VersionWatchService;
import com.czz.hwy.utils.ConstantUtil;
/**
 * 通过此Action里的接口，与系统进行数据交互
 * 
 * @author 佟士儒
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/sysWatchController")
public class SystemWatchController{
   
    @Autowired
    private VersionWatchService versionWatchService;

    @Autowired
    private BindingInformationWatchService bindWatchService;
    
    @Autowired
    private UsersWatchService usersWatchService;
    /**
     * 获取手表APP当前客户端的最新版本信息
     * @param versionCode 当前版本号
     */
    @RequestMapping(value = "/appVerCodeWatch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> appVerCode(Integer version, HttpServletRequest request, 
            HttpServletResponse response) {
        // 定义返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        if(version == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
            return map;
        }
        // 获得后台最新更新系统版本
        App resultApp = versionWatchService.getAppVersionCode();
        if(resultApp != null) {
            //判断从移动端获取的版本号是否为最新版本
            if(version.intValue() < resultApp.getVersionCode()) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", resultApp);
            } else {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.APP_INFO_NEW);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
        }
        return map;
    }
}
