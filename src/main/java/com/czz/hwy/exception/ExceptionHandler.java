package com.czz.hwy.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver{
    Log log = LogFactory.getLog(this.getClass().getName());
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  
            Exception ex) {  
        Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
          
        // 根据不同错误转向不同页面  
        if(ex instanceof IllegalArgumentException) {  
            log.info(ex);
            //return new ModelAndView("error/exception", model);
            return null;  
        }else {  
            /*return new ModelAndView("error/exception", model);  */
            log.info(ex);
            return null;  
        }
    }  
}
