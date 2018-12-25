package tk.mybatis.springboot;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.mapper.HistoryItemsMapper;
import tk.mybatis.springboot.mapper.HistoryMapper;
import tk.mybatis.springboot.mapper.ItemsMapper;
import tk.mybatis.springboot.model.History;
import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.model.Items;

import tk.mybatis.springboot.service.HistoryService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.HostsTemplatesService;
import tk.mybatis.springboot.service.ItemsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RunTests {

	@Autowired
	ItemsService itemsService;
	
	@Autowired
	ItemsMapper itemsMapper;
	
	@Autowired
	HostsService hostsService;
	
	@Autowired
	HostsTemplatesService hostsTemplatesService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	HistoryMapper historyMapper;
	
	@Autowired
	HistoryItemsMapper historyItemsMapper;

	@Test
	public void contextLoads() {
		/*
		 *
		 * 如传入hostid,返回为{参数项1:参数值1, 参数项2:参数值2, 参数项3:参数值3}
		 * 
		*/
		
		JSONArray jsonArray = new JSONArray();
		History history = new History();
		history.setHostid(2l);
		List<History> list = new ArrayList<History>();
		list = historyMapper.select(history);
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			HistoryItems historyItems = new HistoryItems();
			historyItems.setsheetid(list.get(i).getsheetid());
			List<HistoryItems> list1 = new ArrayList<HistoryItems>();
			list1 = historyItemsMapper.select(historyItems);
			jsonObject.put("hostid", list.get(i).getHostid());
			jsonObject.put("sheetid", list.get(i).getsheetid());
			for (int j = 0; j < list1.size(); j++) {
				Items items = new Items();
				items = itemsMapper.selectByPrimaryKey(list1.get(j).getItemid());
				jsonObject.put(items.getItemid().toString(), list1.get(j).getValue());	
			}
			jsonArray.add(jsonObject);		
	}
		System.out.println(JSONObject.toJSONString(jsonArray));
	}

}
