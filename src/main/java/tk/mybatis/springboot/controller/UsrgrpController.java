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


import org.apache.commons.beanutils.ConvertUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

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
import tk.mybatis.springboot.model.Users;
import tk.mybatis.springboot.model.UsersGroups;
import tk.mybatis.springboot.model.Usrgrp;
import tk.mybatis.springboot.request.UsrgrpAddDTO;
import tk.mybatis.springboot.request.UsrgrpUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.RightsService;
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
    UsersService usersService;
	
	
	
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
    	return new ResObject(200, usrgrpService.getAll(pages));
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
    		if (MyUtils.notEmpty(usrgrpAddDTO.getName())) {
    			Usrgrp usrgrp = new Usrgrp();
    			BeanUtils.copyProperties(usrgrpAddDTO, usrgrp);
    			usrgrpService.save(usrgrp);
    			/*
    			 * 判断是否关联用户，或关联主机组
    			 */
    			if (MyUtils.notEmpty(usrgrpAddDTO.getUserids())) {
    				Long[] userids = usrgrpAddDTO.getUserids();
    				List<UsersGroups> uglist = new ArrayList<UsersGroups>();
    				for (int i = 0; i < userids.length; i++) {
						UsersGroups usersGroups = new UsersGroups();
						usersGroups.setUserid(userids[i]);
						usersGroups.setUsrgrpid(usrgrp.getUsrgrpid());
						uglist.add(usersGroups);
					}
    				usersGroupsService.saves(uglist);

    			}
    			
    			if (MyUtils.notEmpty(usrgrpAddDTO.getIds())) {
    				Long[] ids = usrgrpAddDTO.getIds();
    				List<Rights> rlist = new ArrayList<Rights>();
    				for (int i = 0; i < ids.length; i++) {
						Rights rights = new Rights();
						rights.setId(ids[i]);
						rights.setGroupid(usrgrp.getUsrgrpid());
						rlist.add(rights);
					}
    				System.out.print(JSONObject.toJSONString(rlist));
    				rightsService.saves(rlist);
    			}
    			
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("usrgrpid", usrgrp.getUsrgrpid());
    			return new ResObject(200, jsonObject);
    		} else {
    			return new ResObject(400, "主机组名不能为空");   			
    		}     	
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
    public ResObject update(@RequestBody UsrgrpUpdateDTO usrgrpUpdateDTO) {
    	try {
    		if (MyUtils.notEmpty(usrgrpUpdateDTO.getUsrgrpid())) {
    			Usrgrp usrgrp = new Usrgrp();
    			BeanUtils.copyProperties(usrgrpUpdateDTO, usrgrp);
    			usrgrpService.updateById(usrgrp);
    			
    			Long[] userids = usrgrpUpdateDTO.getUserids();
    			if (userids != null) {
        			UsersGroups usersGroups = new UsersGroups();
        			usersGroups.setUsrgrpid(usrgrpUpdateDTO.getUsrgrpid());
        			String str = usersGroupsService.getBy(usersGroups, "userid");
        			Long[] useridss= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
        			Long[] usersids_save = MyUtils.substract(userids, useridss); //写入数据
        			System.out.print("\nuseridss" + usersids_save);
        			Long[] userids_del = MyUtils.substract(useridss, userids); //删除数据
        			if (MyUtils.notEmpty(usersids_save)) {
        				List<UsersGroups> uglist = new ArrayList<UsersGroups>();
        				for (int i = 0; i < usersids_save.length; i++) {
        					UsersGroups uGroups = new UsersGroups();
        					uGroups.setUserid(usersids_save[i]);
        					uGroups.setUsrgrpid(usrgrpUpdateDTO.getUsrgrpid());
        					uglist.add(uGroups);
    					}
     					System.out.print("uglist>>>" + JSONObject.toJSONString(uglist));
    					usersGroupsService.saves(uglist);
        			}
        			if (MyUtils.notEmpty(userids_del)) {
        				for (int i = 0; i < userids_del.length; i++) {
        					usersGroups.setUserid(userids_del[i]);
        					usersGroups.setUsrgrpid(usrgrpUpdateDTO.getUsrgrpid());
        					usersGroupsService.delete(usersGroups);
    					}
        			}	
    			}
		
    			

    			Long[] groupids = usrgrpUpdateDTO.getGroupids();
    			if (groupids != null) {
        			Rights rights = new Rights();
        			rights.setGroupid(usrgrpUpdateDTO.getUsrgrpid());
        			String strr = rightsService.getBy(rights, "id");
        			Long[] groupidss= (Long[]) ConvertUtils.convert(strr.split(","), Long.class);
        			Long[] groupids_save = MyUtils.substract(groupids, groupidss); //写入数据
        			Long[] groupids_del = MyUtils.substract(groupidss, groupids); //删除数据
        			if (MyUtils.notEmpty(groupids_save)) {
        				List<Rights> rlist = new ArrayList<Rights>();
        				for (int i = 0; i < groupids_save.length; i++) {
        					Rights rightss = new Rights();
        					rightss.setId(groupids_save[i]);
        					rightss.setGroupid(usrgrpUpdateDTO.getUsrgrpid());
        					rlist.add(rightss);
    					}
        				rightsService.saves(rlist);
        			}
        			if (MyUtils.notEmpty(groupids_del)) {
        				for (int i = 0; i < groupids_del.length; i++) {
        					rights.setId(groupids_del[i]);
        					rights.setGroupid(usrgrpUpdateDTO.getUsrgrpid());
        					rightsService.delete(rights);
    					}
        			} 
    			}
    			JSONObject jsonObject = new JSONObject(true);
    			jsonObject.put("usrgrpid", usrgrpUpdateDTO.getUsrgrpid());
    			return new ResObject(200, jsonObject);
    		} else {
    			return new ResObject(400, "usrgrpid不能为空");
    		}
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
    

    @ApiOperation(value = "用户组详情", notes = "用户组详情",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject view(@PathVariable Long id) {
    	try {
    		JSONObject jsonObject = new JSONObject(true);
            Usrgrp usrgrp = usrgrpService.getById(id);
            jsonObject.put("usrgrp",usrgrp);
            UsersGroups usersGroups = new UsersGroups();
            usersGroups.setUsrgrpid(id);
            String userids = usersGroupsService.getBy(usersGroups, "userid");
            List<Users> list = new ArrayList<Users>();
            if (MyUtils.notEmpty(userids)) {
            	list = usersService.selectByIds(userids);
            }
            jsonObject.put("users",list);
            return new ResObject(200, jsonObject);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResObject(400, "操作异常"); 
		}
    }
}

