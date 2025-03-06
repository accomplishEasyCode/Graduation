package com.daowen.service;
import com.daowen.entity.Lanmu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daowen.mapper.LanmuMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanmuService extends SimpleBizservice<LanmuMapper>{

    @Autowired
   private LanmuMapper mapper;

    public List<Lanmu> getEntityPlus(Map map){
        return mapper.getEntityPlus(map);
    }

    public Lanmu loadPlus(Map map){
        return mapper.loadPlus(map);
    }
    public Lanmu loadPlus(int id){
        HashMap map=new HashMap();
        map.put("id",id);
        return mapper.loadPlus(map);
    }


    public boolean  deleteLanmu(int id){
        Lanmu lanmu=load("where id="+id);
        if(lanmu==null)
            return false;
        int count=executeUpdate(" delete from lanmu where id="+id);


         int rcount= executeUpdate(" delete from xinxi where lmid="+id);

        return count>0?true:false;

    }







}
