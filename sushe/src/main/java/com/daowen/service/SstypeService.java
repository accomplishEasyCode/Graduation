package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  SstypeService extends  SimpleBizservice<SstypeMapper>{

      @Autowired
      private  SstypeMapper  sstypeMapper;



          public  List<Sstype>   getEntityPlus(HashMap map){
               return  sstypeMapper.getEntityPlus(map);
          }
          
          public  Sstype   loadPlus(HashMap map){
              return sstypeMapper.loadPlus(map);
          }
          
           public  Sstype   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}