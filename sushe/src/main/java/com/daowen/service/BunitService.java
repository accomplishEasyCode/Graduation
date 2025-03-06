package com.daowen.service;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import java.util.*;
import com.daowen.entity.*;

@Service
public   class  BunitService extends  SimpleBizservice<BunitMapper>{

      @Autowired
      private  BunitMapper  bunitMapper;


       public List< Bunit> getEntityPlus(Map map){
          return  bunitMapper.getEntityPlus(map);
      }

      public  Bunit   loadPlus(Map map ){
            return  bunitMapper.loadPlus(map);
      }
      
     public  Bunit   loadPlus(int id ){
             HashMap map=new HashMap();
             map.put("id",id);
            return  bunitMapper.loadPlus(map);
      }


     
}