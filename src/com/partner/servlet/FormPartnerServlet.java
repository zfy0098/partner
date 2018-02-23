package com.partner.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.partner.service.PartnerService;
/**
 *  @author hadoop
 */

@WebServlet("/form")
public class FormPartnerServlet extends HttpServlet {

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
        
        PartnerService partnerService = new PartnerService();
        Map<String,Object> map = partnerService.getPartnerInfo(new Object[]{id});
        request.setAttribute("partner", map);
        request.getRequestDispatcher("/partner.jsp").forward(request , resp);
    }
}
