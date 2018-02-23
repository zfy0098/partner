package com.partner.utils;


import com.partner.dao.BaseDao;
import com.partner.entity.Partner;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 *
 *   优化查询
 *
 *  @author hadoop
 */
public class OnlinePartnerProfitEmail extends BaseDao {


	public void init(Lock lock) throws Exception{


		System.out.println(Thread.currentThread());

		System.out.println("进入init 方法 ， 执行睡眠操作");
		Thread.sleep(10000);
		System.out.println("睡眠停止 继续执行");

		lock.unlock();

	}

	
	public void init(String selectTime , Lock lock) throws Exception {


		// 保存excel 文件路径
		String pathName = "/opt/sh/hehuoren/" + selectTime + "/";

		List<Object[]> proftlist = new ArrayList<>();

		// 每个excel 文件的 sheet 页， 每个sheet 对应每个合伙人的 一个代理商 每个excel 文件对应一个合伙人
		List<String> sheetName = new ArrayList<String>();

		// 获取合伙人集合  and  partnerName='贵州霸王商贸有限公司'
		String sql = "select * from z_partner where active=1  GROUP BY partnerName";
		List<Map<String,Object>> partnerList = queryForList(sql ,  null);
		
		String fileName = "合伙人分润";

		// 遍历合伙人集合
		for (int j = 0; j < partnerList.size(); j++) {
			
			Map<String,Object> map = partnerList.get(j);
			
			Partner p = UtilsConstant.mapToBean(map, Partner.class);


			//  获取 合伙人下面代理商的集合
			String agentListSQL = "select * from z_partner where active=1 and partnerName=?  GROUP  by agentid ";
			List<Map<String, Object>> agentList = queryForList(agentListSQL , new Object[]{p.getPartnerName()});



			// 遍历代理商信息
			for (int z = 0; z < agentList.size(); z++) {

				p = UtilsConstant.mapToBean(agentList.get(z) , Partner.class);

				sheetName.add(p.getPartnerName() + "---" + p.getAgentName());

				// 存放 sheet页数据
//				List<Object[]> list = new ArrayList<Object[]>();


				List<String> dateList = getBetweenDates(selectTime,selectTime);

				// 获取代理商交易类型集合
				String bankidsql = "select * from z_partner where partnerName=? and agentid=? and active=1";
				List<Map<String,Object>> bankidlist = queryForList(bankidsql, new Object[]{p.getPartnerName() , p.getAgentid()});

				// 遍历代理商交易类型
				for (int k = 0; k < bankidlist.size(); k++) {

					Partner partner = UtilsConstant.mapToBean(bankidlist.get(k) , Partner.class);

					String trade = "无卡快捷";
					String proid = "2";

					if("WX".equals(partner.getBankID())){
						trade = "微信";
						proid = "4";
					}else if("Alipay".equals(partner.getBankID())){
						trade = "支付宝";
						proid = "5";

					}else if("UnionPay".equals(partner.getBankID())){
						trade = "银联二维码";
						proid = "7";
					}else if("NET".equals(partner.getBankID())){
						trade = "网关";
						proid = "1";
					}else if("QQ".equals(partner.getBankID())){
						trade = "QQ支付";
						proid = "6";
					}else if("TKUAI".equals(partner.getBankID())){
						trade = "普通快捷（大额无积分）";
						proid = "17";
					}else if("KUAI2B".equals(partner.getBankID())){
						trade = "普通快捷有积分";
						proid = "14";
					}else if("DIRKUAI".equals(partner.getBankID())){
						proid = "13";
						trade = "无短快捷（带积分）";
					}else if("KUAI2B".equals(partner.getBankID())){
						proid = "14";
						trade = "普通快捷有积分";
					}else if("UNIONPAY_QRCODE_LARGE".equals(partner.getBankID())){
						proid = "18";
						trade = "银联二维码大额";
					}

					//  and agentFeeType='ACTIVE'
					String agentFeeSQL = "select * from tbl_pay_agent_fee where agentId='"+partner.getAgentid()+"' and product_id='"+proid+"' ";
					Map<String,Object> agentFee = queryForMap(agentFeeSQL , null);

					if(agentFee == null){
						proftlist.add(new Object[]{partner.getPartnerName() , partner.getAgentName() , "" ,"无代理商费率" , trade , "T0"});
						continue;
					}

					partner.setAgentT1rate(String.valueOf(AmountUtil.mul(agentFee.get("t1Fee").toString() , "1000")));
					partner.setAgentT0rate(String.valueOf(AmountUtil.mul(agentFee.get("t0Fee").toString() , "1000")));


					for (int i = 0; i < dateList.size(); i++) {

						Double profitAmount = 0d;
						DecimalFormat df = new DecimalFormat("######0.00");

						String wxProfit;
						Map<String,String> profitMap;
//						if(!"0".equals(partner.getT1rate())){
//							if("NET".equals(partner.getBankID())||partner.getBankID().toLowerCase().contains("KUAI".toLowerCase())
//									||"UNIONPAY_QRCODE_LARGE".equals(partner.getBankID())){
//								wxProfit = "select bankid , ifnull(sum(profit) , 0) as profit from"
//										+ " (select too.orderAmount , too.merchantNo , t0PayResult , case WHEN t0PayResult=1 then  ROUND(too.orderAmount*("+new BigDecimal(partner.getAgentT0rate()).subtract(new BigDecimal(partner.getT0rate()))+")/1000,2) "
//										+ " when t0PayResult=0  then ROUND(too.orderAmount*("+new BigDecimal(partner.getAgentT1rate()).subtract(new BigDecimal(partner.getT1rate()))+")/1000,2) end  as profit , trxType as bankid "
//										+ " from tbl_online_order as too INNER JOIN tbl_pay_merchant as tpm on too.merchantNo=tpm.merchantNo"
//										+ " where tpm.agent_id='"+ partner.getAgentid() +"' and date(tpm.createDate)<=date('"+dateList.get(i)+"') and date(too.createDate)=date('"+dateList.get(i)+"') and too.orderStatus='SUCCESS' and t0PayResult=0 and TrxType='"+partner.getBankID()+"') as a ";
//							}else{
//								wxProfit = "select bankid , ifnull(sum(profit) , 0) as profit from"
//										+ " (select too.orderAmount , too.merchantNo , t0PayResult , case WHEN t0PayResult=1 then  ROUND(too.orderAmount*("
//										+ new BigDecimal(partner.getAgentT0rate()).subtract(new BigDecimal(partner.getT0rate())) +")/1000,2) "
//										+ " when t0PayResult=0  then ROUND(too.orderAmount*("+ new BigDecimal(partner.getAgentT1rate()).subtract(new BigDecimal(partner.getT1rate())) +")/1000,2) end  as profit , bankid "
//										+ " from tbl_online_order as too INNER JOIN tbl_pay_merchant as tpm on too.merchantNo=tpm.merchantNo"
//										+ " where tpm.agent_id='"+ partner.getAgentid() +"' and date(tpm.createDate)<=date('"+dateList.get(i)+"') and date(too.createDate)=date('"+dateList.get(i)+"') and too.orderStatus='SUCCESS' and t0PayResult=0 and bankId='"+partner.getBankID()+"') as a ";
//							}
//
//							profitMap = queryForMapStr(wxProfit, null);
//
//							profitAmount = AmountUtil.mul(UtilsConstant.strIsEmpty(profitMap.get("profit"))?"1":profitMap.get("profit").toString(), partner.getRatio());
//
//							proftlist.add(new Object[]{partner.getPartnerName() , partner.getAgentName() ,dateList.get(i) ,df.format(profitAmount) , trade , "T1"});
//						}



						if(!"0".equals(partner.getT0rate())){
							if("NET".equals(partner.getBankID())||partner.getBankID().toLowerCase().contains("KUAI".toLowerCase())
									||"UNIONPAY_QRCODE_LARGE".equals(partner.getBankID())){
								wxProfit = "select bankid , ifnull(sum(profit) , 0) as profit from"
										+ " (select too.orderAmount , too.merchantNo , t0PayResult , case WHEN t0PayResult=1 then  ROUND(too.orderAmount*("+new BigDecimal(partner.getAgentT0rate()).subtract(new BigDecimal(partner.getT0rate()))+")/1000,2) "
										+ " when t0PayResult=0  then ROUND(too.orderAmount*("+new BigDecimal(partner.getAgentT1rate()).subtract(new BigDecimal(partner.getT1rate()))+")/1000,2) end  as profit , trxType as bankid "
										+ " from tbl_online_order as too INNER JOIN tbl_pay_merchant as tpm on too.merchantNo=tpm.merchantNo"
										+ " where tpm.agent_id='"+ partner.getAgentid() +"' and date(tpm.createDate)<=date('"+dateList.get(i)+"') and date(too.createDate)=date('"+dateList.get(i)+"') and too.orderStatus='SUCCESS' and t0PayResult=1 and  TrxType='"+partner.getBankID()+"') as a ";


								System.out.println(wxProfit);

							}else{
								wxProfit = "select bankid , ifnull(sum(profit) , 0) as profit from"
										+ " (select too.orderAmount , too.merchantNo , t0PayResult , case WHEN t0PayResult=1 then  ROUND(too.orderAmount*("
										+ new BigDecimal(partner.getAgentT0rate()).subtract(new BigDecimal(partner.getT0rate())) +")/1000,2) "
										+ " when t0PayResult=0  then ROUND(too.orderAmount*("+ new BigDecimal(partner.getAgentT1rate()).subtract(new BigDecimal(partner.getT1rate())) +")/1000,2) end  as profit , bankid "
										+ " from tbl_online_order as too INNER JOIN tbl_pay_merchant as tpm on too.merchantNo=tpm.merchantNo"
										+ " where tpm.agent_id='"+ partner.getAgentid() +"' and date(tpm.createDate)<=date('"+dateList.get(i)+"') and date(too.createDate)=date('"+dateList.get(i)+"') and too.orderStatus='SUCCESS' and t0PayResult=1 and bankId='"+partner.getBankID()+"') as a ";
							}

							profitMap = queryForMapStr(wxProfit, null);

							profitAmount = AmountUtil.mul(UtilsConstant.strIsEmpty(profitMap.get("profit"))?"0":profitMap.get("profit").toString(), partner.getRatio());

							proftlist.add(new Object[]{partner.getPartnerName() , partner.getAgentName() ,dateList.get(i) ,df.format(profitAmount) , trade , "T0"});
						}
					}
				}

			}
		}


		String[] title = {"合伙人名称" , "代理商名称"  , "日期" , "分润金额" , "交易类型" , "结算周期"};
		createExcel2(title, proftlist , pathName , fileName);
		sheetName.clear();
		proftlist.clear();


		String zipFileName = "/opt/sh/hehuoren/" + selectTime + ".zip";
		ZipTool.zip(new File(zipFileName), pathName);
		EmailUtil.sendMail("合伙人分润", "合伙人分润", new String[]{zipFileName});

	}

	

	
	public List<String> getBetweenDates(String startTime, String endTime) throws Exception {

		List<String> dataList = new ArrayList<>();
		dataList.add(startTime.substring(0,10));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = sdf.parse(startTime);
		Date dEnd = sdf.parse(endTime);

		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			dataList.add(sdf.format(calBegin.getTime()));
		}
		return dataList;
	}
	
	
	public void createExcel2( String[] title , List<Object[]> list   ,String pathName   , String  fileName) throws IOException{

		if(new File(pathName +  fileName + ".xls").exists()){
			deleteFile(new File(pathName +  fileName + ".xls"));
		}

		File file = new File(pathName);

		if(!file.exists()){
			file.mkdirs();
		}
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
	    HSSFWorkbook wb = new HSSFWorkbook();


		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("合伙人分润");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue(title[0]);

		/** 设置标题 **/
		for (int i = 1; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}


		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			for (int k = 0; k < list.get(i).length; k++) {
				cell = row.createCell(k);
				cell.setCellValue(list.get(i)[k] == null ? "" : list.get(i)[k].toString());
			}
		}

	    FileOutputStream os = new FileOutputStream(pathName + fileName +".xls");
	    wb.write(os);
	    os.close();
	    wb.close();
	}


	private void deleteFile(File file) {
		//判断文件是否存在
		if (file.exists()) {
			//判断是否是文件
			if (file.isFile()) {
				//删除文件
				file.delete();

			} else if (file.isDirectory()) {
				//如果它是一个目录
				//声明目录下所有的文件 files[];
				File[] files = file.listFiles();
				//遍历目录下所有的文件
				for (int i = 0;i < files.length;i ++) {
					//把每个文件用这个方法进行迭代
					this.deleteFile(files[i]);
				}
				file.delete();//删除文件夹
			}
		} else {
			System.out.println("所删除的文件不存在");
		}
	}


}
