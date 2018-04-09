package com.gloudtek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.gloudtek.dao.PersonnelDao;
import com.gloudtek.entity.PersonnelEntity;
import com.gloudtek.service.PersonnelService;



@Service("personnelService")
public class PersonnelServiceImpl implements PersonnelService {
	@Autowired
	private PersonnelDao personnelDao;
	
	@Override
	public PersonnelEntity queryObject(String id){
		return personnelDao.queryObject(id);
	}
	
	@Override
	public List<PersonnelEntity> queryList(Map<String, Object> map){
		return personnelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return personnelDao.queryTotal(map);
	}
	
	@Override
	public void save(PersonnelEntity personnel){
		personnelDao.save(personnel);
	}
	
	@Override
	public void update(PersonnelEntity personnel){
		personnelDao.update(personnel);
	}
	
	@Override
	public void delete(String id){
		personnelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		personnelDao.deleteBatch(ids);
	}
	
}
