package com.daowen.service;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import java.util.*;
import com.daowen.entity.*;

@Service
public   class  ShangpinService extends  SimpleBizservice<ShangpinMapper>{

      @Autowired
      private  ShangpinMapper  shangpinMapper;


       public List< Shangpin> getEntityPlus(Map map){
          return  shangpinMapper.getEntityPlus(map);
      }

      public  Shangpin   loadPlus(Map map ){
            return  shangpinMapper.loadPlus(map);
      }
      
     public  Shangpin   loadPlus(int id ){
             HashMap map=new HashMap();
             map.put("id",id);
            return  shangpinMapper.loadPlus(map);
      }


     
}