package com.pino.webChat.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pino.webChat.service.UserService;

import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Users implements Serializable {
	
	@Autowired
	UserService userService;
	
	private String nickname;
	private String username;
	private String password;
	private String uimg;
	private HashMap<String, ChatHistory> chatList = new HashMap<> ();
	private List<String> friendList = new ArrayList<> ();
	
	public ChatHistory getHistory(String usr) {
		return chatList.get(usr);
	}
	
	public void insertMsg(ChatMsg msg) {
		
		if (!username.equals(msg.getSender()) && !username.equals(msg.getReceiver())) {
			System.out.println("Insert Message Error!");
			return;
		}else if (username.equals(msg.getSender())) {
			if (!chatList.containsKey(msg.getReceiver())) {
				Users receiver = userService.query(msg.getReceiver());
				insertFriend(receiver);
			}
			ChatHistory hist = chatList.get(msg.getReceiver());
			hist.insertMsg(msg);
			chatList.put(msg.getReceiver(), hist);
		}else {
			if (!chatList.containsKey(msg.getSender())) {
				
				Users sender = userService.query(msg.getSender());
				
				insertFriend(sender);
			}
			ChatHistory hist = chatList.get(msg.getSender());
			hist.insertMsg(msg);
			chatList.put(msg.getSender(), hist);
		}
	}
	
	public boolean containsFriend(String username) {
		return this.chatList.containsKey(username);
	}
	
	public void insertFriend(Users usr) {
		if (usr.getUsername() == null || containsFriend(usr.getUsername())) return;
		
		//ChatFriend friend = new ChatFriend().setUserid(usr.getUsername()).setFuserid(username).setNickname(usr.getNickname());
		this.friendList.add(usr.getUsername());
		
		this.chatList.put(usr.getUsername(), new ChatHistory(this.username, usr.getUsername()));
		
		/*
		if (this.friendList.size() != 0) {
			System.out.println(this.friendList.get(0));
		}else {
			System.out.println("insert failed!");
		}
		*/
	}
	
	@Override
	public String toString() {
		return "Users [id=" + username + ", name=" + nickname + ", password=" + password + "]" + chatList.toString();
	}
}
