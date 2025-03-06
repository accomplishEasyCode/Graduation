package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  StagService extends  SimpleBizservice<StagMapper>{

      @Autowired
      private  StagMapper  stagMapper;



          public  List<Stag>   getEntityPlus(HashMap map){
               return  stagMapper.getEntityPlus(map);
          }
          
          public  Stag   loadPlus(HashMap map){
              return stagMapper.loadPlus(map);
          }
          
           public  Stag   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }
     
}