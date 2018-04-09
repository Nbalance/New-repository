package com.gloudtek.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gloudtek.dao.SysRoleDao;
import com.gloudtek.entity.SysRoleEntity;
import com.gloudtek.service.SysRoleMenuService;
import com.gloudtek.service.SysRoleService;
import com.gloudtek.service.SysUserRoleService;



/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		sysRoleDao.save(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		sysRoleDao.deleteBatch(roleIds);
	}

	@Override
	public String selectRoleName(Long userId) {
		// TODO Auto-generated method stub
		return sysRoleDao.selectRoleName(userId);
	}

	@Override
	public List<String> selectRoleNames(Long userId) {
		// TODO Auto-generated method stub
		return sysRoleDao.selectRoleNames(userId);
	}

	@Override
	public List<String> selectRoleId(Long userId) {
		// TODO Auto-generated method stub
		return sysRoleDao.selectRoleId(userId);
	}

	@Override
	public List<String> selectsponsorDepartment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sysRoleDao.selectsponsorDepartment(map);
	}

}
