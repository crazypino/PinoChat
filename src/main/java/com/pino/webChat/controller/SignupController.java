package com.pino.webChat.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pino.webChat.exception.R;
import com.pino.webChat.pojo.Login;
import com.pino.webChat.pojo.Signup;
import com.pino.webChat.pojo.Users;
import com.pino.webChat.service.UserService;

@Controller
public class SignupController {
	@Autowired
	UserService userService;
	
	@GetMapping("/signup")
	public String tologin() {
		return "user/signup";
	}
	
	@PostMapping("/dosignup")
	@ResponseBody
	public R login(@RequestBody Signup signup, HttpSession session) {
		String usrname = signup.getUsername();
		String pwd = signup.getPassword();
		String pwd2 = signup.getPassword2();
		String nickname = signup.getNickname();
		String uimg = signup.getUimg();
		
		Logger logger = Logger.getLogger("com.pino.webchat");
		if (pwd.equals(pwd2)){
			try {
				userService.insert(usrname, pwd, nickname, uimg);
				Users user = userService.query(usrname);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("sign up failed");
				e.printStackTrace();
				return R.error().message("Sign up failed!");
			}
			return R.ok().message("Signup successfully!");
		}else {
			logger.info("sign up failed");
			return R.error().message("passwords don't match!");
		}
	}
	
}
