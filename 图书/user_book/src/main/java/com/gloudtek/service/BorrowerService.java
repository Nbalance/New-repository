package com.gloudtek.service;

import com.gloudtek.entity.BorrowerEntity;

import java.util.List;
import java.util.Map;

/**
 * 借书详情表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public interface BorrowerService {
	
	BorrowerEntity queryObject(String id);
	
	List<BorrowerEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BorrowerEntity borrower);
	
	void update(BorrowerEntity borrower);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
