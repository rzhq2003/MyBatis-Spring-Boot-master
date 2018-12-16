package tk.mybatis.springboot.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.springboot.model.Rights;
import tk.mybatis.springboot.util.MyMapper;

public interface RightsMapper extends MyMapper<Rights> {
	
	
	@Delete("delete from rights where id not in (${ids}) and groupid = #{usrgrpid}") 
	public void deleteNotIn(@Param("ids") String ids, @Param("usrgrpid") Long usrgrpid);
}