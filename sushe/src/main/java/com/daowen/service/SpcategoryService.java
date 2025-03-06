package com.daowen.service;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import java.util.*;
import com.daowen.entity.*;

@Service
public   class  SpcategoryService extends  SimpleBizservice<SpcategoryMapper>{

      @Autowired
      private  SpcategoryMapper  spcategoryMapper;


       public List< Spcategory> getEntityPlus(Map map){
          return  spcategoryMapper.getEntityPlus(map);
      }

      public  Spcategory   loadPlus(Map map ){
            return  spcategoryMapper.loadPlus(map);
      }
      
     public  Spcategory   loadPlus(int id ){
             HashMap map=new HashMap();
             map.put("id",id);
            return  spcategoryMapper.loadPlus(map);
      }


     
}