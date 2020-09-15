package com.pino.webChat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import com.pino.webChat.pojo.Users;

@Service
public class UserService {
	@Autowired
	RedisTemplate redisTemplate;
	
	public void insert(String usrname, String passwd, String nickname, String uimg) throws Exception{
		if (isExist(usrname)) return;
		
		Users user = new Users();
		user.setUsername(usrname);
		user.setPassword(passwd);
		user.setNickname(nickname);
		user.setUimg(uimg);
		redisTemplate.opsForValue().set(usrname, user);
	}
	
	public void update(Users usr) throws Exception{
		
		String usrname = usr.getUsername();
		if (query(usrname) != null) {
			redisTemplate.opsForValue().set(usrname, usr);
		}
	}
	
	public void delete(String usrname) throws Exception{
		if (!isExist(usrname)) {
			return;
		}else {
			redisTemplate.delete(usrname);
		}
	}
	public boolean isExist(String usrname) {
		return redisTemplate.hasKey(usrname);
	}
	
	public Users query(String usrname) {
		if (!isExist(usrname)) {
			return null;
		}
		
		Users user = (Users) redisTemplate.opsForValue().get(usrname);
		
		return user;
	}

	
}
