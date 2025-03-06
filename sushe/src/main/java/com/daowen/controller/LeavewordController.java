package com.daowen.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daowen.util.JsonResult;
import com.daowen.vo.LeavewordVo;
import com.sun.org.glassfish.gmbal.ParameterNames;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;

//##{{import}}
@Controller
public class LeavewordController extends SimpleController {

    @Autowired
    private LeavewordService leavewordSrv = null;


    @ResponseBody
    @PostMapping("/admin/leaveword/list")
    public JsonResult  list(){
        HashMap<String,Object> map=new HashMap<>();
        String hyid=request.getParameter("hyid");
        String ispaged = request.getParameter("ispaged");

        if(hyid!=null)
            map.put("hyid",hyid);
        map.put("order","order by id desc");

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
            List<LeavewordVo> listLeaveword = leavewordSrv.getEntityPlus(map);
            PageInfo<LeavewordVo> pageInfo = new PageInfo<>(listLeaveword);
            return JsonResult.success(1, "成功", pageInfo);
        }

        return JsonResult.success(1,"获取留言信息",leavewordSrv.getEntityPlus(map));
    }

    @ResponseBody
    @PostMapping("/admin/leaveword/delete")
    public JsonResult  delete(){

        String []ids=request.getParameterValues("ids");
        if(ids==null)
            return JsonResult.error(-1,"参数异常");

        for(String id : ids){
            leavewordSrv.delete("where id="+id);
        }
        return JsonResult.success(1,"删除成功");
    }

    @ResponseBody
    @PostMapping("/admin/leaveword/save")
    public JsonResult save() {
        String dcontent = request.getParameter("dcontent");
        String hyid = request.getParameter("hyid");
        String replyren = request.getParameter("replyren");
        SimpleDateFormat sdfleaveword = new SimpleDateFormat("yyyy-MM-dd");
        Leaveword leaveword = new Leaveword();
        leaveword.setDcontent(dcontent == null ? "" : dcontent);
        leaveword.setPubtime(new Date());
        leaveword.setState(1);
        leaveword.setHyid(hyid == null ? 0 : new Integer(hyid));
        leaveword.setReplytime(new Date());
        leaveword.setReplyren(replyren == null ? "" : replyren);
        leavewordSrv.save(leaveword);
        return JsonResult.success(1,"留言成功",leaveword);
    }

    @ResponseBody
    @PostMapping("/admin/leaveword/reply")
    public JsonResult reply() {
        String id = request.getParameter("id");
        String replyren = request.getParameter("replyren");
        if (id == null)
            return JsonResult.error(-1,"id不能为空");
        Leaveword leaveword = leavewordSrv.load("where id="+id);
        if (leaveword == null)
            return JsonResult.error(-2,"非法数据");
        String replycontent = request.getParameter("replycontent");
        leaveword.setReplycontent(replycontent);
        leaveword.setReplytime(new Date());
        leaveword.setReplyren(replyren);
        leaveword.setState(2);
        leavewordSrv.update(leaveword);
        return JsonResult.success(1,"成功");

    }

    //##{{methods}}


}
