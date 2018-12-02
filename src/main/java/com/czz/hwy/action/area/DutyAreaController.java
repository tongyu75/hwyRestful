package com.czz.hwy.action.area;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.bean.area.DutyPoint;
import com.czz.hwy.bean.mission.Relay;
import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.area.DutyAreaService;
import com.czz.hwy.service.area.DutyPointService;
import com.czz.hwy.service.mission.RelayService;
import com.czz.hwy.service.mission.UserAreaService;
import com.czz.hwy.service.mission.WorkPlansService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 责任区管理
 * @author 佟士儒
 */
@Controller
@RequestMapping(value = "/dutyAreaController")
public class DutyAreaController {
    
    @Autowired
    private DutyAreaService dutyAreaService;
    
    @Autowired
    private DutyPointService dutyPointService;

    @Autowired
    private WorkPlansService workPlansService;
    
    @Autowired
    private UserAreaService userAreaService;
    
    @Autowired
    private RelayService relayService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 责任区信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/area", method=RequestMethod.GET)
    @ResponseBody
    public String getAllDutyArea(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response) {
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
        
        // 对请求参数解码并转换为责任区对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        DutyArea dutyArea = (DutyArea) JSONObject.toBean(json, DutyArea.class);
        // 查询责任区信息记录总条数
        int count=dutyAreaService.getAreaCount(dutyArea);
        // 查询责任区信息记录
        List<DutyArea> lstDutyArea = dutyAreaService.getDutyArea(dutyArea);
        
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", lstDutyArea);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回责任区信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
    
    /**
     * 添加责任区信息
     * @param acceptContent 责任区信息
     */
    @RequestMapping(value="/area", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertDutyArea(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "新增失败，新增信息不能为空！");
            return map;
        }
        
        // 对请求参数解码并转换为责任区对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        DutyArea dutyArea = (DutyArea) JSONObject.toBean(json, DutyArea.class);
        
        // 查询当前系统内是否已经存在该责任区
        DutyArea insertBean=new DutyArea();
        insertBean.setAreaName(dutyArea.getAreaName());
        DutyArea resultDutyArea=dutyAreaService.getDutyAreaByBean(insertBean);
        // 不存在责任区，插入信息的责任区
        if(resultDutyArea==null) {
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 创建者ID
            dutyArea.setCreateId(user.getEmployeeId());
            // 创建时间
            dutyArea.setCreateAt(new Date());
            // 状态标识
            dutyArea.setStatus(1);
            int opinion=dutyAreaService.insertDutyArea(dutyArea);
            // 插入成功
            if(opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
            }else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.INSERT_FAIL_MSG);
            }
        // 责任区已经存在，不允许插入    
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.EXISTS_DUTY_AREA_FAIL);
        }
        return map;
    }
    
    /**
     * 修改责任区信息
     * @param acceptContent 责任区信息
     */
    @RequestMapping(value="/area", method=RequestMethod.PUT)
    @ResponseBody    
    public Map<String, Object> updateDutyArea(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        DutyArea dutyArea = (DutyArea) JSONObject.toBean(json, DutyArea.class);
        // 根据责任区名查询当前系统内是否已经存在该责任区
        DutyArea newArea = new DutyArea();
        newArea.setAreaName(dutyArea.getAreaName());
        DutyArea resultDutyArea = dutyAreaService.getDutyAreaByBean(newArea);
        // 不存在责任区名称，允许修改
        if(resultDutyArea == null) {
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 更新者ID
            dutyArea.setUpdateId(user.getEmployeeId());
            // 更新时间
            dutyArea.setUpdateAt(new Date());
            int opinion=dutyAreaService.updateDutyArea(dutyArea);
            // 修改成功
            if (opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
            } else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.EXISTS_DUTY_AREA_FAIL);
        }            

        return map;
    }
    
    /**
     * 删除责任区信息
     * @param dutyAreaId 责任区ID
     */
    @RequestMapping(value="/area", method=RequestMethod.DELETE)
    @ResponseBody        
    public Map<String, Object> deleteDutyArea(Integer dutyAreaId, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        
        // 根据责任区ID查询责任点信息
        DutyPoint dutyPoint = new DutyPoint();
        dutyPoint.setAreaId(dutyAreaId);
        List<DutyPoint> lstDutyPoint = dutyPointService.getDutyPointListByBean(dutyPoint);
        // 根据责任区ID查询排班计划信息
        WorkPlans workPlans = new WorkPlans();
        workPlans.setAreaId(dutyAreaId);
        List<WorkPlans> lstWorkPlans = workPlansService.getWorkPlansListByBean(workPlans);
        // 根据责任区ID查询员工归属责任区信息
        UserArea userArea = new UserArea();
        userArea.setAreaId(dutyAreaId);
        List<UserArea> lstUserArea = userAreaService.getUserAreaByAreaId(userArea);
        // 根据责任区ID查询替班信息
        Relay relay = new Relay();
        relay.setAreaId(dutyAreaId);
        List<Relay> lstRelay = relayService.getRelayListByAreaId(relay);
        // 如果该责任区不存在责任点、排班信息、员工归属责任区信息和替班信息,允许删除责任区，否则允许删除责任区
        if (lstDutyPoint.size() == 0 && lstWorkPlans.size() == 0 && 
                lstUserArea.size() == 0 && lstRelay.size() == 0) {
            DutyArea area=new DutyArea();
            area.setId(dutyAreaId);
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 更新者ID
            area.setUpdateId(user.getEmployeeId());
            // 更新者时间
            area.setUpdateAt(new Date());
            area.setStatus(2);
            int opinion=dutyAreaService.updateDutyArea(area);
            // 删除成功
            if (opinion > 0) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
            } else {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.DELETE_FAIL_MSG);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.DELETE_DUTY_AREA_FAIL);            
        }
        return map;
    }
    
}
