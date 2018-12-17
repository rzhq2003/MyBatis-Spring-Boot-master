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
import tk.mybatis.springboot.model.HostsTemplates;
import tk.mybatis.springboot.model.Pages;

import tk.mybatis.springboot.req.TemplatesAddDTO;
import tk.mybatis.springboot.req.TemplatesUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.GroupsService;
import tk.mybatis.springboot.service.HostsGroupsService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.HostsTemplatesService;
import tk.mybatis.springboot.service.ItemsService;
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
	
	@Autowired
	ItemsService itemsService;
    
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
        	if (MyUtils.notEmpty(templatesAddDTO.getHost())) {
            	Hosts hosts = new Hosts();
            	BeanUtils.copyProperties(templatesAddDTO, hosts); 
            	Long[] groupids = templatesAddDTO.getGroupids();
            	if (MyUtils.notEmpty(groupids)) {
            		hosts.setStatus(3);
            		hostsService.save(hosts); //写入模板
            		for (int i = 0; i < groupids.length; i++) {
            			HostsGroups hostsGroups = new HostsGroups();
            			hostsGroups.setHostid(hosts.getHostid());
            			hostsGroups.setGroupid(groupids[i]);
            			System.out.print(JSONObject.toJSONString(hostsGroups));
            			hostsGroupsService.save(hostsGroups);
					}
            		
            		Long[] hostids = templatesAddDTO.getHostids();
            		if (MyUtils.notEmpty(hostids)) {
            			for (int i = 0; i < hostids.length; i++) {
							HostsTemplates hostsTemplates = new HostsTemplates();
							hostsTemplates.setHostid(hostids[i]);
							hostsTemplates.setTemplateid(hosts.getHostid());
							hostsTemplatesService.save(hostsTemplates);
						}
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
    		if (MyUtils.notEmpty(templatesUpdateDTO.getHostid())) {
    			Hosts hosts = new Hosts();
    			BeanUtils.copyProperties(templatesUpdateDTO, hosts);
    			hostsService.updateById(hosts);
    			
    			Long[] groupids = templatesUpdateDTO.getGroupids();
    			if (groupids != null) {
    				if (MyUtils.notEmpty(groupids)) {   					
    					HostsGroups hostsGroups = new HostsGroups();
    					hostsGroups.setHostid(templatesUpdateDTO.getHostid());
    					String str = hostsGroupsService.getBy(hostsGroups, "groupid");
    					Long[] groupidss= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
    		 			Long[] groupids_save = MyUtils.substract(groupids, groupidss); //写入数据
            			Long[] groupids_del = MyUtils.substract(groupidss, groupids); //删除数据           			
            			if (MyUtils.notEmpty(groupids_save)) {
            				for (int i = 0; i < groupids_save.length; i++) {
            					hostsGroups.setHostgroupid(null);
            					hostsGroups.setGroupid(groupids_save[i]);
            					hostsGroups.setHostid(templatesUpdateDTO.getHostid());
            					System.out.print("hostsGroups>>>" + JSONObject.toJSONString(hostsGroups));
            					hostsGroupsService.save(hostsGroups);
        					}
            			}
            			if (MyUtils.notEmpty(groupids_del)) {
            				for (int i = 0; i < groupids_del.length; i++) {
            					hostsGroups.setGroupid(groupids_del[i]);
            					hostsGroups.setHostid(templatesUpdateDTO.getHostid());
            					hostsGroupsService.delete(hostsGroups);
        					}
            			}       			
    				} else {
    					return new ResObject(400, "groupid不能为空");	
    				}
    			}
    		    
    			Long[] hostids = templatesUpdateDTO.getHostids();
    			if (hostids != null) {
    				HostsTemplates hostsTemplates = new HostsTemplates();
    				hostsTemplates.setTemplateid(templatesUpdateDTO.getHostid());
    				String str = hostsTemplatesService.getBy(hostsTemplates, "hostid");
    				Long[] hostidss= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
		 			Long[] hostids_save = MyUtils.substract(hostids, hostidss); //写入数据
        			Long[] hostids_del = MyUtils.substract(hostidss, hostids); //删除数据    
        			if (MyUtils.notEmpty(hostids_save)) {
        				for (int i = 0; i < hostids_save.length; i++) {
        					hostsTemplates.setHosttemplateid(null);
        					hostsTemplates.setHostid(hostids_save[i]);
        					hostsTemplates.setTemplateid(templatesUpdateDTO.getHostid());
        					System.out.print("hostsTemplates>>>" + JSONObject.toJSONString(hostsTemplates));
        					hostsTemplatesService.save(hostsTemplates);
    					}
        			}
        			if (MyUtils.notEmpty(hostids_del)) {
        				for (int i = 0; i < hostids_del.length; i++) {
        					hostsTemplates.setHostid(hostids_del[i]);
        					hostsTemplates.setTemplateid(templatesUpdateDTO.getHostid());
        					hostsTemplatesService.delete(hostsTemplates);
    					}
        			} 
        			
        			/*
        			 * 
        			 * 主机模板关联后，相应监控项也进行关联
        			 *         			
        			*/
    			}
  			
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("templateid", templatesUpdateDTO.getHostid());
				return new ResObject(200, jsonObject);	
    		} else {
    			return new ResObject(400, "hostid不以为空");
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
    		JSONObject jsonObject = new JSONObject(true);
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


