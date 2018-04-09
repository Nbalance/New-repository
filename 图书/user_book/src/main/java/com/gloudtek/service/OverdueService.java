package com.gloudtek.service;

import com.gloudtek.entity.OverdueEntity;

import java.util.List;
import java.util.Map;

/**
 * 逾期人员表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public interface OverdueService {
	
	OverdueEntity queryObject(String id);
	
	List<OverdueEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OverdueEntity overdue);
	
	void update(OverdueEntity overdue);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
