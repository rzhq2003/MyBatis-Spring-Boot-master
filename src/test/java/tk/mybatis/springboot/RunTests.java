package tk.mybatis.springboot;





import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.mapper.HistoryItemsMapper;
import tk.mybatis.springboot.mapper.HistoryMapper;
import tk.mybatis.springboot.model.History;
import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.service.HistoryService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.HostsTemplatesService;
import tk.mybatis.springboot.service.ItemsService;
import tk.mybatis.springboot.util.MyUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RunTests {

	@Autowired
	ItemsService itemsService;
	
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
		JSONObject jsonObject = new JSONObject();
		List<History> list = new ArrayList<History>();
		list = historyMapper.selectAll();
		for (int i = 0; i < list.size(); i++) {
			HistoryItems historyItems = new HistoryItems();
			historyItems.setHistorysn(list.get(i).getHistorysn());
			List<HistoryItems> list1 = new ArrayList<HistoryItems>();
			list1 = historyItemsMapper.select(historyItems);
			// System.out.println(JSONObject.toJSONString(list1));	
			jsonObject.put(list.get(i).getHostid().toString(), list1);
		}
		System.out.println(JSONObject.toJSONString(jsonObject));
	}





}
