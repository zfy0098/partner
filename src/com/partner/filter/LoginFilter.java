package com.partner.filter;

/**
 * Created by hadoop on 2017/12/29.
 *
 * @author hadoop
 */

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoginFilter implements Filter {


    public static final String[] path = {"/login" , "/login.jsp"};


    public static List<String> prefixIgnores  = new ArrayList<>();

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //将ServletRequest转换成HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse)response;
        //获取ServletContext
        ServletContext context = req.getSession().getServletContext();
        String requestPath = req.getServletPath();

        if(canIgnore(req)){
            chain.doFilter(request, response);
            return;
        }

        Object obj = req.getSession().getAttribute("user");
        if(obj == null){
            boolean isOk = true;
            for (int i = 0; i < path.length; i++) {
                if(requestPath.equals(path[i])){
                    isOk = false;
                    break;
                }
            }
            if(isOk){
                resp.sendRedirect("/partner/login.jsp");
                return;
            }
        }
        chain.doFilter(request, response);
    }


    private boolean canIgnore(HttpServletRequest request) {
        String url = request.getRequestURI();
        for (String ignore : prefixIgnores) {
            if (url.contains(ignore)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void init(FilterConfig config) throws ServletException {
        String ignoresParam = config.getInitParameter("ignores");
        String[] ignoreArray = ignoresParam.split(",");
        for (String s : ignoreArray) {
            prefixIgnores.add(s);
        }
    }
}
