package com.czz.hwy.action.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.user.AttendanceForPlans;
import com.czz.hwy.service.manager.OneClickAlarmService;
import com.czz.hwy.service.usermanagement.AttendanceForPlansService;
import com.czz.hwy.service.usermanagement.AttendancesService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;

/**
 * 考勤管理
 * 功能描述
 * @author 佟士儒 2016-11-10
 */
@Controller
@RequestMapping(value = "/attendanceController")
public class AttendanceController {

    @Autowired
    private OneClickAlarmService oneClickAlarmService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private AttendanceForPlansService attendanceForPlansService;

    @Autowired
    private AttendancesService attendancesService;
    /**
     * 考勤信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/attendance", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getAlarmInfos(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(StringUtils.isEmpty(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为相应对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        // 考勤信息对象
        AttendanceForPlans attendance = (AttendanceForPlans) JSONObject.toBean(json, AttendanceForPlans.class);
        // 查询考勤信息记录总条数
        int count = attendanceForPlansService.getAttendanceHistoryCount(attendance);
        // 查询考勤信息记录
        List<Map<String, Object>> lstGram = attendanceForPlansService.getAttendanceHistory(attendance);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", lstGram);
        return map;
    }
    
    /**
     * 考勤坐标查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/coordinate", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getCoordinate(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件信息
        Map<String, Object> selectMap = new HashMap<String, Object>();
        // 判断接收参数
        if(StringUtils.isEmpty(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为相应对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        // 考勤时间
        String attendanceTime = json.getString("attendanceTime");
        // 员工ID
        int employeeId = json.getInt("employeeId");
        // 上班时间
        String dutyOnTime = json.getString("dutyOnTime");
        // 下班时间
        String dutyOffTime = json.getString("dutyOffTime");
        selectMap.put("attendanceTime", attendanceTime);
        selectMap.put("employeeId", employeeId);
        selectMap.put("dutyOnTime", dutyOnTime);
        selectMap.put("dutyOffTime", dutyOffTime);
        // 查询考勤坐标信息记录
        List<Map<String, Object>> lstGram = attendancesService.getAttendancesCoordinate(selectMap);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", lstGram.size());
        map.put("rows", lstGram);
        return map;
    }
}
