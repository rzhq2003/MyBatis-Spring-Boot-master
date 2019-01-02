package tk.mybatis.springboot.service;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.HistoryItemsMapper;
import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.util.BaseService;


@Service
public class HistoryItemsService extends BaseService<HistoryItems> {
	
	 @Autowired
	 HistoryItemsMapper historyItemsMapper;
	 public List<HistoryItems> getBySheetid(String sheetid) {
		 return historyItemsMapper.getBySheetid(sheetid);
	 }
	 
	 
	 
	 
}
