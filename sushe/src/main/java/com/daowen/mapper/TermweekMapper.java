package com.daowen.mapper;
import com.daowen.entity.*;
import com.daowen.ssm.simplecrud.SimpleMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
/*
*  学期周
**/
@Repository
public interface TermweekMapper  extends SimpleMapper<Termweek> {

          List<Termweek>   getEntityPlus(HashMap map);
          
           Termweek   loadPlus(HashMap map);

}