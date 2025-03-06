package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  户型
**/
@Repository
public interface StagMapper  extends SimpleMapper<Stag> {

          List<Stag>   getEntityPlus(HashMap map);
          
           Stag   loadPlus(HashMap map);

}