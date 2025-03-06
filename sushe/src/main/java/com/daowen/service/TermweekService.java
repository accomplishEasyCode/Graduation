package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  TermweekService extends  SimpleBizservice<TermweekMapper>{

      @Autowired
      private  TermweekMapper  termweekMapper;



          public  List<Termweek>   getEntityPlus(HashMap map){
               return  termweekMapper.getEntityPlus(map);
          }
          
          public  Termweek   loadPlus(HashMap map){
              return termweekMapper.loadPlus(map);
          }
          
           public  Termweek   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}