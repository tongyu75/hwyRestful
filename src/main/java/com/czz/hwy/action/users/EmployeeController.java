/**
 * 
 */
package com.czz.hwy.action.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.user.Employee;
import com.czz.hwy.bean.user.EmployeeConfig;
import com.czz.hwy.bean.user.EmployeeSelect;
import com.czz.hwy.service.usermanagement.EmployeeConfigService;
import com.czz.hwy.service.usermanagement.EmployeeSelectService;
import com.czz.hwy.service.usermanagement.EmployeeService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * web端用户登录
 * @author 佟士儒
 *
 */
@Controller
public class EmployeeController{
	
	@Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConfigService employeeConfigService;
	
    @Autowired
    private EmployeeSelectService employeeSelectService;
    
	@Resource
	private AccessOrigin accessOrigin;
	
	private final static Logger log = LoggerFactory.getLogger(EmployeeController.class);

    /**
     * 根据设置的查询数量，随机生成姓名
     * 
     * @param financeArray
     * 
     * @return 结果
     */
    @RequestMapping(value = "/employeeShow", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> employeeShow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取设置的数量
        EmployeeConfig bean = new EmployeeConfig();
        
        bean.setId(1);
        EmployeeConfig config = employeeConfigService.getEmployeeConfigByBean(bean);
        // 监督专家数量
        int expert = config.getExpert();
        // 财务类数量
        int finance = config.getFinance();
        // 技术类数量
        int technology = config.getTechnology();

        Employee employee = new Employee();
        employee.setIsDelete(0);
        // 0是不可以,1是可用
        employee.setStatus(1);
        // 从DB中获取不同类型人员的名称以及不同类型人员的数量
        List<Employee> lstEmployee = employeeService.getEmployeeListByBean(employee);
        int expertNum = 0;
        int financeNum = 0;
        int technologyNum = 0;
        List<Employee> lstExpertName = new ArrayList<Employee>();
        List<Employee> lstFinanceName = new ArrayList<Employee>();
        List<Employee> lstTechnologyName = new ArrayList<Employee>();
        for (Employee emp : lstEmployee) {
            // 人员名称
            //String empName = emp.getName();
            // 1是监督专家 2是财务类 3技术类
            if (emp.getType() == 1) {
                lstExpertName.add(emp);
                expertNum++;
            } else if (emp.getType() == 2) {
                lstFinanceName.add(emp);
                financeNum++;
            } else if (emp.getType() == 3) {
                lstTechnologyName.add(emp);
                technologyNum++;
            }
        }
        
        JSONArray returnArray = new JSONArray();
        // 根据每种类型设置的查询数量以及当前DB中存在的数量，获取随机index，根据index获取具体显示人员的名称
        JSONArray expertArray = new JSONArray();
        // 当配置查询的数量大于等于实际存在的用户，则把实际存在的用户全部返回
        if (expert >= expertNum) {
            for (int i = 0; i < expertNum; i++) {
                JSONObject exprtObj = new JSONObject();
                Employee exprt = lstExpertName.get(i);
                exprtObj.put("expertName", exprt.getName());
                expertArray.add(exprtObj);
                // 插入选择表中，以便于再次查询的时候，先列出选择的数据，然后再从没被选择的数据抽取数据进行拼接
                EmployeeSelect exprtSelect = new EmployeeSelect();
                exprtSelect.setSelectId(exprt.getId());
                exprtSelect.setName(exprt.getName());
                exprtSelect.setMajor(exprt.getMajor());
                exprtSelect.setType(exprt.getType());
                exprtSelect.setStatus(1);
                employeeSelectService.insertEmployeeSelect(exprtSelect);
                	
            }
        } else {
        	// 查询已经设置的人员，再有查询需要拼接这个人数
        	EmployeeSelect expertSelect = new EmployeeSelect();
            // 0是不可以用,1是可用
        	expertSelect.setStatus(1);
        	expertSelect.setType(1);
        	List<Integer> exprtIds = new ArrayList<Integer>();
        	List<EmployeeSelect> lstExpertSelect = employeeSelectService.getEmployeeSelectListByBean(expertSelect);
        	for (EmployeeSelect a : lstExpertSelect) {
        		JSONObject exprtObj = new JSONObject();
                exprtObj.put("expertName", a.getName());
                expertArray.add(exprtObj);
                exprtIds.add(a.getSelectId());
        	}
        	// 如果输入查询的数量小于之前已经查询出的数据，那么需要进行重新查询
        	if (expert < exprtIds.size()) {
        		for (Integer id : exprtIds) {
        			employeeSelectService.deleteEmployeeSelectById(id);
        		}
        		expertArray.clear();
        		exprtIds.clear();
        	}
        	
            List<Integer> lstExpertNum = getRandomNumList(expert, 0, expertNum);
            for (Integer expertIndex : lstExpertNum) {
                JSONObject exprtObj = new JSONObject();
                Employee exprt = lstExpertName.get(expertIndex);
                if (!exprtIds.contains(exprt.getId()) && (expertArray.size() < expert)) {
                	exprtObj.put("expertName", exprt.getName());
                    expertArray.add(exprtObj);
                    // 插入选择表中，以便于再次查询的时候，先列出选择的数据，然后再从没被选择的数据抽取数据进行拼接
                    EmployeeSelect exprtSelect = new EmployeeSelect();
                    exprtSelect.setSelectId(exprt.getId());
                    exprtSelect.setName(exprt.getName());
                    exprtSelect.setMajor(exprt.getMajor());
                    exprtSelect.setType(exprt.getType());
                    exprtSelect.setStatus(1);
                    employeeSelectService.insertEmployeeSelect(exprtSelect);
                }
            }
        }
        
        JSONArray financeArray = new JSONArray();
        // 当配置查询的数量大于实际存在的用户，则把实际存在的用户全部返回
        if (finance >= financeNum) {
            for (int i = 0; i < financeNum; i++) {
                JSONObject financeObj = new JSONObject();
                Employee financeEmp = lstFinanceName.get(i);
                financeObj.put("financeName", financeEmp.getName());
                financeArray.add(financeObj);
                // 插入选择表中，以便于再次查询的时候，先列出选择的数据，然后再从没被选择的数据抽取数据进行拼接
                EmployeeSelect exprtSelect = new EmployeeSelect();
                exprtSelect.setSelectId(financeEmp.getId());
                exprtSelect.setName(financeEmp.getName());
                exprtSelect.setMajor(financeEmp.getMajor());
                exprtSelect.setType(financeEmp.getType());
                exprtSelect.setStatus(1);
                employeeSelectService.insertEmployeeSelect(exprtSelect);
            }
        } else {
        	// 查询已经设置的人员，再有查询需要拼接这个人数
        	EmployeeSelect expertSelect = new EmployeeSelect();
            // 0是不可以,1是可用
        	expertSelect.setStatus(1);
        	expertSelect.setType(2);
        	List<Integer> exprtIds = new ArrayList<Integer>();
        	List<EmployeeSelect> lstExpertSelect = employeeSelectService.getEmployeeSelectListByBean(expertSelect);
        	for (EmployeeSelect a : lstExpertSelect) {
        		JSONObject exprtObj = new JSONObject();
                exprtObj.put("financeName", a.getName());
                financeArray.add(exprtObj);
                exprtIds.add(a.getSelectId());
        	}
        	
        	if (finance < exprtIds.size()) {
        		for (Integer id : exprtIds) {
        			employeeSelectService.deleteEmployeeSelectById(id);
        		}
        		financeArray.clear();
        		exprtIds.clear();
        	}
        	
            List<Integer> lstFinanceNum = getRandomNumList(finance, 0, financeNum);
            for (Integer financeIndex : lstFinanceNum) {
                JSONObject financeObj = new JSONObject();
                Employee financeEmp = lstFinanceName.get(financeIndex);
                if (!exprtIds.contains(financeEmp.getId()) && (financeArray.size() < finance)) {
                	financeObj.put("financeName", financeEmp.getName());
                    financeArray.add(financeObj);
                    // 插入选择表中，以便于再次查询的时候，先列出选择的数据，然后再从没被选择的数据抽取数据进行拼接
                    EmployeeSelect exprtSelect = new EmployeeSelect();
                    exprtSelect.setSelectId(financeEmp.getId());
                    exprtSelect.setName(financeEmp.getName());
                    exprtSelect.setMajor(financeEmp.getMajor());
                    exprtSelect.setType(financeEmp.getType());
                    exprtSelect.setStatus(1);
                    employeeSelectService.insertEmployeeSelect(exprtSelect);
                }
            }
        }

        JSONArray technologyArray = new JSONArray();
        // 当配置查询的数量大于实际存在的用户，则把实际存在的用户全部返回
        if (technology >= technologyNum) {
            for (int i = 0; i < technologyNum; i++) {
                JSONObject technologyObj = new JSONObject();
                Employee technologyEmp = lstTechnologyName.get(i);
                technologyObj.put("technologyName", technologyEmp.getName());
                technologyArray.add(technologyObj);
                // 插入选择表中，以便于再次查询的时候，先列出选择的数据，然后再从没被选择的数据抽取数据进行拼接
                EmployeeSelect exprtSelect = new EmployeeSelect();
                exprtSelect.setSelectId(technologyEmp.getId());
                exprtSelect.setName(technologyEmp.getName());
                exprtSelect.setMajor(technologyEmp.getMajor());
                exprtSelect.setType(technologyEmp.getType());
                exprtSelect.setStatus(1);
                employeeSelectService.insertEmployeeSelect(exprtSelect);
            }
        } else {

        	// 查询已经设置的人员，再有查询需要拼接这个人数
        	EmployeeSelect expertSelect = new EmployeeSelect();
            // 0是不可以,1是可用
        	expertSelect.setStatus(1);
        	expertSelect.setType(3);
        	List<Integer> exprtIds = new ArrayList<Integer>();
        	List<EmployeeSelect> lstExpertSelect = employeeSelectService.getEmployeeSelectListByBean(expertSelect);
        	for (EmployeeSelect a : lstExpertSelect) {
        		JSONObject exprtObj = new JSONObject();
                exprtObj.put("technologyName", a.getName());
                technologyArray.add(exprtObj);
                exprtIds.add(a.getSelectId());
        	}
        	
        	if (technology < exprtIds.size()) {
        		for (Integer id : exprtIds) {
        			employeeSelectService.deleteEmployeeSelectById(id);
        		}
        		technologyArray.clear();
        		exprtIds.clear();
        	}
        	
            List<Integer> lstTechnologyNum = getRandomNumList(technology, 0, technologyNum);
            for (Integer technologyIndex : lstTechnologyNum) {
                JSONObject technologyObj = new JSONObject();
                Employee technologyEmp = lstTechnologyName.get(technologyIndex);
                if (!exprtIds.contains(technologyEmp.getId()) && (technologyArray.size() < technology)) { 
                	technologyObj.put("technologyName", technologyEmp.getName());
                    technologyArray.add(technologyObj);
                    // 插入选择表中，以便于再次查询的时候，先列出选择的数据，然后再从没被选择的数据抽取数据进行拼接
                    EmployeeSelect exprtSelect = new EmployeeSelect();
                    exprtSelect.setSelectId(technologyEmp.getId());
                    exprtSelect.setName(technologyEmp.getName());
                    exprtSelect.setMajor(technologyEmp.getMajor());
                    exprtSelect.setType(technologyEmp.getType());
                    exprtSelect.setStatus(1);
                    System.out.println("aaaaa" + technologyEmp.getId() + technologyEmp.getName() + technologyEmp.getMajor() + technologyEmp.getType());
                    employeeSelectService.insertEmployeeSelect(exprtSelect);
                }
                
            }
        }

        returnArray.add(expertArray);
        returnArray.add(financeArray);
        returnArray.add(technologyArray);
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        map.put("employeeName", returnArray.toString());
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        return map;
	}

