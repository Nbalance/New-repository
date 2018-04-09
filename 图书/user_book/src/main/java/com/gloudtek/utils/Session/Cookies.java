package com.gloudtek.utils.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;

import com.gloudtek.entity.LoginParam;

public class Cookies {
	 public void sessionis(HttpServletResponse response, HttpServletRequest request, @RequestBody LoginParam loginParam) { 
		// 记住用户名、密码功能(注意：cookie存放密码会存在安全隐患)
			String checked = loginParam.getChecked();
			if ("true".equals(checked)) { // "1"表示用户勾选记住密码
				Cookie userCookie = new Cookie("username", loginParam.getUsername());
				Cookie pwdCookie = new Cookie("password", loginParam.getPassword());
				userCookie.setMaxAge(60 * 60 * 24 * 7); // 账号存活期为一周
				pwdCookie.setMaxAge(60 * 60 * 24 * 7);// 密码存活期为一周
				userCookie.setPath("/");
				pwdCookie.setPath("/");
				response.addCookie(userCookie);
				response.addCookie(pwdCookie);
			} else {
				Cookie userCookie = new Cookie("username", loginParam.getUsername());
				Cookie pwdCookie = new Cookie("password", loginParam.getPassword());
				userCookie.setMaxAge(0); // 不保存
				pwdCookie.setMaxAge(0);// 不保存
				userCookie.setPath("/");
				pwdCookie.setPath("/");
				response.addCookie(userCookie);
				response.addCookie(pwdCookie);
			}
	 }
}
