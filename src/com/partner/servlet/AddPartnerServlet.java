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


@WebServlet("/add")
public class AddPartnerServlet extends HttpServlet {

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

        String partnername = request.getParameter("partnername");
        String agentName = request.getParameter("agentName");
        String agentNO = request.getParameter("agentNO");
        String bankID = request.getParameter("bankID");
        String t0rate = request.getParameter("t0rate");
        String t1rate = request.getParameter("t1rate");
        String active = request.getParameter("active");
        String ratio = request.getParameter("ratio");

        if(partnername!= null){
            partnername = partnername.trim();
        }
        if(agentName != null){
            agentName = agentName.trim();
        }


        PartnerService partnerService = new PartnerService();
        
        String agentid = partnerService.getAgentID(agentNO);
        
        if(agentid == null){
        	 resp.sendRedirect("/partner/list");
        	 return ;
        }
        
    	partnerService.save(new Object[]{partnername ,agentid ,agentName , agentNO , 
    			bankID ,t0rate ,t1rate , active ,ratio});
        resp.sendRedirect("/partner/list");
    }

}
