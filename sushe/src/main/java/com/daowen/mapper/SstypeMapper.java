package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  宿舍类型
**/
@Repository
public interface SstypeMapper  extends SimpleMapper<Sstype> {

          List<Sstype>   getEntityPlus(HashMap map);
          
           Sstype   loadPlus(HashMap map);

}