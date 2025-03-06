package com.daowen.controller;

import java.util.List;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;

import com.daowen.vo.XinxiVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;

/**************************
 *
 * 资讯资讯控制
 *
 */
@RestController
public class XinxiController extends SimpleController {
    @Autowired
    private XinxiService xinxiSrv = null;
    @Autowired
    private LanmuService lanmuSrv = null;


    @PostMapping("/admin/xinxi/clickstat")
    public JsonResult clickcounttongji(){
        String sql=" select lm.name as name,sum(clickcount) as value from xinxi x,lanmu lm where lm.id=x.lmid group by lm.id ,lm.name ";
        List<HashMap<String,Object>> listXinxi=xinxiSrv.queryToMap(sql);
        return  JsonResult.success(1,"",listXinxi);
    }


    @PostMapping("/admin/xinxi/count")
    public JsonResult counttongji(){
        String sql=" select lm.name as name,count(*) as value from xinxi x,lanmu lm where lm.id=x.lmid group by lm.id ,lm.name   ";
        List<HashMap<String,Object>> listXinxi=xinxiSrv.queryToMap(sql);
        return  JsonResult.success(1,"",listXinxi);
    }


    @PostMapping("/admin/xinxi/mylanmu")
    public JsonResult mylanmu(){
        List<Lanmu> listLanmu=lanmuSrv.getEntityPlus(null);
        return JsonResult.success(1,"获取栏目",listLanmu);
    }

    @PostMapping("/admin/xinxi/cascadelanmu")
    public JsonResult CascadeList(){

        List<Lanmu> listLanmu = lanmuSrv.getEntityPlus(null);
        JSONArray jsonArray=new JSONArray();
        if(listLanmu!=null)
            listLanmu.forEach(c->{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("value",c.getId());
                jsonObject.put("label",c.getName());

                jsonArray.add(jsonObject);
            });
        return JsonResult.success(1,"获取成功",jsonArray);
    }





