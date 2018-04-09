package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 逾期人员表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public class OverdueEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//姓名
	private String name;
	//逾期时间
	private Date overdueTime;
	//联系电话
	private String phone;
	//备注
	private String remark;
	//创建人
	private String createBy;
	//创建日期
	private Date createTime;
	//
	private String updateBy;
	//
	private Date updateTime;
	//删除标致（0未删除1已删除）
	private String isdelete;

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
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：逾期时间
	 */
	public void setOverdueTime(Date overdueTime) {
		this.overdueTime = overdueTime;
	}
	/**
	 * 获取：逾期时间
	 */
	public Date getOverdueTime() {
		return overdueTime;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
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
	 * 设置：创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
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
}
