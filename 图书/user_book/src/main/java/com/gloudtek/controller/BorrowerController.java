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

import com.gloudtek.entity.BorrowerEntity;
import com.gloudtek.service.BorrowerService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;


/**
 * 借书详情表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
@Controller
@RequestMapping("borrower")
public class BorrowerController {
	@Autowired
	private BorrowerService borrowerService;
	
	/**
	@RequestMapping("/borrower.html")
	public String list(){
		return "borrower/borrower.html";
	}
	*/
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("borrower:list")
	public R list(Integer currentPage, Integer pageSize){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (currentPage - 1) * pageSize);
		map.put("limit", pageSize);
		
		//查询列表数据
		List<BorrowerEntity> borrowerList = borrowerService.queryList(map);
		int total = borrowerService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(borrowerList, total, pageSize, currentPage);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("borrower:info")
	public R info(@PathVariable("id") String id){
		BorrowerEntity borrower = borrowerService.queryObject(id);
		
		return R.ok().put("borrower", borrower);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("borrower:save")
	public R save(@RequestBody BorrowerEntity borrower){
		borrowerService.save(borrower);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("borrower:update")
	public R update(@RequestBody BorrowerEntity borrower){
		borrowerService.update(borrower);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("borrower:delete")
	public R delete(@RequestBody String[] ids){
		borrowerService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
