package com.daowen.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
public class QspingfenController extends SimpleController {

    @Autowired
    private QspingfenService qspingfenSrv = null;




    @PostMapping("/admin/chengji/scorescope")
    public JsonResult scorescope(){


        String weekid=request.getParameter("weekid");

        HashMap<String,Object> map=new HashMap<>();
        if(weekid!=null&&!"-1".equals(weekid))
            map.put("weekid",weekid);
        List<HashMap<String,Object>> listMap=qspingfenSrv.getScorescope(map);
        List<HashMap<String,Object>> listScore=new ArrayList<>();
        if(listMap!=null&&listMap.size()>0){
            HashMap<String, Object> map1 = listMap.get(0);
            HashMap map2=new HashMap();
            map2.put("name","不合格寝室");
            map2.put("value",map1.get("failcount"));
            listScore.add(map2);
            map2=new HashMap();
            map2.put("name","合格寝室");
            map2.put("value",map1.get("passcount"));
            listScore.add(map2);
            map2=new HashMap();
            map2.put("name","优秀寝室");
            map2.put("value",map1.get("goodcount"));
            listScore.add(map2);

        }
        return  JsonResult.success(1,"成绩分布",listScore);
    }

    @PostMapping("/admin/chengji/avgscore")
    public JsonResult avgscore(){

        String weekid=request.getParameter("weekid");

        HashMap<String,Object> map=new HashMap<>();
        if(weekid!=null&&!"-1".equals(weekid))
            map.put("weekid",weekid);

        List<HashMap<String,Object>> listMap=qspingfenSrv.getAvgscore(map);
        return  JsonResult.success(1,"获取平均成绩",listMap);
    }

    //
    @PostMapping("/admin/qspingfen/save")
    public JsonResult save() {

        String weekid = request.getParameter("weekid");

        String score = request.getParameter("score");

        String pfren = request.getParameter("pfren");

        String qsid=request.getParameter("qsid");
        String des = request.getParameter("des");

        SimpleDateFormat sdfqspingfen = new SimpleDateFormat("yyyy-MM-dd");
        Qspingfen qspingfen = new Qspingfen();
        qspingfen.setWeekid(weekid == null ? 0 : new Integer(weekid));
        qspingfen.setScore(score == null ? 0 : new Double(score));
        qspingfen.setPfren(pfren == null ? "" : pfren);
        qspingfen.setCreatetime(new Date());
        qspingfen.setQsid(qsid==null?0:new Integer(qsid));
        qspingfen.setDes(des == null ? "" : des);
        //end forEach
        Boolean validateresult = qspingfenSrv.isExist(MessageFormat.format("  where  weekid={0} and qsid={1} ",weekid,qsid));
        if (validateresult)
            return JsonResult.error(-1, "已存在的记录");
        qspingfenSrv.save(qspingfen);
        return JsonResult.success(1, "成功", qspingfen);
    }

    @PostMapping("/admin/qspingfen/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Qspingfen qspingfen = qspingfenSrv.load("where id=" + id);
        if (qspingfen == null)
            return JsonResult.error(-2, "非法数据");

        String weekid = request.getParameter("weekid");
        String qsid=request.getParameter("qsid");

        String score = request.getParameter("score");

        String pfren = request.getParameter("pfren");



        String des = request.getParameter("des");

        SimpleDateFormat sdfqspingfen = new SimpleDateFormat("yyyy-MM-dd");
        qspingfen.setWeekid(weekid == null ? 0 : new Integer(weekid));
        qspingfen.setScore(score == null ? 0 : new Double(score));
        qspingfen.setPfren(pfren == null ? "" : pfren);
        qspingfen.setCreatetime(new Date());
        qspingfen.setQsid(qsid==null?0:new Integer(qsid));
        qspingfen.setDes(des == null ? "" : des);
        qspingfenSrv.update(qspingfen);
        return JsonResult.success(1, "成功", qspingfen);

    }

    @PostMapping("/admin/qspingfen/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        qspingfenSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @RequestMapping("/admin/qspingfen/load")
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Qspingfen qspingfen = qspingfenSrv.loadPlus(new Integer(id));
        if (qspingfen == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", qspingfen);

    }

    @RequestMapping("/admin/qspingfen/info")
    public JsonResult info() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Qspingfen qspingfen = qspingfenSrv.loadPlus(new Integer(id));
        if (qspingfen == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", qspingfen);

    }

    @PostMapping("/admin/qspingfen/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");

        String qsid=request.getParameter("qsid");
        String weekid = request.getParameter("weekid");
        if (weekid != null&&!"-1".equals(weekid))
            map.put("weekid", weekid);
        if(qsid!=null)
            map.put("qsid",qsid);


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
            List<Qspingfen> listQspingfen = qspingfenSrv.getEntityPlus(map);
            PageInfo<Qspingfen> pageInfo = new PageInfo<Qspingfen>(listQspingfen);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", qspingfenSrv.getEntityPlus(map));


    }

    //##{{methods}}


}
