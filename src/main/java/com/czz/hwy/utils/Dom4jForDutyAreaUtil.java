package com.czz.hwy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.czz.hwy.bean.area.DutyPointGather;
import com.czz.hwy.bean.area.EvalPoint;

/**
 * 继续责任点地图信息xml文件
 * @author 佟士儒
 *
 */
//解析责任区域选择点的xml文件
public class Dom4jForDutyAreaUtil {
    /**
     * 解析xml文件的责任点边界点集合信息
     * @author 佟士儒
     * @param fileName,createId
     * @return List
     */
    public static List<DutyPointGather> getDutyAreaPoints(String fileName, int createId, int fileId){
        
        File xmlFile = null;
        //获取xml文件
        xmlFile = new File(fileName);
        
        List<DutyPointGather> dutyPointGathers = null;
        
        Date date = new Date();
        
        if(xmlFile.exists()){//xml文件存在否
            //初始化责任点之边界点集合
            dutyPointGathers = new ArrayList<DutyPointGather>();
            
            SAXReader saxReader = new SAXReader();
            
            Document document;
            try {
                document = saxReader.read(xmlFile);
              //获取责任区域id
                Element element = (Element)document.selectSingleNode("/area");
                
                int area_id = Integer.parseInt(element.attributeValue("id"));
                
                List<Element> pointElements = document.selectNodes("/area/point");
                //判断获取的子节点是否存在
                if(pointElements!=null&&pointElements.size()>0){
                    
                    //迭代责任点信息
                    for(Element pEs:pointElements){
                        
                        //获取责任点信息
                        int point_id = Integer.parseInt(pEs.attributeValue("id"));
                        
                        DutyPointGather dPointGather = null;
                        //获取责任点的边界点信息
                        List<Element> pointGatherElements = pEs.elements("areaPoint");
                        //判断获取的边界点的属性节点是否存在
                        if(pointGatherElements!=null&&pointGatherElements.size()>0){
                            for(Element pGe:pointGatherElements){
                                //初始化
                                dPointGather = new DutyPointGather();
                                //获取边界点顺序
                                int borderPoint_order = Integer.parseInt(pGe.element("id").getText());
                                //获取边界点名称
                                String borderPoint_name = pGe.elementText("name");
                                //获取边界点纬度
                                double borderPoint_lat = Double.parseDouble(pGe.elementText("lat"));
                                //获取边界点经度
                                double borderPoint_lng = Double.parseDouble(pGe.elementText("lng"));
                                //设置边界点属性值
                                dPointGather.setAreaId(area_id);
                                
                                dPointGather.setPointId(point_id);
                                
                                dPointGather.setBorderpointOrder(borderPoint_order);
                                
                                dPointGather.setBorderpointName(borderPoint_name);
                                
                                dPointGather.setBorderpointLat(borderPoint_lat);
                                
                                dPointGather.setBorderpointLng(borderPoint_lng);
                                
                                //dPointGather.setOffset(0.0);
                                
                                dPointGather.setStatus(1);
                                
                                dPointGather.setCreateAt(date);
                                
                                dPointGather.setCreateId(createId);
                                
                                dPointGather.setFile_id(fileId);
                                
                                dutyPointGathers.add(dPointGather);
                                
                            }
                        }
                    }
                }
            } catch (org.dom4j.DocumentException e) {
                System.out.println("xml 文件解析失败");
                e.printStackTrace();
            }
        }else{
            
            System.out.println("文件已不存在");
        }
        return dutyPointGathers;
    }
    /**
     * 解析xml文件的责任区域areaId和责任点pointId,边界点采集文件及考核点采集文件
     * @author 佟士儒
     * @param fileName
     * @return BoderPointFile
     */
    public static Map<String,Integer> getDutyAreaPointInfo(String filePath){
        
        File xmlFile = null;
        
        xmlFile = new File(filePath);
        
        Map<String,Integer> map = null;
        
        //判断上文件是否存在
        if(xmlFile.exists()){
            
            map = new HashMap<String,Integer>();
            
            SAXReader reader = new SAXReader();
            
            Document document;
            try {
                document = reader.read(xmlFile);
                //获取责任区域元素
                Element eArea = (Element)document.selectSingleNode("/area");
                //获取责任点元素
                Element ePoint = null;
                
                int areaId=0;
                
                int pointId=0;
                
                if(eArea!=null){
                    
                    areaId = Integer.parseInt(eArea.attributeValue("id"));
                    
                    ePoint = eArea.element("point");
                    
                    if(ePoint!=null){
                        
                        pointId = Integer.parseInt(ePoint.attributeValue("id"));
                    }
                    
                    map.put("areaId", areaId);
                    
                    map.put("pointId", pointId);
                    
                }
            } catch (org.dom4j.DocumentException e) {
                System.out.println("xml 文件解析失败");
                e.printStackTrace();
            }            
        }else{
            
            System.out.println("文件不存在！");
        }
        return map;
    }
    /**
     * 拼接经纬度字符串
     * @author 佟士儒
     * @param dutyPointGathers
     * @return String
     */
    public static String getLngLatStr(List<DutyPointGather> dutyPointGathers){
        
        StringBuffer sb = new StringBuffer();
        //判断集合是否为空
        if(dutyPointGathers!=null&&dutyPointGathers.size()>0){
            
            for(DutyPointGather temp:dutyPointGathers){
                
                sb.append(temp.getBorderpointLng()).append(", ").append(temp.getBorderpointLat()).append(";");
            }
        }
        
        return sb.toString();
    }
    /**
     * 解析考核点成json
     * @author 佟士儒
     * @param evalPoints
     * @return json
     */
    public static List<Map<String,Double>> getLngLatMap(List<EvalPoint> evalPoints){
        
        List<Map<String,Double>> list = null;
        
        if(evalPoints!=null&&evalPoints.size()>0){
            
            list = new ArrayList<Map<String,Double>>();
            
            Map<String,Double> map=null;
            
            for(EvalPoint e:evalPoints){
                
                map = new HashMap<String,Double>();
                
                map.put("lng", e.getCheckpoint_lng());
                
                map.put("lat", e.getCheckpoint_lat());
                
                list.add(map);
            }
        }
        
        return list;
    }
    /**
     * 解析考核点文件
     * @author 佟士儒
     */
    public static List<EvalPoint> getEvalPoints(String fileName,int createId,int file_id){
        //定义集合用于返回考核点的数据集
        List<EvalPoint> evalPoints = null; 
        
        File file = new File(fileName);
        
        //判断文件是否存在
        if(file.exists()){
            SAXReader reader = new SAXReader();
            
            Document document;
            try {
                document = reader.read(file);
              //责任区域id
                int areaId=0;
                //责任点id
                int pointId=0;
                //获取area节点元素
                Element eArea = (Element)document.selectSingleNode("/area");
                //判断节点是否存在
                if(eArea!=null){
                    //实例化返回集合
                    evalPoints = new ArrayList<EvalPoint>();
                    
                    areaId = Integer.parseInt(eArea.attributeValue("id"));
                    //获取point节点
                    Element ePoint = eArea.element("point");
                    
                    pointId = Integer.parseInt(ePoint.attributeValue("id"));
                    
                    //获取areaPoint所有节点元素
                    List<Element> areaPoints = ePoint.elements("areaPoint");
                    
                    EvalPoint evalPoint = null;
                    
                    //迭代areaPoint集合并解析
                    for(Element e:areaPoints){
                        
                        evalPoint =new EvalPoint();
                        //获取点序号
                        int checkPointOrder = Integer.parseInt(e.elementText("id"));
                        //获取纬度
                        double lat = Double.parseDouble(e.elementText("lat"));
                        //获取经度
                        double lng = Double.parseDouble(e.elementText("lng"));
                        //获取考核点名字
                        String checkPointName = e.elementText("name");
                        //是否必达
                        int isArray = "true".equals(e.elementText("isArray"))?1:2;
                        //是否考核
                        int isCheck = "true".equals(e.elementText("isCheck"))?1:2;
                        //停留时间
                        double stopTime = Double.parseDouble(e.elementText("stopTime"));
                        //预留
                        String remain = e.elementText("remain");
                        
                        evalPoint.setArea_id(areaId);
                        
                        evalPoint.setPoint_id(pointId);
                        
                        evalPoint.setCheckpoint_order(checkPointOrder);
                        
                        evalPoint.setCheckpoint_lat(lat);
                        
                        evalPoint.setCheckpoint_lng(lng);
                        
                        evalPoint.setCheckpoint_name(checkPointName);
                        
                        evalPoint.setIsarray(isArray);
                        
                        evalPoint.setIscheck(isCheck);
                        
                        evalPoint.setStoptime(stopTime);
                        
                        evalPoint.setCreate_at(new Date());
                        
                        evalPoint.setCreate_id(createId);
                        
                        evalPoint.setRemain(remain);
                        
                        evalPoint.setStatus(1);
                        
                        evalPoint.setFile_id(file_id);
                        
                        evalPoints.add(evalPoint);
                    }
                    
                }
            } catch (org.dom4j.DocumentException e1) {
                System.out.println("xml 文件解析失败！");
                e1.printStackTrace();
            }
        }else{
            
            System.out.println("文件已不存在");
        }
        return evalPoints;
    }
}
