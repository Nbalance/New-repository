package com.gloudtek.utils.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;

import com.gloudtek.entity.LoginParam;

public class Session {
	  public void sessionis(HttpServletResponse response, HttpServletRequest request, @RequestBody LoginParam loginParam) {  
		// 用户登录成功后，更新session Map，如重复登录，强制之前session过期
			String sessionid = SessionListener.userMap.get(loginParam.getUsername());
			if (sessionid != null && !sessionid.equals("")) {
				// 注销在线用户,如果session id 相同，不销毁。
				String sessionis =request.getSession().getId();
				if (!sessionid.equals(sessionis)) {
					SessionListener.sessionMap.get(sessionid).invalidate();
					SessionListener.userMap.put(loginParam.getUsername(), request.getSession().getId());
					SessionListener.sessionMap.put(request.getSession().getId(), request.getSession());
				}
			} else {
				if (SessionListener.sessionMap.containsKey(request.getSession().getId())) {
					SessionListener.sessionMap.remove(request.getSession().getId());
					for (String key : SessionListener.userMap.keySet()) {
						if (SessionListener.userMap.get(key).equals(request.getSession().getId())) {
							SessionListener.userMap.remove(key);
						}
					}
				}
				SessionListener.userMap.put(loginParam.getUsername(), request.getSession().getId());
				SessionListener.sessionMap.put(request.getSession().getId(), request.getSession());
			}
	    }  
}
