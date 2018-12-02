package com.czz.hwy.mqtt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ReadFile {
	
	public static Properties properties = null;
	
	public String  getPath(){
		String filePath= this.getClass().getClassLoader().getResource("/").getPath(); 
			return filePath;
	}
	public static Properties getProperties() {
		if(properties == null) {
			InputStream in = null;
			try {
				HttpServletRequest request;
				request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();   
				String ctxPath = request.getSession().getServletContext()
						.getRealPath("/");
				in = new BufferedInputStream(new FileInputStream(ctxPath+"/WEB-INF/classes/mqtt.properties"));
				properties = new Properties();
				properties.load(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}
