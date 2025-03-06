package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  查房
**/
@Repository
public interface ChafangMapper  extends SimpleMapper<Chafang> {

          List<HashMap<String,Object>>   getEntityPlus(HashMap map);

           HashMap<String,Object>   loadPlus(HashMap map);

}