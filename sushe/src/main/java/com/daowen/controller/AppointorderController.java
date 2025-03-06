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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//##{{import}}
@Controller
public class AppointorderController extends SimpleController {

    @Autowired
    private AppointorderService appointorderSrv = null;
    @Autowired
    private HuiyuanService huiyuanSrv=null;

    @Autowired
    private  ShangpinService shangpinSrv=null;



    @ResponseBody
    @PostMapping("/admin/appointorder/sftongji")
    public JsonResult saleTongji() {

        String begdate = request.getParameter("begdate");
        String enddate = request.getParameter("enddate");
        HashMap<String,Object> map=new HashMap<>();
        if(begdate!=null)
            map.put("begdate",begdate);
        if(enddate!=null)
            map.put("enddate",enddate);
        List<HashMap<String,Object>> listMap=appointorderSrv.sfStat(map);

        if(listMap!=null)
            request.setAttribute("listMap",listMap);

        return JsonResult.success(1,"成功",listMap);

    }
    @ResponseBody
    @PostMapping("/admin/appointorder/payment")
    public JsonResult payment(){
        String id=request.getParameter("id");
        String paypwd=request.getParameter("paypwd");
        if(id==null)
            return JsonResult.error(-1,"id不能为空");
        if(paypwd==null)
            return JsonResult.error(-8,"请输入支付密码");
        Appointorder order = appointorderSrv.loadPlus(new Integer(id));
        if(order==null)
            return JsonResult.error(-2,"数据非法");
        Huiyuan huiyuan = huiyuanSrv.loadPlus(order.getHyid());
        if(huiyuan==null)
            return JsonResult.error(-3,"没找到用户数据");

        if(!paypwd.equals(huiyuan.getPassword()))
            return JsonResult.error(-9,"支付密码不正确");

        if(huiyuan.getYue()<order.getFee())
            return JsonResult.error(-4,"账户余额不足");

        huiyuan.setYue(huiyuan.getYue()-order.getFee());
        huiyuan.setJifen(huiyuan.getJifen()+order.getFee().intValue());
        huiyuanSrv.update(huiyuan);
        order.setState(2);
        appointorderSrv.update(order);
        return  JsonResult.error(1,"支付成功");


    }


    @ResponseBody
    @PostMapping("/admin/appointorder/cancel")
    public JsonResult cancel(){
        String id=request.getParameter("id");
        String paypwd=request.getParameter("paypwd");
        if(id==null)
            return JsonResult.error(-1,"id不能为空");

        Appointorder order = appointorderSrv.loadPlus(new Integer(id));
        if(order==null)
            return JsonResult.error(-2,"数据非法");

        order.setState(3);
        appointorderSrv.update(order);
        return  JsonResult.error(1,"取消成功");


    }

    //
    @ResponseBody
    @PostMapping("/admin/appointorder/save")
    public JsonResult save() {

        String ddno = request.getParameter("ddno");

        String yydate = request.getParameter("yydate");

        String fee = request.getParameter("fee");

        String hyid = request.getParameter("hyid");

        String mobile = request.getParameter("mobile");

        String count = request.getParameter("count");
        String danjia=request.getParameter("danjia");

        String targetid = request.getParameter("targetid");

        String des = request.getParameter("des");

        SimpleDateFormat sdfappointorder = new SimpleDateFormat("yyyy-MM-dd");
        Appointorder appointorder = new Appointorder();


        appointorder.setDdno(SequenceUtil.buildSequence("D"));


        if (yydate != null) {
            try {
                appointorder.setYydate(sdfappointorder.parse(yydate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            appointorder.setYydate(new Date());
        }



        appointorder.setHyid(hyid == null ? 0 : new Integer(hyid));


        appointorder.setMobile(mobile == null ? "" : mobile);

        appointorder.setCount(count==null?1:new Integer(count));
        appointorder.setDanjia(danjia==null?0:new Double(danjia));

        appointorder.setState(1);


        appointorder.setFee(appointorder.getCount()*appointorder.getDanjia());


        appointorder.setTargetid(targetid == null ? 0 : new Integer(targetid));


        appointorder.setDes(des == null ? "" : des);


        Shangpin shangpin=shangpinSrv.load("where id="+targetid);
        if(shangpin==null)
            return JsonResult.error(-2,"产品信息异常");

        if(appointorder.getCount()>shangpin.getKucun())
            return JsonResult.error(-3,"产品库存不足, 不能够预定");

        appointorderSrv.save(appointorder);

        shangpin.setKucun(shangpin.getKucun()-appointorder.getCount());
        shangpinSrv.update(shangpin);
        return JsonResult.success(1, "成功", appointorder);
    }



    @ResponseBody
    @PostMapping("/admin/appointorder/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        appointorderSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping("/admin/appointorder/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Appointorder appointorder = appointorderSrv.loadPlus(new Integer(id));
        if (appointorder == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", appointorder);

    }

    @ResponseBody
    @RequestMapping("/admin/appointorder/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Appointorder appointorder = appointorderSrv.loadPlus(new Integer(id));
        if (appointorder == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", appointorder);

    }



    @ResponseBody
    @PostMapping("/admin/appointorder/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String ddno = request.getParameter("ddno");
        String hyid=request.getParameter("hyid");
        if (ddno != null)
            map.put("ddno", ddno);
        if(hyid!=null)
            map.put("hyid",hyid);

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
            List<Appointorder> listAppointorder = appointorderSrv.getEntityPlus(map);
            PageInfo<Appointorder> pageInfo = new PageInfo<Appointorder>(listAppointorder);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", appointorderSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
