package com.partner.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hadoop on 2017/12/29.
 *
 * @author hadoop
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet{


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        resp.setCharacterEncoding("utf-8");


        String userName = request.getParameter("username");
        String password = request.getParameter("password");


        System.out.println("收到的用户名:" + userName);
        System.out.println("收到的密码：" + password);



        if("admin".equals(userName)&&"admin".equals(password)){
            resp.getWriter().print("0000");

            request.getSession().setAttribute("user" , "admin");

        }else{
            resp.getWriter().print("登录信息不正确");
        }
    }
}
