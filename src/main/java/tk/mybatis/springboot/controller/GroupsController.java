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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.springboot.model.Groups;
import tk.mybatis.springboot.model.Hosts;
import tk.mybatis.springboot.model.HostsGroups;
import tk.mybatis.springboot.model.Pages;


import tk.mybatis.springboot.request.GroupsAddDTO;
import tk.mybatis.springboot.request.GroupsUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.GroupsService;
import tk.mybatis.springboot.service.HostsGroupsService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.RightsService;
import tk.mybatis.springboot.service.UsersGroupsService;
import tk.mybatis.springboot.service.UsersService;
import tk.mybatis.springboot.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/***
 * 重点参数说明：
 * 
 ***/

@RestController
@Api(tags = { "groups-module-resource" })
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
	HostsService hostsService;

	@Autowired
	UsersGroupsService usersGroupsService;

	@Autowired
	RightsService rightsService;
	
	@Autowired
	HostsGroupsService hostsGroupsService;

	// 获取用户列表
	@ApiOperation(value = "门店组列表(根据用户)", notes = "门店组列表(根据用户)", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "username", value = "username", required = true, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/{username}", method = RequestMethod.GET) // 接口基本路径
	// json格式传递对象使用RequestBody注解
	public ResObject getByUsername(@PathVariable String username) {
		return new ResObject(200, groupsService.getByUsername(username));
	}

	// 获取用户列表
	@ApiOperation(value = "门店组列表", notes = "门店组列表", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"), })
	@RequestMapping(value = "", method = RequestMethod.GET) // 接口基本路径
	// json格式传递对象使用RequestBody注解
	public ResObject getAll(Pages pages) {
		return new ResObject(200, groupsService.getAll(pages));
	}

	// 用户删除
	@ApiOperation(value = "门店组删除", notes = "门店组删除", produces = "application/json")
	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "ids", value = "用户id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResObject delete(@PathVariable String ids) {
		groupsService.deleteByIds(ids);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("groupids", ids);
		return new ResObject(200, jsonObject);
	}
	
    // 获取用户列表
	@ApiOperation(value = "门店组创建", notes = "门店组创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody GroupsAddDTO groupsAddDTO) {
    	try {
    		if (MyUtils.notEmpty(groupsAddDTO.getName())) {
    			Groups groups = new Groups();
    			BeanUtils.copyProperties(groupsAddDTO, groups);
    			groupsService.save(groups);
    			if (MyUtils.notEmpty(groupsAddDTO.getHostids())) {
    				Long[] hostids = groupsAddDTO.getHostids();
    				List<HostsGroups> list = new ArrayList<HostsGroups>();
    				for (int i = 0; i < hostids.length; i++) {
						HostsGroups hostsGroups = new HostsGroups();
						hostsGroups.setGroupid(groups.getGroupid());
						hostsGroups.setHostid(hostids[i]);
						list.add(hostsGroups);
					}
    				hostsGroupsService.saves(list);
    			}
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("groupid", groups.getGroupid());
    			return new ResObject(200, jsonObject);
    		} else {
    			return new ResObject(400, "name不能为空"); 
    		}    		  			
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }
	
    // 获取用户列表
	@ApiOperation(value = "门店组更新", notes = "门店组更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody GroupsUpdateDTO groupsUpdateDTO) {
    	try {
    		if (MyUtils.notEmpty(groupsUpdateDTO.getGroupid())) {
    			Groups groups = new Groups();
    			BeanUtils.copyProperties(groupsUpdateDTO, groups);
    			groupsService.updateById(groups);
    			Long[] hostids = groupsUpdateDTO.getHostids();
    			if (hostids != null) {
    				HostsGroups hostsGroups = new HostsGroups();
    				hostsGroups.setGroupid(groupsUpdateDTO.getGroupid());
    				String str = hostsGroupsService.getByValues(hostsGroups, "hostid");
    				Long[] hostidss= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
    				Long[] hostids_save = MyUtils.substract(hostids, hostidss); //写入数据
    				Long[] hostids_del = MyUtils.substract(hostidss, hostids); //删除数据
        			if (MyUtils.notEmpty(hostids_save)) {
        				List<HostsGroups> hglist = new ArrayList<HostsGroups>();
        				for (int i = 0; i < hostids_save.length; i++) {
        					HostsGroups hostsGroups_s = new HostsGroups();
        					hostsGroups_s.setHostid(hostids_save[i]);
        					hostsGroups_s.setGroupid(groupsUpdateDTO.getGroupid());
        					hglist.add(hostsGroups_s);
    					}
     					System.out.print("hglist>>>" + JSONObject.toJSONString(hglist));
     					hostsGroupsService.saves(hglist);
        			}
        			if (MyUtils.notEmpty(hostids_del)) {
        				for (int i = 0; i < hostids_del.length; i++) {
        					HostsGroups hostsGroups_d = new HostsGroups();
        					hostsGroups_d.setHostid(hostids_del[i]);
        					hostsGroups_d.setGroupid(groupsUpdateDTO.getGroupid());
        					hostsGroupsService.delete(hostsGroups_d);
    					}
        			}	
    			}
    			
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("groupid", groups.getGroupid());
    			return new ResObject(200, jsonObject);   			
    		} else {
    			return new ResObject(400, "groupid不能为空");	
    		}
    			
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }

	
    @ApiOperation(value = "门店组详情", notes = "门店组详情",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "1000", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResObject view(@PathVariable Long id) {
    	try {
    		JSONObject jsonObject = new JSONObject(true);
            Groups groups = groupsService.getById(id);
            jsonObject.put("groupid",groups.getGroupid());
            jsonObject.put("name",groups.getName());
            HostsGroups hostsGroups = new HostsGroups();
            hostsGroups.setGroupid(id);
            String hostids = hostsGroupsService.getByValues(hostsGroups, "hostid");
            List<Hosts> list = new ArrayList<Hosts>();
            if (MyUtils.notEmpty(hostids)) {
            	list = hostsService.selectByIds(hostids);
            }
            jsonObject.put("hosts",list);
            return new ResObject(200, jsonObject);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResObject(400, "操作异常"); 
		}
    }
	
	
	
}
