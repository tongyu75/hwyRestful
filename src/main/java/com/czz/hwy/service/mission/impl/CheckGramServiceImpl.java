package com.czz.hwy.service.mission.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.CheckGram;
import com.czz.hwy.bean.mission.KindInfo;
import com.czz.hwy.dao.mission.CheckGramDao;
import com.czz.hwy.service.mission.CheckGramService;
@Service
public class CheckGramServiceImpl implements CheckGramService {
	@Autowired
	private CheckGramDao checkGramDao;
	public List<CheckGram> getCheckGramByBean(int employeeId)
	{
		CheckGram checkGram=new CheckGram();
		checkGram.setEmployeeId(employeeId);
		checkGram.setCheckStatus(2);
		return checkGramDao.getInfoListByBean("getCheckGramForSelf",checkGram);
	}
	public CheckGram getCheckGramById(int id)
	{
		return checkGramDao.getInfoById("getCheckGramById",id);
	}
	public List<CheckGram> getCheckGramByBean(int employeeId,String date)
	{
		CheckGram checkGram=new CheckGram();
		checkGram.setEmployeeId(employeeId);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    try {
			Date newDate=dateFormat.parse(date);
			checkGram.setCheckTime(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    List<CheckGram> checkGrams=checkGramDao.getInfoListByBean("getCheckGramForSelf",checkGram);
	    return checkGrams;
	}
	public Map<String,Object> getCheckGramData()
	{
		Map<String,Object> performance=new HashMap<String,Object>();
		List<Map<String, Object>> couMon=checkGramDao.getInfoListMap("getCheckGramFailCouMon");
	      
		List<Map<String, Object>> peoMon=checkGramDao.getInfoListMap("getCheckGramFailPeoMon");
	      
		double peoNum=checkGramDao.getInfoCount("getCheckGramFailPeoNum");
        
		double couNum=checkGramDao.getInfoCount("getCheckGramFailCouNum");
        
		double allDays=checkGramDao.getInfoCount("getCheckGramAllDays");
        
		double avgPeo=peoNum/allDays;
		double avgCou=couNum/allDays;
		performance.put("couMon", couMon);
		performance.put("peoMon", peoMon);
		performance.put("failPeoNum", peoNum);
		performance.put("failCouNum", couNum);
		performance.put("avgPeo", avgPeo);
		performance.put("avgCou", avgCou);
		performance.put("differencePeo", peoNum-avgPeo);
		performance.put("differenceCou", couNum-avgCou);
		return performance;
	}
	public List<KindInfo> getKindInfo(String employeeId, String date) {
		CheckGram checkGram=new CheckGram();
		checkGram.setEmployeeId(Integer.parseInt(employeeId));
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    try {
			Date newDate=dateFormat.parse(date);
			checkGram.setCheckTime(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return checkGramDao.getKindCountInfo(checkGram);
        // return sqlSessionTemplate.selectList("getcheckGramTypeInfo",checkGram);		
	}
	/**
	 * 五克任务统计数据
	 */
	public List<Map<String, Object>> getCheckGramCensus(Map<String, Object> map) {
		return checkGramDao.getInfoListMapByMap("checkGramCensus", map);
	}
	/**
	 * 五克任务统计数据总量
	 */
	public int getCheckGramCensusCount(Map<String, Object> map) {
		return checkGramDao.getInfoCountByMap("checkGramCensusCount",map);
	}
	
	/**
	 * 五克任务获取折线图不合格次数
	 */
	public List<Map<String, Object>> getCheckGramsTsCensus(Map<String, Object> m) {
        return checkGramDao.getInfoListMapByMap("checkGramsTsCensus", m);                
	}
	
	/**
	 * 五克任务获取折线图不合格人数
	 */
	public List<Map<String, Object>> getCheckGramsPsCensus(Map<String, Object> m) {
        return checkGramDao.getInfoListMapByMap("checkGramPsCensus", m);        
	}
	

	/**
	 * 五克任务获取折线图合格次数
	 */
	public List<Map<String, Object>> getCheckGramsPNCensus(Map<String, Object> m) {
        return checkGramDao.getInfoListMapByMap("checkGramPNCensus", m);
	}
	
	/**
	 * 五克任务获取折线图合格人数
	 */
	public List<Map<String, Object>> getCheckGramsPPCensus(Map<String, Object> m) {
		return checkGramDao.getInfoListMapByMap("checkGramsPPCensus", m);
	}
	
	/**
	 * 获取每个责任区当天和昨天的平均克数
	 */
	public List<Map<String, Object>> getAverageGrams(int beforeDayForGramsAVG) {
		return checkGramDao.getInfoListMapByInt("getAverageGrams", beforeDayForGramsAVG) ;
	}
	
	/**
	 * 获取每个责任区近10天的五克次数 2016-09-02
	 */
	public List<Map<String, Object>> getCheckGramsNum(int days) {
		return checkGramDao.getInfoListMapByInt("getCheckGramsNum", days);
	}
	
    /**
     * 城市清洁度分析 近一周道路清洁度趋势图 日平均克数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getAverageGramsForApp(int beforeDayForGramsAVG){
        return checkGramDao.getInfoListMapByInt("getAverageGramsForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 城市清洁度分析 近一周道路清洁度趋势图 总克数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getTotalGramsForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getTotalGramsForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 城市清洁度分析 近一周道路检测合格率趋势图  合格次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getEveryPassNumForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getEveryPassNumForApp", beforeDayForGramsAVG) ;
    } 
    
    /**
     * 城市清洁度分析 近一周道路检测合格率趋势图  总次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getEveryTotalNumForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getEveryTotalNumForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 城市清洁度分析 近一周全市各责任区检测力度清洁度状况表 
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getWeekCleContrastsForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getWeekCleContrastsForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 工作效率 近一周人员考核有效力度趋势图  合格次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getFiveMinPassNumForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getFiveMinPassNumForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 工作效率 近一周道路检测合格率趋势图  总次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getFiveMinTotalNumForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getFiveMinTotalNumForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 工作效率 近7日全市各责任区人员响应力度横向对比报表 （领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getWeekWorkEffConForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getWeekWorkEffForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 综合考虑（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getComEvaluationForApp(int beforeDayForGramsAVG) {
        return checkGramDao.getInfoListMapByInt("getComEvaluationForApp", beforeDayForGramsAVG) ;
    }
    
    /**
     * 管理人员尽职监督 近一周五分钟道路测量地点（领导APP端）
     * @param employeeId 管理人员ID
     * @return
     */
    public List<CheckGram> getlatLngForLeadApp(int employeeId) {
        CheckGram bean = new CheckGram();
        bean.setEmployeeId(employeeId);
        return checkGramDao.getInfoListByBean("getCheGraLatLngForLeadApp", bean);
    }
    
    /**
     * 获取五克考勤信息记录条数。2016-11-14
     * @param map
     * @return
     */
    public int getCheckGramHistoryCount(CheckGram bean) {
        return checkGramDao.getInfoCount("getCheckGramHistoryCount", bean);
    }
    
    /**
     * 获取五克考勤信息记录。2016-11-14
     * @param map
     * @return
     */
    public List<Map<String, Object>> getCheckGramHistory(CheckGram bean){
        return checkGramDao.getInfoListMapByBean("getCheckGramHistory", bean);
    }  
}
