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

import com.czz.hwy.bean.area.BoderPointFile;
import com.czz.hwy.bean.area.DutyPoint;
import com.czz.hwy.bean.area.DutyPointGather;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.area.BoderPointFileService;
import com.czz.hwy.service.area.DutyAreaService;
import com.czz.hwy.service.area.DutyPointGatherService;
import com.czz.hwy.service.area.DutyPointService;
import com.czz.hwy.service.mission.WorkPlansService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 责任点管理
 * @author 佟士儒
 */
@Controller
@RequestMapping(value = "/dutyPointController")
public class DutyPointController {
    
    @Autowired
    private DutyPointService dutyPointService;
    
    @Autowired
    private DutyAreaService dutyAreaService;

    @Resource
    private AccessOrigin accessOrigin;    
    
    @Autowired
    private WorkPlansService workPlansService;
    
    @Autowired
    private BoderPointFileService boderPointFileService;

    @Autowired
    private DutyPointGatherService dutyPointGatherService;
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 根据条件获取责任点信息
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/point", method=RequestMethod.GET)
    @ResponseBody    
    public String getDutyPointsAdmin(String acceptContent, HttpServletRequest request, 
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
        
        // 对请求参数解码并转换为责任点对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        DutyPoint dutyPoint = (DutyPoint) JSONObject.toBean(json, DutyPoint.class);
        // 查询责任点信息记录总条数
        int count = dutyPointService.getAllDutyPointCount(dutyPoint);
        // 查询责任点信息记录
        List<DutyPoint> dutyPoints = dutyPointService.getAllDutyPoint(dutyPoint);

        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", dutyPoints);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回责任点信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
    
    /**
     * 添加责任点信息
     * @param acceptContent 责任点信息
     */
    @RequestMapping(value="/point", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertDutyPoint(String acceptContent, HttpServletRequest request, HttpServletResponse response){
        
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
        
        // 对请求参数解码并转换为责任点对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        DutyPoint dutyPoint = (DutyPoint) JSONObject.toBean(json, DutyPoint.class);
        
        // 判定新增的责任点名称是否重复
        DutyPoint bean = new DutyPoint();
        bean.setPointName(dutyPoint.getPointName());
        List<DutyPoint> temps = dutyPointService.getDutyPointListByBean(bean);
        // 责任点名称重复,不允许新增
        if(temps.size() > 0) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.EXISTS_DUTY_POINT_FAIL);
        } else {
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 创建者
            dutyPoint.setCreateId(user.getEmployeeId());
            // 创建时间
            dutyPoint.setCreateAt(new Date());
            // 状态标识
            dutyPoint.setStatus(1);
            // 插入责任点
            int rtesult =dutyPointService.insertDutyPoint(dutyPoint);
            // 插入成功
            if(rtesult > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
            }else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.INSERT_FAIL_MSG);
            }
        }
        return map;
    }
    
    /**
     * 修改责任点信息
     * @param acceptContent 责任点信息
     */
    @RequestMapping(value="/point", method=RequestMethod.PUT)
    @ResponseBody    
    public Map<String, Object> updateDutyPoint(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
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
        // 对请求参数解码并转换为责任点
        JSONObject json = JSONObject.fromObject(acceptContent);
        DutyPoint dutyPoint = (DutyPoint) JSONObject.toBean(json, DutyPoint.class);

        // 判断用户修改的信息是否在责任点信息中重复
        DutyPoint bean = new DutyPoint();
        // 责任区ID
        bean.setAreaId(dutyPoint.getAreaId());
        // 责任点ID
        bean.setId(dutyPoint.getId());
        // 责任点名称
        bean.setPointName(dutyPoint.getPointName());
        DutyPoint point = dutyPointService.getDutyPointByBean(bean);
        // 不存在责任点名称，允许修改
        if(point == null){
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 修改者ID
            dutyPoint.setUpdateId(user.getEmployeeId());
            // 修改时间
            dutyPoint.setUpdateAt(new Date());
            // 更新责任点信息
            int opinion =dutyPointService.updateDutyPoint(dutyPoint);
            // 更新成功
            if (opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
            } else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
            }
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.EXISTS_DUTY_POINT_FAIL);            
        }
        return map;
    }

    /**
     * 删除责任点信息
     * @param dutyPointId 责任点ID
     */
    @RequestMapping(value="/point", method=RequestMethod.DELETE)
    @ResponseBody            
    public Map<String, Object> deleteDutyPoint(Integer dutyPointId, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        
        // 根据责任点ID查询排班计划信息
        WorkPlans workPlans = new WorkPlans();
        workPlans.setPointId(dutyPointId);
        List<WorkPlans> lstWorkPlans = workPlansService.getWorkPlansListByBean(workPlans);
        // 责任点地图信息文件
        BoderPointFile boder = new BoderPointFile();
        boder.setPointId(dutyPointId);
        List<BoderPointFile> lstBoder = boderPointFileService.getBoderPointFilesListByBean(boder);
        // 责任点地图经纬度信息
        DutyPointGather gather = new DutyPointGather();
        gather.setPointId(dutyPointId);
        List<DutyPointGather> lstGather = dutyPointGatherService.getGatherPointList(gather);
        // 查询责任点是否存在
        DutyPoint point = new DutyPoint();
        point.setId(dutyPointId);
        DutyPoint dutyAreaExits = dutyPointService.getDutyPointByBean(point);
        // 如果责任区存在，并且排班计划中没有此责任点信息，允许删除，否则不允许删除
        if(dutyAreaExits != null && lstWorkPlans.size() == 0 && lstBoder.size() == 0 && lstGather.size() == 0){
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 更新者ID
            dutyAreaExits.setUpdateId(user.getEmployeeId());
            // 更新时间
            dutyAreaExits.setUpdateAt(new Date());    
            // 状态标识(2.删除)
            dutyAreaExits.setStatus(2);
            int opinion =dutyPointService.updateDutyPoint(dutyAreaExits);
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
            map.put("information", ConstantUtil.DELETE_DUTY_POINT_FAIL);
        }
        return map;
    }
}
