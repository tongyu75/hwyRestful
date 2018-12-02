package com.czz.hwy.action.leaderApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.service.usermanagement.AttendanceForPlansService;

/**
 * APP端 工作抽查
 * @author 佟士儒
 *
 */
@Controller
public class WorkInspection {

    @Autowired
    private AttendanceForPlansService attService;
    
    /**
     * 全市当天工作员工考勤状态
     */
    @RequestMapping(value="/allCityWorkAtt", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> allCityWorkAtt(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 全市当天工作员工考勤状态
        List<Map<String, Object>> lstWorkAtt = attService.getAllCityWorkAttForLeaderApp();
        // 取得第一个责任区ID
        int areaId = 0;
        if (lstWorkAtt.size() > 0) {
            Map<String, Object> mp = lstWorkAtt.get(0);
            areaId = (Integer) mp.get("areaId");
        }
        // 初始化考核参数
        List<Map<Object, Object>> lstMp = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> mpParm = this.getInitParam();
        for (Map<String, Object> mp : lstWorkAtt) {
            // 当责任区不同时，建立新的返回值
            if (areaId == (Integer) mp.get("areaId")) {
                // 第一责任区
                if ((Integer) mp.get("areaId") == 1) {
                    this.setParam(mpParm, mp);
                // 第二责任区    
                } else if ((Integer) mp.get("areaId") == 2) {
                    this.setParam(mpParm, mp);
                // 第三责任区    
                } else if ((Integer) mp.get("areaId") == 3) {
                    this.setParam(mpParm, mp);
                // 第四责任区    
                } else if ((Integer) mp.get("areaId") == 4) {
                    this.setParam(mpParm, mp);
                // 第五责任区    
                } else if ((Integer) mp.get("areaId") == 5) {
                    this.setParam(mpParm, mp);
                // 第六责任区
                } else if ((Integer) mp.get("areaId") == 6) {
                    this.setParam(mpParm, mp);
                // 第七责任区    
                } else if ((Integer) mp.get("areaId") == 7) {
                    this.setParam(mpParm, mp);
                }
                mpParm.put("areaName", (String) mp.get("areaName"));
                mpParm.put("areaId", (Integer) mp.get("areaId"));
            } else {
                lstMp.add(mpParm);
                mpParm = this.getInitParam();
                // 第一责任区
                if ((Integer) mp.get("areaId") == 1) {
                    this.setParam(mpParm, mp);
                // 第二责任区    
                } else if ((Integer) mp.get("areaId") == 2) {
                    this.setParam(mpParm, mp);
                // 第三责任区    
                } else if ((Integer) mp.get("areaId") == 3) {
                    this.setParam(mpParm, mp);
                // 第四责任区    
                } else if ((Integer) mp.get("areaId") == 4) {
                    this.setParam(mpParm, mp);
                // 第五责任区    
                } else if ((Integer) mp.get("areaId") == 5) {
                    this.setParam(mpParm, mp);
                // 第六责任区
                } else if ((Integer) mp.get("areaId") == 6) {
                    this.setParam(mpParm, mp);
                // 第七责任区    
                } else if ((Integer) mp.get("areaId") == 7) {
                    this.setParam(mpParm, mp);
                }
                areaId = (Integer) mp.get("areaId");
                // 设置责任区名称
                mpParm.put("areaName", (String) mp.get("areaName"));
                // 设置责任区ID
                mpParm.put("areaId", areaId);
            }
        }
        lstMp.add(mpParm);
        map.put("cityWorkAtt", lstMp);
        return map;
    }
    
    /**
     * 当前责任区的所有员工的工作状态
     * @param areaId 责任区ID
     * @param page 页码
     */
    @RequestMapping(value="/curWorkAtt/{areaId}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> curWorkAtt(@PathVariable(value="areaId") int areaId,
            @PathVariable(value="page") int page, HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 当前责任区的所有员工的工作状态
        List<Map<String, Object>> lstCurWorkAt = attService.getCurWorkAttForLeaderApp(areaId, page);
        map.put("curWorkAtt", lstCurWorkAt);
        return map;
    }    
    
    /**
     * 设置返回参数
     */
    public void setParam(Map<Object, Object> mpParm, Map<String, Object> mp) {
        if ("上班".equals(mp.get("curStatus"))) {
            mpParm.put("workNum", (Long) mp.get("num"));
        } else if ("迟到".equals(mp.get("curStatus"))) {
            mpParm.put("lateNum", (Long) mp.get("num"));
        } else if ("未上班".equals(mp.get("curStatus"))) {
            mpParm.put("offNum", (Long) mp.get("num"));
        } else if ("任务中".equals(mp.get("curStatus"))) {
            mpParm.put("taskNum", (Long) mp.get("num"));
        } else if ("请假中".equals(mp.get("curStatus"))) {
            mpParm.put("leaveNum", (Long) mp.get("num"));
        }    
    }
    
    /**
     * 初始化考核参数
     */
    public Map<Object, Object> getInitParam() {
        // 初始化考核参数
        // 第一责任区
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("areaName", "");
        map.put("areaId", "");
        map.put("leaveNum", 0l);
        map.put("workNum", 0l);
        map.put("offNum", 0l);
        map.put("lateNum", 0l);
        map.put("taskNum", 0l);
        return map;
    }
}
