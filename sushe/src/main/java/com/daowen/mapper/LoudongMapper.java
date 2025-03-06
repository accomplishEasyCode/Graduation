package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import com.daowen.vo.Qshygiene;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  楼栋
**/
@Repository
public interface LoudongMapper  extends SimpleMapper<Loudong> {

          List<Loudong>   getEntityPlus(Map map);
          
          Loudong  loadPlus(Map map);


          List<Qshygiene> getPingfens(HashMap map);

          List<HashMap<String, Object>> rzstat(HashMap<String, Object> map);
}