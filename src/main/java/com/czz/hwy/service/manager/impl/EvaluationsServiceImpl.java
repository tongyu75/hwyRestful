package com.czz.hwy.service.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.Evaluations;
import com.czz.hwy.dao.manager.EvaluationsDao;
import com.czz.hwy.service.manager.EvaluationsService;
/**
 * 考核类别业务层实现类
 * 
 * @author 以克论净环卫管理系统    李宇宁 20150724
 * @version V1.0
 */
@Service
public class EvaluationsServiceImpl implements EvaluationsService {
	// 考核类别dao层接口
	@Autowired
	private EvaluationsDao evaluationsDao;
	/*
	 * 通过考核名字获得该考核详细信息
	 */
	public Evaluations getEvaluationsByBean(String evalName)
	{
		Evaluations evaluations=new Evaluations();
		evaluations.setEvalName(evalName);
		Evaluations resultEvaluations=evaluationsDao.getInfoByBean(evaluations);
		return resultEvaluations;
	}
	public Evaluations getEvaluationsByBean(Evaluations evaluations) {
		// TODO Auto-generated method stub
		return evaluationsDao.getInfoByBean(evaluations);
	}
	
	public List<Map<String,Object>> getEvalJoinDutyAreaByEvalType(Integer evalType){
		return evaluationsDao.getInfoListMapByInt("getEvalJoinDutyAreaByEvalType", evalType);
	}
}
