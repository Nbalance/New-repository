package com.gloudtek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.gloudtek.dao.OverdueDao;
import com.gloudtek.entity.OverdueEntity;
import com.gloudtek.service.OverdueService;



@Service("overdueService")
public class OverdueServiceImpl implements OverdueService {
	@Autowired
	private OverdueDao overdueDao;
	
	@Override
	public OverdueEntity queryObject(String id){
		return overdueDao.queryObject(id);
	}
	
	@Override
	public List<OverdueEntity> queryList(Map<String, Object> map){
		return overdueDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return overdueDao.queryTotal(map);
	}
	
	@Override
	public void save(OverdueEntity overdue){
		overdueDao.save(overdue);
	}
	
	@Override
	public void update(OverdueEntity overdue){
		overdueDao.update(overdue);
	}
	
	@Override
	public void delete(String id){
		overdueDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		overdueDao.deleteBatch(ids);
	}
	
}
