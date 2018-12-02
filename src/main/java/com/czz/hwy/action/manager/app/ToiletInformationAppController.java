package com.czz.hwy.action.manager.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.app.ToiletInformationApp;
import com.czz.hwy.service.manager.app.ToiletInformationAppService;
import com.czz.hwy.utils.ConstantUtil;

/***
 * 公测功能
 * @author 张咏雪
 * @Date 2016-10-21
 */
@Controller
@RequestMapping(value = "/toiletInfAppController")
public class ToiletInformationAppController {
    
    @Autowired
    private ToiletInformationAppService toiletAppService;
    
    /**
     * 获取公测信息列表
     */
    @RequestMapping(value="/toilet", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getInform(){
        Map<String,Object> map = new HashMap<String,Object> ();
        // 获取公测信息列表
        List<ToiletInformationApp> lstToilet = toiletAppService.listToiletInformation();
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", lstToilet.size());
        map.put("rows", lstToilet);

        // 返回公测信息
        JSONObject jsonobject = JSONObject.fromObject(map);
        return jsonobject.toString();
    } 
}
