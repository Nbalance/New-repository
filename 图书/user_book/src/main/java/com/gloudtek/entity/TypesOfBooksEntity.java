package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 图书类别
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public class TypesOfBooksEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//政治
	private String politics;
	//法律
	private String law;
	//军事
	private String military;
	// 经济
	private String economy;
	//文学
	private String literature;
	//艺术
	private String art;
	//备注
	private String remark;
	//删除标致（0未删除1已删除）
	private String isdelete;
	//创建人
	private String createBy;
	//创建时间
	private Date createTime;
	//更新人
	private String updateBy;
	//更新时间
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：政治
	 */
	public void setPolitics(String politics) {
		this.politics = politics;
	}
	/**
	 * 获取：政治
	 */
	public String getPolitics() {
		return politics;
	}
	/**
	 * 设置：法律
	 */
	public void setLaw(String law) {
		this.law = law;
	}
	/**
	 * 获取：法律
	 */
	public String getLaw() {
		return law;
	}
	/**
	 * 设置：军事
	 */
	public void setMilitary(String military) {
		this.military = military;
	}
	/**
	 * 获取：军事
	 */
	public String getMilitary() {
		return military;
	}
	/**
	 * 设置： 经济
	 */
	public void setEconomy(String economy) {
		this.economy = economy;
	}
	/**
	 * 获取： 经济
	 */
	public String getEconomy() {
		return economy;
	}
	/**
	 * 设置：文学
	 */
	public void setLiterature(String literature) {
		this.literature = literature;
	}
	/**
	 * 获取：文学
	 */
	public String getLiterature() {
		return literature;
	}
	/**
	 * 设置：艺术
	 */
	public void setArt(String art) {
		this.art = art;
	}
	/**
	 * 获取：艺术
	 */
	public String getArt() {
		return art;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：删除标致（0未删除1已删除）
	 */
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * 获取：删除标致（0未删除1已删除）
	 */
	public String getIsdelete() {
		return isdelete;
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
}
