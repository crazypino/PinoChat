package com.pino.webChat.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
	private String user1;
	private String user2;
	public List<ChatMsg> list = new ArrayList<> ();
	public ChatHistory() {
		super();
	}
	public ChatHistory(String user1, String user2) {
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public void insertMsg(ChatMsg msg) {
		list.add(msg);
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for (ChatMsg msg : list) {
			ret.append(msg.toString());
		}
		return ret.toString();
	}
}
