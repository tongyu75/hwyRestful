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

import com.czz.hwy.bean.mission.ReportInfo;
import com.czz.hwy.service.mission.ReportInfoService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 新的举报功能控制层，2017-04-26
 * @author 张咏雪 
 *
 */
@Controller
@RequestMapping(value = "/reportInfoController")
public class ReportInfoController {
    
    @Autowired
    private AccessOrigin accessOrigin;    
    
    @Autowired
    private ReportInfoService reportInfoService;//新的举报功能业务层
   
    /**
     * 查询举报信息列表，2017-04-25，分页
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/reportInfo", method=RequestMethod.GET)
    @ResponseBody    
    public String selectReportInfoList(String acceptContent, HttpServletRequest request, HttpServletResponse response){
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
        
        // 对请求参数解码并转换为举报信息对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        ReportInfo reportInfo = (ReportInfo) JSONObject.toBean(json, ReportInfo.class);
//        System.out.println(json.getString("supervisorName"));
//        reportInfo.setSupervisorName(json.getString("supervisorName"));
        
        // 根据条件查询举报记录的总条数
        int total = reportInfoService.getAllReportInfoCountByBean(reportInfo);//根据条件查询记录的总条数,2017-04-26
       
        //根据条件查询举报记录列表，分页
        List<ReportInfo> rows = reportInfoService.getReportInfoListByBean(reportInfo);//根据条件查询举报记录列表，分页,2017-04-26
        
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
