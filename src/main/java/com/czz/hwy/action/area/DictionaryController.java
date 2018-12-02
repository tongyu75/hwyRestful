/**
 * 
 */
package com.czz.hwy.action.area;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.area.DutyPoint;
import com.czz.hwy.bean.manager.EvalTypes;
import com.czz.hwy.bean.user.Departments;
import com.czz.hwy.bean.user.Roles;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.area.DutyAreaService;
import com.czz.hwy.service.area.DutyPointService;
import com.czz.hwy.service.manager.EvalTypeService;
import com.czz.hwy.service.usermanagement.DepartmentsService;
import com.czz.hwy.service.usermanagement.RolesService;
import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
//import com.czz.hwy.service.area.DutyPointService;
//import com.czz.hwy.service.mission.WorkTypesService;
//import com.czz.hwy.service.usermanagement.DepartmentsService;
//import com.czz.hwy.service.usermanagement.RolesService;
//import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 动态字典请求
 * @author 张纪才
 *
 */
@Controller
@RequestMapping(value = "/dictionaryController")
public class DictionaryController {
    
    Map<String,Object> map=null;
    
    @Autowired
    private DutyAreaService dutyAreaService;
    
    @Autowired
    private DutyPointService dutyPointService;
    
    @Resource
    private AccessOrigin accessOrigin;
    
    @Autowired
    private DepartmentsService depService;
    
    @Autowired
    private RolesService rolesService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private EvalTypeService evalTypeService;
    
//    @Autowired
//    private DutyPointService dutyPointService;
//    @Autowired
//    private UsersService usersService;
//    @Autowired
//    private WorkTypesService workTypesService;
//    @Autowired
//    private DepartmentsService departmentService;
//    @Autowired
//    private RolesService rolesService;
    //责任区域名称模糊查询
//    private String q;
    //责任区id
//    private int areaId;
    //角色类型
//    private int role_value;
    //被选择的员工号
//    private String employeeIds;
    
    /**
     * 获取所有部门下拉列表
     */
    @RequestMapping(value="/depList", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getDepartments(HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        Departments dept = new Departments();
        // 状态标识        
        dept.setStatus(1);
        // 设置分页标识（0标识不分页）
        dept.setRows(0);
        // 查询部门列表
        List<Departments> depts = depService.getAllDeptByBean(dept);
        if(depts.size() > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", depts.size());
            map.put("rows", depts);
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.SELECT_FAIL_MSG);
        }
        return map;
    }

    /**
     * 获取所有举报项目下拉列表
     */
    @RequestMapping(value="/reportList", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getReportList(Integer type, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询部门列表
        List<EvalTypes> lstEvalType = evalTypeService.getAllEvalTypeForDic(type);
        if(lstEvalType.size() > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", lstEvalType.size());
            map.put("rows", lstEvalType);
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.SELECT_FAIL_MSG);
        }
        return map;
    }
    
