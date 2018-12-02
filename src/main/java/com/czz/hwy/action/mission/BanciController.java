package com.czz.hwy.action.mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.czz.hwy.bean.mission.Banci;
import com.czz.hwy.bean.mission.DutyForBanci;
import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.mission.BanciService;
import com.czz.hwy.service.mission.DutyForBanciService;
import com.czz.hwy.service.mission.DutyPlansService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 班次维护功能
 * @author 张咏雪
 * @Date 2016-09-28
 */
@Controller
@RequestMapping(value = "/banciController")
public class BanciController {

	@Autowired
	private BanciService banciService;//班次业务层
	
	@Autowired
	private DutyForBanciService dutyForBanciService;//排班业务层
	
	@Autowired
	private DutyPlansService dutyPlansService;//排班计划业务层
	
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
	 * 新增一条班次记录，2016-09-28
	 */
	@RequestMapping(value = "/banCi", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addBanCi(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//			String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
			
			String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
			/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码*************************************/
			
			Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
			
			// 设置允许跨域访问的路径
			CommonUtils.setAccessOrigin(request,response, accessOrigin);
			
			
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","新增失败，班次信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为班次实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date dutyOntime = sdf.parse(json.getString("dutyOntime") + ":00");;
			Date dutyOfftime  = sdf.parse(json.getString("dutyOfftime") + ":00");;
			
			Banci banci = (Banci) JSONObject.toBean(json, Banci.class);
			banci.setDutyOntime(dutyOntime);
			banci.setDutyOfftime(dutyOfftime);
			
			//3.计划开始时间不能大于等于计划结束时间
			if(banci.getDutyOntime().compareTo(banci.getDutyOfftime()) >= 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","上班时间不能晚于下班时间！");
				return map;
			}
			
			//4.同一班组班次时间点有重叠,2016-09-28
			List<Banci> banciList = banciService.getBanciListByBanZuId(banci.getBanzuId() + "");//根据班组Id查询班次集合,2016-09-28
			boolean flag = false;//表示要新增的班次与已新增的班次是否有时间上的重叠
			for(Banci b : banciList){
				if((banci.getDutyOntime().compareTo(b.getDutyOntime()) >= 0 && banci.getDutyOntime().compareTo(b.getDutyOfftime()) <= 0) || 
						(banci.getDutyOfftime().compareTo(b.getDutyOntime()) >= 0 && banci.getDutyOfftime().compareTo(b.getDutyOfftime()) <= 0)){
					flag = true;
					break;
				}
			}
			
			if(flag){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","同一班组的班次时间点有重叠！");
				return map;
			}
			
			/**********************是为了兼容旧系统，才在新的系统中去维护班次序号，一旦全部使用了新系统，有关班次序号的字段和操作即可删除掉**************************************/
			//TODO 是为了兼容旧系统，才在新的系统中去维护班次序号，一旦全部使用了新系统，有关班次序号的字段和操作即可删除掉
			//5.判断班次序号是否已经存在,2016-09-28
			banciList = banciService.getBanciListByNumber(banci.getDutyNumber() + "");//根据班次序号查询班次集合，2016-09-28
			if(banciList.size() > 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","该班次序号已经存在，不能重复添加！");
				return map;
			}
			/**********************是为了兼容旧系统，才在新的系统中去维护班次序号，一旦全部使用了新系统，有关班次序号的字段和操作即可删除掉**************************************/
			
			//6.新增一条班次记录
			banci.setCreateUserId(users.getEmployeeId());
			banci.setCreateAt(new Date());
			int count = banciService.insertBanci(banci);//新增一条班次记录,2016-09-28
			
			//6.根据新增结果，返回失败或成功的提示
			if(count > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.INSERT_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/**
	 * 更新一条班次记录，2016-09-28,
	 * 参数分析：type = 1 表示修改班次信息前，先判断是否存在引用该班次的排班计划，若是存在引用了该班次的排班计划，则提示用户是否继续操作。
	 * type = 2 表示用户同意修改班次信息的同时，修改引用该班次计划的排班计划。
	 * type = 3 表示只修改班次信息，不对排班计划进行修改。
	 */
	@RequestMapping(value = "/banCi", method = RequestMethod.PUT)
    @ResponseBody
	public Map<String,Object> updateBanCi(String acceptContent,String type,
			HttpServletResponse response, HttpServletRequest request){
		
		/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//		String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
		
		String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
		/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
		
		Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		try {
		
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","更新失败，班次信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为班次实体类
			JSONObject json = JSONObject.fromObject(acceptContent);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date dutyOntime = sdf.parse(json.getString("dutyOntime") + ":00");;
			Date dutyOfftime  = sdf.parse(json.getString("dutyOfftime") + ":00");;
			
			Banci banci = (Banci) JSONObject.toBean(json, Banci.class);
			banci.setDutyOntime(dutyOntime);
			banci.setDutyOfftime(dutyOfftime);
			
			//3.计划开始时间不能大于等于计划结束时间
			if(banci.getDutyOntime().compareTo(banci.getDutyOfftime()) >= 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","上班时间不能晚于下班时间！");
				return map;
			}
			
			//4.同一班组班次时间点有重叠,2016-09-28
			List<Banci> banciList = banciService.getBanciListByBanZuId(banci.getBanzuId() + "");//根据班组Id查询班次集合,2016-09-28
			boolean flag = false;//表示要更新的班次与已新增的班次是否有时间上的重叠
			for(Banci b : banciList){
				if(banci.getId() != b.getId()){//确保不是自己比较自己
					if((banci.getDutyOntime().compareTo(b.getDutyOntime()) >=0 && banci.getDutyOntime().compareTo(b.getDutyOfftime()) <= 0) || 
							(banci.getDutyOfftime().compareTo(b.getDutyOntime()) >=0 && banci.getDutyOfftime().compareTo(b.getDutyOfftime()) <= 0)){
						flag = true;
						break;
					}
				}
			}
			
			if(flag){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","同一班组的班次时间点有重叠！");
				return map;
			}
			
			/**********************是为了兼容旧系统，才在新的系统中去维护班次序号，一旦全部使用了新系统，有关班次序号的字段和操作即可删除掉**************************************/
			// TODO 是为了兼容旧系统，才在新的系统中去维护班次序号，一旦全部使用了新系统，有关班次序号的字段和操作即可删除掉
			//5.判断班次序号是否已经存在,2016-09-28
			banciList = banciService.getBanciListByNumber(banci.getDutyNumber() + "");//根据班次序号查询班次集合，2016-09-28
			flag = false;//表示要更新的班次序号与已存在的班次信息中的班次序号重复
			for(Banci b : banciList){
				if(banci.getId() != b.getId()){//确保不是自己比较自己
					flag = true;
				}
			}
			if(flag){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","该班次序号已经存在！");
				return map;
			}
			/**********************是为了兼容旧系统，才在新的系统中去维护班次序号，一旦全部使用了新系统，有关班次序号的字段和操作即可删除掉**************************************/
	
			/**********************是为了兼容旧系统，才在新的系统中去维护排班计划表，一旦使用了新的排班计划，有关就排班计划表的操作操作即可删除掉**************************************/
			// TODO　是为了兼容旧系统，才在新的系统中去维护排班计划表，一旦使用了新的排班计划，有关就排班计划表的操作操作即可删除掉
			//6.判断是否存在引用该班次的排班计划,并做相应处理
			List<DutyForBanci> dutyForBanciList = dutyForBanciService.getDutyForBanciListByNumber(banci.getDutyNumber() + "");//根据班次获取排班计划信息，2016-09-28
			if(dutyForBanciList.size() > 0 && "1".equals(type)){//若是存在引用该班次的排班计划，且需要用户判断是否修改
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "该班次已经被排班计划引用过,若是修改班次信息，排班计划也将被修改，<br/>是否同意继续修改？");
				map.put("type", type);
				return map;
			}else if(dutyForBanciList.size() > 0 && "2".equals(type)){
				//修改排班计划
				List<DutyPlans> list = new ArrayList<DutyPlans>();
				for(DutyForBanci dutyForBanci :  dutyForBanciList){
					DutyPlans dutyPlans = new DutyPlans();
					dutyPlans.setSeq(dutyForBanci.getSeq());
					dutyPlans.setDutyOnTime(banci.getDutyOntime());
					dutyPlans.setDutyOffTime(banci.getDutyOfftime());
					dutyPlans.setUpdateId(users.getEmployeeId());
					dutyPlans.setUpdateAt(new Date());
					list.add(dutyPlans);
				}
				
				dutyPlansService.beatchUpdateForDutyPlans(list);//批量更新排班计划，2016-09-28
			}
			/**********************是为了兼容旧系统，才在新的系统中去维护排班计划表，一旦使用了新的排班计划，有关就排班计划表的操作操作即可删除掉**************************************/
			
			//7.更新一条班次记录
			banci.setUpdateUserId(users.getEmployeeId());
			banci.setUpdateAt(new Date());
			int count = banciService.updateBanci(banci);//更新一条班次记录，2016-09-28
			
			//8.根据更新结果，返回失败或成功的提示
			if(count > 0){
				if("2".equals(type)){
					map.put("information", "修改成功：同时修改了班次信息以及对应的排班计划信息！");
				}else if("3".equals(type)){
					map.put("information", "修改成功：只修改了班次信息，没有修改引用该班次的排班计划！");
				}else if("1".equals(type)){
					map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
				}
				map.put("result", ConstantUtil.SUCCESS);
				map.put("type", type);
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 

	/**
	 * 删除一条班次记录，2016-09-28
	 * 参数分析：type = 1 表示删除班次信息前，先判断是否存在引用该班次的排班计划，若是存在引用了该班次的排班计划，则提示用户是否继续操作。
	 * type = 2 表示用户同意删除班次信息的同时，删除引用该班次计划的排班计划。
	 * type = 3 表示只删除班次信息，不对排班计划进行删除。
	 */
	@RequestMapping(value = "/banCi/{banciId}/{type}", method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> deleteBanCi(@PathVariable(value="banciId") String banciId, @PathVariable(value="type") String type,
			HttpServletResponse response, HttpServletRequest request){
		
		/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
//		String userKey = getUserKey();//模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码
		
//		String userKey = CommonUtils.getCookieValue(request, "userToken");//从cookies中获取保存用户信息的key
		/**********************模拟登陆，将用户信息存到缓存服务器中,在页面中测试时，将注释掉，且释放下面的代码**************************************/
		
//		Users users = (Users) redisTemplate.opsForValue().get(userKey);//获取当前登录用户信息
		
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		try{	
			//1.若是请求参数为空，则返回fail
			if(banciId == null || "".equals(banciId) || type == null || "".equals(type)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.DELETE_FAIL_MSG);
				return map;
			}
			
			//2.根据班次ID获取班次信息
			Banci banci = banciService.getBanciById(banciId);//根据班次Id获取班次信息，2016-09-28
			if(banci == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","删除失败，该班次不存在！");
				return map;
			}
			
			/**********************是为了兼容旧系统，才在新的系统中去维护排班计划表，一旦使用了新的排班计划，有关就排班计划表的操作操作即可删除掉**************************************/
			//TODO 是为了兼容旧系统，才在新的系统中去维护排班计划表，一旦使用了新的排班计划，有关就排班计划表的操作操作即可删除掉
			//3.判断是否存在引用该班次的排班计划,并做相应处理
			List<DutyForBanci> dutyForBanciList = dutyForBanciService.getDutyForBanciListByNumber(banci.getDutyNumber() + "");//根据班次获取排班计划信息，2016-09-28
			if(dutyForBanciList.size() > 0 && "1".equals(type) ){//若是存在引用该班次的排班计划，且需要用户判断是否删除
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", "该班次已经被排班计划引用过,若是删除班次信息，排班计划也将被删除，<br/>是否同意继续删除？");
				map.put("type", type);
				return map;
			}
			
			/**逻辑删除,注释掉，改为物理删除，2016-10-28*/
		    /*else if(dutyForBanciList.size() > 0 && "2".equals(type)){//存在引用该班次的排班计划，删除排班计划
				//修改排班计划,暂时不是物理删除，只是将状态置为2
				List<DutyPlans> list = new ArrayList<DutyPlans>();
				for(DutyForBanci dutyForBanci :  dutyForBanciList){
					dutyForBanci.setStatus(2);
					dutyForBanci.setUpdateAt(new Date());
					dutyForBanci.setUpdateId(users.getEmployeeId());
					DutyPlans dutyPlans = new DutyPlans();
					dutyPlans.setSeq(dutyForBanci.getSeq());
					dutyPlans.setStatus(2);
					dutyPlans.setUpdateId(users.getEmployeeId());
					dutyPlans.setUpdateAt(new Date());
					list.add(dutyPlans);
				}
				
				//4.删除排班计划
				dutyPlansService.beatchDeleteForDutyPlans(list);//批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28
				dutyForBanciService.beatchDeleteForDutyForBanci(dutyForBanciList);//批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28
			}*/
			/**物理删除,注释掉，改为逻辑删除，2016-10-28*/
			else if(dutyForBanciList.size() > 0 && "2".equals(type)){//存在引用该班次的排班计划，删除排班计划
				//修改排班计划,暂时不是物理删除，只是将状态置为2
				List<DutyPlans> list = new ArrayList<DutyPlans>();
				for(DutyForBanci dutyForBanci :  dutyForBanciList){
					DutyPlans dutyPlans = new DutyPlans();
					dutyPlans.setSeq(dutyForBanci.getSeq());
					list.add(dutyPlans);
				}
				
				//4.删除排班计划
				dutyPlansService.beatchDeleteForDutyPlans(list);//批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
				dutyForBanciService.beatchDeleteForDutyForBanci(dutyForBanciList);//批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28,又改成：即批量物理删除排版计划，2016-10-28
			}
			
			/**********************是为了兼容旧系统，才在新的系统中去维护排班计划表，一旦使用了新的排班计划，有关就排班计划表的操作操作即可删除掉**************************************/
			
			/**********************删除一条班次记录，目前状态设置为2，换成新系统后，将进行物理删除,暂时将dutynumber的唯一性索引去掉，换成新系统后，再加上**************************************/
			// TODO 删除一条班次记录，目前状态设置为2，换成新系统后，将进行物理删除
			//5.删除一条班次记录，目前状态设置为2，换成新系统后，将进行物理删除
			/*
			//逻辑删除注释掉，改为物理删除，2016-10-28
			banci.setUpdateUserId(users.getEmployeeId());
			banci.setUpdateAt(new Date());
			banci.setStatus(2);
			int count = banciService.updateBanciForDelete(banci);//逻辑删除一条班次记录，2016-09-28
			*/		
			//物理删除，2016-10-28
			int count = banciService.deleteBanciByBean(banci);//物理删除一条班次记录，2016-10-28
			
			/**********************删除一条班次记录，目前状态设置为2，换成新系统后，将进行物理删除**************************************/
			
			//6.根据删除结果，返回失败或成功的提示
			if(count > 0){
				map.put("result", ConstantUtil.SUCCESS);
				map.put("type", type);
				if("2".equals(type)){
					map.put("information", "删除成功：同时删除了班次信息以及对应的排班计划信息！");
				}else if("3".equals(type)){
					map.put("information", "删除成功：只删除了班次信息，没有删除引用该班次的排班计划！");
				}else if("1".equals(type)){
					map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
				}
			}else{
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.DELETE_FAIL_MSG);
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		return map;
	} 
	
	/**
	 * 查询班次记录，2016-09-28
	 */
	@RequestMapping(value = "/banCi", method = RequestMethod.GET)
    @ResponseBody
	public String selectBanCiList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
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
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String dutyOntimeStr = json.getString("dutyOntime");
			String dutyOfftimeStr = json.getString("dutyOfftime");
			Date dutyOntime = null;
			Date dutyOfftime = null;
			
			if(dutyOntimeStr != null && !"".equals(dutyOntimeStr)){
				dutyOntime = sdf.parse(json.getString("dutyOntime") + ":00");
			}
			
			if(dutyOfftimeStr != null && !"".equals(dutyOfftimeStr)){
				dutyOfftime  = sdf.parse(json.getString("dutyOfftime") + ":00");		
			}
			
			Banci banci = (Banci) JSONObject.toBean(json, Banci.class);
			banci.setDutyOntime(dutyOntime);
			banci.setDutyOfftime(dutyOfftime);
			
			//3.查询班次记录总条数
			int total = banciService.getBanciCount(banci);//查询班次记录总条数，2016-09-28
			
			//4.查询班组记录集合
			List<Banci> rows = banciService.getBanciList(banci);//查询班次记录集合，2016-09-28，分页
			
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
	
	/**
	 * 查询所有的班次记录，用于班次下拉框，2016-09-28
	 */
	@RequestMapping(value = "/banCiForDic", method = RequestMethod.GET)
    @ResponseBody
	public String selectAllBanCiList(HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		//1.若是请求参数为空，则返回fail
		/*if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information",ConstantUtil.SELECT_FAIL_MSG);
			return map.toString();
		}
		
		// 2.对请求参数解码并转换为班组对象
		acceptContent = CommonUtils.getDecodeParam(acceptContent);
		JSONObject json = JSONObject.fromObject(acceptContent);
		Banci banci = (Banci) JSONObject.toBean(json, Banci.class);*/
		Banci banci = new Banci();
		
		//3.查询班组记录总条数
		int total = banciService.getBanciCount(banci);//查询班次记录总条数，2016-09-27
		
		//4.查询所有班组记录集合
		List<Banci> rows = banciService.getAllBanciList(banci);//查询所有班次记录集合，2016-09-28，不分页
		
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
