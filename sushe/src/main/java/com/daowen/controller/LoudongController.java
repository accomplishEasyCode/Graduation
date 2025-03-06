package com.daowen.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
import com.daowen.vo.Qshygiene;
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
public class LoudongController extends SimpleController {

    @Autowired
    private LoudongService loudongSrv = null;

    @Autowired
    private  BunitService bunitSrv=null;


    @ResponseBody
    @PostMapping("/admin/loudong/ssstat")
    public JsonResult ssstat() {

        String ldid = request.getParameter("ldid");

        HashMap<String,Object> map=new HashMap<>();
        if(ldid!=null)
            map.put("ldid",ldid);
        //入住统计
        List<HashMap<String,Object>> listMap=loudongSrv.rzstat(map);

        if(listMap!=null)
            request.setAttribute("listMap",listMap);

        return JsonResult.success(1,"成功",listMap);

    }
    @ResponseBody
    @PostMapping("/admin/sushelou/pingfens")
    public JsonResult pingfens() {
        String name=request.getParameter("name");
        String weekid=request.getParameter("weekid");
        HashMap map = new HashMap();
        if(weekid!=null)
            map.put("weekid",weekid);
        if(name !=null)
            map.put("name",name);
        List<Qshygiene> list = loudongSrv.getPingfens(map);
        return JsonResult.success(1, "获取宿舍评分", list);
    }

    //
    @ResponseBody
    @PostMapping("/admin/loudong/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        SimpleDateFormat sdfloudong = new SimpleDateFormat("yyyy-MM-dd");
        Loudong loudong = new Loudong();


        loudong.setName(name == null ? "" : name);

        Boolean validateresult = loudongSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        loudongSrv.save(loudong);
        return JsonResult.success(1, "成功", loudong);
    }

    @ResponseBody
    @PostMapping("/admin/loudong/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Loudong loudong = loudongSrv.load("where id=" + id);
        if (loudong == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        SimpleDateFormat sdfloudong = new SimpleDateFormat("yyyy-MM-dd");


        loudong.setName(name == null ? "" : name);


        loudongSrv.update(loudong);
        return JsonResult.success(1, "成功", loudong);

    }

    @ResponseBody
    @PostMapping("/admin/loudong/delete")
    public JsonResult delete() {
        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "id不能为空");

        loudongSrv.delete("where id="+id);

        bunitSrv.delete("where ldid="+id);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping("/admin/loudong/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Loudong loudong = loudongSrv.loadPlus(new Integer(id));
        if (loudong == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", loudong);

    }

    @ResponseBody
    @RequestMapping("/admin/loudong/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Loudong loudong = loudongSrv.loadPlus(new Integer(id));
        if (loudong == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", loudong);

    }

    @ResponseBody
    @PostMapping("/admin/loudong/cascadelist")
    public JsonResult cascadelist() {

        HashMap<String, Object> map = new HashMap<>();
        String bjid = request.getParameter("bjid");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);
        if(bjid!=null)
            map.put("bjid",bjid);

        List<Loudong> listLoudong = loudongSrv.getEntityPlus(map);
        JSONArray jsonArray = new JSONArray();
        if (listLoudong != null)
            listLoudong.forEach(c -> {
                JSONObject joLoudong = new JSONObject();
                joLoudong.put("id", c.getId());
                joLoudong.put("value", c.getId());
                joLoudong.put("label",c.getName());
                joLoudong.put("name",c.getName());

                joLoudong.put("type",1);
                if(c.getUnits()!=null&&c.getUnits().size()>0){
                	JSONArray children=new JSONArray();
                	c.getUnits().forEach(unit->{
                          JSONObject joBunit=new JSONObject();
						  joBunit.put("value",unit.getId());
						  joBunit.put("id",unit.getId());
						  joBunit.put("label",unit.getName());
						  joBunit.put("name",unit.getName());
						  joBunit.put("maxcount",unit.getMaxcount());
						  joBunit.put("cicount",unit.getCicount());
						  joBunit.put("bjid",unit.getBjid());
						  joBunit.put("parentid",unit.getLdid());
						  joBunit.put("ldid",unit.getLdid());
						  joBunit.put("type",2);
                          children.add(joBunit);
					});
                    joLoudong.put("children",children);
				}

                jsonArray.add(joLoudong);
            });
        return JsonResult.success(1, "获取成功", jsonArray);
    }


    @ResponseBody
    @PostMapping("/admin/loudong/list")
    public  JsonResult  list(){

        HashMap<String,Object>  map=new HashMap<>();
        String ispaged=request.getParameter("ispaged");

        String bjid = request.getParameter("bjid");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);
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
        if(!"-1".equals(ispaged)) {
            PageHelper.startPage(pageindex,pagesize);
            List<Loudong> listLoudong = loudongSrv.getEntityPlus(map);
            PageInfo<Loudong> pageInfo=new PageInfo<Loudong>(listLoudong);
            return JsonResult.success(1,"成功",pageInfo);
        }
        return JsonResult.success(1,"获取成功", loudongSrv.getEntityPlus(map));


    }




    //##{{methods}}


}
