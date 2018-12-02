/**
 * 
 */
package com.czz.hwy.action.area;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.area.BoderPointFile;
import com.czz.hwy.bean.area.DutyPointGather;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.area.BoderPointFileService;
import com.czz.hwy.service.area.DutyPointGatherService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.Dom4jForDutyAreaUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;
import com.czz.hwy.utils.XmlUtils;

/**
 * 责任点地图采集信息
 * @author 佟士儒
 *
 */
@Controller
@RequestMapping(value = "/showPointController")
public class ShowPointFilesController {
    
    @Autowired
    private BoderPointFileService boderPointFileService;

    @Autowired
    private DutyPointGatherService dutyPointGatherService;
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    /**
     * 责任点地图采集信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/boderPoint", method=RequestMethod.GET)
    @ResponseBody
    public String showBoderPointFileInfo(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map.toString();
        }
        
        // 对请求参数解码并转换为责任点地图采集对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        BoderPointFile boderPoint = (BoderPointFile) JSONObject.toBean(json, BoderPointFile.class);
        
        // 查询责任点及考核点采集上报文件总条数
        int total = boderPointFileService.getBoderPointFilesCount(boderPoint);
        // 查询责任点及考核点采集上报文件
        List<BoderPointFile> lstBoderPoint = boderPointFileService.getBoderPointFiles(boderPoint);
        for (BoderPointFile boder : lstBoderPoint) {
            // 根据采集上报文件ID责任点的边界点采集信息
            DutyPointGather gather = new DutyPointGather();
            gather.setFile_id(boder.getId());
            List<DutyPointGather> lstGather = dutyPointGatherService.getGatherPointList(gather);
            boder.setLstDutyPointGather(lstGather);
        }
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", total);
        map.put("rows", lstBoderPoint);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回责任区信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
    
/*    *//**
     * 责任点地图采集信息保存
     * @param acceptContent 责任点地图采集信息
     *//*
    @RequestMapping(value="/boderPoint", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertBoderPointFileInfo(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map;
        }
        
        // 对请求参数解码并转换为责任点地图采集对象
        JSONArray json = JSONArray.fromObject(acceptContent);
        @SuppressWarnings("unchecked")
        List<BoderPointFile> lstBoderPoint = (List<BoderPointFile>) JSONArray.toArray(json, BoderPointFile.class);
        
        // 从缓存中获取用户信息
        String userToken = CommonUtils.getCookieValue(request, "userToken");
        Users user = (Users) redisTemplate.opsForValue().get(userToken);
        for (BoderPointFile boder : lstBoderPoint) {
            // 创建者ID
            boder.setCreateId(user.getEmployeeId());
            // 创建时间
            boder.setCreateAt(new Date());
            // 状态标识
            boder.setStatus(1);
        }
        // 插入责任点地图采集文件信息
        int opinion = boderPointFileService.insertBoderPointFile(lstBoderPoint);
        
        // 插入经纬度信息
        int flag = 0;
        for (BoderPointFile boder : lstBoderPoint) {
            List<DutyPointGather> dutyPointGathers = Dom4jForDutyAreaUtil.getDutyAreaPoints(
                    boder.getPointFilepath(), user.getEmployeeId(), boder.getId());
            flag = dutyPointGatherService.insertGatherPoint(dutyPointGathers);
        }        
        // 插入成功
        if(opinion > 0 && flag > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.INSERT_FAIL_MSG);
        }
        return map;
    }    */
    
    /**
     * 更新责任点地图信息选中状态
     * @param acceptContent 责任点信息
     */
    @RequestMapping(value="/boderPoint", method=RequestMethod.PUT)
    @ResponseBody    
    public Map<String, Object> updateDutyPoint(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map;
        }
        
        // 对请求参数解码并转换为责任点地图采集对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        BoderPointFile boderPoint = (BoderPointFile) JSONObject.toBean(json, BoderPointFile.class);
        // 从缓存中获取用户信息
        String userToken = CommonUtils.getCookieValue(request, "userToken");
        Users user = (Users) redisTemplate.opsForValue().get(userToken);
        
