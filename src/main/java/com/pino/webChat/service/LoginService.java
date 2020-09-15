package com.pino.webChat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pino.webChat.pojo.Login;
import com.pino.webChat.pojo.Users;

@Service
public class LoginService {
	
	@Autowired
	UserService userService;
	
	public Users doLogin(Login login) {
		String username = login.getUsername();
		String password = login.getPassword();
		
		Users user = userService.query(username);
		
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}else {
			return null;
		}
	}
}
