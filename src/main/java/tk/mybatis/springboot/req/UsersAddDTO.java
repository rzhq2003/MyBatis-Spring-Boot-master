package tk.mybatis.springboot.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import tk.mybatis.springboot.model.Users;
import tk.mybatis.springboot.model.UsersGroups;

@ApiModel(value = "UsersAddDTO", description = "用户对象")
public class UsersAddDTO {

	private List<UsersGroups> usrgrps;
	private Users users;

	public List<UsersGroups> getUsrgrp() {
		return usrgrps;
	}

	public void setUsrgrp(List<UsersGroups> usrgrps) {
		this.usrgrps = usrgrps;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
}
