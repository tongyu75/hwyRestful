package com.czz.hwy.action.mission.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 员工归属责任区管理功能
 * @author 张咏雪
 * @Date 2016-09-29
 */
@Controller
@RequestMapping(value = "/userAreaAppController")
public class UserAreaAppController {

	/*@Autowired
	private UserAreaService userAreaService;//员工归属责任区管理业务层
	
	@Autowired
	private WorkPlansService workPlansService;//新的排班计划功能业务层
	
	@Autowired
	private RelayService relayService;//替班人员业务层
	
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
	
	*//**
	 * 绑定一条员工归属责任区记录，2016-09-29
	 *//*
	@RequestMapping(value = "/userArea", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addUserArea(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			*//**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************//*
			String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
			
//			String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
			*//**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码*************************************//*
			
			Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
			
			// 设置允许跨域访问的路径
			CommonUtils.setAccessOrigin(request,response, accessOrigin);
			
			
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","绑定失败，员工归属责任区信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为员工归属责任区实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			
			UserArea userArea = (UserArea) JSONObject.toBean(json, UserArea.class);
			
			//4.判断该员工是否已经绑定了责任区，若是已绑定，则不允许绑定其他责任区。责任区Id和员工Id是一对一的关系
			UserArea isUserArea = userAreaService.getUserAreaByEmployeeId(userArea);//根据员工Id获取绑定关系记录。2016-09-29
			if(isUserArea != null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","该员工已绑定责任区，若是该员工更换责任区，必须先解除原有责任区关系！");
				return map;
			}	
			
			//5.新增一条员工归属责任区记录
			userArea.setCreateId(users.getEmployeeId());
			userArea.setCreateAt(new Date());
			int count = userAreaService.insertUserAreaByBean(userArea);//新增一条员工归属责任区记录,2016-09-29
			
			//6.根据新增结果，返回失败或成功的提示
			if(count > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", ConstantUtil.BING_SUCCESS_MSG);
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.BING_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	*//**
	 * 解除绑定一条责任区与员工关系记录，2016-09-29
	 *//*
	@RequestMapping(value = "/userArea/{id}", method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> deleteUserArea(@PathVariable(value="id") String id,
			HttpServletResponse response, HttpServletRequest request){
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		try{	
			//1.若是请求参数为空，则返回fail
			if(id == null || "".equals(id)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.UNBING_FAIL_MSG);
				return map;
			}
			
			//2.根据ID获取员工归属责任区关系信息
			UserArea userArea = userAreaService.getUserAreaById(id);//根据ID获取员工归属责任区关系信息，2016-09-29
			if(userArea == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","解除绑定失败，该绑定关系不存在！");
				return map;
			}
			
			//3.判断原来绑定的责任区下，该员工是否存在排班计划,若是存在排班计划，提醒用户先修改排班计划
			WorkPlans workPlans = new WorkPlans();
			workPlans.setAreaId(userArea.getAreaId());
			workPlans.setEmployeeId(userArea.getEmployeeId());
			List<WorkPlans> workPlansList = workPlansService.getWorkPlansListByBean(workPlans);//根据责任区Id和员工Id获取排班计划列表，2016-09-29
			if(workPlansList.size() > 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "解除绑定失败：该员工在该责任区有排班计划，请先删除排班计划，再解除绑定");
				return map;
			}
			
			//5.判断该员工是否已作为替班人员存在
			Relay relay = new Relay();
			relay.setEmployeeId(userArea.getEmployeeId());
			Relay isRelay = relayService.getRelayByBean(relay);//根据员工ID和责任区ID，获取替班人员信息。2016-09-29
			if(isRelay != null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information", "解除绑定失败：该员工是该责任区的替班人员，请先删除替班记录，再解除绑定");
				return map;
			}
			
			
			//4.解除绑定，进行物理删除
			int count = userAreaService.deleteUserAreaById(id);//对一条关系记录解除绑定，2016-09-29
			
			//5.根据删除结果，返回失败或成功的提示
			if(count > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", ConstantUtil.UNBING_SUCCESS_MSG);
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.UNBING_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	*//**
	 * 查询责任区与员工关系记录，2016-09-29，分页
	 *//*
	@RequestMapping(value = "/userArea", method = RequestMethod.GET)
    @ResponseBody
	public String selectUserAreaList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			
			// 2.对请求参数解码并转换为班次对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			UserArea userArae = (UserArea) JSONObject.toBean(json, UserArea.class);
			
			//3.查询员工归属责任区记录总条数
			int total = userAreaService.getUserAreaCount(userArae);//查询员工归属责任区记录总条数，2016-09-29
			
			//4.查询员工归属责任区记录集合，分页
			List<UserArea> rows = userAreaService.getUserAreaList(userArae);//查询员工归属责任区记录集合，2016-09-29，分页
			
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
	
	*//**
	 * 查询所有的责任区与员工关系记录，用于下拉框，2016-09-29,不分页
	 *//*
	@RequestMapping(value = "/userAreaForDic", method = RequestMethod.GET)
    @ResponseBody
	public String selectAllUserAreaiList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information",ConstantUtil.SELECT_FAIL_MSG);
			return map.toString();
		}
		
		// 2.对请求参数解码并转换为员工归属责任区对象
		acceptContent = CommonUtils.getDecodeParam(acceptContent);
		JSONObject json = JSONObject.fromObject(acceptContent);
		UserArea userArea = (UserArea) JSONObject.toBean(json, UserArea.class);
		
		//3.查询员工归属责任区记录总条数
		int total = userAreaService.getUserAreaCount(userArea);//查询员工归属责任区记录总条数，2016-09-29
		
		//4.查询员工归属责任区记录集合，不分页
		List<UserArea> rows = userAreaService.getAllUserAreaListByBean(userArea);//查询员工归属责任区记录集合，2016-09-29，不分页
		
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
*/
}
