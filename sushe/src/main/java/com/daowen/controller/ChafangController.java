package com.daowen.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
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
public class ChafangController extends SimpleController {

    @Autowired
    private ChafangService chafangSrv = null;

    //
    @ResponseBody
    @PostMapping("/admin/chafang/save")
    public JsonResult save() {

        String targetid = request.getParameter("targetid");
        String cfdate = request.getParameter("cfdate");
        String result = request.getParameter("result");
        String des = request.getParameter("des");
        SimpleDateFormat sdfchafang = new SimpleDateFormat("yyyy-MM-dd");
        Chafang chafang = new Chafang();
        chafang.setTargetid(targetid == null ? 0 : new Integer(targetid));
        if (cfdate != null) {
            try {
                chafang.setCfdate(sdfchafang.parse(cfdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            chafang.setCfdate(new Date());
        }
        chafang.setResult(result == null ? "" : result);
        chafang.setDes(des == null ? "" : des);
        Boolean validateresult = chafangSrv.isExist(MessageFormat.format("where targetid={0} and cfdate=''{1}'' ",targetid,cfdate));
        if (validateresult)
            return JsonResult.error(-1, "已经查房过");

        chafangSrv.save(chafang);
        return JsonResult.success(1, "成功", chafang);
    }

    @ResponseBody
    @PostMapping("/admin/chafang/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Chafang chafang = chafangSrv.load("where id=" + id);
        if (chafang == null)
            return JsonResult.error(-2, "非法数据");

        String targetid = request.getParameter("targetid");

        String cfdate = request.getParameter("cfdate");

        String result = request.getParameter("result");

        String des = request.getParameter("des");

        SimpleDateFormat sdfchafang = new SimpleDateFormat("yyyy-MM-dd");


        chafang.setTargetid(targetid == null ? 0 : new Integer(targetid));


        if (cfdate != null) {
            try {
                chafang.setCfdate(sdfchafang.parse(cfdate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            chafang.setCfdate(new Date());
        }


        chafang.setResult(result == null ? "" : result);


        chafang.setDes(des == null ? "" : des);


        chafangSrv.update(chafang);
        return JsonResult.success(1, "成功", chafang);

    }

    @ResponseBody
    @PostMapping("/admin/chafang/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        chafangSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping({"/admin/chafang/load", "/admin/chafang/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        HashMap<String,Object> chafang = chafangSrv.loadPlus(new Integer(id));
        if (chafang == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", chafang);

    }



    @ResponseBody
    @PostMapping("/admin/chafang/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String targetid = request.getParameter("targetid");
        String cfdate = request.getParameter("cfdate");
        if (targetid != null)
            map.put("targetid", targetid);
        if(cfdate!=null)
            map.put("cfdate",cfdate);

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
            List<HashMap<String,Object>> listChafang = chafangSrv.getEntityPlus(map);
            PageInfo<HashMap<String,Object>> pageInfo = new PageInfo<>(listChafang);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", chafangSrv.getEntityPlus(map));


    }


    //##{{methods}}

}
