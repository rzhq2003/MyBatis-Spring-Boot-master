package tk.mybatis.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.HistoryMapper;
import tk.mybatis.springboot.model.History;

import tk.mybatis.springboot.util.BaseService;



@Service
public class HistoryService extends BaseService<History> {

	
	@Autowired
	HistoryMapper historyMapper;
	
	/*
	 * 
	 * 定制查询
	 * 
	 */
    public String getNewCMDB() {
    	return null;
    	
	}
    
}