    /**
     * 获取所有角色下拉列表
     */    
    @RequestMapping(value="/rolList", method=RequestMethod.GET)
    @ResponseBody        
    public Map<String, Object> getRoles(String areaName, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        Roles role = new Roles();
        // 状态标识
        role.setStatus(1);
        // 设置分页标识（0标识不分页）
        role.setRows(0);
        // 查询角色列表
        List<Roles> roles = rolesService.getAllRoles(role);
        if(roles.size() > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", roles.size());
            map.put("rows", roles);
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.SELECT_FAIL_MSG);
        }
        return map;
    }

    /**
     * 通过责任区名称模糊获取责任区信息
     * @param areaName 责任区名称
     * @return
     */
    @RequestMapping(value = "/dutyAreaCombobox", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDutyArea4Combobox(String areaName, HttpServletResponse reponse, HttpServletRequest request){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, reponse, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(areaName)) {
            areaName = CommonUtils.getDecodeParam(areaName);
        }
        List<Map<String,Object>> dutyAreas = dutyAreaService.getDutyAreaByLike(areaName);
        if(dutyAreas.size() > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("total", dutyAreas.size());
            map.put("rows", dutyAreas);
        }else{
            map.put("result", ConstantUtil.FAIL);
        }
        return map;
    }

    /**
     * 通过责任点名称模糊获取责任点信息
     * @param areaName 责任区名称
     * @return
     */
    @RequestMapping(value = "/dutyPointCombobox", method = RequestMethod.GET)
    @ResponseBody
   public Map<String, Object> getDutyPoints4Select(String pointName, HttpServletResponse reponse, HttpServletRequest request){
       // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
       CommonUtils.setAccessOrigin(request, reponse, accessOrigin);
       // 初始化返回值信息
       Map<String, Object> map = new HashMap<String, Object>();        
       //通过责任区域id获取相关责任点集合信息
       if (!StringUtils.isEmpty(pointName)) {
           pointName = CommonUtils.getDecodeParam(pointName);
       }
       List<Map<String,Object>> dutyPoint = dutyPointService.getDutyPointByLike(pointName);
       if(dutyPoint.size() > 0){
           map.put("result", ConstantUtil.SUCCESS);
           map.put("total", dutyPoint.size());
           map.put("rows", dutyPoint);
       }else{
           map.put("result", ConstantUtil.FAIL);
       }
       return map;
   }
    
    /**
     * 根据责任区id获取责任点的集合信息
     * @param acceptContent 查询条件     
     */
    @RequestMapping(value="/pointByAreadId/{areaId}", method=RequestMethod.GET)
    @ResponseBody
    public String getDutyPointByAreadId(@PathVariable(value="areaId") Integer areaId, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        //通过责任区域id获取相关责任点集合信息
        DutyPoint bean = new DutyPoint();
        bean.setAreaId(areaId);
        List<DutyPoint> dutyPoints = dutyPointService.getDutyPointListByBean(bean);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", dutyPoints.size());
        map.put("rows", dutyPoints);
        
        //处理时间格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        //返回责任点数据
        JSONObject json = JSONObject.fromObject(map,jsonConfig);
        return json.toString();
    }    
    
    /**
     * 根据责任区id获取责任点的集合信息
     * @param acceptContent 查询条件     
     */
    @RequestMapping(value="/pointByAreadId", method=RequestMethod.GET)
    @ResponseBody
    public String getPointListByAreadId(Integer areaId, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        //通过责任区域id获取相关责任点集合信息
        DutyPoint bean = new DutyPoint();
        bean.setAreaId(areaId == null ? 0 : areaId);
        List<DutyPoint> dutyPoints = dutyPointService.getDutyPointListByBean(bean);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", dutyPoints.size());
        map.put("rows", dutyPoints);
        
        //处理时间格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        //返回责任点数据
        JSONObject json = JSONObject.fromObject(map,jsonConfig);
        return json.toString();
    }    
    
    /**
     * 获取人员树 根据角色分类
     * @param acceptContent 查询条件     
     */
    @RequestMapping(value="/employeeTree", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getEmployeeTree(HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 定义返回集合list
        List<Object> objs = new ArrayList<Object>();
        
        /*        int[] epIds = null;
        //判断被选择的员工号是否有值
        if(employeeIds!=null&&employeeIds.length()>0){
            
            String[] emps = employeeIds.split(",");
            
            epIds = new int[emps.length];
            
            for(int i=0;i<emps.length;i++){
                
                epIds[i] = Integer.valueOf(emps[i]);
            }
        }*/
        
        Roles roles = new Roles();
        
        roles.setStatus(1);
        
        List<Roles> roless = rolesService.getAllRoles(roles);
        
        Users bean = new Users();
        bean.setRoleId(0);
        List<Users> userss = usersService.getMessageUsersInfoListByBean(bean);
        // 获取责任区信息
        List<Map<String,Object>> dutyAreas = dutyAreaService.getDutyAreaByLike(null);
        // 初始化发布消息,按照责任区区分员工
        LinkedHashMap<String, List<Map<String,Object>>> areaUser = this.initDutyAreaUsers(dutyAreas);
        //迭代用户信息
        if(roless!=null&&roless.size()>0) {
            
            Map<String,Object> obj = null;
            
            List<Object> child = null;
            
            for(Roles r:roless){//根据角色获取相关接收通知的用户信息
                
                obj =  new HashMap<String,Object>();
                
                obj.put("id", "rID_"+r.getValue());
                
                obj.put("text", r.getName());
                
                child = new ArrayList<Object>();
                
                for(Users u:userss){
                    
                    // 角色是环卫工，需要按照责任区来区分员工
                    if(u.getRoleId() == r.getValue() && u.getRoleId() == 1){
                        Map<String,Object> umap1 = new HashMap<String,Object>();
                        // 获取责任区信息
                        List<Map<String,Object>> lstArea = dutyAreaService.getDutyAreaByLike(null);
                        // 将每个员工存入对应的责任区员工Map中
                        for (Map<String,Object> mp : lstArea) {
                            if (mp.get("area_name").equals(u.getAreaName())) {
                                umap1.put("id", u.getEmployeeId());
                                umap1.put("text", u.getShowname()+"("+u.getEmployeeId()+")");
                                List<Map<String,Object>> lstMp = areaUser.get(u.getAreaName());
                                lstMp.add(umap1);
                                break;
                            }
                        }
                    } else if (u.getRoleId() == r.getValue()){
                        //定义存储用户信息的map
                        Map<String,Object> umap = new HashMap<String,Object>();
/*                        if(epIds!=null&&epIds.length>0){//判断员工是否被选中
                            for(int epid:epIds){
                                if(epid==u.getEmployeeId()){
                                    umap.put("checked", true);
                                }
                            }
                        }*/
                        umap.put("id", u.getEmployeeId());
                        
                        umap.put("text", u.getShowname()+"("+u.getEmployeeId()+")");
                        
                        child.add(umap);
                    }
                }
                // 添加角色是环卫工的责任区
                if (r.getValue() == 1) {
                    child.add(areaUser);
                }
                if(child!=null&&child.size()>0){//当子节点不为空的时候则折叠起来
                    obj.put("children", child);
                    obj.put("state", "closed");
                    objs.add(obj);
                }
                
            }
        }
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", objs.size());
        map.put("rows", objs);
        return map;
    }
    
    /**
     * 初始化发布消息,按照责任区区分员工
     */
    public LinkedHashMap<String, List<Map<String,Object>>> initDutyAreaUsers(List<Map<String,Object>> lstArea){
        LinkedHashMap<String, List<Map<String,Object>>> result = new LinkedHashMap<String, List<Map<String,Object>>>();
        for (Map<String,Object> area : lstArea) {
            // 根据责任区初始化变量,用于存储员工
            result.put((String) area.get("area_name"), new ArrayList<Map<String,Object>>());
        }
        return result;
    }
   
    /**
     * 获取人员列表，2016-11-15，不分页，可根据员工ID或员工名称进行查询
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public String selectUsersList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
        
		Map<String,Object> map = new HashMap<String,Object>();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为排班计划对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Users users = (Users) JSONObject.toBean(json, Users.class);
			
			//3.查询员工记录总条数
			int total = usersService.getUserCount(users);//查询员工记录总条数，2016-11-15
			
			//4.查询员工记录集合，分页
			List<Users> rows = usersService.getUserList(users);//查询员工记录集合，2016-11-15，不分页
			
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
     * 根据责任区ID获取 排班计划中的用户  2016-11-21
     */
    @RequestMapping(value = "/usersByAreaId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUsersByAreaId(String acceptContent,HttpServletResponse response, HttpServletRequest request){
        
        Map<String,Object> map = new HashMap<String,Object>  ();
        // 0.设置允许跨域访问的路径
        CommonUtils.setAccessOrigin(request,response, accessOrigin);
        
       //1.若是请求参数为空，则返回fail
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",ConstantUtil.SELECT_FAIL_MSG);
            return map;
        }
        
        // 2.对请求参数解码并转换为排班计划对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        Users users = (Users) JSONObject.toBean(json, Users.class);
        
        // 3.查询员工记录集合
        List<Map<String, Object>> rows = usersService.getUserIdByAreaId(users);
        
        // 4.根据查询结果，返回相应数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", rows.size());
        map.put("rows", rows);

        return map;
    } 
    
    
    /**
     * 通过责任区名称模糊获取责任区信息
     */
    /*@RequestMapping(value = "/getDutyAreas4Select/{q}/", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDutyAreas4Select(@PathVariable(value="q") String q){
        //初始化返回值信息
        map = new HashMap<String,Object>();
//        String q = null;
        List<Map<String,Object>> dutyAreas = dutyAreaService.getDutyAreaByLike(q);
        map.put("total", dutyAreas.size());
        map.put("rows", dutyAreas);
        return map;
    }*/
    /*

    *//**
     * 通过责任点名称模糊获取责任点信息
     *//*
    public void getDutyPoints4Select(){
        //通过责任区域id获取相关责任点集合信息
        List<Map> dutyPoints = null;
        
        dutyPoints = dutyPointService.getDutyPointByLike(q);
        
        map = new HashMap<String,Object>();
        
        map.put("total", dutyPoints.size());
        
        map.put("rows", dutyPoints);
        
        super.returnJson(map);
    }
    *//**
     * 通过责任区获取责任点信息
     *//*
    public void getDutyPoints4Combox(){
        
        //根据责任区id获取相关责任点集合信息
        List<Map<String,String>> dutyPoints = null;
        
        dutyPoints = dutyPointService.getDutyPointInfosById(areaId);
        
        super.returnJsonArr(dutyPoints);
    }*/
    /**
     * 根据用户角色，姓名模糊查询用户信息
     *//*
    public void getUsers4Combogrid(){
        
        List<Users> us = null;
        
        Users u = new Users();
        
        u.setRoleId(role_value);
        
        u.setShowname(q);
        
        us = usersService.getUsersInfoByRoleIdName(u);
        
        super.returnJsonArr(us);;
    }
    *//**
     * 获取人员下拉列表
     *//*
    public void getUsers4Combobox(){
        List<Users> us = null;
        
        Users u = new Users();
        
        u.setShowname(q);
        
        u.setRoleId(role_value);
        
        us = usersService.getUsersInfoByRoleIdName(u);
        
        map = new HashMap<String,Object>();
        
        map.put("total", us.size());
        
        map.put("rows", us);
        
        super.returnJson(map);
    }
    *//**
     * 获取任务类型字典
     *//*
    public void getDutyWorkType(){
        
        List<WorkTypes> workTypes = workTypesService.getListWorkTypes();
        
        super.returnJsonArr(workTypes);
    }
    *//**
     * 获取部门字典
     *//*
    public void getDepts4Combogrid(){
        
        List<Departments> depts = null;
        
        Departments bean = new Departments();
        
        bean.setPage(0);
        
        bean.setRows(0);
        
        bean.setDeptName(q);
        
        depts = departmentService.getDeptForSearch(bean);
        
        map = new HashMap<String,Object>();
        
        map.put("total", depts.size());
        
        map.put("rows",depts);
        
        super.returnJson(map);
    }
    */
    
    /**
     * 获取人员树 根据角色分类
     */
    /*public void getEmployeeTree(){
        
        //定义返回集合list
        List<Map<String,Object>> objs = new ArrayList<Map<String,Object>>();
        
        int[] epIds = null;
        //判断被选择的员工号是否有值
        if(employeeIds!=null&&employeeIds.length()>0){
            
            String[] emps = employeeIds.split(",");
            
            epIds = new int[emps.length];
            
            for(int i=0;i<emps.length;i++){
                
                epIds[i] = Integer.valueOf(emps[i]);
            }
        }
        
        Roles roles = new Roles();
        
        roles.setStatus(1);
        
        List<Roles> roless = rolesService.getAllRoles(roles);
        
        List<Users> userss = usersService.getUsersWithSubtopics();
        //迭代用户信息
        if(roless!=null&&roless.size()>0){
            
            Map<String,Object> obj = null;
            
            List<Map<String,Object>> child = null;
            
            for(Roles r:roless){//根据角色获取相关接收通知的用户信息
                
                obj =  new HashMap<String,Object>();
                
                obj.put("id", "rID_"+r.getValue());
                
                obj.put("text", r.getName());
                
                child = new ArrayList<Map<String,Object>>();
                
                for(Users u:userss){
                    
                    if(u.getRoleId()==r.getValue()){
                        //定义存储用户信息的map
                        Map<String,Object> umap = new HashMap<String,Object>();
                        
                        if(epIds!=null&&epIds.length>0){//判断员工是否被选中
                            
                            for(int epid:epIds){
                                
                                if(epid==u.getEmployeeId()){
                                    
                                    umap.put("checked", true);
                                }
                            }
                            
                        }
                        umap.put("id", u.getEmployeeId());
                        
                        umap.put("text", u.getShowname()+"("+u.getEmployeeId()+")");
                        
                        child.add(umap);
                    }
                }
                
                if(child!=null&&child.size()>0){//当子节点不为空的时候则折叠起来
                    obj.put("children", child);
                    
                    
                    obj.put("state", "closed");
                    
                    objs.add(obj);
                }
                
            }
        }
        super.returnJsonArr(objs);
    }
    */
    
    /*public void setQ(String q) {
        this.q = q;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public void setRole_value(int role_value) {
        this.role_value = role_value;
    }
    public void setEmployeeIds(String employeeIds) {
        this.employeeIds = employeeIds;
    }
    */
}
