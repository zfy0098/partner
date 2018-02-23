package com.partner.utils;


/**
 * Created by hadoop on 2018/2/22.
 *
 * @author hadoop
 */
public class EmailUtil {




    public static void sendMail(String title , String context , String[] files){

        System.out.println("开始发送邮件");

        MailBean mb = new MailBean();
        // 设置SMTP主机(163)，若用126，则设为：smtp.126.com
        mb.setHost("smtp.qiye.163.com");
        // 设置发件人邮箱的用户名
        mb.setUsername("zhoufangyu@ronghuijinfubj.com");
        // 设置发件人邮箱的密码，需将*号改成正确的密码
        mb.setPassword("siyanlv3@");
        // 设置发件人的邮箱
        mb.setFrom("zhoufangyu@ronghuijinfubj.com");

        // 设置收件人的邮箱
        mb.setTo("qingsuan@ronghuijinfubj.com");
        mb.setTo("zhoufangyu@ronghuijinfubj.com");

        //  设置抄送人
        mb.setCopyColumn("ronghui@ronghuijinfubj.com");
        // 设置邮件的主题
        mb.setSubject(title);
        // 设置邮件的正文
        mb.setContent(context);

        //  添加附件
        if(files!=null){
            for (String file : files) {
                mb.attachFile(file);
            }
        }

        SendMail sm = new SendMail();
        System.out.println("正在发送邮件...");

        // 发送邮件
        if (sm.sendMail(mb)){
            System.out.println("发送成功!");
        }else{
            System.out.println("发送失败!");
        }

    }
}
