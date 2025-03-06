package com.daowen.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.daowen.util.JsonResult;
import com.daowen.util.MailUtil;
import com.daowen.util.MimeMessageDescription;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.daowen.entity.*;
import com.daowen.service.*;
import com.daowen.ssm.simplecrud.SimpleController;
import com.daowen.webcontrol.PagerMetal;
import org.springframework.web.bind.annotation.ResponseBody;

/**************************
 *
 *学生管理控制
 *
 */
@Controller
public class HuiyuanController extends SimpleController {
    @Autowired
    private HuiyuanService huiyuanSrv = null;
    @Autowired
    private BunitService bunitSrv=null;




    @ResponseBody
    @PostMapping("/admin/huiyuan/forgetpw")
    public JsonResult forgetpw() {
        String accountname = request.getParameter("accountname");
        Huiyuan h = huiyuanSrv.load("where accountname='" + accountname + "'");
        if (h == null)
            return JsonResult.error(-1, "不存在的账号");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", "/forgetpwnext.jsp?id=" + h.getId());
        return JsonResult.success(1, "", jsonObject);
    }

    @ResponseBody
    @PostMapping("/admin/huiyuan/sendpwemail")
    public JsonResult sendpwemail() {
        String id = request.getParameter("id");
        Huiyuan h = huiyuanSrv.load("where id=" + id);
        if (h == null)
            return JsonResult.error(-1, "不存在的账号");
        MimeMessageDescription mmd = new MimeMessageDescription();
        mmd.setReceAccount(h.getAddress());
        mmd.setReceAccountRemark(h.getName());
        mmd.setSubject("忘记密码-密码重置邮件");
        mmd.setContent(MessageFormat.format("亲忘记密码<a href=\"http://localhost:8080{0}/resetpw.jsp?id={1,number,#}\">重置密码</a>", request.getContextPath(), h.getId()));
        MailUtil.send(mmd);
        return JsonResult.success(1, "");
    }




    @ResponseBody
    @PostMapping("/admin/huiyuan/chongzhi")
    public JsonResult chongzhi() {
        String amount = request.getParameter("amount");

        String hyid = request.getParameter("hyid");
        if (hyid == null || hyid == "")
            return JsonResult.error(-1,"账户编号不能为空");
        Huiyuan huiyuan = huiyuanSrv.load(new Integer(hyid));
        if (huiyuan == null)
            return JsonResult.error(-1,"账户不合法");

        huiyuan.setYue(huiyuan.getYue() + Double.valueOf(amount));
        huiyuanSrv.update(huiyuan);
        request.getSession().setAttribute("huiyuan", huiyuan);

        return JsonResult.success(1,"充值成功");
    }



    @ResponseBody
    @PostMapping("/admin/huiyuan/modifypaypw")
    public JsonResult modifyPaypw() {
        String password1 = request.getParameter("password1");
        String repassword1 = request.getParameter("repassword1");

        String id = request.getParameter("id");
        if (id == null || id == "")
            return JsonResult.error(-1, "id不能为空");
        Huiyuan huiyuan = huiyuanSrv.load(new Integer(id));
        if (huiyuan == null)
            return JsonResult.error(-2, "数据不能为空");

        if (!huiyuan.getPassword().equals(password1))
            return JsonResult.error(-3, "原始密码不正确，不能修改");

        huiyuan.setPaypwd(repassword1);
        huiyuanSrv.update(huiyuan);
        request.getSession().setAttribute("huiyuan", huiyuan);
        return JsonResult.success(1, "成功");

    }



    @ResponseBody
    @PostMapping("/admin/huiyuan/modifypw")
    public JsonResult modifyPw() {
        String password1 = request.getParameter("password1");
        String repassword1 = request.getParameter("repassword1");

        String id = request.getParameter("id");
        if (id == null || id == "")
            return JsonResult.error(-1, "id不能为空");
        Huiyuan huiyuan = huiyuanSrv.load(new Integer(id));
        if (huiyuan == null)
            return JsonResult.error(-2, "数据不能为空");

        if (!huiyuan.getPassword().equals(password1))
            return JsonResult.error(-3, "原始密码不正确，不能修改");

        huiyuan.setPassword(repassword1);
        huiyuanSrv.update(huiyuan);
        request.getSession().setAttribute("huiyuan", huiyuan);
        return JsonResult.success(1, "成功");

    }


