package com.gloudtek.service;

import com.gloudtek.entity.PersonnelEntity;

import java.util.List;
import java.util.Map;

/**
 * 个人信息表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2017-12-15 10:48:44
 */
public interface PersonnelService {
	
	PersonnelEntity queryObject(String id);
	
	List<PersonnelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PersonnelEntity personnel);
	
	void update(PersonnelEntity personnel);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
