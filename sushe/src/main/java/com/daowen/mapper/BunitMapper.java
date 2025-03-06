package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  建筑寝室
**/
@Repository
public interface BunitMapper  extends SimpleMapper<Bunit> {

          List<Bunit>   getEntityPlus(Map map);
          
          Bunit  loadPlus(Map map);
          
          

}