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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.springboot.model.History;
import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.model.Items;
import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.HistoryItemsService;
import tk.mybatis.springboot.service.HistoryService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.HostsTemplatesService;
import tk.mybatis.springboot.service.ItemsService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/***
 * 重点参数说明：

***/

@RestController
@Api(tags={"history-module-resource"})
@RequestMapping(value = "/api/v1/protected/history")
public class HistoryController {
	
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
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	HistoryItemsService historyItemsService;
	
    // 获取用户列表
    @ApiOperation(value = "参数值列表", notes = "参数值列表",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "hostid", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "{hostid}", method = RequestMethod.GET)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    public ResObject getBys(Pages pages, @PathVariable Long hostid) {
		JSONArray jsonArray = new JSONArray();
		History history = new History();
		history.setHostid(hostid);
		List<History> list = new ArrayList<History>();
		list = historyService.select(history);
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			HistoryItems historyItems = new HistoryItems();
			historyItems.setsheetid(list.get(i).getSheetid());
			List<HistoryItems> list1 = new ArrayList<HistoryItems>();
			list1 = historyItemsService.select(historyItems);
			jsonObject.put("hostid", list.get(i).getHostid());
			jsonObject.put("sheetid", list.get(i).getSheetid());
			for (int j = 0; j < list1.size(); j++) {
				Items items = new Items();
				items = itemsService.getById(list1.get(j).getItemid());
				jsonObject.put(items.getItemid().toString(), list1.get(j).getValue());	
			}
			jsonArray.add(jsonObject);		
		}
		
		System.out.println(JSONObject.toJSONString(jsonArray));
		return new ResObject(200, jsonArray);
					
		}
  	



}


