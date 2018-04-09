package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 图书表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public class BookEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//作者
	private String author;
	//书名
	private String bookName;
	//图书种类
	private String type;
	//出版日期
	private Date firstEditionTime;
	//是否出借(0为出借11出借）
	private String whetherToLend;
	//出借日期
	private Date loanDate;
	//出借次数
	private String numberOfLoans;
	//借书详情id
	private String borrowerId;
	//备注
	private String remark;
	//创建人
	private String createBy;
	//创建日期
	private Date createTime;
	//更新人
	private String updateBy;
	//更新日期
	private Date updateTime;
	//
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
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：书名
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * 获取：书名
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * 设置：图书种类
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：图书种类
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：出版日期
	 */
	public void setFirstEditionTime(Date firstEditionTime) {
		this.firstEditionTime = firstEditionTime;
	}
	/**
	 * 获取：出版日期
	 */
	public Date getFirstEditionTime() {
		return firstEditionTime;
	}
	/**
	 * 设置：是否出借(0为出借11出借）
	 */
	public void setWhetherToLend(String whetherToLend) {
		this.whetherToLend = whetherToLend;
	}
	/**
	 * 获取：是否出借(0为出借11出借）
	 */
	public String getWhetherToLend() {
		return whetherToLend;
	}
	/**
	 * 设置：出借日期
	 */
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	/**
	 * 获取：出借日期
	 */
	public Date getLoanDate() {
		return loanDate;
	}
	/**
	 * 设置：出借次数
	 */
	public void setNumberOfLoans(String numberOfLoans) {
		this.numberOfLoans = numberOfLoans;
	}
	/**
	 * 获取：出借次数
	 */
	public String getNumberOfLoans() {
		return numberOfLoans;
	}
	/**
	 * 设置：借书详情id
	 */
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	/**
	 * 获取：借书详情id
	 */
	public String getBorrowerId() {
		return borrowerId;
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
	 * 设置：更新日期
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新日期
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * 获取：
	 */
	public String getIsdelete() {
		return isdelete;
	}
}
