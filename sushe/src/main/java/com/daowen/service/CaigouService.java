package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  CaigouService extends  SimpleBizservice<CaigouMapper>{

      @Autowired
      private  CaigouMapper  caigouMapper;



          public  List<Caigou>   getEntityPlus(HashMap map){
               return  caigouMapper.getEntityPlus(map);
          }
          
          public  Caigou   loadPlus(HashMap map){
              return caigouMapper.loadPlus(map);
          }
          
           public  Caigou   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }

    public List<HashMap<String, Object>> cgStat(HashMap<String, Object> map) {
             return caigouMapper.cgStat(map);
    }
}