package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  SupplyorService extends  SimpleBizservice<SupplyorMapper>{

      @Autowired
      private  SupplyorMapper  supplyorMapper;



          public  List<Supplyor>   getEntityPlus(HashMap map){
               return  supplyorMapper.getEntityPlus(map);
          }
          
          public  Supplyor   loadPlus(HashMap map){
              return supplyorMapper.loadPlus(map);
          }
          
           public  Supplyor   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}