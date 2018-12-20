/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.controller;


import com.alibaba.fastjson.JSONObject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.model.Users;
import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.model.Usrgrp;
import tk.mybatis.springboot.request.UsersUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.UsersGroupsService;
import tk.mybatis.springboot.service.UsersService;
import tk.mybatis.springboot.service.UsrgrpService;
import tk.mybatis.springboot.util.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/***
 * 重点参数说明：

***/

@RestController
@Api(tags={"user-module-resource"})
@RequestMapping(value = "/api/v1/protected/users")
public class UsersController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    UsersService usersService;
	
	@Autowired
	UsrgrpService UsrgrpService;
	
	@Autowired
	UsersGroupsService UsersGroupsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	HttpServletRequest request;
	
    // 获取用户列表
    @ApiOperation(value = "用户列表", notes = "用户列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "", method = RequestMethod.GET)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    public ResObject getAll(Pages pages) {
    	return new ResObject(200, usersService.getAll(pages));
    }
    
    // 获取用户详情
    @ApiOperation(value = "用户详情", notes = "用户详情",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject view(@PathVariable Long id) {
    	try {
        	JSONObject jsonObject = new JSONObject(true);
            Users users = usersService.getById(id);
            jsonObject.put("users",users);
            UsersGroups usersGroups = new UsersGroups();
            usersGroups.setUserid(id);
            String usrgrpids = UsersGroupsService.getBy(usersGroups, "usrgrpid");
            System.out.print(usrgrpids);
            List<Usrgrp> list = new ArrayList<Usrgrp>();
            list = UsrgrpService.selectByIds(usrgrpids);
            jsonObject.put("usrgrps",list);
            return new ResObject(200, jsonObject);  
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResObject(400, "操作异常"); 
		}
  
    }
    
    // 获取用户详情
    @ApiOperation(value = "用户更新", notes = "用户更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject update(@RequestBody UsersUpdateDTO usersUpdateDTO) {
    	if (MyUtils.notEmpty(usersUpdateDTO.getUserid())) {
        	Users users = new Users();
        	BeanUtils.copyProperties(usersUpdateDTO, users);
    		if (MyUtils.notEmpty(users.getPassword())) {
    			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
    		}
        	Integer re = usersService.updateById(users);
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("userid", users.getUserid());
            return new ResObject(200, jsonObject, re.toString());    
    	} else {
    		return new ResObject(400, "userid不能为空");  
    	}

    }
   
    // 用户删除
    @ApiOperation(value = "用户删除", notes = "用户删除", produces = "application/json")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "ids", value = "用户id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject delete(@PathVariable String ids) {
    		usersService.deleteByIds(ids);
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("userids", ids);
    		return new ResObject(200,jsonObject);
    }
    


}


