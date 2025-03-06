package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  寝室评分
**/
@Repository
public interface QspingfenMapper  extends SimpleMapper<Qspingfen> {

          List<Qspingfen>   getEntityPlus(HashMap map);
          
           Qspingfen   loadPlus(HashMap map);

           List<HashMap<String, Object>> getAvgscore(HashMap<String, Object> map);

           //获取成绩分布
           List<HashMap<String, Object>> getScorescope(HashMap<String, Object> map);
}