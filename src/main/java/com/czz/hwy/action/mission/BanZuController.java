package com.czz.hwy.action.mission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.BanZu;
import com.czz.hwy.bean.mission.Banci;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.mission.BanZuService;
import com.czz.hwy.service.mission.BanciService;
import com.czz.hwy.service.mission.WorkPlansService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 班次组别维护功能
 * @author 张咏雪
 * @Date 2016-09-27
 */
@Controller
@RequestMapping(value = "/banZuController")
public class BanZuController {
	
	@Autowired
	private BanZuService banZuService;//班组业务层

	@Autowired
	private BanciService banciService;//班次业务层
	
	@Autowired
	private WorkPlansService workPlansService;//新的排班计划业务层

	@Resource
    private AccessOrigin accessOrigin;//跨域

	@SuppressWarnings("rawtypes")
    @Autowired
	private RedisTemplate redisTemplate;//缓存服务器
	
	@SuppressWarnings("unchecked")
	public String getUserKey(){
		Users users  = new Users();
		users.setEmployeeId(2015101501);
		 //将用户信息储存到缓存服务器中
        String userKey = CommonUtils.getEncryptByPublicKey("2015101501");
        redisTemplate.opsForValue().set(userKey,users );
        redisTemplate.expire(userKey, 30, TimeUnit.MINUTES);
        return userKey;
	}
	
