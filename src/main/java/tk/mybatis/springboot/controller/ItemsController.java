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
import tk.mybatis.springboot.model.HostsTemplates;
import tk.mybatis.springboot.model.Items;
import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.req.ItemsAddDTO;
import tk.mybatis.springboot.req.ItemsUpdateDTO;

import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.HostsTemplatesService;
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
	
	@Autowired
	HostsTemplatesService hostsTemplatesService;

	
    // 获取用户列表
    @ApiOperation(value = "监控项列表", notes = "监控项列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "", method = RequestMethod.GET)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    public ResObject getAll(Pages pages) {       
    	return new ResObject(200, itemsService.getAll(pages));
    }
	

	@ApiOperation(value = "监控项创建", notes = "监控项创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody ItemsAddDTO itemsAddDTO) {
		try {
			
			/*
			 * 
			 * 若监控项建立在模板上，则关联到相应主机已建立
			 * 若不是模板上，则创建而已
			 * 
			*/
			if (itemsAddDTO.getName() != null && itemsAddDTO.getHostid() != null) {
				Items items = new Items();
				BeanUtils.copyProperties(itemsAddDTO, items);
				itemsService.save(items);
				Hosts hosts = new Hosts();				
				hosts = hostsService.getById(itemsAddDTO.getHostid());
				if (hosts.getStatus() == 3) {
					System.out.print("当前为模板监控项,添加相关联主机监控\n");
					HostsTemplates hostsTemplates = new HostsTemplates();
					hostsTemplates.setTemplateid(items.getHostid());
					String str = hostsTemplatesService.getBy(hostsTemplates, "hostid");
					if (MyUtils.isNotEmpty(str)) {
						Long[] hostids= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
						for (int i = 0; i < hostids.length; i++) {
							Items itemss = new Items();
							BeanUtils.copyProperties(itemsAddDTO, itemss);
							itemss.setHostid(hostids[i]);
							itemss.setTemplateid(items.getItemid());
							itemsService.save(itemss);
						}						
					}
					}			
				JSONObject jsonObject = new JSONObject(true);
				jsonObject.put("itemid", items.getItemid());
				return new ResObject(200, jsonObject);
			} else {
				return new ResObject(400, "name或hostid不能为空");
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
		
	}
	
    // 获取用户列表
	@ApiOperation(value = "监控项更新", notes = "监控项更新",produces = "application/json")
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
    		 * 监控模板资料更改相关联监控也更改 name description enable
    		 * 
    		 */
    		if (MyUtils.notEmpty(itemsUpdateDTO.getItemid())) {
    			Items items = new Items();
    			items.setTemplateid(itemsUpdateDTO.getItemid());
    			String str = itemsService.getIds(items);
    			items.setTemplateid(null);
    			if (str.length() != 0) {
    				str = str.concat("," + itemsUpdateDTO.getItemid().toString());
    			} else {
					str = itemsUpdateDTO.getItemid().toString();
				}
    			Long[] itemids= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
    			for (int i = 0; i < itemids.length; i++) {
    				BeanUtils.copyProperties(itemsUpdateDTO, items);
    				items.setItemid(itemids[i]);
    				itemsService.updateById(items);
				}
    			JSONObject jsonObject = new JSONObject(true);
    			jsonObject.put("itemids", itemids);
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
    
    @ApiOperation(value = "监控项删除", notes = "监控项删除", produces = "application/json")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "ids", value = "监控项id逗号分隔，如1,2,3", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN')")
    public ResObject delete(@PathVariable String ids) {
    		itemsService.deleteByIds(ids);
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("itemids", ids);
    		return new ResObject(200,jsonObject);
    }
    
    @ApiOperation(value = "监控项详情", notes = "监控项详情",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
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


