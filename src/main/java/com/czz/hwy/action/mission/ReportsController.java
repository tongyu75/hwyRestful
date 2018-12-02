/**
 * 
 */
package com.czz.hwy.action.mission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.Reports;
import com.czz.hwy.service.mission.ReportsService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 监督举报信息
 * @author 佟士儒
 *
 */
@Controller
@RequestMapping(value = "/reportsController")
public class ReportsController {
    
    @Autowired
    private AccessOrigin accessOrigin;    
    
    @Autowired
    private ReportsService reportsService;
    /**
     * 监督举报查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/reports", method=RequestMethod.GET)
    @ResponseBody    
    public String showMessages(String acceptContent, HttpServletRequest request, 
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
        
        // 对请求参数解码并转换为部门信息对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        Reports reports = (Reports) JSONObject.toBean(json, Reports.class);
        
        // 查询消息信息记录总条数
        int total = reportsService.getReportsHistoryCount(reports);
        // 查询消息信息记录
        List<Reports> rows = reportsService.getReportsHistory(reports);
        
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", total);
        map.put("rows", rows);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回责任区信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
        
    }
    
}
