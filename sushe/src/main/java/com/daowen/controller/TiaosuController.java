package com.daowen.controller;

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

//##{{import}}
@Controller
public class TiaosuController extends SimpleController {

    @Autowired
    private TiaosuService tiaosuSrv = null;
    @Autowired
    private BunitService  bunitSrv=null;
    @Autowired
    private HuiyuanService huiyuanSrv=null;

    //
    @ResponseBody
    @PostMapping("/admin/tiaosu/save")
    public JsonResult save() {

        String ddno = request.getParameter("ddno");
        String title = request.getParameter("title");
        String applyor = request.getParameter("applyor");
        String ldid = request.getParameter("ldid");
        String unitid = request.getParameter("unitid");
        String oldqs = request.getParameter("oldqs");
        String des = request.getParameter("des");
        String spdes = request.getParameter("spdes");
        SimpleDateFormat sdftiaosu = new SimpleDateFormat("yyyy-MM-dd");
        Tiaosu tiaosu = new Tiaosu();
        tiaosu.setDdno(SequenceUtil.buildSequence("D"));
        tiaosu.setTitle(title == null ? "" : title);
        tiaosu.setCreatetime(new Date());
        tiaosu.setApplyor(applyor == null ? 0 : new Integer(applyor));
        tiaosu.setState(1);
        tiaosu.setLdid(ldid == null ? 0 : new Integer(ldid));
        tiaosu.setUnitid(unitid == null ? 0 : new Integer(unitid));
        tiaosu.setOldqs(oldqs == null ? "" : oldqs);
        tiaosu.setDes(des == null ? "" : des);
        tiaosu.setSpdes(spdes == null ? "" : spdes);
        Boolean validateresult = tiaosuSrv.isExist("  where  ddno='" + ddno + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");
        tiaosuSrv.save(tiaosu);
        return JsonResult.success(1, "成功", tiaosu);
    }

    @ResponseBody
    @PostMapping("/admin/tiaosu/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Tiaosu tiaosu = tiaosuSrv.load("where id=" + id);
        if (tiaosu == null)
            return JsonResult.error(-2, "非法数据");

        String title = request.getParameter("title");
        String applyor = request.getParameter("applyor");
        String ldid = request.getParameter("ldid");
        String unitid = request.getParameter("unitid");
        String oldqs = request.getParameter("oldqs");
        String des = request.getParameter("des");
        String spdes = request.getParameter("spdes");
        tiaosu.setTitle(title == null ? "" : title);
        tiaosu.setCreatetime(new Date());
        tiaosu.setApplyor(applyor == null ? 0 : new Integer(applyor));
        tiaosu.setLdid(ldid == null ? 0 : new Integer(ldid));
        tiaosu.setUnitid(unitid == null ? 0 : new Integer(unitid));
        tiaosu.setOldqs(oldqs == null ? "" : oldqs);
        tiaosu.setDes(des == null ? "" : des);
        tiaosu.setSpdes(spdes == null ? "" : spdes);
        tiaosuSrv.update(tiaosu);
        return JsonResult.success(1, "成功", tiaosu);

    }

    @ResponseBody
    @PostMapping("/admin/tiaosu/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        tiaosuSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping({"/admin/tiaosu/load", "/admin/tiaosu/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Tiaosu tiaosu = tiaosuSrv.loadPlus(new Integer(id));
        if (tiaosu == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", tiaosu);

    }


    @ResponseBody
    @PostMapping("/admin/tiaosu/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String ddno = request.getParameter("ddno");
        String applyor = request.getParameter("applyor");
        if (ddno != null)
            map.put("ddno", ddno);
        if(applyor!=null)
        	map.put("applyor",applyor);

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
            List<Tiaosu> listTiaosu = tiaosuSrv.getEntityPlus(map);
            PageInfo<Tiaosu> pageInfo = new PageInfo<Tiaosu>(listTiaosu);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", tiaosuSrv.getEntityPlus(map));


    }

    @ResponseBody
    @PostMapping("/admin/tiaosu/shenpi")
    public JsonResult  shenpi(){

        String id=request.getParameter("id");

        String  state =request.getParameter("state");
        String  reply=request.getParameter("replydes");
        String  shenpiren=request.getParameter("shenpiren");
        int stateCode=2;

        if(id==null)
            return JsonResult.error(-1,"id不能为空");;
        Tiaosu  tiaosu=tiaosuSrv.load(" where id="+ id);

        if(tiaosu==null)
            return JsonResult.error(-1,"数据非法");;
        if(state!=null)
            stateCode=Integer.parseInt(state);
        tiaosu.setState(stateCode);
        tiaosu.setSpdes(reply==null?"":reply);

        if(tiaosu.getState()==2){
          Bunit bunit=bunitSrv.loadPlus(new Integer(tiaosu.getUnitid()));
          if(bunit!=null) {
              if(bunit.getMaxcount()<=bunit.getCicount()){
                  return JsonResult.error(-1,"宿舍人员已满不能分配");
              }
              Huiyuan huiyuan = huiyuanSrv.load("where id=" + tiaosu.getApplyor());
              huiyuan.setLdid(tiaosu.getLdid());
              huiyuan.setUnitid(tiaosu.getUnitid());
              huiyuan.setState(2);
              tiaosuSrv.update(tiaosu);
              huiyuanSrv.update(huiyuan);
              bunit.setCicount(bunit.getCicount()+1);
              bunitSrv.update(bunit);
          }

        }
        return JsonResult.success(1,"审核成功");


    }


    //##{{methods}}

}
