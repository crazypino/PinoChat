package com.pino.webChat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pino.webChat.pojo.ChatHistory;
import com.pino.webChat.pojo.ChatMsg;
import com.pino.webChat.pojo.Users;

@Service
public class ChatMsgService {
	
	@Autowired
	UserService userService;
	
	public ChatHistory getChatHistory(Users user1, Users user2) {
		return user1.getHistory(user2.getUsername());
	}
	
	public void insertMsg(String username, ChatMsg msg) throws Exception{
		Users user1 = userService.query(username);
		///*********8
		//System.out.println("InsetMsg: "+ user1.getUsername());
		user1.insertMsg(msg);
		userService.update(user1);
	}
}
