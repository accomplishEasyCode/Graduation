package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  BaoxiuService extends  SimpleBizservice<BaoxiuMapper>{

      @Autowired
      private  BaoxiuMapper  baoxiuMapper;



          public  List<Baoxiu>   getEntityPlus(HashMap map){
               return  baoxiuMapper.getEntityPlus(map);
          }
          
          public  Baoxiu   loadPlus(HashMap map){
              return baoxiuMapper.loadPlus(map);
          }
          
           public  Baoxiu   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}