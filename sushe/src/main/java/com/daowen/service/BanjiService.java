package com.daowen.service;
import com.daowen.entity.Banji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daowen.mapper.BanjiMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BanjiService extends SimpleBizservice<BanjiMapper>{


    @Autowired
    private BanjiMapper banjiMapper;


    public List<Banji> getEntityPlus(Map map){
        return  banjiMapper.getEntityPlus(map);
    }

    public  Banji   loadPlus(Map map ){
        return  banjiMapper.loadPlus(map);
    }

    public Banji loadPlus(int id ){
        HashMap map=new HashMap();
        map.put("id",id);
        return  banjiMapper.loadPlus(map);
    }

    public List<Banji> getTeachClasses(Map map){
        return banjiMapper.getTeachClasses(map);
    }


    public List<Banji> getWaits(){
        return  banjiMapper.getWaits();
    }
}
