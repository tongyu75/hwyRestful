package com.czz.hwy.service.manager;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.manager.Evaluations;

public interface EvaluationsService {
	public Evaluations getEvaluationsByBean(String evalName);
	public Evaluations getEvaluationsByBean(Evaluations evaluations);
	

	
	public List<Map<String,Object>> getEvalJoinDutyAreaByEvalType(Integer evalType);
	
}
