package com.czz.hwy.action.leaderApp;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.czz.hwy.utils.CommonUtils;

/**
 * APP端 工作效率
 * @author 佟士儒
 *
 */
@Controller
public class WorkEfficiency {
    @Autowired
    private AreaIdsForLeaderApp areaIdsForLeaderApp;
    
    @Autowired
    private CheckGramService checkGramService;
    
    /**
     * 城市道路清洁度总体分析
     */
    @RequestMapping(value="/workEff", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> cleAnalyst(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 近一周人员考核有效力度趋势图
        // 取得每天合格数
        List<Map<String, Object>> lstPassNum = checkGramService.getFiveMinPassNumForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 取得每天总次数
        List<Map<String, Object>> lstTotalNum = checkGramService.getFiveMinTotalNumForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 初始化一周之内每天对应的合格数
        List<Map<String, Object>> fiveMinDay = CommonUtils.getDefautDay();
        // 初始化一周之内每天对应的合格率
        List<Map<String, Object>> passRateDay = CommonUtils.getDefautDay();
        // 7日总合格次数
        long sevDayPassNum = 0;
        // 7日总次数
        long sevDayTotlaNum = 0;
        for (Map<String, Object> mpGRAM : lstPassNum) {
            // 每日合格数
            long evePassNum = (Long) mpGRAM.get("passNum");
            // 日期（天）
            String day = (String) mpGRAM.get("showDay");
            // 设置一周之内每天对应的合格数
            for (Map<String, Object> mp : fiveMinDay) {
                if (mp.containsKey(day)) {
                    mp.put(day, evePassNum);
                }
            }
            // 每日总次数
            long eveTotalNum = 0;
            for (Map<String, Object> mpTotalNum : lstTotalNum) {
                if (day.equals(mpTotalNum.get("showDay"))) {
                    // 每日总次数
                    eveTotalNum = (Long) mpTotalNum.get("totalNum");
                    break;
                }
            }
            // 7日总合格次数
            sevDayPassNum = sevDayPassNum + evePassNum;
            // 7日总次数
            sevDayTotlaNum = sevDayTotlaNum + eveTotalNum;
            // 设置一周之内每天对应的合格数
            BigDecimal bdEvePassNum = new BigDecimal(evePassNum);
            int avgPassRate = Math.round(bdEvePassNum.divide(new BigDecimal(eveTotalNum), 1, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).floatValue());;;
            // 近一周道路检测合格率趋势图
            for (Map<String, Object> mp : passRateDay) {
                if (mp.containsKey(day)) {
                    mp.put(day, avgPassRate);
                }
            }
        }
        
        // 每天平均次数
        BigDecimal bdPassNum = new BigDecimal(sevDayPassNum);
        int sevDayAvgNum = Math.round(bdPassNum.divide(new BigDecimal(7), 
                2, BigDecimal.ROUND_HALF_UP).floatValue());
        map.put("sevDayAvgNum", sevDayAvgNum);
        // 每天平均合格率
        BigDecimal bdSevDayPassNum = new BigDecimal(sevDayPassNum);
        int sevDayPassRate = Math.round(bdSevDayPassNum.divide(new BigDecimal(sevDayTotlaNum), 
                2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).floatValue());
        map.put("sevDayPassRate", sevDayPassRate);
        // 近一周人员考核有效力度趋势图
        map.put("fiveMinDay", fiveMinDay);        
        // 近一周道路检测合格率趋势图
        map.put("passRateDay", passRateDay);        
        
        // 近7日全市各责任区人员响应力度横向对比报表
        // 取得状况表数据
        List<Map<String, Object>> workEffCon = checkGramService.getWeekWorkEffConForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 返回值中（包括近7日全市各责任区人员响应力度横向对比报表,近一周全市各责任区道路清洁度横向对比图,近一周全市各责任区道路检测合格率横向对比图）
        map.put("workEffCon", workEffCon);       
        return map;
    }
}