        // 首先将当前责任区和责任点的地图采集状态全部设为未选中，然后再进行选择状态的操作
        BoderPointFile boder = new BoderPointFile();
        // 责任区ID
        boder.setAreaId(boderPoint.getAreaId());
        // 责任点ID
        boder.setPointId(boderPoint.getPointId());
        // 未选中
        boder.setApproveStatus(2);
        // 修改者ID
        boder.setUpdateId(user.getEmployeeId());
        // 修改时间
        boder.setUpdateAt(new Date());
        int flag = boderPointFileService.updateBoderPointFilesApprStatus(boder);
        
        // 更新选中状态
        BoderPointFile bean = new BoderPointFile();
        // id
        bean.setId(boderPoint.getId());
        // 选中
        bean.setApproveStatus(1);
        // 修改者ID
        bean.setUpdateId(user.getEmployeeId());
        // 修改时间
        bean.setUpdateAt(new Date());
        int opinion = boderPointFileService.updateBoderPointFilesApprStatus(bean);
        
        // 更新成功
        if (flag > 0 && opinion > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
        } else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
        }
        return map;
    }
    
    /**
     * 删除责任点地图采集信息
     * @param dutyAreaId 责任区ID
     */
    @RequestMapping(value="/boderPoint", method=RequestMethod.DELETE)
    @ResponseBody        
    public Map<String, Object> deleteDutyArea(Integer boderPointId, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        
        // 根据ID查询责任点地图采集信息
        BoderPointFile bean = new BoderPointFile();
        bean.setId(boderPointId);
        BoderPointFile boderPoint = boderPointFileService.getBoderPointFilesByBean(bean);
        // 如果该责任点地图采集信息存在，允许删除，否则不允许删除
        if (boderPoint != null) {
            // 责任点及考核点采集上报文件删除
            BoderPointFile boder = new BoderPointFile();
            boder.setId(boderPointId);
            // 从缓存中获取用户信息
            String userToken = CommonUtils.getCookieValue(request, "userToken");
            Users user = (Users) redisTemplate.opsForValue().get(userToken);
            // 更新者ID
            boder.setUpdateId(user.getEmployeeId());
            // 更新者时间
            boder.setUpdateAt(new Date());
            // 状态标识
            boder.setStatus(2);
            int opinion = boderPointFileService.updateBoderPointFiles(boder);
            
            // 责任点的边界点采集信息删除
            DutyPointGather gather = new DutyPointGather();
            // 责任点及考核点采集上报文件Id
            gather.setFile_id(boderPointId);
            // 更新者ID
            gather.setUpdateId(user.getEmployeeId());
            // 更新者时间
            gather.setUpdateAt(new Date());
            // 状态标识
            gather.setStatus(2);
            int flag = dutyPointGatherService.updateGatherPoint(gather);
            // 删除成功
            if (opinion > 0 && flag > 0) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
            } else {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.DELETE_FAIL_MSG);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.DELETE_BODER_POINT_FAIL);            
        }
        return map;
    }    
    
    /**
     * 责任点地图信息新增,将页面采集的责任点经纬度信息生成xml文件并且上传到服务器上同时将采集到的经纬度信息插入DB中
     * @param acceptContent 责任点地图采集信息
     */
    @RequestMapping(value="/boderPoint", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveBoderPointFileInfo(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        
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
        
        // 从缓存中获取用户信息
        String userToken = CommonUtils.getCookieValue(request, "userToken");
        Users user = (Users) redisTemplate.opsForValue().get(userToken);
        // 对请求参数解码并转换为责任点地图采集对象
        List<DutyPointGather> lstBoderPoint = new ArrayList<DutyPointGather>();
        JSONArray json = JSONArray.fromObject(acceptContent);
        for (int i = 0; i < json.size(); i++) {
            JSONObject object = JSONObject.fromObject(json.get(i));
            DutyPointGather gather = (DutyPointGather) JSONObject.toBean(object, DutyPointGather.class);
            // 创建者ID
            gather.setCreateId(user.getEmployeeId());
            // 创建时间
            gather.setCreateAt(new Date());
            // 状态标识
            gather.setStatus(1);
            lstBoderPoint.add(gather);
        }
        // 将采集的责任点地图信息生成xml
        Map<String, Object> mp = XmlUtils.writerXml(lstBoderPoint, 1, request);

        // 责任点地图采集文件信息
        BoderPointFile boderFile = new BoderPointFile();
        // 责任区ID
        boderFile.setAreaId((Integer) mp.get("areaId"));
        // 责任点ID
        boderFile.setPointId((Integer) mp.get("pointId"));
        // 责任点的采集的文件名
        boderFile.setFileFileName((String) mp.get("fileName"));
        // 责任点的区域的文件保存路径
        boderFile.setPointFilepath((String) mp.get("filePath"));
        // 选中状态(1.选中2.未选中)
        boderFile.setApproveStatus(2);
        // 创建者ID
        boderFile.setCreateId(user.getEmployeeId());
        // 创建时间
        boderFile.setCreateAt(new Date());
        // 状态标识
        boderFile.setStatus(1);
        // 状态标识(1.责任点2.考核点)
        boderFile.setPointType(1);
        // 插入边界点采集上报文件
        int opinion = boderPointFileService.insertBoderPointFile(boderFile);

        // 插入责任点的边界点采集信息
        for (DutyPointGather gather : lstBoderPoint) {
            // 边界点采集上报文件的ID
            gather.setFile_id(boderFile.getId());
        }
        int flag = dutyPointGatherService.insertGatherPoint(lstBoderPoint);
        
        // 生成xml
        if(opinion > 0 && flag > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("fileId", boderFile.getId());
        }else{
            map.put("result", ConstantUtil.FAIL);
        }
        return map;
    }    
    
    /**
     * 根据责任点地图信息中xml文件名称解析服务器中的xml，获取经纬度
     * @param acceptContent 责任点地图采集信息
     */
    @RequestMapping(value="/boderPointLatLng", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getBoderPointLatLng(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",ConstantUtil.SELECT_FAIL_MSG);
            return map;
        }
        
        // 对请求参数解码并转换为责任点地图采集对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        BoderPointFile boder = (BoderPointFile) JSONObject.toBean(json, BoderPointFile.class);
        
        // 从缓存中获取用户信息
        String userToken = CommonUtils.getCookieValue(request, "userToken");
        Users user = (Users) redisTemplate.opsForValue().get(userToken);
        // 获取经纬度信息
        List<DutyPointGather> dutyPointGathers = Dom4jForDutyAreaUtil.getDutyAreaPoints(
                boder.getPointFilepath(), user.getEmployeeId(), boder.getId());
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", dutyPointGathers.size());
        map.put("rows", dutyPointGathers);
        return map;
    }    
    
    /**
     * 根据责任区ID查询所有责任点的地图信息
     * @param acceptContent 责任点地图采集信息
     */
    @RequestMapping(value="/boderPointLatLngByArea/{areaId}", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getBoderPointLatLngByAreaId(@PathVariable(value="areaId") Integer areaId, HttpServletRequest request, 
            HttpServletResponse response){
        
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        
        // 根据ID查询责任点地图采集信息
        BoderPointFile bean = new BoderPointFile();
        bean.setAreaId(areaId);
        List<BoderPointFile> boderPoint = boderPointFileService.getBoderPointFilesListByBean(bean);
        for (BoderPointFile boder : boderPoint) {
            // 根据采集上报文件ID责任点的边界点采集信息
            DutyPointGather gather = new DutyPointGather();
            gather.setFile_id(boder.getId());
            List<DutyPointGather> lstGather = dutyPointGatherService.getGatherPointList(gather);
            boder.setLstDutyPointGather(lstGather);
        }
        // 设置返回数据
        if (boderPoint.size() > 0) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
            map.put("total", boderPoint.size());
            map.put("rows", boderPoint);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.SELECT_FAIL_MSG);
        }
        return map;
    }    
}
