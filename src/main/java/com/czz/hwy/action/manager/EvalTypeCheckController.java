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

import com.czz.hwy.bean.area.DutyArea;
import com.czz.hwy.bean.manager.EvalTypeCheck;
import com.czz.hwy.service.manager.EvalTypeCheckService;
import com.czz.hwy.service.manager.EvaluationsService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 考核参数设计Controller
 * 功能描述
 * @author 陈禹霖 2016-11-25
 */
@Controller
@RequestMapping(value = "/evalTypeCheckController")
public class EvalTypeCheckController {
	
	@Resource
	private EvalTypeCheckService evalTypeCheckService;
	
	@Resource
	private EvaluationsService evaluationsService;
	
    @Resource
    private AccessOrigin accessOrigin;
	
    /**
	 * 查询evalTypeCheck
	 * @return
	 */
	@RequestMapping(value = "/getEvalTypeCheck", method = RequestMethod.GET)
    @ResponseBody
    public String getEvalType(HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object> ();
        List<EvalTypeCheck> evalTypeCheckList = evalTypeCheckService.selectEvalTypeCheck();
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information","成功");
        map.put("rows", evalTypeCheckList);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 添加evalTypeCheck
	 * @return
	 */
	@RequestMapping(value = "/addEvalTypeCheck", method = RequestMethod.GET)
    @ResponseBody
    public String addEvalTypeCheck(String type, String evalName, HttpServletRequest request, 
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
        	evalTypeCheckService.addEvalTypeCheck(type,evalName);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information","成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 修改evalTypeCheck
	 * @return
	 */
	@RequestMapping(value = "/updateEvalTypeCheck", method = RequestMethod.GET)
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
        	evalTypeCheckService.updateEvalTypeCheck(id, type, evalName);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 删除evalTypeCheck
	 * @return
	 */
	@RequestMapping(value = "/delEvalTypeCheck", method = RequestMethod.GET)
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
        	evalTypeCheckService.delEvalTypeCheck(id);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "成功");
        }
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
    public String getEvaluations(String evalValue, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object> ();
        if (evalValue == null || "".equals(evalValue)) {
        	map.put("result", ConstantUtil.FAIL);
            map.put("information","失败");
            map.put("rows", "参数evalValue不能为空或NULL");
        }else{
        	List<DutyArea> dutyAreaList = evalTypeCheckService.getDutyArea();
        	List<Map<String,Object>>  evaluationsList = evaluationsService.getEvalJoinDutyAreaByEvalType(Integer.parseInt(evalValue));
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information","成功");
            map.put("dutyAreaList", dutyAreaList);
            map.put("evaluationsList", evaluationsList);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
	
	/**
	 * 修改evaluations
	 * 参数：责任区id
	 * 		type
	 * 		阈值
	 * 		罚金
	 * 		evalValue
	 * 		evalName
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
        	evalTypeCheckService.updateEvaluations(array);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "成功");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
	}
	
}
