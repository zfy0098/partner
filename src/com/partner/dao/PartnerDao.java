package com.partner.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2017/12/25.
 *
 * @author hadoop
 */
public class PartnerDao extends BaseDao{

	
	private PartnerDao() {}

	public static PartnerDao getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	private static enum Singleton {
		INSTANCE;

		private PartnerDao partnerDao;

		// JVM会保证此方法绝对只调用一次
		private Singleton() {
			partnerDao = new PartnerDao();
		}

		public PartnerDao getInstance() {
			return partnerDao;
		}
	}
	

    public List<Map<String,Object>> partnerList(Object[] obj){
        String sql = "select * from z_partner where partnerName like concat('%' , ? ,'%' ) and  "
        		+ "agentName like concat('%' , ? ,'%' ) and  agentNO like concat('%' , ? ,'%' )  ";
        return queryForList(sql , obj);
    }


    public List<Map<String,Object>> partnerByid(String id){
    	String sql = "select * from z_partner where id=?";
    	return queryForList(sql , new Object[]{id});
	}
    
    
    public Map<String,Object> getPartnerInfo(Object[] obj){
    	String sql = "select * from z_partner where id=?";
    	return queryForMap(sql, obj);
    }
    
    public int update(Object[] obj){
    	String sql = "update z_partner set t0rate=? , t1rate=? , active=? ,ratio=? where id=?";
    	return executeSql(sql, obj);
    }
    
    
    public int save(Object[] obj){
    	String sql = "insert into z_partner (partnerName , agentid , agentName , agentNO , bankID , t0rate , t1rate , active , ratio)"
    			+ "values (?,?,?,?,?,?,?,?,?)";
    	return executeSql(sql, obj);
    }
    
    
    public String getAgentID(String agentNo){
    	String sql = "select *from tbl_pay_agent where agentNo=?";
    	Map<String,Object> map = queryForMap(sql, new Object[]{agentNo});
    	if(map == null || map.isEmpty()){
    		return null;
    	}
    	return map.get("id")==null?null:map.get("id").toString();
    }
    
}
