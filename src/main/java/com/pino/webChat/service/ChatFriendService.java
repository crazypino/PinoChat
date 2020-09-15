package com.pino.webChat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pino.webChat.pojo.ChatFriend;
import com.pino.webChat.pojo.ChatHistory;
import com.pino.webChat.pojo.Users;

@Service
public class ChatFriendService {
	@Autowired
	UserService userService;
	
	
	public boolean ifFriends(Users usr1, Users usr2) {
		if (usr1 == null || usr2 == null) {
			return false;
		}
		
		return usr1.containsFriend(usr2.getUsername());
	}
	
	public ChatHistory searchFriend(Users usr, String fname) {
		if (usr == null) return null;
		
		Users friend = userService.query(fname);
		
		if (friend == null || !ifFriends(usr, friend)) {
			return null;
		}
		
		return usr.getHistory(fname);
	}
	
	public List<Users> getChatFriends(String userid){
		Users user = userService.query(userid);
		List<String> list = user.getFriendList();
		List<Users> ret = new ArrayList<> ();
		for (String username : list) {
			Users usr = userService.query(username);
			ret.add(usr);
		}
		
		return ret;
	}
	
	public void addFriends(Users usr1, Users usr2) throws Exception {
		usr1.insertFriend(usr2);
		usr2.insertFriend(usr1);
		
		userService.update(usr1);
		userService.update(usr2);
		
	}
}
