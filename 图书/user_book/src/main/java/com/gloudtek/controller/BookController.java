package com.gloudtek.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.gloudtek.entity.BookEntity;
import com.gloudtek.service.BookService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;


/**
 * 图书表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
@Controller
@RequestMapping("book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	/**
	@RequestMapping("/book.html")
	public String list(){
		return "book/book.html";
	}
	*/
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("book:list")
	public R list(Integer currentPage, Integer pageSize){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (currentPage - 1) * pageSize);
		map.put("limit", pageSize);
		
		//查询列表数据
		List<BookEntity> bookList = bookService.queryList(map);
		int total = bookService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(bookList, total, pageSize, currentPage);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("book:info")
	public R info(@PathVariable("id") String id){
		BookEntity book = bookService.queryObject(id);
		
		return R.ok().put("book", book);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("book:save")
	public R save(@RequestBody BookEntity book){
		bookService.save(book);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("book:update")
	public R update(@RequestBody BookEntity book){
		bookService.update(book);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("book:delete")
	public R delete(@RequestBody String[] ids){
		bookService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
