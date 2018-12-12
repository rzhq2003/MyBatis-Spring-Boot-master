package tk.mybatis.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.service.UsersGroupsService;






@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RunTests {

	@Autowired
	UsersGroupsService usersGroupsService;
	
	
	



	@Test
	public void contextLoads() {
		UsersGroups usersGroups = new UsersGroups();
		usersGroups.setUserid(30l);
		String ids = usersGroupsService.getBy(usersGroups, "id");
		System.out.println(ids);

	}





}
