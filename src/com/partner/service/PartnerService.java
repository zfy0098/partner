package com.partner.service;

import com.partner.dao.PartnerDao;

import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2017/12/25.
 *
 * @author hadoop
 */
public class PartnerService {

    PartnerDao partnerDao = PartnerDao.getInstance();


    public List<Map<String, Object>> partnerList(Object[] obj) {
        return partnerDao.partnerList(obj);
    }


    public List<Map<String,Object>> partnerByid(String id){
        return partnerDao.partnerByid(id);
    }


    public Map<String, Object> getPartnerInfo(Object[] obj) {
        return partnerDao.getPartnerInfo(obj);
    }

    public int update(Object[] obj) {
        return partnerDao.update(obj);
    }

    public int save(Object[] obj) {
        return partnerDao.save(obj);
    }

    public String getAgentID(String agentNo) {
        return partnerDao.getAgentID(agentNo);
    }

}
