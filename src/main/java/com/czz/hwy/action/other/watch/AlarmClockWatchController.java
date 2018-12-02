package com.czz.hwy.action.other.watch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.service.mission.watch.DutyPlansWatchService;
import com.czz.hwy.utils.ConstantUtil;

/***
 * 通信录管理
 * @author 佟士儒
 */
@Controller
@RequestMapping(value = "/alaCloWatchController")
public class AlarmClockWatchController {

    @Autowired
    private DutyPlansWatchService dutyPlansWatchService;
    
    /**
     * 闹钟查询
     * @param employeeId 员工ID
     */
    @RequestMapping(value="/alarmClockWatch", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllDutyArea(Integer employeeId, HttpServletRequest request, 
            HttpServletResponse response) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        if(employeeId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
            return map;
        }
        // 查询闹钟信息
        List<Map<String,Object>> lstAlarm = dutyPlansWatchService.getAlarmClockByEmployeeId(employeeId.intValue());
        
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", lstAlarm);
        
        // 设置返回数据
        return map;
    }
}
