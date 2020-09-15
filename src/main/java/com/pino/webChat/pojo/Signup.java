package com.pino.webChat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Signup {
	private String username;
	private String password;
	private String password2;
	private String nickname;
	private String uimg;
}
