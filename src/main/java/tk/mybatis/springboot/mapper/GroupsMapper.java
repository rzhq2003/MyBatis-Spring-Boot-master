package tk.mybatis.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.springboot.model.Groups;
import tk.mybatis.springboot.util.MyMapper;

public interface GroupsMapper extends MyMapper<Groups> {
	
	
	@Select("select groups.*"
			+ " from users "
			+ " inner join users_groups on users.userid = users_groups.userid "
			+ " inner join usrgrp on users_groups.usrgrpid = usrgrp.usrgrpid "
			+ " inner join rights on usrgrp.usrgrpid = rights.groupid "
			+ " inner join groups on rights.id = groups.groupid "
			+ " where users.username = #{username}")
	public List<Groups> getByUsername(String username);
}