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
import tk.mybatis.springboot.model.HostsTemplates;
import tk.mybatis.springboot.model.Pages;

import tk.mybatis.springboot.req.TemplatesAddDTO;
import tk.mybatis.springboot.req.TemplatesUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.GroupsService;
import tk.mybatis.springboot.service.HostsGroupsService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.HostsTemplatesService;
import tk.mybatis.springboot.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/***
 * 重点参数说明：

***/

@RestController
@Api(tags={"template-module-resource"})
@RequestMapping(value = "/api/v1/protected/template")
public class TemplatesController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	HostsService hostsService;
	
	@Autowired
	HostsGroupsService hostsGroupsService;
	
	@Autowired
	HostsTemplatesService hostsTemplatesService;
	
	@Autowired
	GroupsService groupsService;
	
	@Autowired
	HttpServletRequest request;
    
    // add update delete view list
    @ApiOperation(value = "模板列表", notes = "模板列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "", method = RequestMethod.GET)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    public ResObject getAll(Pages pages) {       
    	return new ResObject(200, hostsService.getAllTemplates(pages));
    }
    
    @ApiOperation(value = "模板创建", notes = "模板创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody TemplatesAddDTO templatesAddDTO) { 
    	try {	
        	if (MyUtils.notEmpty(templatesAddDTO.getTemplates().getHost())) {
            	Hosts hosts = new Hosts();
            	BeanUtils.copyProperties(templatesAddDTO.getTemplates(), hosts); 
            	if (MyUtils.notEmpty(templatesAddDTO.getGroups())) {
            		List<HostsGroups> groupslist = new ArrayList<HostsGroups>();
            		groupslist = templatesAddDTO.getGroups();
            		hosts.setStatus(3);
            		hostsService.save(hosts);
					for (int i = 0; i < groupslist.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
						groupslist.get(i).setHostid(hosts.getHostid());
					}
					System.out.print(JSONObject.toJSONString(groupslist));
					hostsGroupsService.saves(groupslist);
					
					if (MyUtils.notEmpty(templatesAddDTO.getHosts())) {
						List<HostsTemplates> hostslist = new ArrayList<HostsTemplates>();
						hostslist = templatesAddDTO.getHosts();
						for (int i = 0; i < hostslist.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
							hostslist.get(i).setTemplateid(hosts.getHostid());
						}
						System.out.print(JSONObject.toJSONString(hostslist));
						hostsTemplatesService.saves(hostslist);
					}
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("templateid", hosts.getHostid());
					return new ResObject(200, jsonObject);	
            	} else {
            		return new ResObject(400, "groupid不能为空"); 
            	}	
        	} else {
        		return new ResObject(400, "主机名称不能为空"); 
        	}   		
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}   	
    }
    
    
    @ApiOperation(value = "模板更新", notes = "模板更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject update(@RequestBody TemplatesUpdateDTO templatesUpdateDTO) { 
    	/*
    	 * hostid必填段
    	 * groups 无保持不变，有，先执行删除，后重新写入，hosts同理操作;
    	 */
    	try {
    		if (MyUtils.notEmpty(templatesUpdateDTO.getTemplateid())) {
    			Hosts hosts = new Hosts();
    			hosts.setHostid(templatesUpdateDTO.getTemplateid());
    			BeanUtils.copyProperties(templatesUpdateDTO, hosts);
    			hostsService.updateById(hosts);
    	
    			if (MyUtils.notEmpty(templatesUpdateDTO.getGroups())) {
    				// 执行删除，后执行写入  templateid
    				HostsGroups hostsGroups = new HostsGroups();
    				System.out.print("templateid:::" + templatesUpdateDTO.getTemplateid());
    				hostsGroups.setHostid(templatesUpdateDTO.getTemplateid());
    				System.out.print("hostsGroups" + JSONObject.toJSONString(hostsGroups));
    				String templateids = hostsGroupsService.getIds(hostsGroups);
    				System.out.print("templateids:::待删除" + templateids); 
    				if (templateids.length() != 0) {
    					hostsGroupsService.deleteByIds(templateids);
    				}
    				
    				// 重新写入
            		List<HostsGroups> groupslist = new ArrayList<HostsGroups>();
            		groupslist = templatesUpdateDTO.getGroups();
					for (int i = 0; i < groupslist.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
						groupslist.get(i).setHostid(templatesUpdateDTO.getTemplateid());
					}
					System.out.print(JSONObject.toJSONString(groupslist));
					hostsGroupsService.saves(groupslist);
    				
    			}
    		
    			if (templatesUpdateDTO.getHosts() != null) {
    				// 执行删除，后执行写入  templateid
    				HostsTemplates hostsTemplates = new HostsTemplates();
    				System.out.print("templateid:::" + templatesUpdateDTO.getTemplateid());
    				hostsTemplates.setTemplateid(templatesUpdateDTO.getTemplateid());
    				System.out.print("hostsTemplates" + JSONObject.toJSONString(hostsTemplates));
    				String hostsids = hostsTemplatesService.getIds(hostsTemplates);
    				System.out.print("ids:::待删除" + hostsids); 
    				if (hostsids.length() != 0) {
    					hostsTemplatesService.deleteByIds(hostsids);
    				}				
    				// 重新写入
    				if (!templatesUpdateDTO.getHosts().isEmpty()) {
                		List<HostsTemplates> hostslist = new ArrayList<HostsTemplates>();
                		hostslist = templatesUpdateDTO.getHosts();
                		System.out.print("hostslist:::" + JSONObject.toJSONString(hostslist));
                		System.out.print("size:::" + hostslist.size());
                		
    					for (int i = 0; i < hostslist.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
    						hostslist.get(i).setTemplateid(templatesUpdateDTO.getTemplateid());
    					}
    					System.out.print(JSONObject.toJSONString(hostslist));
    					hostsTemplatesService.saves(hostslist);    	
    				}				
    			}   			
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("templateid", templatesUpdateDTO.getTemplateid());
				return new ResObject(200, jsonObject);	
    		} else {
    			return new ResObject(400, "templateid不以为空");
    		}	
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}   	
    }
    
    // 用户删除
    @ApiOperation(value = "模板详情", notes = "模板详情", produces = "application/json")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    	})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResObject view(@PathVariable Long id) {
    	try {
    		JSONObject jsonObject = new JSONObject();
    		Hosts hosts = hostsService.getById(id);
    		
    		HostsGroups hostsGroups = new HostsGroups();
    		hostsGroups.setHostid(id);
    		String groupsids = hostsGroupsService.getBy(hostsGroups, "groupid");
    		List<Groups> groupslist = new ArrayList<Groups>();
    		groupslist = groupsService.selectByIds(groupsids);
    		
    		HostsTemplates hostsTemplates = new HostsTemplates();
    		hostsTemplates.setTemplateid(id);
    		String hostsids = hostsTemplatesService.getBy(hostsTemplates, "hostid");
    		List<Hosts> hostslist = hostsService.selectByIds(hostsids);
    		
    		jsonObject.put("templates",hosts);
    		jsonObject.put("groups", groupslist);
    		jsonObject.put("hosts", hostslist);
    		return new ResObject(200, jsonObject);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResObject(400, "操作异常"); 
		}
    }
    
    
    // 用户删除
    @ApiOperation(value = "模板删除", notes = "模板删除", produces = "application/json")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "ids", value = "模板id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject delete(@PathVariable String ids) {
    		hostsService.deleteByIds(ids);
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("templatesid", ids);
    		return new ResObject(200,jsonObject, "1");
    }
}


