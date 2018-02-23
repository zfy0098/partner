package com.partner.servlet;

import com.partner.utils.TimerOnlinePartnerProfit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  @author hadoop
 */


@WebServlet("/download")
public class DownLoadProfitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


	private Lock lock = new ReentrantLock();


	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
//      resp.setContentType("multipart/form-data");
//    	resp.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(selectTime + ".zip", "UTF-8"));

		String selectTime = request.getParameter("selectTime");

    	if(selectTime == null || selectTime.trim().isEmpty()){
    		return ;
    	}

		boolean flag = false;

		try {
			flag = lock.tryLock();
			if(flag){
				System.out.println("获取锁成功");

				ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);

				newScheduledThreadPool.schedule(new TimerOnlinePartnerProfit(selectTime , lock) , 1  , TimeUnit.MILLISECONDS);

				resp.getWriter().print("操作成功，注意查收邮件");
			}else{

				System.out.println("获取锁失败");
				resp.getWriter().print("当前有任务在执行，请稍后再试");
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(flag){
				lock.unlock();
			}
		}





//			OutputStream ops = null;
//			FileInputStream fis = null;
//			byte[] buffer = new byte[8192];
//			int bytesRead = 0;
//
//			try {
//				ops = resp.getOutputStream();
//				fis = new FileInputStream("/opt/sh/hehuoren/" + selectTime + ".zip");
//				while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
//					ops.write(buffer, 0, bytesRead);
//				}
//				ops.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					if (fis != null) {
//						fis.close();
//					}
//					if (ops != null) {
//						ops.close();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
    }
}
