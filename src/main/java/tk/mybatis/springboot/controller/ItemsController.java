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

import tk.mybatis.springboot.req.ItemsAddDTO;
import tk.mybatis.springboot.response.ResObject;
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
	@ApiOperation(value = "监控项创建", notes = "监控项创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
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
					Long[] hostids= (Long[]) ConvertUtils.convert(str.split(","), Long.class);
					List<Items> list = new ArrayList<Items>();
					for (int i = 0; i < hostids.length; i++) {
						Items itemss = new Items();
						itemss.setHostid(hostids[i]);
						itemss.setName(itemsAddDTO.getName());
						itemss.setTemplateid(items.getItemid());
						itemss.setDescription(itemsAddDTO.getDescription());
						itemss.setEnable(0);
						list.add(itemss);
						System.out.print(JSONObject.toJSONString(itemss));
					}
					itemsService.saves(list);
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
    


}

