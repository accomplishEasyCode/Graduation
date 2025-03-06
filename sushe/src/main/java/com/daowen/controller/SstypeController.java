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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;

//##{{import}}
@Controller
public class SstypeController extends SimpleController {

    @Autowired
    private SstypeService sstypeSrv = null;

    //
    @ResponseBody
    @PostMapping("/admin/sstype/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        String renshu = request.getParameter("renshu");

        SimpleDateFormat sdfsstype = new SimpleDateFormat("yyyy-MM-dd");
        Sstype sstype = new Sstype();


        sstype.setName(name == null ? "" : name);


        sstype.setRenshu(renshu == null ? 0 : new Integer(renshu));


        //end forEach

        Boolean validateresult = sstypeSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        sstypeSrv.save(sstype);
        return JsonResult.success(1, "成功", sstype);
    }

    @ResponseBody
    @PostMapping("/admin/sstype/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Sstype sstype = sstypeSrv.load("where id=" + id);
        if (sstype == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        String renshu = request.getParameter("renshu");

        SimpleDateFormat sdfsstype = new SimpleDateFormat("yyyy-MM-dd");


        sstype.setName(name == null ? "" : name);


        sstype.setRenshu(renshu == null ? 0 : new Integer(renshu));


        sstypeSrv.update(sstype);
        return JsonResult.success(1, "成功", sstype);

    }

    @ResponseBody
    @PostMapping("/admin/sstype/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        sstypeSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping({"/admin/sstype/load", "/admin/sstype/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Sstype sstype = sstypeSrv.loadPlus(new Integer(id));
        if (sstype == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", sstype);

    }


    @ResponseBody
    @PostMapping("/admin/sstype/list")
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
            List<Sstype> listSstype = sstypeSrv.getEntityPlus(map);
            PageInfo<Sstype> pageInfo = new PageInfo<Sstype>(listSstype);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", sstypeSrv.getEntityPlus(map));


    }


    //##{{methods}}

}
