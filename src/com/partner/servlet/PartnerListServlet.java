package com.partner.servlet;

import com.partner.service.PartnerService; 
import com.partner.utils.UtilsConstant;
import sun.plugin.com.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2017/12/22.
 *
 * @author hadoop
 */
@WebServlet("/list")
public class PartnerListServlet extends HttpServlet {

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
        String agentname = request.getParameter("agentname");
        String agentno = request.getParameter("agentno");


        PartnerService partnerService = new PartnerService();
        String id = (String) request.getAttribute("id");

        if(!UtilsConstant.strIsEmpty(id)){
            Map<String,Object> map = partnerService.getPartnerInfo(new Object[]{id});
            partnername = map.get("partnerName").toString();
        }

        if(UtilsConstant.strIsEmpty(partnername)){
        	partnername = "";
        }
        
        if(UtilsConstant.strIsEmpty(agentname)){
        	agentname = "";
        }
        
        if(UtilsConstant.strIsEmpty(agentno)){
        	agentno = "";
        }

        List<Map<String,Object>> list = partnerService.partnerList(new Object[]{partnername ,  agentname , agentno  });
        request.setAttribute("list" , list);
        request.setAttribute("partnername" , partnername);
        request.setAttribute("agentname" , agentname);
        request.setAttribute("agentno" , agentno);
        request.getRequestDispatcher("/partnerList.jsp").forward(request , resp);
    }
}
