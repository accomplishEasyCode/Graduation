package com.daowen.mapper;

import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

/*
 *  进货
 **/
@Repository
public interface CaigouMapper extends SimpleMapper<Caigou> {

    List<Caigou> getEntityPlus(HashMap map);

    Caigou loadPlus(HashMap map);

    List<HashMap<String, Object>> cgStat(HashMap<String, Object> map);
}