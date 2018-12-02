package com.czz.hwy.service.manager;

import java.util.List;

import net.sf.json.JSONArray;

import com.czz.hwy.bean.manager.EvalTypes;

/**
 * 获取考核类型
 * @author Administrator
 *
 */
public interface EvalTypeService {

	public List<EvalTypes> getEvalType();

	/**
	 * @param options
	 * @return
	 */
	public List<EvalTypes> getEvalType(String options);
	public List<EvalTypes> getEvalType(int roleId);

	public EvalTypes getEvalTypeInfo(EvalTypes evalType);
	
	public List<EvalTypes> selectEvalType();
	
	public void addEvalType(String type,String evalName);
	
	public EvalTypes selectEvalTypeById(String id);
	
	public void updateEvalType(String id, String type, String evalName);
	
	public void delEvalType(String id);
	
	public void updateEvaluations(JSONArray array);

    /**
     * 查询所有的考核项目用于下拉列表的显示
     */
    public List<EvalTypes> getAllEvalTypeForDic(Integer type);
}
