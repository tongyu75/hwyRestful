package com.czz.hwy.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {
    
    // 天
    static SimpleDateFormat sdfDD = new SimpleDateFormat("dd");
    
    /**
     * 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
     */    
    public static void setAccessOrigin(HttpServletRequest request, HttpServletResponse response, 
            AccessOrigin accessOrigin){
        // 获取客户端服务器的地址（访问者访问客户端的服务器地址，不是访问者的地址）
        String originUrl = request.getHeader("origin");
        response.addHeader("Access-Control-Allow-Origin", originUrl);
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }

    /**
    * 对从url穿过来的中文参数是进行解码，防止乱码
    * @param param 解码内容
    */          
    public static String getDecode(String param)
    {
        String result = StringUtils.EMPTY;
        try {
            result = java.net.URLDecoder.decode(param,"UTF-8");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      return result; 
    }
    
   /**
    * 解决get请求参数中文乱码问题 ，2016-09-27
    */
    public static String getDecodeParam(String param){
    	try {
    		param = new String(param.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return param;
    }
   
    
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }
     
    /**
     * RSA非对称密钥加密
     * @param param 加密内容
     * @throws Exception 
     */          
    public static String getEncryptByPublicKey(String param) {
        HashMap<String, Object> map = null;
        try {
            map = RSAUtils.getKeys();
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }  
        //生成公钥和私钥  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
        //模  
        String modulus = publicKey.getModulus().toString();  
        //公钥指数  
        String public_exponent = publicKey.getPublicExponent().toString();  
        //使用模和指数生成公钥和私钥  
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);  
        //加密后的密文  
        String mi = null;
        try {
            mi = RSAUtils.encryptByPublicKey(param, pubKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return mi;
    }
      
    /**
     * RSA非对称密钥解密
     * @param param 解密内容
     * @throws Exception 
     */          
    public static String getDecryptByPrivateKey(String param) {
        HashMap<String, Object> map = null;
        try {
            map = RSAUtils.getKeys();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        //生成公钥和私钥  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
        //模  
        String modulus = publicKey.getModulus().toString();  
        //私钥指数  
        String private_exponent = privateKey.getPrivateExponent().toString();  
        //使用模和指数生成公钥和私钥  
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);    
        //解密后的明文  
        String ming = null;
        try {
            ming = RSAUtils.decryptByPrivateKey(param, priKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
            return ming; 
    }      
       
    /**
     * 获取Cookie值
     * @param param 解密内容
     * @throws Exception 
     */          
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        String cookieValue = StringUtils.EMPTY;
        Cookie[] cookie = request.getCookies();
        for (Cookie c : cookie) {
            if (cookieName.equals(c.getName())) {
                cookieValue =  c.getValue();
                break;
            }
        }
        return cookieValue;
    }             
    
    /** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
     * 
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
     * 
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
     * 192.168.1.100 
     * 
     * 用户真实IP为： 192.168.1.110 
     * 
     * @param request 
     * @return 
     */ 
    public static String getIpAddress(HttpServletRequest request) { 
      String ip = request.getHeader("x-forwarded-for"); 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getHeader("Proxy-Client-IP"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getHeader("WL-Proxy-Client-IP"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getHeader("HTTP_CLIENT_IP"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
      } 
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getRemoteAddr(); 
      } 
      return "http://" + ip; 
    }     
    
    /**
     * 设置默认七天内容，然后调用者根据实际情况设置值
     */              
    public static List<Map<String, Object>> getDefautDay() {
        List<Map<String, Object>> lstMp = new ArrayList<Map<String, Object>>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 前七天
        Map<String, Object> map1 = new LinkedHashMap<String, Object>();
        Calendar calendar7 = Calendar.getInstance();
        calendar7.setTime(new Date());
        calendar7.add(Calendar.DAY_OF_MONTH, -7);
        String day7 = sdfDD.format(calendar7.getTime());
        map1.put(day7, 0);
        lstMp.add(map1);
        
        // 前六天
        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        Calendar calendar6 = Calendar.getInstance();
        calendar6.setTime(new Date());
        calendar6.add(Calendar.DAY_OF_MONTH, -6);
        String day6 = sdfDD.format(calendar6.getTime());
        map2.put(day6, 0);
        lstMp.add(map2);
        
        // 前五天
        Map<String, Object> map3 = new LinkedHashMap<String, Object>();
        Calendar calendar5 = Calendar.getInstance();
        calendar5.setTime(new Date());
        calendar5.add(Calendar.DAY_OF_MONTH, -5);
        String day5 = sdfDD.format(calendar5.getTime());
        map3.put(day5, 0);
        lstMp.add(map3);
        
        // 前四天
        Map<String, Object> map4 = new LinkedHashMap<String, Object>();
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(new Date());
        calendar4.add(Calendar.DAY_OF_MONTH, -4);
        String day4 = sdfDD.format(calendar4.getTime());
        map4.put(day4, 0);
        lstMp.add(map4);
        
        // 前三天
        Map<String, Object> map5 = new LinkedHashMap<String, Object>();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(new Date());
        calendar3.add(Calendar.DAY_OF_MONTH, -3);
        String day3 = sdfDD.format(calendar3.getTime());
        map5.put(day3, 0);
        lstMp.add(map5);
        
        // 前二天
        Map<String, Object> map6 = new LinkedHashMap<String, Object>();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.add(Calendar.DAY_OF_MONTH, -2);
        String day2 = sdfDD.format(calendar2.getTime());
        map6.put(day2, 0);
        lstMp.add(map6);
        
        // 前一天
        Map<String, Object> map7 = new LinkedHashMap<String, Object>();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String day1 = sdfDD.format(calendar.getTime());
        map7.put(day1, 0);
        lstMp.add(map7);
        
        return lstMp;
    }    

    /**
     * 将Map转换为JSON字符串，2017-04-25
     * @param map
     * @return
     */
    public static String JsonObjectToStringForMap(Map<String, Object> map){
    	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
    }
}