	/**
	 * 新增一条班组记录，2016-09-27
	 */
	@RequestMapping(value = "/banZu", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addBanZu(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
//		String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
		
		String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
		
		Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","新增失败，班组信息不能为空！");
			return map;
		}
		
		//2.若请求不为空，则将请求参数转换为班组实体类
		JSONObject json = JSONObject.fromObject(acceptContent);
		BanZu banZu = (BanZu) JSONObject.toBean(json, BanZu.class);
		
		//3.判断班组名称是否已经存在,2016-09-27
		BanZu isBanZu = banZuService.getBanZuByBean(banZu);
		if(isBanZu != null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","该班组名称已经存在，不能重复添加！");
			return map;
		}
		
		//4.新增一条班组记录
		banZu.setCreateUserId(users.getEmployeeId());
		banZu.setCreateAt(new Date());
		int count = banZuService.addBanZuByBean(banZu);
		
		//5.根据新增结果，返回失败或成功的提示
		if(count > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
		}else{
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.INSERT_FAIL_MSG);
		}
		return map;
	} 

	/**
	 * 更新一条班组记录，2016-09-27
	 */
	@RequestMapping(value = "/banZu", method = RequestMethod.PUT)
    @ResponseBody
	public Map<String,Object> updateBanZu(String banzuName,String banzuId,
			HttpServletResponse response, HttpServletRequest request){
		
//		String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
		
		String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
		
		Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(banzuName == null || "".equals(banzuName) || banzuId == null || "".equals(banzuId)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","修改失败，班组信息不能为空！");
			return map;
		}
		
		//2.将接收到的参数封装进实体类中
		BanZu banZu = new BanZu();
		banZu.setBanzuName(banzuName);
		
		//3.判断班组名称是否已经存在,2016-09-27
		BanZu isBanZu = banZuService.getBanZuByBean(banZu);
		if(isBanZu != null && isBanZu.getBanzuId() != Integer.parseInt(banzuId) ){//若是班组存在，且班组ID与接收的班组ID值不一致，则认为班组重复，返回fail
			map.put("result", ConstantUtil.FAIL);
			map.put("information","该班组名称已经存在！");
			return map;
		}
		
		//4.更新一条班组记录
		banZu.setBanzuId(Integer.parseInt(banzuId));
		banZu.setUpdateUserId(users.getEmployeeId());
		banZu.setUpdateAt(new Date());
		int count = banZuService.updateBanZuByBean(banZu);//更新一条班组记录，2016-09-27
		
		//5.根据更新结果，返回失败或成功的提示
		if(count > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
		}else{
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
		}
		return map;
	} 

	/**
	 * 删除一条班组记录，2016-09-27
	 */
	@RequestMapping(value = "/banZu/{banzuId}", method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> deleteBanZu(@PathVariable(value="banzuId") String banzuId ,HttpServletResponse response, HttpServletRequest request){
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(banzuId == null || "".equals(banzuId)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information",ConstantUtil.DELETE_FAIL_MSG);
			return map;
		}
		
		//2.判断该班组ID是否被班次表引用过，若是被引用过，则提示不能删除
		List<Banci> banciList = banciService.selectBanciListByBanZuId(banzuId);//根据班组ID，查询是否有班次记录正在使用该班组ID，2016-09-27
		if(banciList.size() > 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","该班组信息正在被班次信息使用，需先删除对应班次记录，才能删除该班组记录，否则不允许删除！");
			return map;
		}
		
		//3.判断该班组ID是否被排班计划引用过，若是被引用过，则提示不能删除
		List<WorkPlans> workPlansList = workPlansService.selectWorkPlansListByBanZuId(banzuId);//根据班组ID，查询出排班计划集合，2016-10-10
		if(workPlansList.size() > 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","该班组信息正在被排班计划使用，需先删除对应排班计划记录，才能删除该班组记录，否则不允许删除！");
			return map;
		}
		
		//3.删除一条班组记录
		int count = banZuService.deleteBanZuById(banzuId);//删除一条班组记录，2016-09-27
		
		//4.根据删除结果，返回失败或成功的提示
		if(count > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
		}else{
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.DELETE_FAIL_MSG);
		}
		return map;
	} 
	
	/**
	 * 查询班组记录，2016-09-27
	 */
	@RequestMapping(value = "/banZu", method = RequestMethod.GET)
    @ResponseBody
	public String selectBanZuList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information",ConstantUtil.SELECT_FAIL_MSG);
			return map.toString();
		}
		
		// 2.对请求参数解码并转换为班组对象
		acceptContent = CommonUtils.getDecodeParam(acceptContent);
		JSONObject json = JSONObject.fromObject(acceptContent);
		BanZu banZu = (BanZu) JSONObject.toBean(json, BanZu.class);
		
		//3.查询班组记录总条数
		int total = banZuService.selectBanZuCountByBean(banZu);//查询班组记录总条数，2016-09-27
		
		//4.查询班组记录集合
		List<BanZu> rows = banZuService.selectBanZuListByBean(banZu);//查询班组记录集合，2016-09-27，分页
		
		//5.根据查询结果，返回相应数据
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
	
	/**
	 * 查询所有的班组记录(所有被班次绑定和没有被班次绑定的班组记录)，用于班组下拉框，2016-09-28,
	 */
	@RequestMapping(value = "/banZuForDic", method = RequestMethod.GET)
    @ResponseBody
	public String selectAllBanZuList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information",ConstantUtil.SELECT_FAIL_MSG);
			return map.toString();
		}
		
		// 2.对请求参数解码并转换为班组对象
		acceptContent = CommonUtils.getDecodeParam(acceptContent);
		JSONObject json = JSONObject.fromObject(acceptContent);
		BanZu banZu = (BanZu) JSONObject.toBean(json, BanZu.class);
		
		//3.查询班组记录总条数
		int total = banZuService.selectBanZuCountByBean(banZu);//查询班组记录(所有被班次绑定和没有被班次绑定的班组记录)总条数，2016-09-27
		
		//4.查询所有班组记录集合
		List<BanZu> rows = banZuService.selectAllBanZuListByBean(banZu);//查询所有班组记录集合(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-28，不分页
		
		//5.根据查询结果，返回相应数据
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
	
	/**
	 * 查询所有的班组记录(所有被班次绑定的班组信息列表)，用于班组下拉框，2016-09-28,
	 */
	@RequestMapping(value = "/banZuForBanci", method = RequestMethod.GET)
    @ResponseBody
	public String selectBanZuForBanciList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information",ConstantUtil.SELECT_FAIL_MSG);
			return map.toString();
		}
		
		// 2.对请求参数解码并转换为班组对象
		acceptContent = CommonUtils.getDecodeParam(acceptContent);
		JSONObject json = JSONObject.fromObject(acceptContent);
		BanZu banZu = (BanZu) JSONObject.toBean(json, BanZu.class);
		
		//3.查询班组记录总条数
		int total = banZuService.selectBanZuForBanciCountByBean(banZu);//查询所有被班次绑定的班组记录总条数，2016-10-17
		
		//4.查询所有班组记录集合
		List<BanZu> rows = banZuService.selectBanZuForBanciListByBean(banZu);//查询所有被班次绑定的班组记录集合，2016-10-17，不分页
		
		//5.根据查询结果，返回相应数据
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
