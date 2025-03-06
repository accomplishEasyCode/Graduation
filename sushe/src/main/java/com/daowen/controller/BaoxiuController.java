package com.daowen.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;

//##{{import}}
@Controller
public class BaoxiuController extends SimpleController {

    @Autowired
    private BaoxiuService baoxiuSrv = null;
    //
    @ResponseBody
    @PostMapping("/admin/baoxiu/save")
    public JsonResult save() {

        String sbno = request.getParameter("sbno");

        String typeid = request.getParameter("typeid");

        String createtime = request.getParameter("createtime");

        String bxrid = request.getParameter("bxrid");

        String tupian = request.getParameter("tupian");

        String des = request.getParameter("des");

        String replydes = request.getParameter("replydes");

        SimpleDateFormat sdfbaoxiu = new SimpleDateFormat("yyyy-MM-dd");
        Baoxiu baoxiu = new Baoxiu();


        baoxiu.setSbno(sbno == null ? "" : sbno);


        baoxiu.setTypeid(typeid == null ? 0 : new Integer(typeid));


        baoxiu.setState(1);


        baoxiu.setDdno(SequenceUtil.buildSequence("B"));
        baoxiu.setCreatetime(new Date());

        baoxiu.setBxrid(bxrid == null ? 0 : new Integer(bxrid));


        baoxiu.setTupian(tupian == null ? "" : tupian);


        baoxiu.setDes(des == null ? "" : des);


        baoxiu.setReplydes(replydes == null ? "" : replydes);

        Boolean validateresult = baoxiuSrv.isExist("  where  ddno='" + baoxiu.getDdno() + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        baoxiuSrv.save(baoxiu);
        return JsonResult.success(1, "成功", baoxiu);
    }

    @ResponseBody
    @PostMapping("/admin/baoxiu/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Baoxiu baoxiu = baoxiuSrv.load("where id=" + id);
        if (baoxiu == null)
            return JsonResult.error(-2, "非法数据");

        String sbno = request.getParameter("sbno");

        String typeid = request.getParameter("typeid");


        String bxrid = request.getParameter("bxrid");

        String tupian = request.getParameter("tupian");

        String des = request.getParameter("des");

        String replydes = request.getParameter("replydes");

        SimpleDateFormat sdfbaoxiu = new SimpleDateFormat("yyyy-MM-dd");


        baoxiu.setSbno(sbno == null ? "" : sbno);


        baoxiu.setTypeid(typeid == null ? 0 : new Integer(typeid));

        baoxiu.setCreatetime(new Date());



        baoxiu.setBxrid(bxrid == null ? 0 : new Integer(bxrid));


        baoxiu.setTupian(tupian == null ? "" : tupian);


        baoxiu.setDes(des == null ? "" : des);


        baoxiu.setReplydes(replydes == null ? "" : replydes);


        baoxiuSrv.update(baoxiu);
        return JsonResult.success(1, "成功", baoxiu);

    }

    @ResponseBody
    @PostMapping("/admin/baoxiu/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        baoxiuSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping("/admin/baoxiu/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Baoxiu baoxiu = baoxiuSrv.loadPlus(new Integer(id));
        if (baoxiu == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", baoxiu);

    }

    @ResponseBody
    @RequestMapping("/admin/baoxiu/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Baoxiu baoxiu = baoxiuSrv.loadPlus(new Integer(id));
        if (baoxiu == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", baoxiu);

    }



    @ResponseBody
    @PostMapping("/admin/baoxiu/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String sbno = request.getParameter("sbno");
        String ddno=request.getParameter("ddno");
        String bxrid=request.getParameter("bxrid");
        if (sbno != null)
            map.put("sbno", sbno);
        if(bxrid!=null)
            map.put("bxrid",bxrid);
        if(ddno!=null)
            map.put("ddno",ddno);

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
            List<Baoxiu> listBaoxiu = baoxiuSrv.getEntityPlus(map);
            PageInfo<Baoxiu> pageInfo = new PageInfo<Baoxiu>(listBaoxiu);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", baoxiuSrv.getEntityPlus(map));


    }


    @ResponseBody
    @PostMapping("/admin/baoxiu/shenpi")
    public JsonResult  shenpi(){

        String id=request.getParameter("id");

        String  state =request.getParameter("state");
        String  reply=request.getParameter("replydes");
        String  shenpiren=request.getParameter("shenpiren");
        int stateCode=2;
        //验证错误url
        String errorurl=request.getParameter("errorurl");
        if(id==null)
            return JsonResult.error(-1,"id不能为空");;
        Baoxiu  baoxiu=baoxiuSrv.load(" where id="+ id);

        if(baoxiu==null)
            return JsonResult.error(-1,"数据非法");;
        if(state!=null)
            stateCode=Integer.parseInt(state);
        baoxiu.setState(stateCode);
        baoxiu.setReplydes(reply);
        baoxiuSrv.update(baoxiu);
        return JsonResult.success(1,"审核成功");


    }

    //##{{methods}}


}
