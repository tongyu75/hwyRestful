package com.czz.hwy.service.manager;

import java.util.List;

import net.sf.json.JSONArray;

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.bean.manager.EvalTypeCheck;


/**
 * 获取考核类型
 * @author Administrator
 *
 */
public interface EvalTypeCheckService {
	
	public List<EvalTypeCheck> selectEvalTypeCheck();
	
	public void addEvalTypeCheck(String type,String evalName);
	
	public void updateEvalTypeCheck(String id, String type, String evalName);
	
	public void delEvalTypeCheck(String id);
	
	public List<DutyArea> getDutyArea();
	
	public void updateEvaluations(JSONArray array);

	
}
