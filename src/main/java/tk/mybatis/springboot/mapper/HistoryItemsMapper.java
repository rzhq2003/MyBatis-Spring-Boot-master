package tk.mybatis.springboot.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.util.MyMapper;

public interface HistoryItemsMapper extends MyMapper<HistoryItems> {
	
	@Select("select items.itemid"
			+ "	,history_items.historyitemid,history_items.sheetid,history_items.clock,history_items.`value` "
			+ " from history "
			+ " left join items on history.templateid = items.templateid "
			+ " left join history_items on items.itemid = history_items.itemid and history.sheetid = history_items.sheetid "
			+ " where history.sheetid = #{sheetid}")
	public List<HistoryItems> getBySheetid(String sheetid);

}