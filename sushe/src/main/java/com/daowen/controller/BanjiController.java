package com.daowen.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
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
public class BanjiController extends SimpleController {

    @Autowired
    private BanjiService banjiSrv = null;


    //
    @PostMapping("/admin/banji/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        SimpleDateFormat sdfbanji = new SimpleDateFormat("yyyy-MM-dd");
        Banji banji = new Banji();
        banji.setName(name == null ? "" : name);
        //end forEach
        Boolean validateresult = banjiSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");
        banjiSrv.save(banji);
        return JsonResult.success(1, "成功", banji);
    }



    @PostMapping("/admin/banji/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Banji banji = banjiSrv.load("where id=" + id);
        if (banji == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        SimpleDateFormat sdfbanji = new SimpleDateFormat("yyyy-MM-dd");


        banji.setName(name == null ? "" : name);


        banjiSrv.update(banji);
        return JsonResult.success(1, "成功", banji);

    }

    @PostMapping("/admin/banji/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        banjiSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping({"/admin/banji/load","/admin/banji/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Banji banji = banjiSrv.loadPlus(new Integer(id));
        if (banji == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", banji);

    }


    @PostMapping("/admin/banji/teachs")
    public JsonResult teachs() {

        HashMap<String, Object> map = new HashMap<>();

        String teacherid = request.getParameter("teacherid");
        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);
        if(teacherid!=null)
            map.put("teacherid",teacherid);

        return JsonResult.success(1, "获取成功", banjiSrv.getTeachClasses(map));


    }

    @PostMapping("/admin/banji/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);

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
            List<Banji> listBanji = banjiSrv.getEntityPlus(map);
            PageInfo<Banji> pageInfo = new PageInfo<Banji>(listBanji);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", banjiSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
