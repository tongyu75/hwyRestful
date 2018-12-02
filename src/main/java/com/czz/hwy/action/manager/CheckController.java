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

import com.czz.hwy.bean.mission.CheckGram;
import com.czz.hwy.bean.mission.CheckTime;
import com.czz.hwy.bean.mission.Reports;
import com.czz.hwy.service.manager.OneClickAlarmService;
import com.czz.hwy.service.mission.CheckGramService;
import com.czz.hwy.service.mission.CheckTimeService;
import com.czz.hwy.service.mission.ReportsService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;

/**
 * 考核管理
 * 功能描述
 * @author 佟士儒 2016-11-10
 */
@Controller
@RequestMapping(value = "/checkController")
public class CheckController {

    @Autowired
    private OneClickAlarmService oneClickAlarmService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private CheckGramService checkGramService;
    
    @Autowired
    private CheckTimeService checkTimeService;
    
    @Autowired
    private ReportsService reportsService;
    /**
     * 五克、五分钟、纪律监察考核信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/check", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getAlarmInfos(String type, String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为相应对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        // 五克
        if ("1".equals(type)) {
            // 五克信息对象
            CheckGram checkGram = (CheckGram) JSONObject.toBean(json, CheckGram.class);
            // 查询五克信息记录总条数
            int count = checkGramService.getCheckGramHistoryCount(checkGram);
            // 查询五克信息记录
            List<Map<String, Object>> lstGram = checkGramService.getCheckGramHistory(checkGram);
            // 设置返回数据
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", count);
            map.put("rows", lstGram);
        // 五分钟
        } else if ("2".equals(type)) {
            // 五分钟信息对象
            CheckTime checkTime = (CheckTime) JSONObject.toBean(json, CheckTime.class);
            // 查询五分钟信息记录总条数
            int count = checkTimeService.getCheckTimeHistoryCount(checkTime);
            // 查询五分钟信息记录
            List<Map<String, Object>> lstTime = checkTimeService.getCheckTimeHistory(checkTime);
            // 设置返回数据
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", count);
            map.put("rows", lstTime);
            // 纪律监察
        } else if ("3".equals(type)) {
            // 纪律监察信息对象
            Reports reports = (Reports) JSONObject.toBean(json, Reports.class);            
            // 查询纪律监察信息记录总条数
            int count = reportsService.getReportsHistoryJCCount(reports);
            // 查询纪律监察信息记录
            List<Map<String, Object>> lstReports = reportsService.getReportsHistoryJC(reports);
            // 设置返回数据
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", count);
            map.put("rows", lstReports);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.SELECT_FAIL_MSG);
        }
        
        return map;
    }
}
