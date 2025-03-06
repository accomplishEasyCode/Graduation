package com.daowen.service;
import com.daowen.entity.*;
import org.springframework.stereotype.Service;
import com.daowen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
@Service
public   class  QspingfenService extends  SimpleBizservice<QspingfenMapper>{

      @Autowired
      private  QspingfenMapper  qspingfenMapper;



          public  List<Qspingfen>   getEntityPlus(HashMap map){
               return  qspingfenMapper.getEntityPlus(map);
          }
          
          public  Qspingfen   loadPlus(HashMap map){
              return qspingfenMapper.loadPlus(map);
          }
          
           public  Qspingfen   loadPlus(int id){
                 HashMap map = new HashMap();
	         map.put("id",id);
	        return this.loadPlus(map);
          }

    public List<HashMap<String, Object>> getAvgscore(HashMap<String, Object> map) {
             return qspingfenMapper.getAvgscore(map);
    }

    public List<HashMap<String, Object>> getScorescope(HashMap<String, Object> map) {
              return qspingfenMapper.getScorescope(map);
    }
}