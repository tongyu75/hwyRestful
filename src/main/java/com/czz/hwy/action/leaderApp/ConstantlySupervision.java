package com.czz.hwy.action.leaderApp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.service.area.DutyAreaService;

/**
 * APP端 实时监督
 * @author 佟士儒
 *
 */
@Controller
public class ConstantlySupervision {

    @Autowired
    private DutyAreaService dutyAreaService;
    
    /**
     * 获取责任区
     */
    @RequestMapping(value="/dutyAreaLeaApp", method = RequestMethod.GET)
    @ResponseBody
    public List<DutyArea> dutyAreaLeaApp(HttpServletRequest request, 
            HttpServletResponse response) {
        // 获取责任区
        List<DutyArea> lstArea = dutyAreaService.getDutyAreaForLeaderApp();
        return lstArea;
    }
}
