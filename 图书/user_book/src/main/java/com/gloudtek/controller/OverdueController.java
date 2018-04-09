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

import com.gloudtek.entity.OverdueEntity;
import com.gloudtek.service.OverdueService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;


/**
 * 逾期人员表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
@Controller
@RequestMapping("overdue")
public class OverdueController {
	@Autowired
	private OverdueService overdueService;
	
	/**
	@RequestMapping("/overdue.html")
	public String list(){
		return "overdue/overdue.html";
	}
	*/
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("overdue:list")
	public R list(Integer currentPage, Integer pageSize){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (currentPage - 1) * pageSize);
		map.put("limit", pageSize);
		
		//查询列表数据
		List<OverdueEntity> overdueList = overdueService.queryList(map);
		int total = overdueService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(overdueList, total, pageSize, currentPage);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("overdue:info")
	public R info(@PathVariable("id") String id){
		OverdueEntity overdue = overdueService.queryObject(id);
		
		return R.ok().put("overdue", overdue);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("overdue:save")
	public R save(@RequestBody OverdueEntity overdue){
		overdueService.save(overdue);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("overdue:update")
	public R update(@RequestBody OverdueEntity overdue){
		overdueService.update(overdue);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("overdue:delete")
	public R delete(@RequestBody String[] ids){
		overdueService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
