package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  ChafangService extends  SimpleBizservice<ChafangMapper>{

      @Autowired
      private  ChafangMapper  chafangMapper;



          public  List<HashMap<String,Object>>   getEntityPlus(HashMap map){
               return  chafangMapper.getEntityPlus(map);
          }
          
          public  HashMap<String,Object>   loadPlus(HashMap map){
              return chafangMapper.loadPlus(map);
          }
          
           public  HashMap<String,Object>   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}