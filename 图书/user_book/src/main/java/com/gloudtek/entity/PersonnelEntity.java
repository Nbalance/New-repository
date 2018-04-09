package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 个人信息表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2017-12-15 10:48:44
 */
public class PersonnelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//人员id
	private String id;
	//人员姓名
	private String personnelName;
	//性别(1男:2女)
	private String sex;
	//年龄
	private String age;
	//籍贯
	private String placeOrigin;
	//个人信息地址
	private String fileName;
	//创建时间
	private Date createTime;
	//创建人
	private String createBy;
	//更新时间
	private Date updateTime;
	//更新人
	private String updateBy;
	//是否删除(0未删除:1已删除)
	private Integer isdatele;

	/**
	 * 设置：人员id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：人员id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：人员姓名
	 */
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	
	/**
	 * 获取：性别(1男:2女)
	 */
	public String getPersonnelName() {
		return personnelName;
	}
	/**
	 * 获取：性别(1男:2女)
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别(1男:2女)
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：年龄
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * 获取：年龄
	 */
	public String getAge() {
		return age;
	}
	/**
	 * 设置：籍贯
	 */
	public void setPlaceOrigin(String placeOrigin) {
		this.placeOrigin = placeOrigin;
	}
	/**
	 * 获取：籍贯
	 */
	public String getPlaceOrigin() {
		return placeOrigin;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：是否删除(0未删除:1已删除)
	 */
	public void setIsdatele(Integer isdatele) {
		this.isdatele = isdatele;
	}
	/**
	 * 获取：是否删除(0未删除:1已删除)
	 */
	public Integer getIsdatele() {
		return isdatele;
	}
}
