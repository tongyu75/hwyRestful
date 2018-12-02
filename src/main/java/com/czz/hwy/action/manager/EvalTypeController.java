package com.czz.hwy.action.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.EvalTypes;
import com.czz.hwy.service.manager.EvalTypeService;
import com.czz.hwy.service.manager.EvaluationsService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 考核参数设计Controller
 * 功能描述
 * @author 陈禹霖 2016-11-07
 */
@Controller
@RequestMapping(value = "/evalTypeController")
public class EvalTypeController {
	
	@Resource
	private EvalTypeService evalTypeService;
	
	@Resource
	private EvaluationsService evaluationsService;
	
    @Resource
    private AccessOrigin accessOrigin;
	
	/**
	 * 查询evalType
	 * @return
	 */
	@RequestMapping(value = "/getEvalType", method = RequestMethod.GET)
    @ResponseBody
    public String getEvalType(HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object> ();
        List<EvalTypes> evalTypesList = evalTypeService.selectEvalType();
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information","成功");
        map.put("rows", evalTypesList);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 查询evaluations
	 * @return
	 */
	@RequestMapping(value = "/getEvaluations", method = RequestMethod.GET)
    @ResponseBody
    public String getEvaluations(String evalType, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object> ();
        if (evalType == null || "".equals(evalType)) {
        	map.put("result", ConstantUtil.FAIL);
            map.put("information","失败");
            map.put("rows", "参数evalType不能为空或NULL");
        }else{
        	List<Map<String,Object>>  evaluationsList = evaluationsService.getEvalJoinDutyAreaByEvalType(Integer.parseInt(evalType));
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information","成功");
            map.put("rows", evaluationsList);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 添加evalType
	 * @return
	 */
	@RequestMapping(value = "/addEvalType", method = RequestMethod.GET)
    @ResponseBody
    public String addEvalType(String type,String evalName, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        evalName = CommonUtils.getDecodeParam(evalName);
        Map<String,Object> map = new HashMap<String,Object> ();
        if (type == null || "".equals(type)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数type不能为空或NULL");
        }else if (evalName == null || "".equals(evalName)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数evalName不能为空或NULL");
        }else{
        	evalTypeService.addEvalType(type,evalName);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information","成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 根据id查询evalType
	 * @return
	 */
	@RequestMapping(value = "/selectEvalTypeById", method = RequestMethod.GET)
    @ResponseBody
    public String selectEvalTypeById(String id, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object> ();
        if (id == null || "".equals(id)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数id不能为空或NULL");
        }else{
        	 EvalTypes evalType = evalTypeService.selectEvalTypeById(id);
             map.put("result", ConstantUtil.SUCCESS);
             map.put("information", "成功");
             map.put("rows",evalType);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 修改evalType
	 * @return
	 */
	@RequestMapping(value = "/updateEvalType", method = RequestMethod.GET)
    @ResponseBody
    public String updateEvalType(String id, String type, String evalName, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        evalName = CommonUtils.getDecodeParam(evalName);
        Map<String,Object> map = new HashMap<String,Object> ();
        if (id == null || "".equals(id)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数id不能为空或NULL");
        }else if (type == null || "".equals(type)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数type不能为空或NULL");
        }else if (evalName == null || "".equals(evalName)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数evalName不能为空或NULL");
        }else{
        	 evalTypeService.updateEvalType(id, type, evalName);
             map.put("result", ConstantUtil.SUCCESS);
             map.put("information", "成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 删除evalType
	 * @return
	 */
	@RequestMapping(value = "/delEvalType", method = RequestMethod.GET)
    @ResponseBody
    public String delEvalType(String id, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object> ();
        if (id == null || "".equals(id)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数id不能为空或NULL");
        }else{
        	evalTypeService.delEvalType(id);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 修改evaluations
	 */
	@RequestMapping(value = "/updateEvaluations", method = RequestMethod.POST)
    @ResponseBody
	public String updateEvaluations(String evaluations, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
		Map<String,Object> map = new HashMap<String,Object>();
        if (evaluations == null || "".equals(evaluations)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "失败");
            map.put("rows", "参数evaluations不能为空或NULL");
        }else{
        	JSONArray array = JSONArray.fromObject(evaluations);
            evalTypeService.updateEvaluations(array);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
	}
}
