package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  商品
**/
@Repository
public interface ShangpinMapper  extends SimpleMapper<Shangpin> {

          List<Shangpin>   getEntityPlus(Map map);
          
          Shangpin  loadPlus(Map map);
          
          

}