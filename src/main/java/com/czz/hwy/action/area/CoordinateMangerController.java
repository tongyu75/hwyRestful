package com.czz.hwy.action.area;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.service.usermanagement.AttendancesService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;

/***
 * 坐标管理功能
 * @author 张咏雪
 * @Date 2016-09-08
 *
 */
@Controller
@RequestMapping(value = "/coordinateManger")
public class CoordinateMangerController {
	
	@Autowired
	private AttendancesService attendancesService;

	@Resource
    private AccessOrigin accessOrigin;
	
	/**
	 * 获取当前上班人员的最新坐标：某责任区，2016-09-19
	 */
	@RequestMapping(value = "/getNewCoorList", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> getNewCoorList(String acceptContent, HttpServletResponse response,HttpServletRequest request){
	    
		Map<String, Object> selectMap = new HashMap<String, Object>();
		String dateStr = null;
		String areaId = null;
		
	    // 判断请求信息是否为空
        if (acceptContent != null && !"".equals(acceptContent)) {
        	JSONObject json = JSONObject.fromObject(acceptContent);
        	areaId = json.getString("areaId");
        	dateStr = json.getString("dateStr");
        }
		
		Date date = new Date();
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeSdf = new SimpleDateFormat("HH:ss:mm");
		String timeStr = timeSdf.format(date);
		if(dateStr == null || "".equals(dateStr)){//若是dateStr为空，就取当前时间的年月日
			dateStr = dateSdf.format(date);
		}
		selectMap.put("areaId", areaId);
		selectMap.put("dateStr", dateStr);
		selectMap.put("timeStr", timeStr);
		
		List<Map<String,Object>> coorList =  attendancesService.getNewCoorList(selectMap);//获取某责任区，当前时间正在上班的员工的最新坐标。2016-09-14
		
		Map<String,Object> map = new HashMap<String,Object> ();
		if(coorList.size() > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information",coorList );
		}else{
			map.put("result", ConstantUtil.FAIL);
		}
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		return map;
	} 

	/**
	 * 获取某个员工所有的坐标点：某日期，某时间前所有的坐标点，2016-09-19
	 * @return
	 */
	@RequestMapping(value = "/getEmployeeOfCoorList", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> getEmployeeOfCoorList(String acceptContent, HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> selectMap = new HashMap<String, Object>();
		String dateStr = null;
		String areaId = null;
		String employeeName = null;
		
	    // 判断请求信息是否为空
        if (acceptContent != null && !"".equals(acceptContent)) {
        	JSONObject json = JSONObject.fromObject(acceptContent);
        	areaId = json.getString("areaId");
        	dateStr = json.getString("dateStr");
        	employeeName = json.getString("employeeName");
        }
		
		Date date = new Date();
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeSdf = new SimpleDateFormat("HH:ss:mm");
		String timeStr = timeSdf.format(date);
		if(dateStr == null || "".equals(dateStr)){//若是dateStr为空，就取当前时间的年月日
			dateStr = dateSdf.format(date);
		}
		selectMap.put("areaId", areaId);
		selectMap.put("employeeName", employeeName);
		selectMap.put("dateStr", dateStr);
		selectMap.put("timeStr", timeStr);
		
		List<Map<String,Object>> coorList = attendancesService.getEmployeeOfCoorList(selectMap);//获取某个员工所有的坐标点：某日期，某时间前所有的坐标点，2016-09-15
		Map<String,Object> map = new HashMap<String,Object> ();
		if(coorList.size() > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information",coorList);
		}else{
			map.put("result", ConstantUtil.FAIL);
		}
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		return map;
	}
	
	/**
	 * 获取当前上班人员的最新的两个坐标：某责任区，2016-09-26
	 */
	@RequestMapping(value = "/getNewTwoCoorList", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> getNewTwoCoorList(String acceptContent, HttpServletResponse response,HttpServletRequest request){
	    
		Map<String, Object> selectMap = new HashMap<String, Object>();
		String dateStr = null;
		String areaId = null;
		
	    // 判断请求信息是否为空
        if (acceptContent != null && !"".equals(acceptContent)) {
        	JSONObject json = JSONObject.fromObject(acceptContent);
        	areaId = json.getString("areaId");
        	dateStr = json.getString("dateStr");
        }
		
		Date date = new Date();
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeSdf = new SimpleDateFormat("HH:ss:mm");
		String timeStr = timeSdf.format(date);
		if(dateStr == null || "".equals(dateStr)){//若是dateStr为空，就取当前时间的年月日
			dateStr = dateSdf.format(date);
		}
		selectMap.put("areaId", areaId);
		selectMap.put("dateStr", dateStr);
		selectMap.put("timeStr", timeStr);
		
		List<Map<String,Object>> coorList =  attendancesService.getNewTwoCoorList(selectMap);//获取某责任区，当前时间正在上班的员工的最新的两个坐标。2016-09-26
		
		Map<String,Object> map = new HashMap<String,Object> ();
		if(coorList.size() > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information",coorList );
		}else{
			map.put("result", ConstantUtil.FAIL);
		}
		// 设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		return map;
	} 
	
    /**
     * 获取经纬度信息，用于当前用户轨迹的查看。
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/attendanceCoordinate", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getAttendanceCoordinate(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件信息
        Map<String, Object> selectMap = new HashMap<String, Object>();
        // 判断接收参数
        if(StringUtils.isEmpty(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为相应对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        // 责任区ID
        String areaId = json.getString("areaId");
        // 员工ID
        int employeeId = json.getInt("employeeId");
        // 开始时间
        String startTimeStr = json.getString("startTimeStr");
        // 结束时间
        String endTimeStr = json.getString("endTimeStr");
        selectMap.put("areaId", areaId);
        selectMap.put("employeeId", employeeId);
        selectMap.put("startTimeStr", startTimeStr);
        selectMap.put("endTimeStr", endTimeStr);
        // 查询考勤坐标信息记录
        List<Map<String, Object>> lstGram = attendancesService.getCoordinateForUsrePath(selectMap);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", lstGram.size());
        map.put("rows", lstGram);
        return map;
    }    
}
