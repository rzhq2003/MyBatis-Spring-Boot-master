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

import tk.mybatis.springboot.model.Hosts;
import tk.mybatis.springboot.model.HostsGroups;
import tk.mybatis.springboot.model.Pages;

import tk.mybatis.springboot.request.HostsAddDTO;
import tk.mybatis.springboot.request.HostsUpdateDTO;
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

***/

@RestController
@Api(tags={"hosts-module-resource"})
@RequestMapping(value = "/api/v1/protected/hosts")
public class HostsController {
	
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
	@ApiOperation(value = "门店列表", notes = "门店列表", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"), })
	@RequestMapping(value = "", method = RequestMethod.GET) // 接口基本路径
	// json格式传递对象使用RequestBody注解
	public ResObject getAll(Pages pages) {
		return new ResObject(200, hostsService.getAllHosts(pages));
	}

	@ApiOperation(value = "门店删除", notes = "门店删除", produces = "application/json")
	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "ids", value = "用户id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResObject delete(@PathVariable String ids) {
		try {
			hostsService.deleteByIds(ids);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("hostids", ids);
			return new ResObject(200, jsonObject);
		} catch (Exception e) {
			return new ResObject(400, "操作异常");
		}

	}
	
    @ApiOperation(value = "门店详情", notes = "门店详情",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "1000", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResObject view(@PathVariable Long id) {
    	try {
            return new ResObject(200, hostsService.getById(id));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResObject(400, "操作异常"); 
		}
    }
    
	
	@ApiOperation(value = "门店创建", notes = "门店创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody HostsAddDTO hostsAddDTO) {
    	try {
    		if (MyUtils.notEmpty(hostsAddDTO.getHost()) && MyUtils.notEmpty(hostsAddDTO.getName())) {
    			Hosts hosts = new Hosts();
    			BeanUtils.copyProperties(hostsAddDTO, hosts);
    			Long[] groupids = hostsAddDTO.getGroupids();
    			if (MyUtils.notEmpty(groupids)) {
    				hostsService.save(hosts);
    				List<HostsGroups> list = new ArrayList<HostsGroups>();
    				for (int i = 0; i < groupids.length; i++) {
						HostsGroups hostsGroups = new HostsGroups();
						hostsGroups.setGroupid(groupids[i]);
						hostsGroups.setHostid(hosts.getHostid());
						list.add(hostsGroups);
					}
    				hostsGroupsService.saves(list);
    				JSONObject jsonObject = new JSONObject();
    				jsonObject.put("hostid", hosts.getHostid());
    				return new ResObject(200, jsonObject);
    				
    			} else {
    				return new ResObject(400, "groupids不能为空"); 
    			}
    		} else {
    			return new ResObject(400, "host或name不能为空"); 
    		}    		  			
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }
	
	
	@ApiOperation(value = "门店更新", notes = "门店更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody HostsUpdateDTO hostsUpdateDTO) {
    	try {
    		if (MyUtils.notEmpty(hostsUpdateDTO.getHostid())) {
    			Hosts hosts = new Hosts();
    			BeanUtils.copyProperties(hostsUpdateDTO, hosts);
    			hostsService.updateById(hosts);
    			Long[] groupids = hostsUpdateDTO.getGroupids();
    			if (groupids != null) {
    				HostsGroups hostsGroups = new HostsGroups();
    				hostsGroups.setHostid(hostsUpdateDTO.getHostid());
    				String str = hostsGroupsService.getByValues(hostsGroups, "groupid");
    				System.out.println("str>>>" + str);
    				Long[] groupidss= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
    				Long[] groupids_save = MyUtils.substract(groupids, groupidss); //写入数据
    				Long[] groupids_del = MyUtils.substract(groupidss, groupids); //删除数据
        			if (MyUtils.notEmpty(groupids_save)) {
        				List<HostsGroups> hglist = new ArrayList<HostsGroups>();
        				for (int i = 0; i < groupids_save.length; i++) {
        					HostsGroups hostsGroups_s = new HostsGroups();
        					hostsGroups_s.setGroupid(groupids_save[i]);
        					hostsGroups_s.setHostid(hostsUpdateDTO.getHostid());
        					hglist.add(hostsGroups_s);
    					}
     					System.out.println("hglist>>>" + JSONObject.toJSONString(hglist));
     					hostsGroupsService.saves(hglist);
        			}
        			if (MyUtils.notEmpty(groupids_del)) {
        				for (int i = 0; i < groupids_del.length; i++) {
        					HostsGroups hostsGroups_d = new HostsGroups();
        					hostsGroups_d.setGroupid(groupids_del[i]);
        					hostsGroups_d.setHostid(hostsUpdateDTO.getHostid());
        					hostsGroupsService.delete(hostsGroups_d);
    					}
        			}	
    				
    			}
    			
    			JSONObject jsonObject = new JSONObject();
				jsonObject.put("hostid", hosts.getHostid());
    			return new ResObject(200, jsonObject); 

    		} else {
    			return new ResObject(400, "hostid不能为空"); 
    		}    		  			
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }
	
}


