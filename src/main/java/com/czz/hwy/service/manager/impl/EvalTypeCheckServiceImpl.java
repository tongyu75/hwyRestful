package com.czz.hwy.service.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.bean.manager.EvalTypeCheck;
import com.czz.hwy.bean.manager.Evaluations;
import com.czz.hwy.dao.area.DutyAreaDao;
import com.czz.hwy.dao.area.DutyPointDao;
import com.czz.hwy.dao.manager.EvalTypeCheckDao;
import com.czz.hwy.dao.manager.EvaluationsDao;
import com.czz.hwy.service.manager.EvalTypeCheckService;
/**
 * 获取考核类型
 * @author Administrator
 *
 */
@Service
@Transactional
public class EvalTypeCheckServiceImpl implements EvalTypeCheckService {
	
	@Autowired
	private EvalTypeCheckDao evalTypeCheckDao;
	
	@Autowired
	private EvaluationsDao evaluationsDao;
	
	@Autowired
	private DutyAreaDao dutyAreaDao;
	
	@Autowired
	private DutyPointDao dutyPointDao;
	
	/**
	 * 查询evalTypeCheck
	 */
	public List<EvalTypeCheck> selectEvalTypeCheck() {
		return evalTypeCheckDao.getAllInfo("selectEvalTypeCheck");
	}
	
	/**
	 * 添加evalTypeCheck
	 */
	public void addEvalTypeCheck(String type,String evalName){
		EvalTypeCheck evalTypeCheck = new EvalTypeCheck();
		evalTypeCheck.setCreateAt(new Date());
		evalTypeCheck.setEvalName(evalName);
		Integer evalValue = evalTypeCheckDao.getInfoCount("selectMaxEvalValueCheck");
		if(0 == evalValue){
			evalValue = 1;
		}else{
			evalValue++;
		}
		evalTypeCheck.setEvalValue(evalValue);
		evalTypeCheck.setType(Integer.parseInt(type));
		evalTypeCheckDao.insertInfo("insertEvalTypeCheck", evalTypeCheck);
	}
	/**
	 * 修改evalTypeCheck
	 * @param id
	 * @param type
	 * @param evalName
	 */
	public void updateEvalTypeCheck(String id, String type, String evalName){
		EvalTypeCheck evalTypeCheck = evalTypeCheckDao.getInfoById("selectEvalTypeCheckById", Integer.parseInt(id));
		EvalTypeCheck evalTypeCheck2 = new EvalTypeCheck();
		evalTypeCheck2.setUpdateAt(new Date());
		evalTypeCheck2.setType(Integer.parseInt(type));
		evalTypeCheck2.setEvalName(evalName);
		evalTypeCheck2.setId(Integer.parseInt(id));
		evalTypeCheckDao.updateInfoByBean("updateEvalTypeCheck", evalTypeCheck2);
		List<Map<String,Object>> evaluationsList = evaluationsDao.getInfoListMapByInt("selectEvaluationsByEvalType", evalTypeCheck.getEvalValue());
		for(Map<String,Object> evaluationsMap:evaluationsList){
			Evaluations evaluations = new Evaluations();
			evaluations.setId((Integer) evaluationsMap.get("id"));
			evaluations.setEvalName(evalName);
			evaluations.setUpdateAt(new Date());
			evaluationsDao.updateInfoByBean("updateEvaluations", evaluations);
		}
	}
	
	/**
	 * 删除evalTypeCheck
	 */
	public void delEvalTypeCheck(String id){
		EvalTypeCheck evalTypeCheck = evalTypeCheckDao.getInfoById("selectEvalTypeCheckById", Integer.parseInt(id));
		evalTypeCheckDao.deleteInfoByPk("deleteEvalTypeCheckById", Integer.parseInt(id));
		evaluationsDao.deleteInfoByPk("deleteEvaluationsByEvalType", evalTypeCheck.getEvalValue());
	}
	
	/**
	 * 获取责任区
	 */
	public List<DutyArea> getDutyArea(){
		return dutyAreaDao.getAllInfo("getDutyAreaByStatus");
	}
	
	/**
	 * 修改责任区阈值或罚款
	 * @param array
	 */
	public void updateEvaluations(JSONArray array){
		JSONObject jsonObject = JSONObject.fromObject(array.get(0));
		String type = (String) jsonObject.get("type");
		String evalType = (String) jsonObject.get("evalType");
		evaluationsDao.deleteInfoByPk("deleteEvaluationsByEvalType", Integer.parseInt(evalType));
		String evalName = (String) jsonObject.get("evalName");
		Evaluations evaluations = new Evaluations();
		evaluations.setEvalType(Integer.parseInt(evalType));
		evaluations.setEvalName(evalName);
		evaluations.setCreateAt(new Date());
		if("1".equals(type)){
			evaluations.setLimitUnit("克");
			for (int i = 0; i < array.size(); i++) {
	            JSONObject obj = JSONObject.fromObject(array.get(i));
	            String areaId = (String) obj.get("areaId");
	            List<Map<String,Object>> dutyPointList = dutyPointDao.getInfoListMapByInt("getDutyPointByStatus", Integer.parseInt(areaId));
	            String fine = (String) obj.get("fine");
            	String limitValue = (String) obj.get("limitValue");
	            for(Map<String,Object> map:dutyPointList){
					Integer id = (Integer) map.get("id");
					evaluations.setAreaId(Integer.parseInt(areaId));
					evaluations.setPointId(id);
					evaluations.setFine(fine);
					evaluations.setLimitValue(Double.parseDouble(limitValue));
					evaluationsDao.insertInfo("insertEvaluations", evaluations);
				}
			}
		}else{
			for (int i = 0; i < array.size(); i++) {
	            JSONObject obj = JSONObject.fromObject(array.get(i));
	            String fine = (String) obj.get("fine");
	            String areaId = (String) obj.get("areaId");
	            evaluations.setAreaId(Integer.parseInt(areaId));
	            evaluations.setFine(fine);
				evaluationsDao.insertInfo("insertEvaluations", evaluations);
			}
		}
	}
	
	
}
