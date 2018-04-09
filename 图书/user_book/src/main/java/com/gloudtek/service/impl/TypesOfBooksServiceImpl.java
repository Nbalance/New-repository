package com.gloudtek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.gloudtek.dao.TypesOfBooksDao;
import com.gloudtek.entity.TypesOfBooksEntity;
import com.gloudtek.service.TypesOfBooksService;



@Service("typesOfBooksService")
public class TypesOfBooksServiceImpl implements TypesOfBooksService {
	@Autowired
	private TypesOfBooksDao typesOfBooksDao;
	
	@Override
	public TypesOfBooksEntity queryObject(String id){
		return typesOfBooksDao.queryObject(id);
	}
	
	@Override
	public List<TypesOfBooksEntity> queryList(Map<String, Object> map){
		return typesOfBooksDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return typesOfBooksDao.queryTotal(map);
	}
	
	@Override
	public void save(TypesOfBooksEntity typesOfBooks){
		typesOfBooksDao.save(typesOfBooks);
	}
	
	@Override
	public void update(TypesOfBooksEntity typesOfBooks){
		typesOfBooksDao.update(typesOfBooks);
	}
	
	@Override
	public void delete(String id){
		typesOfBooksDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		typesOfBooksDao.deleteBatch(ids);
	}
	
}
