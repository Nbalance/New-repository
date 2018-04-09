package com.gloudtek.service;

import com.gloudtek.entity.TypesOfBooksEntity;

import java.util.List;
import java.util.Map;

/**
 * 图书类别
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-08 19:51:48
 */
public interface TypesOfBooksService {
	
	TypesOfBooksEntity queryObject(String id);
	
	List<TypesOfBooksEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TypesOfBooksEntity typesOfBooks);
	
	void update(TypesOfBooksEntity typesOfBooks);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