    /**
     * 重置按钮清楚DB中已经选中的数据
     * 
     * @param financeArray
     * 
     * @return 结果
     */
    @RequestMapping(value = "/resetSelectEmployee", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> resetSelectEmployee(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        // 从DB中获取不同类型人员的名称以及不同类型人员的数量
        int flag = employeeSelectService.resetSelectEmployee();
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("data", flag);
        return map;
	}
    
    
    /**
     * 修改查询配置人数信息
     * 
     * @param acceptContent
     *            部门信息
     */
    @RequestMapping(value = "/employeeConfig", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateEmployeeConfig(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if (acceptContent == null || "".equals(acceptContent)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        EmployeeConfig employeeConfig = (EmployeeConfig) JSONObject.toBean(json, EmployeeConfig.class);

        int opinion = employeeConfigService.updateEmployeeConfigByBean(employeeConfig);
        // 修改成功
        if (opinion > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
        }
        return map;
    }

    /**
     * 显示人员列表
     * 
     * @param financeArray
     * 
     * @return 结果
     */
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> getEmployee(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        Employee employee = new Employee();
        employee.setIsDelete(0);
        // 从DB中获取不同类型人员的名称以及不同类型人员的数量
        List<Employee> lstEmployee = employeeService.getEmployeeListByBean(employee);
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("data", lstEmployee);
        return map;
	}
    
    /**
     * 添加人员信息
     * 
     * @param acceptContent
     *            部门信息
     */
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addEmployee(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if (acceptContent == null || "".equals(acceptContent)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Employee employee = (Employee) JSONObject.toBean(json, Employee.class);
        employee.setStatus(1);
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        
        int opinion = employeeService.insertEmployee(employee);
        // 添加成功
        if (opinion > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
            map.put("id", employee.getId());
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.INSERT_FAIL_MSG);
        }
        return map;
    }
    
    /**
     * 修改人员信息
     * 
     * @param acceptContent
     *            部门信息
     */
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateEmployee(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if (acceptContent == null || "".equals(acceptContent)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Employee employee = (Employee) JSONObject.toBean(json, Employee.class);

        int opinion = employeeService.updateEmployeeByBean(employee);
        // 添加成功
        if (opinion > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
        }
        return map;
    }
    
    /**
     * 修改人员信息
     * 
     * @param acceptContent
     *            部门信息
     */
    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteEmployee(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if (acceptContent == null || "".equals(acceptContent)) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        
        int id = json.getInt("id");
        int opinion = employeeService.deleteEmployeeById(id);
        // 添加成功
        if (opinion > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.DELETE_FAIL_MSG);
        }
        return map;
    }
    
    // 定义生成随机数并且装入集合容器的方法
    // 方法的形参列表分别为：生成随机数的个数、生成随机数的值的范围最小值为start(包含start)、值得范围最大值为end(不包含end)
    // 可取值范围可表示为[start,end)
    private List<Integer> getRandomNumList(int nums, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<Integer>();

        // 2.创建Random对象
        Random r = new Random();
        // 循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while (list.size() != nums) {
            int num = r.nextInt(end - start) + start;
            if (!list.contains(num)) {
                list.add(num);
            }
        }

        return list;
    }
}
