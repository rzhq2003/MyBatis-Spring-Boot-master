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

import tk.mybatis.springboot.model.Items;
import tk.mybatis.springboot.service.ItemsService;



@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RunTests {

	@Autowired
	ItemsService itemsService;

	@Test
	public void contextLoads() {
		Items items = new Items();
		items.setHostid(9l);
		List<Items> list = new ArrayList<Items>();
		list = itemsService.select(items);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setHostid(1l);
			list.get(i).setTemplateid(list.get(i).getItemid());
			list.get(i).setItemid(null);
		}
		itemsService.saves(list);
		System.out.print(JSONObject.toJSONString(list));

	
		

	}





}
