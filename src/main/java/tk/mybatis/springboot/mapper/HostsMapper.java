package tk.mybatis.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.springboot.model.Hosts;
import tk.mybatis.springboot.util.MyMapper;

public interface HostsMapper extends MyMapper<Hosts> {
	
    @Select("select * from hosts where status = 0")
    public List<Hosts> selectHosts();
    
    @Select("select * from hosts where status = 3")
    public List<Hosts> selectTemplates();
    
}