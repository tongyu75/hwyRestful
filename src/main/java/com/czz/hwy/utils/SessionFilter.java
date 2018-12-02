package com.czz.hwy.utils;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SessionFilter implements Filter {



    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        HttpServletRequest req = (HttpServletRequest) request;
/*        SysUsers user = null;
        if (user == null) {
            String path = request.getServletContext().getContextPath();
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('请重新登录！')");
            out.println("window.open ('" + path + "/user/toLogin','_top')");
            out.println("</script>");
            out.println("</html>");
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }*/
    }

    public void init(FilterConfig filterConfig) throws ServletException {}

}
