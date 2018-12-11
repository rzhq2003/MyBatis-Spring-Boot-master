package tk.mybatis.springboot;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		String ids = "1,2,3,4,5";
		String[] strs = ids.split(",");
		List<Rights> list = new ArrayList<Rights>();
		for (String s:strs) {
			Rights rights = new Rights();
			rights.setGroupid(1l);
			rights.setId(Long.parseLong(s));
			list.add(rights);
		}
		

	}





}
