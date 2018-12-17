package tk.mybatis.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.UsersGroupsMapper;
import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.util.BaseService;

@Service
public class UsersGroupsService extends BaseService<UsersGroups> {

	@Autowired
	UsersGroupsMapper usersGroupsMapper;
	

	
}
