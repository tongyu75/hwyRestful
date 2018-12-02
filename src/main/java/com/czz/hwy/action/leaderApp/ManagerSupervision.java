package com.czz.hwy.action.leaderApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.CheckGram;
import com.czz.hwy.bean.mission.CheckTime;
import com.czz.hwy.bean.mission.NoQualifiedNumber;
import com.czz.hwy.service.mission.CheckGramService;
import com.czz.hwy.service.mission.CheckTimeService;
import com.czz.hwy.service.mission.NoQualifiedNumberService;
import com.czz.hwy.utils.ConstantUtil;

/**
 * APP端 管理人员尽职监督
 * @author 佟士儒
 *
 */
@Controller
public class ManagerSupervision {

    @Autowired
    private NoQualifiedNumberService noQualifiedNumberService;
    
    @Autowired
    private CheckTimeService checkTimeService;
    
    @Autowired
    private CheckGramService checkGramService;
    
    /**
     * 道路浮沉监督(今日道路浮尘测量信息)
     */
    @RequestMapping(value="/todayFiveGramSupervision/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> todayFiveGramSupervision(@PathVariable(value="type") int type,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        // 获取七天内每人每天五分钟或五克不合格次数用于排名
        List<NoQualifiedNumber> lstBean = noQualifiedNumberService.getNoFiveGramMinNumForApp(type);
        // 初始化从当前时间算起的一周日期
        List<Map<String, Object>> day = getDefautDay();
        // 排名变量
        int i = 1;
        // 算出每一天的排名情况
        for (Map<String, Object> mp : day) {
            for (int j = 1; j < lstBean.size() + 1; j++) {
                if (mp.containsKey(lstBean.get(j - 1).getShowDay())) {
                    if (j == 1 || i == 1) {
                        lstBean.get(j - 1).setRank("1");
                        i = 0;
                    } else {
                        NoQualifiedNumber bean1 = null;
                        NoQualifiedNumber bean2 = null;
                        if (j == 1) {
                            bean1 = lstBean.get(j - 1);
                        } else {
                            bean1 = lstBean.get(j - 1);
                            bean2 = lstBean.get(j - 2);
                        }
                        if (bean1.getNum() == bean2.getNum())  {
                            // 当前排名与上一位排名相等
                            lstBean.get(j - 1).setRank(bean2.getRank());
                        } else {
                            // 当前排名与上一位排名不相等,上一位排名+1
                            lstBean.get(j - 1).setRank(String.valueOf(Integer.parseInt(bean2.getRank()) + 1));
                        }
                    }
                } else {
                    i = 1;
                }
            }
        }
        
        // 日期format
        SimpleDateFormat sdfDD = new SimpleDateFormat("dd");
        // 获取当前日期对应日
        String currentDay = sdfDD.format(new Date());
        // 获取当前日期对应的前一日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String beforeDay = sdfDD.format(calendar.getTime());
        
        // 今日道路浮尘测量信息
        List<NoQualifiedNumber> todayInfo = new ArrayList<NoQualifiedNumber>();
        for (NoQualifiedNumber bean : lstBean) {
            // 获取每人今日浮尘监督信息
            if (bean.getShowDay().equals(currentDay)) {
                // 当前排名
                String rank = bean.getRank();
                // 前日排名
                String beforeRank = StringUtils.EMPTY;
                for (NoQualifiedNumber n : lstBean) {
                    // 获取前日的排名
                    if ((n.getEmployeeId().intValue() ==  bean.getEmployeeId().intValue())
                            && (n.getShowDay().equals(beforeDay))) {
                        beforeRank = n.getRank();
                        break;
                    }
                }
                // 前日没有排名, 排名无变化
                if (StringUtils.isEmpty(beforeRank)) {
                    bean.setRankFlag("2");
                // 当前排名大于前日排名, 排名上升
                } else if (rank.compareTo(beforeRank) < 0) {
                    bean.setRankFlag("1");
                // 当前排名等于前日排名, 排名无变化
                } else if (rank.compareTo(beforeRank) == 0) {
                    bean.setRankFlag("2");
                // 当前排名小于前日排名, 排名下降
                } else if (rank.compareTo(beforeRank) > 0) {
                    bean.setRankFlag("3");
                }
                todayInfo.add(bean);
            }           
        }
        // 今日道路浮尘测量信息
        if (todayInfo.size() > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("todayFiveGramTime", todayInfo);
        } else {
            map.put("result", ConstantUtil.FAIL);
        }
        return map;
    }
    
