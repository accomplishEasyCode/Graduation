package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  供应商
**/
@Repository
public interface SupplyorMapper  extends SimpleMapper<Supplyor> {

          List<Supplyor>   getEntityPlus(HashMap map);
          
           Supplyor   loadPlus(HashMap map);

}