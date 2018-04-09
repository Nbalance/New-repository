package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 借书详情表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public class BorrowerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//借书人
	private String bookBorrower;
	//借书日期
	private Date borrowerTime;
	//是否还书（是 ：还了，否：没还）
	private String returnType;
	//联系电话
	private String phone;
	//创建者
	private String createBy;
	//创建日期
	private Date createTime;
	//创建人
	private String updateBy;
	//更新时间
	private Date updateTime;
	//删除标志（0未删除1已删除）
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
	 * 设置：借书人
	 */
	public void setBookBorrower(String bookBorrower) {
		this.bookBorrower = bookBorrower;
	}
	/**
	 * 获取：借书人
	 */
	public String getBookBorrower() {
		return bookBorrower;
	}
	/**
	 * 设置：借书日期
	 */
	public void setBorrowerTime(Date borrowerTime) {
		this.borrowerTime = borrowerTime;
	}
	/**
	 * 获取：借书日期
	 */
	public Date getBorrowerTime() {
		return borrowerTime;
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
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者
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
	 * 设置：创建人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：创建人
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
	/**
	 * 设置：删除标志（0未删除1已删除）
	 */
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * 获取：删除标志（0未删除1已删除）
	 */
	public String getIsdelete() {
		return isdelete;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
