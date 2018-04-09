package com.gloudtek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.gloudtek.dao.BorrowerDao;
import com.gloudtek.entity.BorrowerEntity;
import com.gloudtek.service.BorrowerService;



@Service("borrowerService")
public class BorrowerServiceImpl implements BorrowerService {
	@Autowired
	private BorrowerDao borrowerDao;
	
	@Override
	public BorrowerEntity queryObject(String id){
		return borrowerDao.queryObject(id);
	}
	
	@Override
	public List<BorrowerEntity> queryList(Map<String, Object> map){
		return borrowerDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return borrowerDao.queryTotal(map);
	}
	
	@Override
	public void save(BorrowerEntity borrower){
		borrowerDao.save(borrower);
	}
	
	@Override
	public void update(BorrowerEntity borrower){
		borrowerDao.update(borrower);
	}
	
	@Override
	public void delete(String id){
		borrowerDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		borrowerDao.deleteBatch(ids);
	}
	
}
