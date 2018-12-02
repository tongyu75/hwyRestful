package com.czz.hwy.action.manager.watch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.TopicsVo;
import com.czz.hwy.service.manager.watch.TopicsWatchService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 通知管理
 * @author 佟士儒
 */
@Controller
@RequestMapping(value = "/informationWatchController")
public class InformationWatchController {

    // 系统通知业务层接口
    @Autowired
    private TopicsWatchService topicsWatchService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    /**
     * 获取通知信息
     */
    @RequestMapping(value="/infomationWatch", method = RequestMethod.POST)
    @ResponseBody    
    public String getAllInfo(Integer employeeId, Integer versionCode) {

        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        // 从请求信息中获取请求字段
        if(employeeId == null || versionCode == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
            return map.toString();
        }
        TopicsVo topicsVo = new TopicsVo();
        topicsVo.setEmployeeId(employeeId.intValue());
        topicsVo.setVsrsionCode(versionCode.intValue());
        List<Map<String, Object>> topicsVoList = topicsWatchService.getTopicsInfo(topicsVo);    
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", topicsVoList);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = JSONObject.fromObject(map, jsonConfig);
        return json.toString();
    }
}
