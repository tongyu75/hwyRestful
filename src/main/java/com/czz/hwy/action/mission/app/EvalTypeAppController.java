package com.czz.hwy.action.mission.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.EvalTypeApp;
import com.czz.hwy.bean.mission.app.EvaluationsApp;
import com.czz.hwy.service.mission.app.EvalTypeAppService;
import com.czz.hwy.service.mission.app.EvalTypeCheckAppService;
import com.czz.hwy.service.mission.app.EvaluationsAppService;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 任务考核分类功能，用于app接口
 * 五克，五分钟，监察，举报
 * @author 张咏雪
 * @Date 2016-11-09
 */
@Controller
@RequestMapping(value = "/evalTypeAppController")
public class EvalTypeAppController {

	@Autowired
	private EvalTypeAppService evalTypeAppService;//考核分类业务层
	
	@Autowired
	private EvalTypeCheckAppService evalTypeCheckAppService;
	
	@Autowired
	private EvaluationsAppService evaluationsAppService;
	
	
	/**
	 * 获取五克，五分钟，举报，监督对应的考核分类集合，不分页，2016-11-09
	 * 1：五克 ， 2 五分钟， 3 举报， 4 监督
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/evalTypeApp/{type}", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectEvalTypeAppList(@PathVariable(value="type")int type, String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		try {
			int total = 0;
			List<EvalTypeApp> rows = new ArrayList<EvalTypeApp>();
			//1.若是请求参数为空，则返回fail
			if(type == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			// 2.对请求参数解码并解析出请求参数
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			String areaId = json.getString("areaId");//员工ID
			EvalTypeApp evalTypeApp = new EvalTypeApp();
			if(type == 3){
				evalTypeApp.setType(type);
				
				//3.获取举报对应的考核分类集合总条数
				total = evalTypeAppService.getEvalTypeCountByBeanApp(evalTypeApp);//根据type值，获取举报对应的考核分类集合总条数，2016-11-09
				
				//4.获取举报对应的考核分类集合
				List<EvalTypeApp> evalTypeAppList = evalTypeAppService.getEvalTypeListByBeanApp(evalTypeApp);//根据type值，获取举报对应的考核分类集合，不分页，2016-11-09，不分页
				for(EvalTypeApp etp:evalTypeAppList){
					evalTypeApp = new EvalTypeApp();
					evalTypeApp.setEvalValue(etp.getEvalValue());
					evalTypeApp.setEvalName(etp.getEvalName());
					rows.add(evalTypeApp);
				}
			}else{
				//根据type值获取考核指标
				List<Integer> evalTypeCheckList = evalTypeCheckAppService.selectEvalTypeCheck(type);
				EvaluationsApp evaluationsApp = new EvaluationsApp();
				evaluationsApp.setAreaId(Integer.parseInt(areaId));
				evaluationsApp.setIntegerList(evalTypeCheckList);
				if(evalTypeCheckList.size() != 0){
					//5.获取五克，五分钟，监督对应的考核分类集合总条数
					total = evaluationsAppService.getCountByListAndAreaId(evaluationsApp);//根据type值，获取五克，五分钟，监督对应的考核分类集合总条数，2016-11-29
					//6.获取五克，五分钟，监督对应的考核分类集合
					List<EvaluationsApp> evalutionsAppList = evaluationsAppService.getEvaluationsByListAndAreaId(evaluationsApp);//根据type值，获取五克，五分钟，监督对应的考核分类集合，不分页，2016-11-29，不分页
					for(EvaluationsApp _evaluationsApp:evalutionsAppList){
						evalTypeApp = new EvalTypeApp();
						evalTypeApp.setEvalValue(_evaluationsApp.getEvalType());
						evalTypeApp.setEvalName(_evaluationsApp.getEvalName());
						evalTypeApp.setFine(_evaluationsApp.getFine());
						rows.add(evalTypeApp);
					}
				}
			}
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", total);
			map.put("rows", rows);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
	} 



}
