package com.gloudtek.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.gloudtek.entity.TypesOfBooksEntity;
import com.gloudtek.service.TypesOfBooksService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;


/**
 * 图书类别
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
@Controller
@RequestMapping("typesofbooks")
public class TypesOfBooksController {
	@Autowired
	private TypesOfBooksService typesOfBooksService;
	
	/**
	@RequestMapping("/typesofbooks.html")
	public String list(){
		return "typesofbooks/typesofbooks.html";
	}
	*/
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("typesofbooks:list")
	public R list(Integer currentPage, Integer pageSize){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (currentPage - 1) * pageSize);
		map.put("limit", pageSize);
		
		//查询列表数据
		List<TypesOfBooksEntity> typesOfBooksList = typesOfBooksService.queryList(map);
		int total = typesOfBooksService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(typesOfBooksList, total, pageSize, currentPage);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("typesofbooks:info")
	public R info(@PathVariable("id") String id){
		TypesOfBooksEntity typesOfBooks = typesOfBooksService.queryObject(id);
		
		return R.ok().put("typesOfBooks", typesOfBooks);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("typesofbooks:save")
	public R save(@RequestBody TypesOfBooksEntity typesOfBooks){
		if (typesOfBooks.getId() == null || "".equals(typesOfBooks.getId())) {
			typesOfBooks.setId(UUID.randomUUID().toString());
			typesOfBooks.setIsdelete("0");
			typesOfBooksService.save(typesOfBooks);
		} else {
			typesOfBooksService.update(typesOfBooks);
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("typesofbooks:update")
	public R update(@RequestBody TypesOfBooksEntity typesOfBooks){
		typesOfBooksService.update(typesOfBooks);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("typesofbooks:delete")
	public R delete(@RequestBody String[] ids){
		typesOfBooksService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
