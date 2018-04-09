package com.gloudtek.utils.Session;

import javax.servlet.http.HttpSession;  
import javax.servlet.http.HttpSessionEvent;  
import javax.servlet.http.HttpSessionListener;  
import java.util.HashMap; 

public class SessionListener implements HttpSessionListener  {
	public static HashMap<String,String> userMap = new HashMap<String,String>();  
	  
    public static HashMap<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();  
  
  
    @Override  
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {  
  
    }  
  
    @Override  
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {  
        HttpSession session = httpSessionEvent.getSession();  
        String sessionId = session.getId();  
        sessionMap.remove(sessionId);  
        for(String key : userMap.keySet()){  
            if(userMap.get(key).equals(sessionId)){  
                userMap.remove(key);  
            }  
        }  
  
    }  
}  
