package com.daowen.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
import com.daowen.util.SequenceUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//##{{import}}
@RestController
public class CaigouController extends SimpleController {

    @Autowired
    private CaigouService caigouSrv = null;
    @Autowired
    private ShangpinService shangpinSrv=null;


    //
    @PostMapping("/admin/caigou/save")
    public JsonResult save() {


        String price = request.getParameter("price");

        String userid = request.getParameter("userid");

        String spid = request.getParameter("spid");

        String count = request.getParameter("count");

        String danwei = request.getParameter("danwei");

        String supplyid = request.getParameter("supplyid");

        String des = request.getParameter("des");

        SimpleDateFormat sdfcaigou = new SimpleDateFormat("yyyy-MM-dd");
        Caigou caigou = new Caigou();


        caigou.setDdno(SequenceUtil.buildSequence("D"));
        caigou.setCreatetime(new Date());
        caigou.setUserid(userid == null ? 0 : new Integer(userid));
        caigou.setState(1);
        caigou.setPrice(price==null?0:Double.valueOf(price));
        caigou.setSpid(spid == null ? 0 : new Integer(spid));
        caigou.setCount(count == null ? 0 : new Integer(count));
        caigou.setDanwei(danwei == null ? "" : danwei);
        caigou.setSupplyid(supplyid == null ? 0 : new Integer(supplyid));
        caigou.setDes(des == null ? "" : des);
        caigou.setTotalfee(caigou.getCount()*caigou.getPrice());
        //end forEach
        caigouSrv.save(caigou);
        Shangpin shangpin =shangpinSrv.loadPlus(caigou.getSpid());
        if(shangpin!=null){
            shangpin.setKucun(shangpin.getKucun()+caigou.getCount());
            shangpinSrv.update(shangpin);
        }

        return JsonResult.success(1, "成功", caigou);
    }


    @PostMapping("/admin/caigou/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        caigouSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/caigou/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Caigou caigou = caigouSrv.loadPlus(new Integer(id));
        if (caigou == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", caigou);

    }

    @ResponseBody
    @PostMapping("/admin/caigou/cgstat")
    public JsonResult cgstat() {

        String begdate = request.getParameter("begindate");
        String enddate = request.getParameter("enddate");
        HashMap<String,Object> map=new HashMap<>();
        if(begdate!=null)
            map.put("begdate",begdate);
        if(enddate!=null)
            map.put("enddate",enddate);
        List<HashMap<String,Object>> listMap=caigouSrv.cgStat(map);

        if(listMap!=null)
            request.setAttribute("listMap",listMap);

        return JsonResult.success(1,"成功",listMap);

    }


    @RequestMapping("/admin/caigou/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Caigou caigou = caigouSrv.loadPlus(new Integer(id));
        if (caigou == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", caigou);

    }


    @PostMapping("/admin/caigou/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String ddno = request.getParameter("ddno");
        if (ddno != null)
            map.put("ddno", ddno);

        int pageindex = 1;
        int pagesize = 10;
        // 获取当前分页
        String currentpageindex = request.getParameter("currentpageindex");
        // 当前页面尺寸
        String currentpagesize = request.getParameter("pagesize");
        // 设置当前页
        if (currentpageindex != null)
            pageindex = new Integer(currentpageindex);
        // 设置当前页尺寸
        if (currentpagesize != null)
            pagesize = new Integer(currentpagesize);
        if (!"-1".equals(ispaged)) {
            PageHelper.startPage(pageindex, pagesize);
            List<Caigou> listCaigou = caigouSrv.getEntityPlus(map);
            PageInfo<Caigou> pageInfo = new PageInfo<Caigou>(listCaigou);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", caigouSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
