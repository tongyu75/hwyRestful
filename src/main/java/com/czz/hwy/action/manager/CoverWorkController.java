package com.czz.hwy.action.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.CoverWork;
import com.czz.hwy.service.manager.CoverWorkService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 代班记录查询功能,用于pc端
 * @author 张咏雪
 * @Date 2016-11-16
 */
@Controller
@RequestMapping(value = "/coverWorkController")
public class CoverWorkController {

	@Autowired
	private CoverWorkService coverWorkService;//代班业务层
	
	 @Resource
	 private AccessOrigin accessOrigin;

	/**
	 * 根据查询条件，查询代班记录列表，分页，2016-11-16
	 */
	@RequestMapping(value = "/coverWork", method = RequestMethod.GET)
    @ResponseBody
	public String selectCoverWorkList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为代班对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			CoverWork coverWork = (CoverWork) JSONObject.toBean(json, CoverWork.class);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(json.getString("coverFromTime") == null || "".equals(json.getString("coverFromTime"))){
				coverWork.setCoverFromTime(null);
			}else{
				coverWork.setCoverFromTime(sdf.parse(json.getString("coverFromTime")));
			}
			if(json.getString("coverToTime") == null || "".equals(json.getString("coverToTime"))){
				coverWork.setCoverToTime(null);
			}else{
				coverWork.setCoverToTime(sdf.parse(json.getString("coverToTime")));
			}
			
			//3.查询代班信息记录总条数
			int total = coverWorkService.getCoverWorkCount(coverWork);//查询代班信息记录总条数，2016-11-16
			
			//4.查询任务信息记录集合，分页
			List<CoverWork> rows = coverWorkService.getCoverWorkList(coverWork);//查询代班信息记录集合，2016-11-17，分页
			
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
