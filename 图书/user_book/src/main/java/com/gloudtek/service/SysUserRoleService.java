package com.gloudtek.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.SysUserRoleEntity;
import com.gloudtek.utils.RoleUtil;



/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:24
 */
public interface SysUserRoleService {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	List<SysUserRoleEntity> queryList(Map<String, Object> map);
	
	void delete(Long userId);
	
	void saveRole(RoleUtil roleUtil);
	
	void updateRole(RoleUtil roleUtil);
	
	void saveOrUpdate(Long userId);

}
