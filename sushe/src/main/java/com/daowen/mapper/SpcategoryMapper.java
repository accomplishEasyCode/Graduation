package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  商品类别
**/
@Repository
public interface SpcategoryMapper  extends SimpleMapper<Spcategory> {

          List<Spcategory>   getEntityPlus(Map map);
          
          Spcategory  loadPlus(Map map);
          
          

}