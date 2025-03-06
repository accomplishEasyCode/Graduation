package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  报修
**/
@Repository
public interface BaoxiuMapper  extends SimpleMapper<Baoxiu> {

          List<Baoxiu>   getEntityPlus(HashMap map);
          
           Baoxiu   loadPlus(HashMap map);

}