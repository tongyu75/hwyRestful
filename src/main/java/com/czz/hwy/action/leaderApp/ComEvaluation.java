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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.leaderApp.AreaIdsForLeaderApp;
import com.czz.hwy.service.mission.CheckGramService;

/**
 * APP端 综合考核
 * @author 佟士儒
 *
 */
@Controller
public class ComEvaluation {
    @Autowired
    private AreaIdsForLeaderApp areaIdsForLeaderApp;
    
    @Autowired
    private CheckGramService checkGramService;
    
    /**
     * 综合考核
     */
    @RequestMapping(value="/comEvaluation", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> cleAnalyst(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 取得综合考核
        List<Map<String, Object>> lstComEvaluation = checkGramService.getComEvaluationForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 返回结果集
        List<Map<Object, Object>> lstCom = new ArrayList<Map<Object, Object>>();
        // 存储责任区ID
        List<Integer> lstId = new ArrayList<Integer>();
        // 取得第一个责任区ID
        int areaId = 0;
        if (lstComEvaluation.size() > 0) {
            Map<String, Object> mp = lstComEvaluation.get(0);
            areaId = (Integer) mp.get("areaId");
            lstId.add(areaId);
        }
        // 初始化考核参数
        Map<Object, Object> mpParm = this.getInitParam();
        // 设置参数
        for (Map<String, Object> mp : lstComEvaluation) {
            // 只有 第一到第七责任区显示
            if ((Integer) mp.get("areaId") == 1 || (Integer) mp.get("areaId") == 2 || (Integer) mp.get("areaId") == 3 || 
                (Integer) mp.get("areaId") == 4 || (Integer) mp.get("areaId") == 5 || (Integer) mp.get("areaId") == 6 || 
                (Integer) mp.get("areaId") == 7 ) {
                // 当责任区不同时，建立新的返回值
                if (areaId == (Integer) mp.get("areaId")) {
                    // 第一责任区
                    if ((Integer) mp.get("areaId") == 1) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第二责任区    
                    } else if ((Integer) mp.get("areaId") == 2) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第三责任区    
                    } else if ((Integer) mp.get("areaId") == 3) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第四责任区    
                    } else if ((Integer) mp.get("areaId") == 4) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第五责任区    
                    } else if ((Integer) mp.get("areaId") == 5) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第六责任区
                    } else if ((Integer) mp.get("areaId") == 6) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第七责任区    
                    } else if ((Integer) mp.get("areaId") == 7) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                    }
                    mpParm.put("areaName", (String) mp.get("areaName"));
                    mpParm.put("areaId", (Integer) mp.get("areaId"));
                } else {
                    lstCom.add(mpParm);
                    mpParm = this.getInitParam();
                    // 第一责任区
                    if ((Integer) mp.get("areaId") == 1) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第二责任区    
                    } else if ((Integer) mp.get("areaId") == 2) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第三责任区    
                    } else if ((Integer) mp.get("areaId") == 3) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第四责任区    
                    } else if ((Integer) mp.get("areaId") == 4) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第五责任区    
                    } else if ((Integer) mp.get("areaId") == 5) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第六责任区
                    } else if ((Integer) mp.get("areaId") == 6) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                        // 第七责任区    
                    } else if ((Integer) mp.get("areaId") == 7) {
                        // 设置对应的考核数量
                        mpParm.put((Integer) mp.get("supervisorType"), (Long) mp.get("number"));
                    }
                    areaId = (Integer) mp.get("areaId");
                    lstId.add(areaId);
                    // 设置责任区名称
                    mpParm.put("areaName", (String) mp.get("areaName"));
                    // 设置责任区ID
                    mpParm.put("areaId", areaId);
                }
            }
        }
        lstCom.add(mpParm);
        // 对于上面没有取得综合考核的责任区设定为0
        // 设置责任区ID
        if (!lstId.contains(1)) {
            // 初始化考核参数
            Map<Object, Object> parm1 = this.getInitParam();
            // 设置责任区名称
            parm1.put("areaName", "第一责任区");
            // 设置责任区ID
            parm1.put("areaId", 1);
            lstCom.add(parm1);
        }
        if (!lstId.contains(2)) {
            // 初始化考核参数
            Map<Object, Object> parm2 = this.getInitParam();
            // 设置责任区名称
            parm2.put("areaName", "第二责任区");
            // 设置责任区ID
            parm2.put("areaId", 2);
            lstCom.add(parm2);
        } 
        if (!lstId.contains(3)) {
            // 初始化考核参数
            Map<Object, Object> parm3 = this.getInitParam();
            // 设置责任区名称
            parm3.put("areaName", "第三责任区");
            // 设置责任区ID
            parm3.put("areaId", 3);
            lstCom.add(parm3);
        } 
        if (!lstId.contains(4)) {
            // 初始化考核参数
            Map<Object, Object> parm4 = this.getInitParam();
            // 设置责任区名称
            parm4.put("areaName", "第四责任区");
            // 设置责任区ID
            parm4.put("areaId", 4);
            lstCom.add(parm4);
        } 
        if (!lstId.contains(5)) {
            // 初始化考核参数
            Map<Object, Object> parm5 = this.getInitParam();
            // 设置责任区名称
            parm5.put("areaName", "第五责任区");
            // 设置责任区ID
            parm5.put("areaId", 5);
            lstCom.add(parm5);
        }
        if (!lstId.contains(6)) {
            // 初始化考核参数
            Map<Object, Object> parm6 = this.getInitParam();
            // 设置责任区名称
            parm6.put("areaName", "第六责任区");
            // 设置责任区ID
            parm6.put("areaId", 6);
            lstCom.add(parm6);
        }
        if (!lstId.contains(7)) {
            // 初始化考核参数
            Map<Object, Object> parm7 = this.getInitParam();
            // 设置责任区名称
            parm7.put("areaName", "第七责任区");
            // 设置责任区ID
            parm7.put("areaId", 7);
            lstCom.add(parm7);
        }
        map.put("comEvaluation", lstCom);
        return map;
    }
    
    /**
     * 初始化考核参数
     */
    public Map<Object, Object> getInitParam() {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(3, 0);
        map.put(4, 0);
        map.put(5, 0);
        map.put(6, 0);
        map.put(7, 0);
        map.put(8, 0);
        map.put(9, 0);
        map.put(10, 0);        
        map.put(11, 0);
        map.put(12, 0);
        map.put(13, 0);
        map.put(14, 0);        
        map.put(15, 0);
        map.put(16, 0);        
        map.put(17, 0);        
        map.put(18, 0);                
        return map;
    }
}
