package com.czz.hwy.service.mission;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.CheckGram;
import com.czz.hwy.bean.mission.KindInfo;

public interface CheckGramService {
	public List<CheckGram> getCheckGramByBean(int employeeId);
	public CheckGram getCheckGramById(int id);
	public List<CheckGram> getCheckGramByBean(int employeeId,String date);
	public Map<String,Object> getCheckGramData();
	public List<KindInfo> getKindInfo(String employeeId, String date);
	/**五克任务合格与不合格人数和次数的统计
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getCheckGramCensus(Map<String, Object> map);
	/**
	 * @param map
	 * @return
	 */
	public int getCheckGramCensusCount(Map<String, Object> map);
	/**
	 * 五克任务获取折线图不合格次数
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> getCheckGramsTsCensus(Map<String, Object> m);
	
	/**
	 * 五克任务获取折线图不合格人数
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> getCheckGramsPsCensus(Map<String, Object> m);
	
	/**
	 * 五克任务获取折线图合格次数
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> getCheckGramsPNCensus(Map<String, Object> m);
	
	/**
	 * 五克任务获取折线图合格人数
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> getCheckGramsPPCensus(Map<String, Object> m);
	
	/**
	 * 获取每个责任区当天和昨天的平均克数
	 * @param beforeDayForGramsAVG 
	 */
	public List<Map<String, Object>> getAverageGrams(int beforeDayForGramsAVG);
	
	/**
	 * 获取每个责任区近10天的五克次数 2016-09-02
	 * @return
	 */
	public List<Map<String, Object>> getCheckGramsNum(int days);
	
    /**
     * 城市清洁度分析 近一周道路清洁度趋势图（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getAverageGramsForApp(int beforeDayForGramsAVG);	
    
    /**
     * 城市清洁度分析 近一周道路清洁度趋势图 总克数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getTotalGramsForApp(int beforeDayForGramsAVG);
    
    /**
     * 城市清洁度分析 近一周道路检测合格率趋势图  合格次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getEveryPassNumForApp(int beforeDayForGramsAVG);   
    
    /**
     * 城市清洁度分析 近一周道路检测合格率趋势图  总次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getEveryTotalNumForApp(int beforeDayForGramsAVG);       
    
    /**
     * 城市清洁度分析 近一周全市各责任区检测力度清洁度状况表 （领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getWeekCleContrastsForApp(int beforeDayForGramsAVG);           
    
    /**
     * 工作效率 近一周人员考核有效力度趋势图  合格次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getFiveMinPassNumForApp(int beforeDayForGramsAVG);           
    
    /**
     * 工作效率 近一周道路检测合格率趋势图  总次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getFiveMinTotalNumForApp(int beforeDayForGramsAVG);               
    
    /**
     * 工作效率 近一周道路检测合格率趋势图  总次数（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getWeekWorkEffConForApp(int beforeDayForGramsAVG);               
    
    /**
     * 综合考虑（领导APP端）
     * @param beforeDayForGramsAVG 
     */
    public List<Map<String, Object>> getComEvaluationForApp(int beforeDayForGramsAVG);           
    
    /**
     * 管理人员尽职监督 近一周五克道路测量地点（领导APP端）
     * @param employeeId 管理人员ID
     * @return
     */
    public List<CheckGram> getlatLngForLeadApp(int employeeId);
    
    /**
     * 获取五克考勤信息记录条数。2016-11-14
     * @param reports
     * @return
     */
    public int getCheckGramHistoryCount(CheckGram bean);
    
    /**
     * 获取五克考勤信息记录。2016-11-14
     * @param reports
     * @return
     */
    public List<Map<String, Object>> getCheckGramHistory(CheckGram bean);
}
