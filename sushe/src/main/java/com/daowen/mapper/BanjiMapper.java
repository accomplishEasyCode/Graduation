package com.daowen.mapper;
import com.daowen.entity.Banji;

import com.daowen.ssm.simplecrud.SimpleMapper;

import java.util.List;
import java.util.Map;

public interface BanjiMapper extends SimpleMapper<Banji> {

    List<Banji> getEntityPlus(Map map);

    Banji  loadPlus(Map map);

    List<Banji> getWaits();

    List<Banji> getTeachClasses(Map map);

}
