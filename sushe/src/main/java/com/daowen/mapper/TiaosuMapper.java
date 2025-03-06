package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  宿舍申请
**/
@Repository
public interface TiaosuMapper  extends SimpleMapper<Tiaosu> {

          List<Tiaosu>   getEntityPlus(HashMap map);
          
           Tiaosu   loadPlus(HashMap map);

}