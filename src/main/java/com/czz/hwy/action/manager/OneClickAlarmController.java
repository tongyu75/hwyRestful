package com.czz.hwy.action.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.OneClickAlarm;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.manager.OneClickAlarmService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 一键报警的action
 * 功能描述
 * @author 张咏雪 2016-11-01
 */
@Controller
@RequestMapping(value = "/oneClickAlarmController")
public class OneClickAlarmController {

    @Autowired
    private OneClickAlarmService oneClickAlarmService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 报警信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/oneClickAlarm", method=RequestMethod.GET)
    @ResponseBody    
    public String getAlarmInfos(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map.toString();
        }
        
        // 对请求参数解码并转换为报警信息对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        OneClickAlarm alarm = (OneClickAlarm) JSONObject.toBean(json, OneClickAlarm.class);
        
        // 查询报警信息记录总条数
        int count = oneClickAlarmService.getAlarmCountByBean(alarm);
        // 查询报警信息记录
        List<OneClickAlarm> lstAlarm = oneClickAlarmService.getAlarmInfosByBean(alarm);
        
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", lstAlarm);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回报警信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }

    /**
     * 报警处理
     * @param acceptContent 报警信息
     */
    @RequestMapping(value="/oneClickAlarm", method=RequestMethod.PUT)
    @ResponseBody    
    public Map<String, Object> setAlarmInfo(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(StringUtils.isEmpty(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        OneClickAlarm alarm = (OneClickAlarm) JSONObject.toBean(json, OneClickAlarm.class);

        // 从缓存中获取用户信息
        String userToken = CommonUtils.getCookieValue(request, "userToken");
        Users user = (Users) redisTemplate.opsForValue().get(userToken);
        // 更新者ID
        alarm.setUpdateId(String.valueOf(user.getEmployeeId()));
        // 更新时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        alarm.setUpdateAt(sdf.format(new Date()));
        // 报警处理
        int opinion = oneClickAlarmService.updateAlarmInfo(alarm);
        // 修改成功
        if (opinion > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
        } else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
        }
        
        return map;
    }
}