    /**
     * 道路浮沉监督(近一周道路浮尘测量信息)
     */
    @RequestMapping(value="/sevenDayfiveGramSup/{employeeId}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> sevenDayfiveGramSup(@PathVariable(value="employeeId") int employeeId, 
            @PathVariable(value="type") int type, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        // 获取七天内每人每天五分钟不合格次数用于排名
        List<NoQualifiedNumber> lstBean = noQualifiedNumberService.getNoFiveGramMinNumForApp(type);
        // 初始化从当前时间算起的一周日期
        List<Map<String, Object>> day = getDefautDay();
        // 排名变量
        int i = 1;
        // 算出每一天的排名情况
        for (Map<String, Object> mp : day) {
            for (int j = 1; j < lstBean.size() + 1; j++) {
                if (mp.containsKey(lstBean.get(j - 1).getShowDay())) {
                    if (j == 1 || i == 1) {
                        lstBean.get(j - 1).setRank("1");
                        i = 0;
                    } else {
                        NoQualifiedNumber bean1 = null;
                        NoQualifiedNumber bean2 = null;
                        if (j == 1) {
                            bean1 = lstBean.get(j - 1);
                        } else {
                            bean1 = lstBean.get(j - 1);
                            bean2 = lstBean.get(j - 2);
                        }
                       if (bean1.getNum() == bean2.getNum())  {
                           // 当前排名与上一位排名相等
                           lstBean.get(j - 1).setRank(bean2.getRank());
                       } else {
                           // 当前排名与上一位排名不相等,上一位排名+1
                           lstBean.get(j - 1).setRank(String.valueOf(Integer.parseInt(bean2.getRank()) + 1));
                       }
                    }
                } else {
                    i = 1;
                }
            }
        }
        
        // 初始化从当前时间算起的一周日期
        List<Map<String, Object>> sevenDay = getSevenDay();
        for (NoQualifiedNumber bean : lstBean) {
            for (Map<String, Object> mp : sevenDay) {
                if ((bean.getEmployeeId() == employeeId) 
                        && (mp.containsKey(bean.getShowDay()))) {
                    mp.put("rank", bean.getRank());
                    mp.put("num", bean.getNum());
                }
            }
        }
        
        // 近一周道路浮尘测量信息
        if (lstBean.size() > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("sevenDayFiveGramTime", sevenDay);
        } else {
            map.put("result", ConstantUtil.FAIL);
        }
        
        // 五克
        if (type == 1) {
            // 近一周道路浮尘测量地点
            List<CheckGram> lstCheckGram = checkGramService.getlatLngForLeadApp(employeeId);
            if (lstCheckGram.size() > 0) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("checkTimeGramLatLng", lstCheckGram);
            } else {
                map.put("result", ConstantUtil.FAIL);
            }
        // 五分钟    
        } else {
            // 近一周道路浮尘测量地点
            List<CheckTime> lstCheckTime = checkTimeService.getlatLngForLeadApp(employeeId);
            if (lstCheckTime.size() > 0) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("checkTimeGramLatLng", lstCheckTime);
            } else {
                map.put("result", ConstantUtil.FAIL);
            }
        }
        return map;
    }
    
    /**
     * 设置默认八天内容，然后调用者根据实际情况设置值
     */              
    public static List<Map<String, Object>> getDefautDay() {
        // 天
        SimpleDateFormat sdfDD = new SimpleDateFormat("dd");
        List<Map<String, Object>> lstMp = new ArrayList<Map<String, Object>>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 前七天
        Map<String, Object> map1 = new LinkedHashMap<String, Object>();
        Calendar calendar7 = Calendar.getInstance();
        calendar7.setTime(new Date());
        calendar7.add(Calendar.DAY_OF_MONTH, -7);
        String day7 = sdfDD.format(calendar7.getTime());
        map1.put(day7, 0);
        lstMp.add(map1);
        
        // 前六天
        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        Calendar calendar6 = Calendar.getInstance();
        calendar6.setTime(new Date());
        calendar6.add(Calendar.DAY_OF_MONTH, -6);
        String day6 = sdfDD.format(calendar6.getTime());
        map2.put(day6, 0);
        lstMp.add(map2);
        
        // 前五天
        Map<String, Object> map3 = new LinkedHashMap<String, Object>();
        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(new Date());
        calendar5.add(Calendar.DAY_OF_MONTH, -5);
        String day5 = sdfDD.format(calendar5.getTime());
        map3.put(day5, 0);
        lstMp.add(map3);
        
        // 前四天
        Map<String, Object> map4 = new LinkedHashMap<String, Object>();
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(new Date());
        calendar4.add(Calendar.DAY_OF_MONTH, -4);
        String day4 = sdfDD.format(calendar4.getTime());
        map4.put(day4, 0);
        lstMp.add(map4);
        
        // 前三天
        Map<String, Object> map5 = new LinkedHashMap<String, Object>();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(new Date());
        calendar3.add(Calendar.DAY_OF_MONTH, -3);
        String day3 = sdfDD.format(calendar3.getTime());
        map5.put(day3, 0);
        lstMp.add(map5);
        
        // 前二天
        Map<String, Object> map6 = new LinkedHashMap<String, Object>();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.add(Calendar.DAY_OF_MONTH, -2);
        String day2 = sdfDD.format(calendar2.getTime());
        map6.put(day2, 0);
        lstMp.add(map6);
        
        // 前一天
        Map<String, Object> map7 = new LinkedHashMap<String, Object>();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String day1 = sdfDD.format(calendar.getTime());
        map7.put(day1, 0);
        lstMp.add(map7);
        
        // 当前天
        Map<String, Object> map8 = new LinkedHashMap<String, Object>();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String day = sdfDD.format(calendar.getTime());
        map8.put(day, 0);
        lstMp.add(map8);
        
        return lstMp;
    }    
    
    /**
     * 设置默认七天内容，然后调用者根据实际情况设置值
     */              
    public static List<Map<String, Object>> getSevenDay() {
        // 天
        SimpleDateFormat sdfDD = new SimpleDateFormat("dd");
        List<Map<String, Object>> lstMp = new ArrayList<Map<String, Object>>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 前七天
        Map<String, Object> map1 = new LinkedHashMap<String, Object>();
        Calendar calendar7 = Calendar.getInstance();
        calendar7.setTime(new Date());
        calendar7.add(Calendar.DAY_OF_MONTH, -7);
        String day7 = sdfDD.format(calendar7.getTime());
        map1.put("day", day7);
        map1.put(day7, 0);
        lstMp.add(map1);
        
        // 前六天
        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        Calendar calendar6 = Calendar.getInstance();
        calendar6.setTime(new Date());
        calendar6.add(Calendar.DAY_OF_MONTH, -6);
        String day6 = sdfDD.format(calendar6.getTime());
        map2.put("day", day6);
        map2.put(day6, 0);
        lstMp.add(map2);
        
        // 前五天
        Map<String, Object> map3 = new LinkedHashMap<String, Object>();
        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(new Date());
        calendar5.add(Calendar.DAY_OF_MONTH, -5);
        String day5 = sdfDD.format(calendar5.getTime());
        map3.put("day", day5);
        map3.put(day5, 0);
        lstMp.add(map3);
        
        // 前四天
        Map<String, Object> map4 = new LinkedHashMap<String, Object>();
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(new Date());
        calendar4.add(Calendar.DAY_OF_MONTH, -4);
        String day4 = sdfDD.format(calendar4.getTime());
        map4.put("day", day4);
        map4.put(day4, 0);
        lstMp.add(map4);
        
        // 前三天
        Map<String, Object> map5 = new LinkedHashMap<String, Object>();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(new Date());
        calendar3.add(Calendar.DAY_OF_MONTH, -3);
        String day3 = sdfDD.format(calendar3.getTime());
        map5.put("day", day3);
        map5.put(day3, 0);
        lstMp.add(map5);
        
        // 前二天
        Map<String, Object> map6 = new LinkedHashMap<String, Object>();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.add(Calendar.DAY_OF_MONTH, -2);
        String day2 = sdfDD.format(calendar2.getTime());
        map6.put("day", day2);
        map6.put(day2, 0);
        lstMp.add(map6);
        
        // 前一天
        Map<String, Object> map7 = new LinkedHashMap<String, Object>();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String day1 = sdfDD.format(calendar.getTime());
        map7.put("day", day1);
        map7.put(day1, 0);
        lstMp.add(map7);
        
        return lstMp;
    }        
}
