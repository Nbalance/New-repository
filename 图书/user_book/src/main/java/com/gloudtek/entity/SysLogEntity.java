package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-04 21:20:01
 */
public class SysLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//uuid
	private String id;
	//用户名
	private String username;
	//时间
	private Date time;
	//类型
	private String type;
	//详情
	private String detail;
	//IP地址
	private String ip;
	//归属地
	private String place;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}

}
