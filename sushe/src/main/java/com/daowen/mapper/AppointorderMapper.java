package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  预约订单
**/
@Repository
public interface AppointorderMapper  extends SimpleMapper<Appointorder> {

          List<Appointorder>   getEntityPlus(HashMap map);
          
           Appointorder   loadPlus(HashMap map);

          List<HashMap<String, Object>> sfStat(HashMap<String, Object> map);
}