package com.daowen.service;

import com.daowen.entity.Xinxi;

import com.daowen.vo.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daowen.mapper.XinxiMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.util.*;

@Service
public class XinxiService extends SimpleBizservice<XinxiMapper>  {

    @Autowired
    private XinxiMapper xinxiMapper;

    @Autowired
    private LanmuService lanmuSrv=null;

    public List<XinxiVo> getEntityPlus(HashMap map){
        return xinxiMapper.getEntityPlus(map);
    }
    public  List<XinxiVo> findXinxi(int lmid){
        HashMap<String,Object> map=new HashMap();
        map.put("lmid",lmid);
        map.put("state",2);
        return xinxiMapper.getEntityPlus(map);
    }
    public XinxiVo loadPlus(int id){
        HashMap<String,Object> map=new HashMap();
        map.put("id",id);
        return xinxiMapper.loadPlus(map);
    }



    public List<Xinxi> getRelated(int id){
        int maxSize=this.getRecordCount("");
        int count=3;
        if(maxSize<count)
            count=maxSize;

        List<Integer> listPos= random(count,maxSize);
        List<Xinxi> list=getEntity("");
        List<Xinxi> listRan=new ArrayList<>();
        int index=0;
        for(Xinxi xinxi :list){
            if(has(listPos,index++))
                listRan.add(xinxi);
        }
        return listRan;
    }
    private  boolean has(List<Integer> list,Integer val){
        for(Integer i :list){
            if(val==i)
                return true;
        }
        return false;
    }

    private List<Integer> random(int count,int maxSize){
        Random r = new Random();
        ArrayList<Integer> h = new ArrayList<>();
        if(count>maxSize)
            count=maxSize;
//        循环生成随机数
        while (h.size() < count) {
            LinkedHashSet<Integer> set = new LinkedHashSet<>();//利用set集合去重
            h.add(r.nextInt((maxSize) + 1));//获取1至20的随机数，nextInt（20）的取值是0至19，所有后面+1
            set.addAll(h);//利用set集合 addAll h集合，达到去重目的
            h.clear();//清空h集合
            h.addAll(set);//再将set集合元素还给h集合
        }

        Collections.sort(h, Collections.reverseOrder());//获取随机数的降序排序
        System.out.println("降序为后：" + h);
        return h;
    }

}
