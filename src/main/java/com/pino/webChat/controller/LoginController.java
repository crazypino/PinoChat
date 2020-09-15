package com.pino.webChat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pino.webChat.exception.R;
import com.pino.webChat.pojo.Login;
import com.pino.webChat.pojo.Users;
import com.pino.webChat.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@GetMapping("/")
	public String tologin() {
		return "user/login";
	}
	
	@GetMapping("/login")
	public String tologin2() {
		return "user/login";
	}
	
	@PostMapping("/dologin")
	@ResponseBody
	public R login(@RequestBody Login login, HttpSession session) {
		Users user = loginService.doLogin(login);
		
		if (user == null) {
			return R.error().message("wrong account or password");
		}else {
			session.setAttribute("userid", user.getUsername());
			return R.ok().message("Login successfully!");
		}
	}
}