    @PostMapping("/admin/xinxi/list")
    public  JsonResult list(){
        String lmid=request.getParameter("lmid");
        String title = request.getParameter("title");
        String pubren= request.getParameter("pubren");
        String tuijian=request.getParameter("tuijian");
        String ispaged=request.getParameter("ispaged");
        String subtypeid= request.getParameter("subtypeid");
        HashMap<String ,Object> map=new HashMap();
        if (title != null)
            map.put("title",title);
        if(!"0".equals(lmid))
            map.put("lmid",lmid);
        if(subtypeid!=null)
            map.put("subtypeid",subtypeid);
        if(pubren!=null)
            map.put("pubren",pubren);
        if(tuijian!=null)
            map.put("tuijian",tuijian);
        map.put("order"," order by id desc ");
        //
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
            PageHelper.startPage(pageindex, pagesize);
            PageInfo<XinxiVo> pageInfo = new PageInfo<XinxiVo>( xinxiSrv.getEntityPlus(map));
            return JsonResult.success(1, "获取成功", pageInfo);
        }
        return JsonResult.success(1,"获取成功",xinxiSrv.getEntityPlus(map));
    }



    @PostMapping("/admin/xinxi/hot")
    public  JsonResult hotxinxi(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("tuijian",1);
        map.put("order","order by clickcount desc");
        map.put("topcount",10);
        List<XinxiVo> listXinxi=xinxiSrv.getEntityPlus(map);
        return JsonResult.success(1,"获取信息",listXinxi);
    }


    @PostMapping("/admin/xinxi/load")
    public  JsonResult load(){
        String id=request.getParameter("id");
        if(id==null)
            return JsonResult.error(-1,"参数异常");
        XinxiVo xinxi=xinxiSrv.loadPlus(Integer.parseInt(id));
        return JsonResult.success(1,"获取信息",xinxi);
    }

    @PostMapping("/admin/xinxi/info")
    public  JsonResult info(){
        String id=request.getParameter("id");
        if(id==null)
            return JsonResult.error(-1,"参数异常");
        XinxiVo xinxi=xinxiSrv.loadPlus(Integer.parseInt(id));
        xinxiSrv.executeUpdate("update xinxi set clickcount=clickcount+1 where id="+id);

        return JsonResult.success(1,"获取信息",xinxi);
    }



	@GetMapping("/admin/xinxi/related")
    public JsonResult  related(){
       String id=request.getParameter("id");
       if(id==null||id.equals(""))
       	  return JsonResult.error(-1,"参数异常");
		List<Xinxi> relateds = xinxiSrv.getRelated(Integer.parseInt(id));
		return JsonResult.success(1,"",relateds);
	}


	@PostMapping("/admin/xinxi/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1,"ids不能为空");

        String SQL = " where id in(" + String.join(",", ids)+ ")";
        System.out.println("sql=" + SQL);
        xinxiSrv.delete(SQL);
        return JsonResult.success(1,"删除成功");
    }



    @PostMapping("/admin/xinxi/save")
    public JsonResult save() {

        //获取标题
        String title = request.getParameter("title");
        //获取图片
        String tupian = request.getParameter("tupian");
        //获取栏目编号
        String lmid = request.getParameter("lmid");
        String subtypeid= request.getParameter("subtypeid");
        String pubren=request.getParameter("pubren");
        String state = request.getParameter("state");
        String tagid=request.getParameter("tagid");
        String fileurl=request.getParameter("fileurl");
        String tuijian=request.getParameter("tuijian");
        //获取备注
        String dcontent = request.getParameter("dcontent");
        SimpleDateFormat sdfxinxi = new SimpleDateFormat("yyyy-MM-dd");
        Xinxi xinxi = new Xinxi();
        // 设置标题
        xinxi.setTitle(title == null ? "" : title);
        // 设置图片
        xinxi.setTupian(tupian == null ? "" : tupian);
        // 设置栏目编号
        xinxi.setLmid(lmid == null ? 0 : new Integer(lmid));
        xinxi.setSubtypeid(subtypeid==null?0:new Integer(subtypeid));
        // 设置点击率
        xinxi.setClickcount(0);
        xinxi.setTuijian(tuijian==null?1:new Integer(tuijian));

        xinxi.setTagid(tagid==null?0:new Integer(tagid));

        xinxi.setState(state==null?2:Integer.parseInt(state));

        xinxi.setPubtime(new Date());

        xinxi.setPubren(pubren==null?"":pubren);

        // 设置备注
        xinxi.setDcontent(dcontent == null ? "" : dcontent);
        xinxiSrv.save(xinxi);
        return JsonResult.success(1,"新增成功");
    }

    @PostMapping("/admin/xinxi/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1,"id不能为空");
        Xinxi xinxi = xinxiSrv.load(new Integer(id));
        if (xinxi == null)
            return JsonResult.success(1,"非法数据");
        //获取标题
        String title = request.getParameter("title");
        //获取图片
        String tupian = request.getParameter("tupian");
        //获取栏目编号
        String lmid = request.getParameter("lmid");
        String tagid = request.getParameter("tagid");
        String fileurl = request.getParameter("fileurl");
        String tuijian=request.getParameter("tuijian");
        //获取栏目名
        String subtypeid = request.getParameter("subtypeid");
        //获取点击率
        String clickcount = request.getParameter("clickcount");
        //获取备注
        String dcontent = request.getParameter("dcontent");
        SimpleDateFormat sdfxinxi = new SimpleDateFormat("yyyy-MM-dd");
       // 设置标题
        xinxi.setTitle(title);
        // 设置图片
        xinxi.setTupian(tupian);
        // 设置栏目编号
        xinxi.setLmid(lmid == null ? 0 : new Integer(lmid));
        xinxi.setSubtypeid(subtypeid==null?0:new Integer(subtypeid));

        xinxi.setTagid(tagid==null?0:new Integer(tagid));

        xinxi.setTuijian(tuijian==null?1:new Integer(tuijian));
        // 设置点击率
        xinxi.setClickcount(clickcount == null ? 0 : new Integer(clickcount));
        xinxi.setPubtime(new Date());
        // 设置备注
        xinxi.setDcontent(dcontent==null?"":dcontent);
        xinxiSrv.update(xinxi);
        return JsonResult.success(1,"更新成功");
    }





}
