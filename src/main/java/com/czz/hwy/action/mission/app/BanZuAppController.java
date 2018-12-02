package com.czz.hwy.action.mission.app;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.BanZu;
import com.czz.hwy.service.mission.app.BanZuAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 班次组别维护功能
 * @author 张咏雪
 * @Date 2016-10-18
 */
@Controller
@RequestMapping(value = "/banZuAppController")
public class BanZuAppController {
	
	@Autowired
	private BanZuAppService banZuAppService;//班组业务层

	/**
	 * 查询所有的班组记录(所有被班次绑定的班组信息列表)，用于班组下拉框，2016-10-18,
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/banZuForBanci", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectBanZuForBanciList(HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.查询班组记录总条数
		int total = banZuAppService.selectBanZuForBanciCountByBean();//查询所有被班次绑定的班组记录总条数，2016-10-18
		
		//2.查询所有班组记录集合
		List<BanZu> rows = banZuAppService.selectBanZuForBanciListByBean();//查询所有被班次绑定的班组记录集合，2016-10-18，不分页
		
		//3.根据查询结果，返回相应数据
		map.put("result", ConstantUtil.SUCCESS);
		map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
		map.put("total", total);
		map.put("rows", rows);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
	} 
}
