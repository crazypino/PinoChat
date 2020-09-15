package com.pino.webChat.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.pino.webChat.exception.R;
import com.pino.webChat.pojo.ChatFriend;
import com.pino.webChat.pojo.ChatMsg;
import com.pino.webChat.pojo.Users;
import com.pino.webChat.service.ChatFriendService;
import com.pino.webChat.service.UserService;


@Controller
public class ChatController {
	@Autowired
	UserService userService;
	
	@Autowired 
	ChatFriendService chatFriendService;
	

	
	@PostMapping(value = "/chat/setimg")
    @ResponseBody
    public JSONObject upldpics(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		JSONObject res = new JSONObject();
        LocalDate today = LocalDate.now();
        Instant timestamp = Instant.now();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filenames = today + String.valueOf(timestamp.toEpochMilli()) + "."+ext;
        file.transferTo(new File("/home/pino/eclipse-workspace/webChat/src/main/resources/static/img/" + filenames));
        String url = "/img/" + filenames;
        res.put("uimg", url); 
        return res;
    }
	
	
	//look for users
	@PostMapping("/chat/lkuser/{username}")
	@ResponseBody
	public R lkuser(@PathVariable("username") String username) {
		if (userService.query(username) == null) {
			return R.error().message("The user doesn't exist");
		}
		else {
			return R.ok().data("userinfo", userService.query(username)).message("User Information");
		}
	}
	
	
	//add friends
	
	@PostMapping("/chat/adduser/{fusername}")
	@ResponseBody
	public R tofuseridchat(@PathVariable("fusername")String fusername, HttpSession session) throws Exception {
		String username = (String) session.getAttribute("userid");
		if (username.equals(fusername)) {
			return R.error().message("can't add youself as a friend");
		}
		
		Users user1 = userService.query(username);
		Users user2 = userService.query(fusername);
		/*
		//test
		if (user2 != null) {
			System.out.println("friend: " + user2.getUsername() + "\n");
		}else {
			System.out.println("friend is null");
		}
		//***************
		 */
		if (user1 == null || user2 == null) return R.error().message("The user doesn't exist");
		
		
		chatFriendService.addFriends(user1, user2);
		
		return R.ok().message("Friend added");
	}
	
	@GetMapping("/chat/ct")
	public String tochat() {
		return "/chat/chats";
	}
	
	@PostMapping("/chat/lkfriends")
	@ResponseBody 
	public List<Users> lkfriends(HttpSession session){
		String userid = (String) session.getAttribute("userid");
		
		List<Users> list = chatFriendService.getChatFriends(userid);
		return chatFriendService.getChatFriends(userid);
	}
	
	@PostMapping("/chat/lkuschatmsg/{receiveuserid}")
	@ResponseBody
	public List<ChatMsg> lkfriends(HttpSession session, @PathVariable("receiveuserid")String receiveuserid){
		String userid = (String) session.getAttribute("userid");
		Users user = userService.query(userid);
		return chatFriendService.searchFriend(user, receiveuserid).list;
	}
}
	
	
	

