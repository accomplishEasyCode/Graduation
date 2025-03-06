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
public class SpcategoryController extends SimpleController {

    @Autowired
    private SpcategoryService spcategorySrv = null;


    //
    @PostMapping("/admin/spcategory/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        SimpleDateFormat sdfspcategory = new SimpleDateFormat("yyyy-MM-dd");
        Spcategory spcategory = new Spcategory();


        spcategory.setName(name == null ? "" : name);


        //end forEach

        Boolean validateresult = spcategorySrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        spcategorySrv.save(spcategory);
        return JsonResult.success(1, "成功", spcategory);
    }

    @PostMapping("/admin/spcategory/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Spcategory spcategory = spcategorySrv.load("where id=" + id);
        if (spcategory == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        SimpleDateFormat sdfspcategory = new SimpleDateFormat("yyyy-MM-dd");


        spcategory.setName(name == null ? "" : name);


        spcategorySrv.update(spcategory);
        return JsonResult.success(1, "成功", spcategory);

    }

    @PostMapping("/admin/spcategory/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        spcategorySrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/spcategory/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Spcategory spcategory = spcategorySrv.loadPlus(new Integer(id));
        if (spcategory == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", spcategory);

    }

    @RequestMapping("/admin/spcategory/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Spcategory spcategory = spcategorySrv.loadPlus(new Integer(id));
        if (spcategory == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", spcategory);

    }

    @PostMapping("/admin/spcategory/picklist")
    public JsonResult picklist() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);

        List<Spcategory> listSpcategory = spcategorySrv.getEntityPlus(map);
        JSONArray jsonArray = new JSONArray();
        if (listSpcategory != null)
            listSpcategory.forEach(c -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", c.getId());
                //  jsonObject.put("label",c.getName());
                //  jsonObject.put("text",c.getName());
                jsonArray.add(jsonObject);
            });
        return JsonResult.success(1, "获取成功", jsonArray);
    }

    @PostMapping("/admin/spcategory/list")
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
            List<Spcategory> listSpcategory = spcategorySrv.getEntityPlus(map);
            PageInfo<Spcategory> pageInfo = new PageInfo<Spcategory>(listSpcategory);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", spcategorySrv.getEntityPlus(map));


    }

    //##{{methods}}


}
