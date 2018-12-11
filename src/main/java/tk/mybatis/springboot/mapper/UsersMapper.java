package tk.mybatis.springboot.mapper;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.springboot.model.Users;
import tk.mybatis.springboot.util.MyMapper;

public interface UsersMapper extends MyMapper<Users> {
	
    @Select("select * from users where username = #{username}")
    public Users findByUsername(String username);

	
}