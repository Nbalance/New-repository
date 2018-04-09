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

import com.gloudtek.entity.SysLogEntity;
import com.gloudtek.service.SysLogService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.Query;
import com.gloudtek.utils.R;
import com.gloudtek.utils.TotalLine;


/**
 * 
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-04 21:20:01
 */
@Controller
@RequestMapping("/api/v1/syslog")
public class SysLogController {
	@Autowired
	private SysUserService SysUserService;
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
/*	@RequiresPermissions("syslog:list")*/
	public R list(@RequestBody Query query){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (query.getCurrentPage() - 1) * query.getPageSize());
		map.put("limit", query.getPageSize());
		if (query.getQueryName()!=null && !query.getQueryName().equals("")) {
			map.put("queryName", query.getQueryName());
		}
	
		//查询列表数据
		List<SysLogEntity> sysLogList = sysLogService.queryList(map);
		int total = sysLogService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(sysLogList, total, query.getCurrentPage(), query.getPageSize());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/lists")
	public R lists(){
		Map<String, Object> map = new HashMap<>();
		//查询用户在线总数
		int line =SysUserService.lineTotal();
		int lines =SysUserService.linesTotal();
		int total = SysUserService.queryTotal(map);
		
		TotalLine totalLine = new TotalLine();
		totalLine.setLine(line);
		totalLine.setLines(lines);
		totalLine.setTotal(total);
		return R.ok().put("page", totalLine);
	}
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syslog:info")
	public R info(@PathVariable("id") String id){
		SysLogEntity sysLog = sysLogService.queryObject(id);
		
		return R.ok().put("sysLog", sysLog);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("syslog:save")
	public R save(@RequestBody SysLogEntity sysLog){
		sysLogService.save(sysLog);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("syslog:update")
	public R update(@RequestBody SysLogEntity sysLog){
		sysLogService.update(sysLog);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/deletes")
/*	@RequiresPermissions("syslog:delete")*/
	public R delete(@RequestBody String[] ids){
		sysLogService.deleteBatch(ids);
		return R.ok();
	}
	
}
