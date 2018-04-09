package com.gloudtek.service;

import com.gloudtek.entity.BookEntity;

import java.util.List;
import java.util.Map;

/**
 * 图书表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public interface BookService {
	
	BookEntity queryObject(String id);
	
	List<BookEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BookEntity book);
	
	void update(BookEntity book);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
