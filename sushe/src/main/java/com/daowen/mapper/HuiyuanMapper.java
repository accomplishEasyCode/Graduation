package com.daowen.mapper;

import com.daowen.entity.Huiyuan;

import com.daowen.ssm.simplecrud.SimpleMapper;

import java.util.List;
import java.util.Map;

public interface HuiyuanMapper extends SimpleMapper<Huiyuan> {

	List<Huiyuan> getEntityPlus(Map map);
	Huiyuan loadPlus(Map map);

}
