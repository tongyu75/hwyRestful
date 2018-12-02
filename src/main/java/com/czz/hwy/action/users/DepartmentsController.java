package com.czz.hwy.action.users;

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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.user.Departments;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.usermanagement.DepartmentsService;
import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

@Controller
@RequestMapping(value = "/deptController")
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;
    
    @Autowired
    private UsersService usersService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 部门信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/dept", method=RequestMethod.GET)
    @ResponseBody
    public String getDepltForSearch(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response) {
        
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",  "查询失败，查询信息不能为空！");
            return map.toString();
        }
        
        // 对请求参数解码并转换为部门信息对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        Departments dept = (Departments) JSONObject.toBean(json, Departments.class);

        // 查询部门信息记录总条数
        int count = departmentsService.getDeptCount(dept);
        // 查询部门信息记录
        List<Departments> lstDept = departmentsService.getAllDeptByBean(dept);

        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", lstDept);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回用户信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
    
    /**
     * 添加部门信息
     * @param acceptContent 部门信息
     */
    @RequestMapping(value="/dept", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertDept(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "新增失败，新增信息不能为空！");
            return map;
        }
        
        // 对请求参数解码并转换为责任区对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Departments dept = (Departments) JSONObject.toBean(json, Departments.class);
        
        // 查询部门名称是否存在
        Departments beanName = new Departments();
        beanName.setDeptName(dept.getDeptName());
        Departments department = departmentsService.getDepartmentByBean(beanName);
        if(department == null) {
            // 查询部门ID是否存在
            Departments beanId = new Departments();
            beanId.setDeptId(dept.getDeptId());
            Departments resultDeptByDeptId = departmentsService.getDepartmentByBean(beanId);
            if(resultDeptByDeptId == null) {
                Departments addDepartments = new Departments();
                addDepartments.setDeptDesc(dept.getDeptDesc());
                addDepartments.setDeptName(dept.getDeptName());
                addDepartments.setDeptId(dept.getDeptId());
                addDepartments.setCreateAt(new Date());
                addDepartments.setStatus(1);
                int opinion = departmentsService.insertDepartments(addDepartments);
                // 插入成功
                if(opinion > 0) {
                    map.put("result", ConstantUtil.SUCCESS);
                    map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
                } else{
                    map.put("result", ConstantUtil.FAIL);
                    map.put("information", ConstantUtil.INSERT_FAIL_MSG);
                }
            } else {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", "部门ID已存在。");
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "部门名称已存在。");
        }
        return map;
    }
    
    /**
     * 删除部门信息
     * @param id ID
     * @param deptId 部门ID     
     */
    @RequestMapping(value="/dept", method=RequestMethod.DELETE)
    @ResponseBody      
    public Map<String, Object> deleteDept(Integer id, Integer deptId, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();

        // 查询当前部门是否存在员工
        Users user=new Users();
        user.setDeptId(deptId);
        user.setRows(0);
        List<Users> users=usersService.getAllUserByBean(user);
        // 不存在员工，可以进行删除部门
        if(users.isEmpty()) {
            Departments dept = new Departments();
            dept.setId(id);
            dept.setUpdateAt(new Date());
            dept.setStatus(2);
            // 删除部门
            int opinion = departmentsService.deleteDepartments(dept);
            // 删除成功
            if (opinion > 0) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
            } else {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.DELETE_FAIL_MSG);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "该部门仍存在员工。");
        }
        return map;
    }
    
    /**
     * 修改部门信息
     * @param acceptContent 部门信息
     */
    @RequestMapping(value="/dept", method=RequestMethod.PUT)
    @ResponseBody    
    public Map<String, Object> updateDept(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Departments dept = (Departments) JSONObject.toBean(json, Departments.class);
        
        // 查询部门名称是否存在
        Departments beanName = new Departments();
        beanName.setDeptName(dept.getDeptName());
        Departments department = departmentsService.getDepartmentByBean(beanName);
        if(department == null) {
        	// 修改部门信息
            dept.setUpdateAt(new Date());
            int opinion=departmentsService.updateDepartments(dept);
            // 修改成功
            if (opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
            } else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
            }                
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "部门名称已存在。");
        }
        return map;
    }
}
