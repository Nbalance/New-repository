package com.gloudtek.service;

import com.gloudtek.entity.SysLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2018-04-04 21:20:01
 */
public interface SysLogService {
	
	SysLogEntity queryObject(String id);
	
	List<SysLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysLogEntity sysLog);
	
	void update(SysLogEntity sysLog);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

}
