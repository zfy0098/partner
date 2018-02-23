package com.partner.tag;

/**
 * Created by hadoop on 2018/1/4.
 *
 * @author hadoop
 */
public class BankIDTag {

    public static String getBankIDName(String bankID){
        String name ;
        switch (bankID){
            case "NET":
                name = "在线网银支付";
                break;
            case "OnlineKuaiPay":
                name = "普通快捷（无积分）";
                break;
            case "WX":
                name = "微信支付";
                break;
            case "Alipay":
                name = "支付宝支付";
                break;
            case "QQ":
                name = "QQ支付";
                break;
            case "UnionPay":
                name = "银联二维码";
                break;
            case "DIRKUAI":
                name = "无短快捷（带积分）";
                break;
            case "KUAI2B":
                name = "普通快捷（带积分）";
                break;
            case "KUAI2C":
                name = "小额快捷（无积分）";
                break;
            case "ESKUAI":
                name = "特殊快捷（对公结算）";
                break;
            case "TKUAI":
                name = "大额快捷(无积分)";
                break;
            case "UNIONPAY_QRCODE_LARGE":
                name = "银联二维码(大额)";
                break;
            case "MKUAI":
                name = "同名快捷（无积分）";
                break;
            default:
                    name = "未知";
                    break;
        }
        return name;
    }


    public static String activeProfit(String active){
        String isOk = "计算";
        if(!"1".equals(active)){
            isOk = "不计算";
        }
        return isOk;
    }
}