    @ResponseBody
    @PostMapping("/admin/huiyuan/save")
    public JsonResult save() {

        String accountname = request.getParameter("accountname");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        String idcardno = request.getParameter("idcardno");
        String photo = request.getParameter("photo");
        String jiguan = request.getParameter("jiguan");
        String logintimes = request.getParameter("logintimes");
        String yue = request.getParameter("yue");
        String birthday = request.getParameter("birthday");
        String jifen = request.getParameter("jifen");
        String address = request.getParameter("address");

        String bjid=request.getParameter("bjid");

        String des = request.getParameter("des");

        SimpleDateFormat sdfhuiyuan = new SimpleDateFormat("yyyy-MM-dd");
        Huiyuan huiyuan = new Huiyuan();
        huiyuan.setAccountname(accountname);
        huiyuan.setName(name == null ? "" : name);
        huiyuan.setSex(sex == null ? "" : sex);
        if (accountname != null)
            huiyuan.setTel(huiyuan.getAccountname());
        else
            huiyuan.setTel(tel);
        huiyuan.setIdcardno(idcardno == null ? "" : idcardno);

        huiyuan.setPhoto(photo == null ? "" : photo);
        huiyuan.setBjid(bjid==null?1:new Integer(bjid));
        huiyuan.setState(1);
        huiyuan.setJiguan(jiguan == null ? "" : jiguan);
        huiyuan.setCreatetime(new Date());
        huiyuan.setLogintimes(logintimes == null ? 0 : new Integer(logintimes));
        huiyuan.setYue(yue == null ? (double) 0 : new Double(yue));
        if (birthday != null) {
            try {
                huiyuan.setBirthday(sdfhuiyuan.parse(birthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            huiyuan.setBirthday(new Date());
        }
        huiyuan.setJifen(jifen == null ? 0 : new Integer(jifen));
        huiyuan.setAddress(address == null ? "" : address);
        huiyuan.setDes(des == null ? "" : des);
        huiyuan.setPassword(password==null?"123456":password);
        huiyuan.setPaypwd(huiyuan.getPassword());
        huiyuan.setLdid(0);
        huiyuan.setUnitid(0);
        Boolean validateresult = huiyuanSrv.isExist("where accountname='" + accountname + "'");
        if (validateresult)
            return JsonResult.error(-1, "已存在的学号");

        huiyuanSrv.save(huiyuan);

        return JsonResult.success(1, "成功", huiyuan);
    }


    @ResponseBody
    @PostMapping("/admin/huiyuan/update")
    public JsonResult update() {

        String id = request.getParameter("id");
        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Huiyuan huiyuan = huiyuanSrv.load("where id=" + id);
        if (huiyuan == null)
            return JsonResult.error(-2, "非法数据");

        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        String idcardno = request.getParameter("idcardno");
        String bjid = request.getParameter("bjid");
        String photo = request.getParameter("photo");
        String jiguan = request.getParameter("jiguan");
        String logintimes = request.getParameter("logintimes");
        String birthday = request.getParameter("birthday");

        String address = request.getParameter("address");
        String des = request.getParameter("des");
        SimpleDateFormat sdfhuiyuan = new SimpleDateFormat("yyyy-MM-dd");

        huiyuan.setName(name==null?"":name);
        huiyuan.setSex(sex);
        if(tel!=null)
           huiyuan.setTel(tel);
        huiyuan.setIdcardno(idcardno);
        huiyuan.setPhoto(photo==null?"/upload/nopic.jpg":photo);
        huiyuan.setJiguan(jiguan==null?"":jiguan);
        huiyuan.setLogintimes(logintimes == null ? 0 : new Integer(logintimes));
        if (birthday != null) {
            try {
                huiyuan.setBirthday(sdfhuiyuan.parse(birthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        huiyuan.setAddress(address==null?"":address);

        huiyuan.setBjid(bjid==null?1:new Integer(bjid));
        huiyuan.setDes(des==null?"":des);
        huiyuanSrv.update(huiyuan);
        return JsonResult.success(1, "成功", huiyuan);
    }

    @ResponseBody
    @PostMapping("/admin/huiyuan/delete")
    public JsonResult delete() {
        String[] ids = request.getParameterValues("ids");
        if (ids == null)
            return JsonResult.error(-1, "ids不能为空");
        String spliter = ",";
        String where = " where id  in(" + join(spliter, ids) + ")";
        huiyuanSrv.delete(where);
        return JsonResult.success(1, "不能为空");
    }

    @ResponseBody
    @RequestMapping({"/admin/huiyuan/load","/admin/huiyuan/info"})
    public JsonResult load() {
        String id = request.getParameter("id");

        if (id == null)
            return JsonResult.error(-1, "ID不能为空");
        Huiyuan huiyuan = huiyuanSrv.loadPlus(new Integer(id));
        if (huiyuan == null)
            return JsonResult.error(-2, "非法数据");
        return JsonResult.success(1, "成功", huiyuan);

    }



    @ResponseBody
    @PostMapping("/admin/huiyuan/list")
    public JsonResult list() {

        HashMap<String, Object> map = new HashMap<>();
        String ispaged = request.getParameter("ispaged");
        String ldid=request.getParameter("ldid");
        String unitid=request.getParameter("unitid");
        String bjid=request.getParameter("bjid");

        String accountname = request.getParameter("accountname");
        String name = request.getParameter("name");

        if (accountname != null)
            map.put("accountname", accountname);
        if (name != null)
            map.put("name", name);
        if(ldid!=null)
            map.put("ldid",ldid);
        if(unitid!=null)
            map.put("unitid",unitid);
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
            List<Huiyuan> listHuiyuan = huiyuanSrv.getEntityPlus(map);
            PageInfo<Huiyuan> pageInfo = new PageInfo<Huiyuan>(listHuiyuan);
            return JsonResult.success(1, "成功", pageInfo);
        }
        return JsonResult.success(1, "获取成功", huiyuanSrv.getEntityPlus(map));


    }



    @ResponseBody
    @PostMapping("/admin/huiyuan/count")
    public JsonResult counttongji() {
        String sql = " select  case xtype when xtype=1 then '重点学生' when xtype=2 then '非重点学生' else '非重点学生' end as name,count(*) as value from huiyuan group by xtype ";
        List<HashMap<String, Object>> listTongji = huiyuanSrv.queryToMap(sql);
        return JsonResult.success(1, "", listTongji);
    }


}
