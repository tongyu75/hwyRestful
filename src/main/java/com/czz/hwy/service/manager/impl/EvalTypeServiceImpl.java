package com.czz.hwy.service.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.bean.manager.EvalTypes;
import com.czz.hwy.bean.manager.Evaluations;
import com.czz.hwy.dao.area.DutyAreaDao;
import com.czz.hwy.dao.area.DutyPointDao;
import com.czz.hwy.dao.manager.EvalTypeDao;
import com.czz.hwy.dao.manager.EvaluationsDao;
import com.czz.hwy.service.manager.EvalTypeService;
/**
 * 获取考核类型
 * @author Administrator
 *
 */
@Service
public class EvalTypeServiceImpl implements EvalTypeService {
	
	@Autowired
	private EvalTypeDao evalDao;
	
	@Autowired
	private DutyAreaDao dutyAreaDao;
	
	@Autowired
	private DutyPointDao dutyPointDao;
	
	@Autowired
	private EvaluationsDao evaluationsDao;
	
	// 获取所有考核类型
	public List<EvalTypes> getEvalType() {
		return evalDao.getEvalTypeList();
	}
	
	public List<EvalTypes> getEvalType(String options) {
		return evalDao.getEvalTypeList(options);
	}
	
	public List<EvalTypes> getEvalType(int roleId) {
		return evalDao.getEvalTypeList(roleId);
	}
	
	public EvalTypes getEvalTypeInfo(EvalTypes evalType) {
		return evalDao.getEvalTypeInfo(evalType);
	}
	
	public List<EvalTypes> selectEvalType() {
		return evalDao.getAllInfo("selectEvalType");
	}
	
	public void addEvalType(String type,String evalName){
		EvalTypes evalType = new EvalTypes();
		evalType.setCreateAt(new Date());
		evalType.setEvalName(evalName);
		Integer evalValue = evalDao.getInfoCount("selectMaxEvalValue");
		if(0 == evalValue){
			evalValue = 1;
		}else{
			evalValue++;
		}
		evalType.setEvalValue(evalValue);
		evalType.setType(Integer.parseInt(type));
		if("1".equals(type)){
			evalType.setRole1(0);
			evalType.setRole2(0);
			evalType.setRole3(0);
			evalType.setRole4(0);
			evalType.setIsjcy(0);
			evalType.setIskhy(0);
			evalType.setIshwg(0);
		}else if("2".equals(type)){
			evalType.setRole1(0);
			evalType.setRole2(0);
			evalType.setRole3(0);
			evalType.setRole4(0);
			evalType.setIsjcy(0);
			evalType.setIskhy(0);
			evalType.setIshwg(0);
		}else if("3".equals(type)){
			evalType.setRole1(1);
			evalType.setRole2(0);
			evalType.setRole3(0);
			evalType.setRole4(0);
			evalType.setIsjcy(0);
			evalType.setIskhy(0);
			evalType.setIshwg(0);
		}else if("4".equals(type)){
			evalType.setRole1(0);
			evalType.setRole2(1);
			evalType.setRole3(1);
			evalType.setRole4(1);
			evalType.setIsjcy(0);
			evalType.setIskhy(0);
			evalType.setIskhy(0);
		}
		evalDao.insertInfo("insertEvalType", evalType);
		if("1".equals(type)){
			List<DutyArea> dutyAreaList = dutyAreaDao.getAllInfo("getDutyAreaByStatus");
			Evaluations evaluations = new Evaluations();
			evaluations.setCreateAt(new Date());
			evaluations.setEvalName(evalName);
			evaluations.setEvalType(evalValue);
			evaluations.setLimitValue(5.0);
			evaluations.setLimitUnit("克");
			for(DutyArea dutyArea:dutyAreaList){
				List<Map<String,Object>> dutyPointList = dutyPointDao.getInfoListMapByInt("getDutyPointByStatus", dutyArea.getId());
				for(Map map:dutyPointList){
					Integer id = (Integer) map.get("id");
					evaluations.setAreaId(dutyArea.getId());
					evaluations.setPointId(id);
					evaluationsDao.insertInfo("insertEvaluations", evaluations);
				}
			}
			
		}
		
	}
	
	public EvalTypes selectEvalTypeById(String id){
		return evalDao.getInfoById("selectEvalTypeById", Integer.parseInt(id));
	}
	
	public void updateEvalType(String id, String type, String evalName){
		EvalTypes evalTypes = evalDao.getInfoById("selectEvalTypeById", Integer.parseInt(id));
		EvalTypes evalType = new EvalTypes();
		evalType.setUpdateAt(new Date());
		evalType.setType(Integer.parseInt(type));
		evalType.setEvalName(evalName);
		evalType.setId(Integer.parseInt(id));
		evalDao.updateInfoByBean("updateEvalType", evalType);
		List<Map<String,Object>> evaluationsList = evaluationsDao.getInfoListMapByInt("selectEvaluationsByEvalType", evalTypes.getEvalValue());
		for(Map evaluationsMap:evaluationsList){
			Evaluations evaluations = new Evaluations();
			evaluations.setId((Integer) evaluationsMap.get("id"));
			evaluations.setEvalName(evalName);
			evaluations.setUpdateAt(new Date());
			evaluationsDao.updateInfoByBean("updateEvaluations", evaluations);
		}
	}
	
	public void delEvalType(String id){
		EvalTypes evalType = evalDao.getInfoById("selectEvalTypeById", Integer.parseInt(id));
		evalDao.deleteInfoByPk("deleteEvalTypeById", Integer.parseInt(id));
		evaluationsDao.deleteInfoByPk("deleteEvaluationsByEvalType", evalType.getEvalValue());
	}
	
	public void updateEvaluations(JSONArray array){
		for (int i = 0; i < array.size(); i++) {
            JSONObject obj = JSONObject.fromObject(array.get(i));
            String id = (String) obj.get("id");
            Evaluations evaluations = evaluationsDao.getInfoById("getEvaluationsById", Integer.parseInt(id));
            List<Map<String,Object>> evaluationsList = evaluationsDao.getInfoListMapByInt("selectEvaluationsByAreaId", evaluations.getAreaId());
            for(Map evalustionsMap:evaluationsList){
            	Evaluations evaluations2 = new Evaluations();
            	evaluations2.setId((Integer) evalustionsMap.get("id"));
    			evaluations2.setUpdateAt(new Date());
    			String limitValue = (String) obj.get("limitValue");
    			evaluations2.setLimitValue(Double.parseDouble(limitValue));
    			evaluationsDao.updateInfoByBean("updateEvaluations", evaluations2);
            }
		}
	}

    /**
     * 查询所有的考核项目用于下拉列表的显示
     */
    public List<EvalTypes> getAllEvalTypeForDic(Integer type) {
        return evalDao.getInfoListByIntegerId("getAllEvalTypeForDic", type);
    }
	
}
