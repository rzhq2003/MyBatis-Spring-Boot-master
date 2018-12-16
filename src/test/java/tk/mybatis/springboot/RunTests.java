package tk.mybatis.springboot;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import tk.mybatis.springboot.mapper.UsersGroupsMapper;



@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RunTests {

	@Autowired
	UsersGroupsMapper usersGroupsMapper;

	@Test
	public void contextLoads() {


	
		

	}





}
