package com.daowen.controller;



import javax.servlet.http.HttpSession;

import com.daowen.entity.Huiyuan;

import com.daowen.service.HuiyuanService;
import com.daowen.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daowen.entity.Users;
import com.daowen.service.UsersService;
import com.daowen.ssm.simplecrud.SimpleController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class AdminUserController extends SimpleController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private HuiyuanService huiyuanSrv=null;


	@ResponseBody
	@PostMapping("/admin/login")
	public JsonResult login() {
		String usertype = request.getParameter("usertype");

		if (usertype != null && usertype.equals("1")) {
			return adminLogin(1);
		}
		if (usertype != null && usertype.equals("2")) {
			return adminLogin(2);
		}
		if (usertype != null && usertype.equals("3")) {
			return adminLogin(3);
		}
		if (usertype != null && usertype.equals("4")) {
			return huiyuanLogin();
		}

		return JsonResult.error(-2,"账户类型不对");

	}

	private JsonResult adminLogin(int type) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HashMap<String,Object> map=new HashMap<>();
		map.put("username",username);

		Users u = usersService.loadPlus(map);
		if (u == null) {
			return JsonResult.error(-1,"用户名不存在");
		}

		if (password!=null&&!password.equals(u.getPassword())) {
			return JsonResult.error(-3,"用户名和密码不匹配");
		}
		if(u.getRoleid()!=type){
			return JsonResult.error(-4,"用户类型不对");
		}
		HttpSession session = request.getSession();
		usersService.executeUpdate("update users set logtimes=logtimes+1 where id="+u.getId());
		session.setAttribute("users", u);
		return JsonResult.success(1,"登录成功",u);
	}




	private JsonResult huiyuanLogin() {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HashMap<String, Object> map=new HashMap<>();
		if(username!=null)
			map.put("accountname",username);

		Huiyuan u = huiyuanSrv.loadPlus(map);

		if(u==null)
			return JsonResult.error(-1,"不存在的账户");

		if(u!=null&&!password.equals(u.getPassword()))
			return  JsonResult.error(-2,"不匹配的用户名和密码");

		return JsonResult.success(1,"登录成功",u);
	}

	@PostMapping("/adminuser/exit")
	@ResponseBody
	public JsonResult exit(){

		String usertype=request.getParameter("usertype");

		if("1".equals(usertype)) {
			if (request.getSession().getAttribute("users") != null) {
				request.getSession().removeAttribute("users");
			}
		}
		if("2".equals(usertype)) {
			if (request.getSession().getAttribute("huiyuan") != null) {
				request.getSession().removeAttribute("huiyuan");
			}
		}
		return  JsonResult.success(1,"退出成功");
	}



}

