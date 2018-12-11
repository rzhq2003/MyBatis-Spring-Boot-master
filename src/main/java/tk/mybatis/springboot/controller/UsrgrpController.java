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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.alibaba.fastjson.JSONObject;


import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.model.Rights;
import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.model.Usrgrp;
import tk.mybatis.springboot.req.UsrgrpAddDTO;
import tk.mybatis.springboot.req.UsrgrpUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.RightsService;
import tk.mybatis.springboot.service.UsersGroupsService;
import tk.mybatis.springboot.service.UsrgrpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/***
 * 重点参数说明：
***/

@RestController
@Api(tags = {"usrgrp-module-resource"})
@RequestMapping(value = "/api/v1/protected/usrgrp")
public class UsrgrpController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    UsrgrpService usrgrpService;
	
	@Autowired
	UsersGroupsService usersGroupsService;
	
	@Autowired
	RightsService rightsService;
	
	
	
	

	
	@Autowired
	HttpServletRequest request;
	
    // 获取用户列表
    @ApiOperation(value = "用户组列表", notes = "用户组列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "", method = RequestMethod.GET)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    public ResObject getAll(Pages pages) {       
        return new ResObject(200,usrgrpService.getAll(pages));
    }
    
    // 获取用户列表
	@ApiOperation(value = "用户组创建", notes = "用户组创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody UsrgrpAddDTO usrgrpAddDTO) {
    	try {
    		JSONObject jsonObject = new JSONObject();
    		Usrgrp usrgrp = new Usrgrp();
    		
            if (usrgrpAddDTO.getName() != null) {
            	usrgrp.setName(usrgrpAddDTO.getName());
                usrgrpService.save(usrgrp);                      
            	
                if (usrgrpAddDTO.getUserids() != null) {         
                	String[] Userids = usrgrpAddDTO.getUserids().split(",");
            		List<UsersGroups> list = new ArrayList<UsersGroups>();
            		for (String s:Userids) {
            			UsersGroups usersGroups = new UsersGroups();
            			usersGroups.setUserid(Long.parseLong(s));
            			usersGroups.setUsrgrpid(usrgrp.getUsrgrpid());
            			System.out.println(JSONObject.toJSONString(usersGroups));
            			list.add(usersGroups);
            		}
                	usersGroupsService.saves(list);
                }
                
                if (usrgrpAddDTO.getIds() != null) {         
                	String[] ids = usrgrpAddDTO.getIds().split(",");
            		List<Rights> list = new ArrayList<Rights>();
            		for (String s:ids) {
            			Rights rights = new Rights();
            			rights.setId(Long.parseLong(s));
            			rights.setGroupid(usrgrp.getUsrgrpid());
            			System.out.println(JSONObject.toJSONString(rights));
            			list.add(rights);
            		}
                	rightsService.saves(list);
                }
                 
                
                
            }
                    	        		   		
    		jsonObject.put("usrgrpid", usrgrp.getUsrgrpid());
        	return new ResObject(200, jsonObject);
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }
	
    
    // 获取用户列表
	@ApiOperation(value = "用户组更新", notes = "用户组更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody UsrgrpUpdateDTO usrgrpUpdateDTO) {
    	try {
    		JSONObject jsonObject = new JSONObject();
    		Usrgrp usrgrp = new Usrgrp();
    		                    	        		   		
    		jsonObject.put("usrgrpid", usrgrp.getUsrgrpid());
        	return new ResObject(200, jsonObject);
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }

    
    // 用户删除
    @ApiOperation(value = "用户组删除", notes = "用户组删除", produces = "application/json")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "ids", value = "用户组id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject delete(@PathVariable String ids) {
    		usrgrpService.deleteByIds(ids);
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("usrgrpids", ids);
    		return new ResObject(200,jsonObject, "1");
    }
    
}

