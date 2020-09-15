package com.pino.webChat.controller;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.pino.webChat.pojo.ChatMsg;
import com.pino.webChat.service.ChatMsgService;

@Controller
@ServerEndpoint(value="/websocket/{userno}")
public class ChatWebSocket {
	
	
	
	private static ChatMsgService chatMsgService;
	
	@Autowired
	public void setChatService(ChatMsgService chatMsgService) {
		ChatWebSocket.chatMsgService = chatMsgService;
	}
	
	private static int onlineCount = 0;
	
	private static ConcurrentHashMap<String, ChatWebSocket> webSocketSet = new ConcurrentHashMap<> ();
	
	private Session webSocketSession;
	
	private String userno = "";
	
	
	
	
	@OnOpen
	public void onopen(@PathParam(value="userno") String param, Session webSocketSession) {
		this.userno = param;
		this.webSocketSession = webSocketSession;
		webSocketSet.put(param, this);
		addOnlineCount();
	}
	
	
	@OnClose
	public void onClose() {
		if (!userno.equals("")) {
			webSocketSet.remove(userno);
			subOnlineCount();
		}
	}
	
	@SuppressWarnings("unused")
	@OnMessage
	public void onMessage(String chatmsg, Session session) throws Exception {
		JSONObject jsonObject = JSONObject.parseObject(chatmsg);
		
		sendToUser(jsonObject.toJavaObject(ChatMsg.class));
	}
	
	public void sendToUser(ChatMsg msg) throws Exception{
		String receiver = msg.getReceiver();
		String msgcontent = msg.getSendtext();
		msg.setSender(userno);
		chatMsgService.insertMsg(userno, msg);
		chatMsgService.insertMsg(receiver, msg);
		try {
			if (webSocketSet.get(receiver) != null) {
				webSocketSet.get(receiver).sendMessage(userno+"|"+msgcontent);
			}else {
				webSocketSet.get(userno).sendMessage("0"+"|"+"The user is not online!");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}
	
	public void sendMessage(String message) throws IOException{
		this.webSocketSession.getBasicRemote().sendText(message);
	}
	
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}
	
	public static synchronized void addOnlineCount() {
		onlineCount++;
	}
	
	public static synchronized void subOnlineCount() {
		onlineCount--;
	}
	
	
}
