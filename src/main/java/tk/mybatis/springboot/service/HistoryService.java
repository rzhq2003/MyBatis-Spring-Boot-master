package tk.mybatis.springboot.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.HistoryItemsMapper;
import tk.mybatis.springboot.mapper.HistoryMapper;
import tk.mybatis.springboot.model.History;
import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.request.HistoryAddDTO;

import tk.mybatis.springboot.util.BaseService;
import tk.mybatis.springboot.util.MyUtils;



@Service
public class HistoryService extends BaseService<History> {

	
	@Autowired
	HistoryMapper historyMapper;
	
	@Autowired
	HistoryItemsMapper historyItemsMapper;
	
	/*
	 * 
	 * 定制查询
	 * 
	 */
    public History add(HistoryAddDTO historyAddDTO) {
		String sheetid = MyUtils.getOrderIdByUUId();
		History history = new History();
		history.setSheetid(sheetid);
		BeanUtils.copyProperties(historyAddDTO, history);
		historyMapper.insert(history);
		List<HistoryItems> list = new ArrayList<HistoryItems>();
		list = historyAddDTO.getListItems();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSheetid(sheetid);
			list.get(i).setClock(System.currentTimeMillis());
		}
		historyItemsMapper.insertList(list);
		return history;
    	
	}
    
}
