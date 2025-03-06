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
import org.springframework.web.bind.annotation.RestController;

//##{{import}}
@RestController
public class LanmuController extends SimpleController {

    @Autowired
    private LanmuService lanmuSrv = null;


    //
    @PostMapping("/admin/lanmu/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        SimpleDateFormat sdflanmu = new SimpleDateFormat("yyyy-MM-dd");
        Lanmu lanmu = new Lanmu();


        lanmu.setName(name == null ? "" : name);


        //end forEach

        Boolean validateresult = lanmuSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        lanmuSrv.save(lanmu);
        return JsonResult.success(1, "成功", lanmu);
    }

    @PostMapping("/admin/lanmu/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Lanmu lanmu = lanmuSrv.load("where id=" + id);
        if (lanmu == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        SimpleDateFormat sdflanmu = new SimpleDateFormat("yyyy-MM-dd");


        lanmu.setName(name == null ? "" : name);


        lanmuSrv.update(lanmu);
        return JsonResult.success(1, "成功", lanmu);

    }

    @PostMapping("/admin/lanmu/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        lanmuSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/lanmu/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Lanmu lanmu = lanmuSrv.loadPlus(new Integer(id));
        if (lanmu == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", lanmu);

    }

    @RequestMapping("/admin/lanmu/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Lanmu lanmu = lanmuSrv.loadPlus(new Integer(id));
        if (lanmu == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", lanmu);

    }

    @PostMapping("/admin/lanmu/picklist")
    public JsonResult picklist() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);

        List<Lanmu> listLanmu = lanmuSrv.getEntityPlus(map);
        JSONArray jsonArray = new JSONArray();
        if (listLanmu != null)
            listLanmu.forEach(c -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", c.getId());
                //  jsonObject.put("label",c.getName());
                //  jsonObject.put("text",c.getName());
                jsonArray.add(jsonObject);
            });
        return JsonResult.success(1, "获取成功", jsonArray);
    }

    @PostMapping("/admin/lanmu/list")
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
            List<Lanmu> listLanmu = lanmuSrv.getEntityPlus(map);
            PageInfo<Lanmu> pageInfo = new PageInfo<Lanmu>(listLanmu);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", lanmuSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
