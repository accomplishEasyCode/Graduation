package com.daowen.service;
import com.daowen.vo.Qshygiene;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;
import java.util.*;
import com.daowen.entity.*;

@Service
public   class  LoudongService extends  SimpleBizservice<LoudongMapper>{

      @Autowired
      private  LoudongMapper  loudongMapper;


       public List<Loudong> getEntityPlus(Map map){
          return  loudongMapper.getEntityPlus(map);
      }

      public  Loudong   loadPlus(Map map ){
            return  loudongMapper.loadPlus(map);
      }
      
     public  Loudong   loadPlus(int id ){
             HashMap map=new HashMap();
             map.put("id",id);
            return  loudongMapper.loadPlus(map);
      }


    public List<Qshygiene> getPingfens(HashMap map) {
           return loudongMapper.getPingfens(map);
    }

    public List<HashMap<String, Object>> rzstat(HashMap<String, Object> map) {
           return  loudongMapper.rzstat(map);
    }
}