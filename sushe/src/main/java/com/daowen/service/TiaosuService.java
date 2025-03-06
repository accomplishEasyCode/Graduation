package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  TiaosuService extends  SimpleBizservice<TiaosuMapper>{

      @Autowired
      private  TiaosuMapper  tiaosuMapper;



          public  List<Tiaosu>   getEntityPlus(HashMap map){
               return  tiaosuMapper.getEntityPlus(map);
          }
          
          public  Tiaosu   loadPlus(HashMap map){
              return tiaosuMapper.loadPlus(map);
          }
          
           public  Tiaosu   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}