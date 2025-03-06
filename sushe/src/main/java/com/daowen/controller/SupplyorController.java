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
public class SupplyorController extends SimpleController {

    @Autowired
    private SupplyorService supplyorSrv = null;


    //
    @PostMapping("/admin/supplyor/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        String fax = request.getParameter("fax");

        String email = request.getParameter("email");

        String address = request.getParameter("address");

        SimpleDateFormat sdfsupplyor = new SimpleDateFormat("yyyy-MM-dd");
        Supplyor supplyor = new Supplyor();


        supplyor.setName(name == null ? "" : name);


        supplyor.setFax(fax == null ? "" : fax);


        supplyor.setEmail(email == null ? "" : email);


        supplyor.setAddress(address == null ? "" : address);


        //end forEach

        Boolean validateresult = supplyorSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        supplyorSrv.save(supplyor);
        return JsonResult.success(1, "成功", supplyor);
    }

    @PostMapping("/admin/supplyor/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Supplyor supplyor = supplyorSrv.load("where id=" + id);
        if (supplyor == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        String fax = request.getParameter("fax");

        String email = request.getParameter("email");

        String address = request.getParameter("address");

        SimpleDateFormat sdfsupplyor = new SimpleDateFormat("yyyy-MM-dd");


        supplyor.setName(name == null ? "" : name);


        supplyor.setFax(fax == null ? "" : fax);


        supplyor.setEmail(email == null ? "" : email);


        supplyor.setAddress(address == null ? "" : address);


        supplyorSrv.update(supplyor);
        return JsonResult.success(1, "成功", supplyor);

    }

    @PostMapping("/admin/supplyor/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        supplyorSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/supplyor/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Supplyor supplyor = supplyorSrv.loadPlus(new Integer(id));
        if (supplyor == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", supplyor);

    }

    @RequestMapping("/admin/supplyor/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Supplyor supplyor = supplyorSrv.loadPlus(new Integer(id));
        if (supplyor == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", supplyor);

    }



    @PostMapping("/admin/supplyor/list")
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
            List<Supplyor> listSupplyor = supplyorSrv.getEntityPlus(map);
            PageInfo<Supplyor> pageInfo = new PageInfo<Supplyor>(listSupplyor);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", supplyorSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
