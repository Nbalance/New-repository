package com.gloudtek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.gloudtek.dao.BookDao;
import com.gloudtek.entity.BookEntity;
import com.gloudtek.service.BookService;



@Service("bookService")
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;
	
	@Override
	public BookEntity queryObject(String id){
		return bookDao.queryObject(id);
	}
	
	@Override
	public List<BookEntity> queryList(Map<String, Object> map){
		return bookDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bookDao.queryTotal(map);
	}
	
	@Override
	public void save(BookEntity book){
		bookDao.save(book);
	}
	
	@Override
	public void update(BookEntity book){
		bookDao.update(book);
	}
	
	@Override
	public void delete(String id){
		bookDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		bookDao.deleteBatch(ids);
	}
	
}
