package com.pino.webChat.pojo;

import java.util.Date;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class ChatMsg implements Serializable{
	public int senderId;
	public int receiverId;
	public Date sendTime;
	private String sender;
	private String msgtype;
	private String sendtext;
	private String receiver;
	
	public ChatMsg(int senderId, int receiverId, String content, String sender, String receiver) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.sendtext = content;
		this.sendTime = new Date();
		this.sender = sender;
		this.receiver = receiver;
		
	}
	/*
	public String getMsg() {
		return content;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public Date getDate() {
		return sendTime;
	}
	*/
	
	public String Time() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = dateFormat.format(sendTime).toString();
		return time;
	}
	/*
	
	public String toString() {
		return "Sender: " + sender + " sendTime : " + getSendTime() + " content: " + content;
	}
	
	*/
}
