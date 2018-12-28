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



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.GroupsService;
import tk.mybatis.springboot.service.RightsService;
import tk.mybatis.springboot.service.UsersGroupsService;
import tk.mybatis.springboot.service.UsersService;

import javax.servlet.http.HttpServletRequest;

/***
 * 重点参数说明：

***/

@RestController
@Api(tags={"groups-module-resource"})
@RequestMapping(value = "/api/v1/protected/groups")
public class GroupsController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	GroupsService groupsService;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	UsersGroupsService usersGroupsService;
	
	@Autowired
	RightsService rightsService;
	
    // 获取用户列表
    @ApiOperation(value = "门店组列表(根据用户)", notes = "门店组列表(根据用户)",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "username", value = "username", required = true, dataType = "string", paramType = "path")
    	})
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)//接口基本路径
    // json格式传递对象使用RequestBody注解
    public ResObject getByUsername(@PathVariable String username) {
    	return new ResObject(200, groupsService.getByUsername(username));
    }
    
    // 获取用户列表
    @ApiOperation(value = "门店组列表", notes = "门店组列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	})
    @RequestMapping(value = "", method = RequestMethod.GET)//接口基本路径
    // json格式传递对象使用RequestBody注解
    public ResObject getAll(Pages pages) {   	
    	return new ResObject(200, groupsService.getAll(pages));
    }
    
    // 用户删除
    @ApiOperation(value = "门店组删除", notes = "门店组删除", produces = "application/json")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "ids", value = "用户id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject delete(@PathVariable String ids) {
    		groupsService.deleteByIds(ids);
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("groupids", ids);
    		return new ResObject(200,jsonObject);
    }
    
    
    


}


