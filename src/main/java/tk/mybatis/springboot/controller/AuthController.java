package tk.mybatis.springboot.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.model.LoginUser;

import tk.mybatis.springboot.model.Users;
import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.req.UsersAddDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.UsersGroupsService;
import tk.mybatis.springboot.service.UsersService;
import tk.mybatis.springboot.util.MyUtils;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

/**
 * Created by echisan on 2018/6/23
 */
@RestController
@Api(tags = { "auth-module-resource" })
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UsersService usersService;
	
	@Autowired
	UsersGroupsService usersGroupsService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@ApiOperation(value = "用户注册", notes = "用户注册", produces = "application/json")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@Transactional(rollbackOn = Exception.class)
	public ResObject registerUser(@RequestBody UsersAddDTO usersAddDTO) {
		try {
			if (MyUtils.notEmpty(usersAddDTO.getUsername())) {
				Users users = new Users();
				BeanUtils.copyProperties(usersAddDTO, users);
				if (MyUtils.notEmpty(users.getPassword())) {
					users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
				}
				System.out.println(JSONObject.toJSONString(users));
				
				if (MyUtils.notEmpty(usersAddDTO.getUsrgrps())) {
					List<UsersGroups> usrgrpslist = new ArrayList<UsersGroups>();
					usrgrpslist = usersAddDTO.getUsrgrps();
					usersService.save(users);
					for (int i = 0; i < usrgrpslist.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
						usrgrpslist.get(i).setUserid(users.getUserid());
					}
					usersGroupsService.saves(usrgrpslist);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userid", users.getUserid());
					return new ResObject(200, jsonObject);			
				} else {
					return new ResObject(400, "usrgrpid不能为空");
				}	

				
			} else {
				return new ResObject(400, "username不能为空");
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			return new ResObject(400, "操作异常");
		}
	}

	@ApiOperation(value = "用户登陆", notes = "用户登陆", produces = "application/json")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResObject login(@RequestBody LoginUser loginUser) {
		return new ResObject();
	}

	@ApiOperation(value = "用户登出", notes = "用户登出", produces = "application/json")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResObject logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth:::" + auth);
		if (MyUtils.notEmpty(auth)) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ResObject(200, "成功");
	}
	

}
