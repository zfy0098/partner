package com.partner.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * Created by hadoop on 2018/2/22.
 *
 * @author hadoop
 */
public class TimerOnlinePartnerProfit implements Runnable {



    private String selectTime ;

    private Lock lock;



    public TimerOnlinePartnerProfit(String selectTime ,  Lock lock ){
        this.selectTime = selectTime;
        this.lock = lock;
    }


    @Override
    public void run(){

        try {
            OnlinePartnerProfitEmail onlinePartnerProfitEmail =  new OnlinePartnerProfitEmail();
            onlinePartnerProfitEmail.init(selectTime , lock);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
