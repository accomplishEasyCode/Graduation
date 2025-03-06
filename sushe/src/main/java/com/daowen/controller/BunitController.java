package com.daowen.controller;

import java.lang.ref.ReferenceQueue;
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
public class BunitController extends SimpleController {

    @Autowired
    private BunitService bunitSrv = null;


    //
    @ResponseBody
    @PostMapping("/admin/bunit/save")
    public JsonResult save() {

        String name = request.getParameter("name");
        String typeid=request.getParameter("typeid");
        String haswm=request.getParameter("haswm");
        String hasac=request.getParameter("hasac");
        String des=request.getParameter("des");
        String ldid = request.getParameter("ldid");
        String maxcount = request.getParameter("maxcount");
        String bjid=request.getParameter("bjid");
        Bunit bunit = new Bunit();
        bunit.setName(name == null ? "" : name);
        bunit.setLdid(ldid == null ? 0 : new Integer(ldid));
        bunit.setBjid(bjid==null?0:new Integer(bjid));
        bunit.setMaxcount(maxcount == null ? 0 : new Integer(maxcount));
        bunit.setCicount(0);
        bunit.setTypeid(typeid==null?0:new Integer(typeid));
        bunit.setHaswm(haswm==null?0:new Integer(haswm));
        bunit.setHasac(hasac==null?0:new Integer(hasac));
        bunit.setDes(des==null?"":des);
        Boolean validateresult = bunitSrv.isExist(MessageFormat.format("  where  name=''{0}'' and ldid={1} ",name,ldid));
        if (validateresult)
            return JsonResult.error(-1, "已存在的寝室");

        bunitSrv.save(bunit);
        return JsonResult.success(1, "成功", bunit);
    }

    @ResponseBody
    @PostMapping("/admin/bunit/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Bunit bunit = bunitSrv.load("where id=" + id);
        if (bunit == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        String ldid = request.getParameter("ldid");
        String maxcount = request.getParameter("maxcount");
        String bjid = request.getParameter("bjid");
        String typeid=request.getParameter("typeid");
        String haswm=request.getParameter("haswm");
        String hasac=request.getParameter("hasac");
        String des=request.getParameter("des");
        bunit.setName(name == null ? "" : name);
        bunit.setBjid(bjid==null?0:new Integer(bjid));
        bunit.setMaxcount(maxcount == null ? 0 : new Integer(maxcount));
        bunit.setLdid(ldid == null ? 0 : new Integer(ldid));
        bunit.setTypeid(typeid==null?0:new Integer(typeid));
        bunit.setHaswm(haswm==null?0:new Integer(haswm));
        bunit.setHasac(hasac==null?0:new Integer(hasac));
        bunit.setDes(des==null?"":des);
        bunitSrv.update(bunit);
        return JsonResult.success(1, "成功", bunit);

    }

    @ResponseBody
    @PostMapping("/admin/bunit/delete")
    public JsonResult delete() {
        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "id不能为空");

        bunitSrv.delete("where id="+id);
        return JsonResult.success(1, "成功");
    }

    @ResponseBody
    @RequestMapping({"/admin/bunit/load","/admin/bunit/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Bunit bunit = bunitSrv.loadPlus(new Integer(id));
        if (bunit == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", bunit);

    }




    @ResponseBody
    @PostMapping("/admin/bunit/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");
        String ldid=request.getParameter("ldid");
        String typeid=request.getParameter("typeid");
        String bjid=request.getParameter("bjid");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);
        if(typeid!=null)
            map.put("typeid",typeid);
        if(ldid!=null)
            map.put("ldid",ldid);
        if(bjid!=null)
            map.put("bjid",bjid);

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
            List<Bunit> listBunit = bunitSrv.getEntityPlus(map);
            PageInfo<Bunit> pageInfo = new PageInfo<Bunit>(listBunit);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", bunitSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
