package tk.mybatis.springboot.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.util.MyMapper;

public interface UsersGroupsMapper extends MyMapper<UsersGroups> {
	
	@Delete("delete from users_groups where userid not in (${userids}) and usrgrpid = #{usrgrpid}") 
	public void deleteNotIn(@Param("userids") String userids, @Param("usrgrpid") Long usrgrpid);
	
	
}