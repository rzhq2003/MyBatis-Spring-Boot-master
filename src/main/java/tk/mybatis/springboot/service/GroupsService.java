package tk.mybatis.springboot.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.GroupsMapper;
import tk.mybatis.springboot.model.Groups;
import tk.mybatis.springboot.util.BaseService;

@Service
public class GroupsService extends BaseService<Groups> {
	
	 @Autowired
	 GroupsMapper groupsMapper;
	 
	public List<Groups> getByUsername(String username){
		return groupsMapper.getByUsername(username);
	}


	

}
