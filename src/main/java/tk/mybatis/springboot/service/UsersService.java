package tk.mybatis.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import tk.mybatis.springboot.mapper.UsersMapper;
import tk.mybatis.springboot.model.JwtUser;
import tk.mybatis.springboot.model.Users;
import tk.mybatis.springboot.util.BaseService;

/**
 * Created by echisan on 2018/6/23
 */
@Service
public class UsersService extends BaseService<Users> implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	Users users = usersMapper.findByUsername(s);
        if (users == null) {
        	 throw new UsernameNotFoundException(String.format("没有该用户 '%s'.", s));
        } else {
        	return new JwtUser(users);
        }        
    }
    
    // 增加新方法,Dao层也需同步
    public Users findByUsername(String username) {
    	return usersMapper.findByUsername(username);	
	}
    


}
