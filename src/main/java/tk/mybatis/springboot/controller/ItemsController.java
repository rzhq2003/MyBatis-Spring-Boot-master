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
import tk.mybatis.springboot.model.Hosts;

import tk.mybatis.springboot.model.Items;
import tk.mybatis.springboot.request.ItemsAddDTO;
import tk.mybatis.springboot.request.ItemsUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.HostsService;

import tk.mybatis.springboot.service.ItemsService;
import tk.mybatis.springboot.util.MyUtils;


import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/***
 * 重点参数说明：

***/

@RestController
@Api(tags={"items-module-resource"})
@RequestMapping(value = "/api/v1/protected/items")
public class ItemsController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ItemsService itemsService;
	
	@Autowired
	HostsService hostsService;
	


	
    // 获取用户列表
    @ApiOperation(value = "参数项列表", notes = "参数项列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "templateid", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "{templateid}", method = RequestMethod.GET)//接口基本路径
    // json格式传递对象使用RequestBody注解
    public ResObject getBys(@PathVariable Long templateid) {   
    	System.out.print("hotsid>>>" + templateid);
    	Items items = new Items();
    	items.setTemplateid(templateid);
    	return new ResObject(200, itemsService.select(items));
    }
	

	@ApiOperation(value = "参数项创建", notes = "参数项创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody ItemsAddDTO itemsAddDTO) {
		try {
			
			if (itemsAddDTO.getName() != null && itemsAddDTO.getTemplateid() != null) {
				Hosts hosts = new Hosts();				
				hosts = hostsService.getById(itemsAddDTO.getTemplateid());
				if (hosts.getStatus() == 3) {
					Items items = new Items();
					BeanUtils.copyProperties(itemsAddDTO, items);
					itemsService.save(items);
					JSONObject jsonObject = new JSONObject(true);
					jsonObject.put("itemid", items.getItemid());
					return new ResObject(200, jsonObject);
				} else {
					return new ResObject(400, "templateid传入错误");
				}			
			} else {
				return new ResObject(400, "name或templateid不能为空");
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
		
	}
	
    // 获取用户列表
	@ApiOperation(value = "参数项更新", notes = "参数项更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject update(@RequestBody ItemsUpdateDTO itemsUpdateDTO) {
    	try {
    		/*
    		 * 参数模板资料更改相关联参数也更改 name description enable
    		 * 
    		 */
    		if (MyUtils.notEmpty(itemsUpdateDTO.getItemid())) {
    			Items items = new Items();
    			BeanUtils.copyProperties(itemsUpdateDTO, items);
				itemsService.updateById(items);
    			JSONObject jsonObject = new JSONObject(true);
    			jsonObject.put("itemid", items.getItemid());
    			return new ResObject(200, jsonObject);
    		} else {
    			return new ResObject(400, "itemid不能为空");
    		}
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
    }
    
    @ApiOperation(value = "参数项删除", notes = "参数项删除", produces = "application/json")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "ids", value = "参数项id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject delete(@PathVariable String ids) {
    		itemsService.deleteByIds(ids);
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("itemids", ids);
    		return new ResObject(200,jsonObject);
    }
    
    @ApiOperation(value = "参数项详情", notes = "参数项详情",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResObject view(@PathVariable Long id) {
    	try {
    		JSONObject jsonObject = new JSONObject(true);
            Items items = itemsService.getById(id);
            jsonObject.put("items",items); 
            return new ResObject(200, jsonObject);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResObject(400, "操作异常"); 
		}
    }

}


