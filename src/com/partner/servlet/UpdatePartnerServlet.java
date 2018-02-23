package com.partner.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.partner.service.PartnerService;
/**
 *  @author hadoop
 */

@WebServlet("/update")
public class UpdatePartnerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req , resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        String id = request.getParameter("id");
        String t0rate = request.getParameter("t0rate");
        String t1rate = request.getParameter("t1rate");
        String active = request.getParameter("active");
        String ratio = request.getParameter("ratio");



        String partnername = request.getParameter("partnername");
        
        
        PartnerService partnerService = new PartnerService();
        partnerService.update(new Object[]{ t0rate  , t1rate  , active , ratio, id});

        request.setAttribute("id" , id);
        request.setAttribute("partnername"  , partnername);
        request.getRequestDispatcher("/list").forward(request , resp);
//        resp.sendRedirect("/partner/list");
    }
}
