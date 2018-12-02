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
 * APP端 城市清洁度分析
 * @author 佟士儒
 *
 */
@Controller
public class CityCleanlinessAnalyst {

    @Autowired
    private AreaIdsForLeaderApp areaIdsForLeaderApp;
    
    @Autowired
    private CheckGramService checkGramService;
    
    /**
     * 城市道路清洁度总体分析
     */
    @RequestMapping(value="/cleAnalyst", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> cleAnalyst(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        Map<String,Object> map = new HashMap<String,Object>();
        // 近一周道路清洁度趋势图
        // 取得总克数
        List<Map<String, Object>> totalGram = checkGramService.getTotalGramsForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 取得每日平均克数
        List<Map<String, Object>> lstAvgGram = checkGramService.getAverageGramsForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 初始化一周之内每天对应的克数
        List<Map<String, Object>> gramesDay = CommonUtils.getDefautDay();
        for (Map<String, Object> mpGRAM : lstAvgGram) {
            // 每日平均克数
            double avg = (Double) mpGRAM.get("avergeGrames");
            
            // 日期（天）
            String day = (String) mpGRAM.get("showDay");
            // 设置一周之内每天对应的克数
            for (Map<String, Object> mp : gramesDay) {
                if (mp.containsKey(day)) {
                    mp.put(day, avg);
                }
            }
        }
        
        // 近一周道路检测合格率趋势图
        // 取得合格次数
        List<Map<String, Object>> lstPassNum = checkGramService.getEveryPassNumForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 取得总次数
        List<Map<String, Object>> lstTotalNum = checkGramService.getEveryTotalNumForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 初始化一周之内每天对应的合格率
        List<Map<String, Object>> passRateDay = CommonUtils.getDefautDay();
        // 7日总合格次数
        long sevDayPassNum = 0;
        // 取得7日总次数
        long sevDayTotalNum = 0;
        for (Map<String, Object> mpPassNum : lstPassNum) {
            // 每日合格次数
            long evePassNum = (Long) mpPassNum.get("passNum");
            // 每日总次数
            long eveTotalNum = 0;
            for (Map<String, Object> mpTotalNum : lstTotalNum) {
                if (mpPassNum.get("showDay").equals(mpTotalNum.get("showDay"))) {
                    eveTotalNum = (Long) mpTotalNum.get("totalNum");
                    break;
                }
            }
            // 7日总合格次数
            sevDayPassNum = sevDayPassNum + evePassNum;
            // 7日总次数
            sevDayTotalNum = sevDayTotalNum + eveTotalNum;
            // 日期（天）
            String day = (String) mpPassNum.get("showDay");
            // 设置一周之内每天对应的克数
            BigDecimal bdEvePassNum = new BigDecimal(evePassNum);
            int avgPassRate = Math.round(bdEvePassNum.divide(new BigDecimal(eveTotalNum), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).floatValue());
            for (Map<String, Object> mp : passRateDay) {
                if (mp.containsKey(day)) {
                    mp.put(day, avgPassRate);
                }
            }
        }
        
        // 全市近7日五克考核总体检测平均合格率
        BigDecimal bdSevDayPassNum = new BigDecimal(sevDayPassNum);
        int sevDayArgPassRate = Math.round(bdSevDayPassNum.divide(new BigDecimal(sevDayTotalNum), 
                2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).floatValue());
        map.put("sevDayPassNum", sevDayArgPassRate);
        // 每天平均克数
        BigDecimal totalGrames = new BigDecimal((Double)totalGram.get(0).get("totalGrames"));
        BigDecimal avgDayGrames = totalGrames.divide(new BigDecimal(sevDayTotalNum), 
                1, BigDecimal.ROUND_HALF_UP);
        map.put("avgDayGrames", avgDayGrames);
        // 近一周道路清洁度趋势图
        map.put("gramesDay", gramesDay);        
        // 近一周道路检测合格率趋势图
        map.put("passRateDay", passRateDay);        

        // 近一周全市各责任区检测力度清洁度状况表
        // 取得状况表数据
        List<Map<String, Object>> cleContrasts = checkGramService.getWeekCleContrastsForApp(
                areaIdsForLeaderApp.getBeforeDayForGramsAVG());
        // 返回值中（包括近一周全市各责任区检测力度清洁度状况表,近一周全市各责任区道路清洁度横向对比图,近一周全市各责任区道路检测合格率横向对比图）
        map.put("cleContrasts", cleContrasts);        
        return map;
    }
}
