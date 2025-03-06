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
public class ShangpinController extends SimpleController {

    @Autowired
    private ShangpinService shangpinSrv = null;




    @ResponseBody
    @PostMapping("/admin/shangpin/count")
    public JsonResult counttongji(){
        String sql=" select t.name as name,count(*) as value from shangpin s,spcategory t where t.id=s.typeid group by t.name,t.id  ";
        List<HashMap<String,Object>> listTongji=shangpinSrv.queryToMap(sql);
        return  JsonResult.success(1,"",listTongji);
    }



    @PostMapping("/admin/shangpin/saledstat")
    public JsonResult saleStat(){
        String sql=" select sp.name as name,sum(count) as value from shangpin sp,orderitem oi where sp.id=oi.spid  ";

        sql+=" group by sp.id,sp.name ";
        List<HashMap<String,Object>> listTongji=shangpinSrv.queryToMap(sql);
        return  JsonResult.success(1,"",listTongji);
    }

    //
    @PostMapping("/admin/shangpin/save")
    public JsonResult save() {

        String name = request.getParameter("name");

        String typeid = request.getParameter("typeid");

        String tupian = request.getParameter("tupian");

        String price = request.getParameter("price");

        String pinpai = request.getParameter("pinpai");

        String xinghao = request.getParameter("xinghao");

        String kucun = request.getParameter("kucun");

        String danwei = request.getParameter("danwei");

        String createtime = request.getParameter("createtime");

        String des = request.getParameter("des");


        Shangpin shangpin = new Shangpin();


        shangpin.setName(name == null ? "" : name);


        shangpin.setTypeid(typeid == null ? 0 : new Integer(typeid));


        shangpin.setTupian(tupian == null ? "" : tupian);


        shangpin.setPrice(price == null ? 0 : new Double(price));


        shangpin.setPinpai(pinpai == null ? "" : pinpai);


        shangpin.setXinghao(xinghao == null ? "" : xinghao);


        shangpin.setKucun(kucun == null ? 0 : new Integer(kucun));


        shangpin.setDanwei(danwei == null ? "" : danwei);



        shangpin.setCreatetime(new Date());



        shangpin.setDes(des == null ? "" : des);


        //end forEach

        Boolean validateresult = shangpinSrv.isExist("  where  name='" + name + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");

        shangpinSrv.save(shangpin);
        return JsonResult.success(1, "成功", shangpin);
    }

    @PostMapping("/admin/shangpin/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Shangpin shangpin = shangpinSrv.load("where id=" + id);
        if (shangpin == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");

        String typeid = request.getParameter("typeid");

        String tupian = request.getParameter("tupian");

        String price = request.getParameter("price");

        String pinpai = request.getParameter("pinpai");

        String xinghao = request.getParameter("xinghao");

        String kucun = request.getParameter("kucun");

        String danwei = request.getParameter("danwei");

        String createtime = request.getParameter("createtime");

        String des = request.getParameter("des");


        shangpin.setName(name == null ? "" : name);


        shangpin.setTypeid(typeid == null ? 0 : new Integer(typeid));


        shangpin.setTupian(tupian == null ? "" : tupian);


        shangpin.setPrice(price == null ? 0 : new Double(price));


        shangpin.setPinpai(pinpai == null ? "" : pinpai);

        shangpin.setXinghao(xinghao == null ? "" : xinghao);


        shangpin.setKucun(kucun == null ? 0 : new Integer(kucun));


        shangpin.setDanwei(danwei == null ? "" : danwei);


        if (createtime != null) {

            shangpin.setCreatetime(new Date());
        }


        shangpin.setDes(des == null ? "" : des);


        shangpinSrv.update(shangpin);
        return JsonResult.success(1, "成功", shangpin);

    }

    @PostMapping("/admin/shangpin/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        shangpinSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/shangpin/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Shangpin shangpin = shangpinSrv.loadPlus(new Integer(id));
        if (shangpin == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", shangpin);

    }

    @RequestMapping("/admin/shangpin/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Shangpin shangpin = shangpinSrv.loadPlus(new Integer(id));
        if (shangpin == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", shangpin);

    }


    @PostMapping("/admin/shangpin/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");
        String typeid=request.getParameter("typeid");

        String name = request.getParameter("name");
        if (name != null)
            map.put("name", name);

        if(typeid!=null)
            map.put("typeid",typeid);

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
            List<Shangpin> listShangpin = shangpinSrv.getEntityPlus(map);
            PageInfo<Shangpin> pageInfo = new PageInfo<Shangpin>(listShangpin);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", shangpinSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
