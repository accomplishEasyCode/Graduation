package com.daowen.mapper;
import com.daowen.entity.Lanmu;
import com.daowen.ssm.simplecrud.SimpleMapper;


import java.util.List;
import java.util.Map;

public interface LanmuMapper extends SimpleMapper<Lanmu> {

    List<Lanmu> getEntityPlus(Map map);


    Lanmu loadPlus(Map map);

}
