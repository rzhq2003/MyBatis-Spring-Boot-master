package tk.mybatis.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.model.Rights;
import tk.mybatis.springboot.service.RightsService;





@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RunTests {

	@Autowired
	RightsService rightsService;
	
	
	



	@Test
	public void contextLoads() {
		Rights rights = new Rights();
		rights.setGroupid(9l);
		System.out.println(JSONObject.toJSONString(rightsService.select(rights)));

	}





}
