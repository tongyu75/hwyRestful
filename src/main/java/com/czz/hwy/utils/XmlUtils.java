package com.czz.hwy.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.czz.hwy.bean.area.DutyPointGather;

public class XmlUtils {
    
    
    /**
     * 写xml文件
     * 
     * @param dutyPoint 采集的地图信息
     * @param type 采集类型（1.责任点 2边界点）
     */
    public static Map<String, Object> writerXml(List<DutyPointGather> dutyPoint, int type, HttpServletRequest request) {
        
        // 返回值
        Map<String, Object> map = new HashMap<String, Object>();
        // 年月如时分秒毫秒
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
        String time =  formatter.format(new Date());
        
        // 文件路径
        String path = StringUtils.EMPTY;
        // 采集类型（1.责任点 2边界点）
        if(type==1){
            path = request.getServletContext().getRealPath("/public/point");
        }else{
            path = request.getServletContext().getRealPath("/public/eval");
        }
        // 责任区ID
        String areaId = String.valueOf(dutyPoint.get(0).getAreaId());
        // 设置文件路径
        map.put("areaId", dutyPoint.get(0).getAreaId());
        // 责任点ID
        String pointId = String.valueOf(dutyPoint.get(0).getPointId());
        // 设置文件路径
        map.put("pointId", dutyPoint.get(0).getPointId());
        File dir = new File(path);
        // 创建文件上传目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 上传的文件（每次上传的文件都加时间戳当前时间的毫秒值，文件都是新增操作）
        File newFile = new File(path + "/" + areaId + '_' + pointId + "_" + time + "_" + new Date().getTime() + ".xml");
        // 设置文件名
        map.put("fileName", areaId + '_' + pointId + "_" + time + ".xml");
        // 设置文件路径
        map.put("filePath", newFile.getPath());
        try {
            // 将地图信息写到xml中
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            // document.setXmlStandalone(false);
            Element area = document.createElement("area"); 
            // xml中责任区ID
            area.setAttribute("id", areaId);
            // xml中责任区名称
            area.setAttribute("name", dutyPoint.get(0).getAreaName());
            document.appendChild(area); 
            Element point = document.createElement("point"); 
            // xml中责任点ID
            point.setAttribute("id", pointId);
            // xml中责任点名称
            point.setAttribute("name", dutyPoint.get(0).getPointName());        
            // xml中point属性的ID
            point.setAttribute("bjid", time);        
            // 遍历采集到的经纬度信息
            for (DutyPointGather bean : dutyPoint) {
                Element areaPoint = document.createElement("areaPoint");
                // xml中采集顺序ID
                Element id = document.createElement("id");             
                id.appendChild(document.createTextNode(String.valueOf(bean.getBorderpointOrder())));
                areaPoint.appendChild(id);
                // xml中纬度
                Element lat = document.createElement("lat");             
                lat.appendChild(document.createTextNode(String.valueOf(bean.getBorderpointLat())));
                areaPoint.appendChild(lat);
                // xml中经度
                Element lng = document.createElement("lng");             
                lng.appendChild(document.createTextNode(String.valueOf(bean.getBorderpointLng())));            
                areaPoint.appendChild(lng);
                // xml中名称
                Element name = document.createElement("name");             
                name.appendChild(document.createTextNode(bean.getBorderpointName()));
                areaPoint.appendChild(name);
                point.appendChild(areaPoint);
            }
            area.appendChild(point);
            TransformerFactory tf = TransformerFactory.newInstance();
                try {
                    Transformer transformer = tf.newTransformer();
                    DOMSource source = new DOMSource(document);
                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  
                    try {
                        PrintWriter pw = new PrintWriter(new FileOutputStream(newFile));
                        StreamResult result = new StreamResult(pw);
                        // 生成XML
                        transformer.transform(source, result);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        map.put("result", false);
                    } catch (TransformerException e) {
                        e.printStackTrace();
                        map.put("result", false);
                    }
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                    map.put("result", false);
                }
                map.put("result", true);
 
        } catch (ParserConfigurationException e1) {
            map.put("result", false);
        }
        return map;
    }
}
