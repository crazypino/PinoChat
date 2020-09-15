package com.pino.webChat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.pino.webChat.pojo.Users;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	RedisTemplate redisTemplate;
	@Test
	public void test() throws Exception{
		Users user = new Users();
		user.setUsername("pino");
		user.setPassword("123456");
		
		redisTemplate.opsForValue().set(user.getUsername(), user);
		System.out.println("result：{}" + redisTemplate.opsForValue().get("pino"));
	}
}
