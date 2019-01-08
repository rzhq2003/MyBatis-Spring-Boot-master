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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import tk.mybatis.springboot.model.History;
import tk.mybatis.springboot.model.HistoryItems;
import tk.mybatis.springboot.model.Hosts;
import tk.mybatis.springboot.model.Items;
import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.request.HistoryAddDTO;
import tk.mybatis.springboot.request.HistoryUpdateDTO;
import tk.mybatis.springboot.response.ResObject;
import tk.mybatis.springboot.service.HistoryItemsService;
import tk.mybatis.springboot.service.HistoryService;
import tk.mybatis.springboot.service.HostsService;
import tk.mybatis.springboot.service.ImportService;
import tk.mybatis.springboot.service.ItemsService;
import tk.mybatis.springboot.util.ExcelImportUtils;
import tk.mybatis.springboot.util.MyUtils;


import java.io.InputStream;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


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
	ImportService importService;
	

	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	HistoryItemsService historyItemsService;
	
    // 获取用户列表
    @ApiOperation(value = "参数值列表", notes = "参数值列表", produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "hostid", required = true, dataType = "Long", paramType = "path"),
    	@ApiImplicitParam(name = "templateid", required = true, dataType = "Long", paramType = "path")
    	})
    @RequestMapping(value = "{templateid}/{hostid}", method = RequestMethod.GET)//接口基本路径
    @PreAuthorize("hasRole('ADMIN')")
    // json格式传递对象使用RequestBody注解
    public ResObject getBys(Pages pages, @PathVariable Long templateid, @PathVariable Long hostid) {
		History history = new History();
		history.setHostid(hostid);
		history.setTemplateid(templateid);
		List<History> list = new ArrayList<History>();
        if (pages.getPage() != null && pages.getRows() != null) {
            PageHelper.startPage(pages.getPage(), pages.getRows(), "historyid");
        }  
		list = historyService.select(history);
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("hostid", list.get(i).getHostid());
			map.put("sheetid", list.get(i).getSheetid());
			
			List<HistoryItems> listItems = new ArrayList<HistoryItems>();
			listItems = historyItemsService.getBySheetid(list.get(i).getSheetid());
			System.out.println(JSONObject.toJSONString(listItems));
			List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>();
			for (int j = 0; j < listItems.size(); j++) {
				Items items = new Items();
				items = itemsService.getById(listItems.get(j).getItemid());
				Map<String, Object> maps = new LinkedHashMap<String, Object>();
				maps.put("itemid",items.getItemid());
				maps.put("name", items.getName());
				maps.put("historyitemid", listItems.get(j).getHistoryitemid());
				maps.put("value", listItems.get(j).getValue());
				maps.put("clock", listItems.get(j).getClock());
				listMaps.add(maps);
			}
	
			map.put("items", listMaps);

			listMap.add(map);
		}   
		
		JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("pageInfo", new PageInfo<History>(list));
        jsonObject.put("listItems", listMap);
        jsonObject.put("page", pages.getPage());
        jsonObject.put("rows", pages.getRows());
		return new ResObject(200, jsonObject);	
	}
  	

	@ApiOperation(value = "参数值创建", notes = "参数值创建",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/add", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject add(@RequestBody HistoryAddDTO historyAddDTO) {
		try {
			History history = historyService.add(historyAddDTO);
			return new ResObject(200, history);			
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}
		
	}
	
	
	@ApiOperation(value = "参数值更新", notes = "参数值更新",produces = "application/json")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//接口基本路径
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    // json格式传递对象使用RequestBody注解
    @Transactional(rollbackOn = Exception.class)
    public ResObject update(@RequestBody HistoryUpdateDTO historyUpdateDTO) {
		try {
			String sheetid = historyUpdateDTO.getSheetid();
			if (MyUtils.notEmpty(sheetid)) {
				List<HistoryItems> list = new ArrayList<HistoryItems>();
				list = historyUpdateDTO.getListItems();
				for (int i = 0; i < list.size(); i++) {
					Long itemid = list.get(i).getItemid();
					if (MyUtils.notEmpty(itemid)) {
						HistoryItems historyItems = new HistoryItems();
						historyItems.setSheetid(sheetid);
						historyItems.setItemid(itemid);
						String historyitemid = historyItemsService.getIds(historyItems);
						if (MyUtils.notEmpty(historyitemid)) {
							historyItems.setHistoryitemid(Long.parseLong(historyitemid));
						}
						historyItems.setValue(list.get(i).getValue());
						historyItems.setClock(System.currentTimeMillis());
						historyItemsService.saveAndUpdate(historyItems);
					}
				}
				JSONObject jsonObject = new JSONObject(true);
				jsonObject.put("sheetid", sheetid);
				return new ResObject(200, jsonObject);
			} else {
				return new ResObject(400, "sheetid不能为空");
				
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
	        return new ResObject(400, "操作异常");
		}		
	}
	
	
    @ApiOperation(value = "参数值删除", notes = "参数值删除", produces = "application/json")
    @RequestMapping(value = "/delete/{sheetids}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "sheetids", value = "用户sheetids逗号分隔，如123456789123456789", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResObject delete(@PathVariable String sheetids) {   	
    	try {
        	String[] strArr = sheetids.split(",");
        	if (MyUtils.notEmpty(strArr)) {
        		for (int i = 0; i < strArr.length; i++) {
            		History history = new History();
            		history.setSheetid(strArr[i]);
            		if (MyUtils.notEmpty(history)) {
            			historyService.delete(history);
            		}
    			}
        	}
        	return new ResObject(200, sheetids);
		} catch (Exception e) {
			return new ResObject(400, "操作异常");
		}
    }
    
    @ApiOperation(value = "参数值详情", notes = "参数值详情", produces = "application/json")
    @RequestMapping(value = "/view/{sheetid}", method = RequestMethod.DELETE)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header"),
    	@ApiImplicitParam(name = "sheetid", value = "如123456789123456789", required = true, dataType = "String", paramType = "path") 
    	})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResObject view(@PathVariable String sheetid) {
    	try {
        	History history = new History();
        	history.setSheetid(sheetid);
        	history = historyService.selectOne(history);
        	Long hostid = history.getHostid();
        	Hosts hosts =new Hosts();
            hosts.setHostid(hostid);
            hosts = hostsService.selectOne(hosts);
            List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("hostid", hostid);
            map.put("name", hosts.getName());
            

			List<HistoryItems> listItems = new ArrayList<HistoryItems>();
			listItems = historyItemsService.getBySheetid(sheetid);
			System.out.println(JSONObject.toJSONString(listItems));
			List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>();
			for (int j = 0; j < listItems.size(); j++) {
				Items items = new Items();
				items = itemsService.getById(listItems.get(j).getItemid());
				Map<String, Object> maps = new LinkedHashMap<String, Object>();
				maps.put("itemid",items.getItemid());
				maps.put("name", items.getName());
				maps.put("historyitemid", listItems.get(j).getHistoryitemid());
				maps.put("value", listItems.get(j).getValue());
				maps.put("clock", listItems.get(j).getClock());
				listMaps.add(maps);
			}
			
			map.put("items", listMaps);
			listMap.add(map); 
        	return new ResObject(200, listMap);
		} catch (Exception e) {
			return new ResObject(400, "操作异常");
		}
    }


	@ApiOperation(value = "Excel数据导入", notes = "Excel数据导入", produces = "application/json")
    @RequestMapping(value = "importExcel", method = RequestMethod.POST)
    @ApiImplicitParams({ 
    	@ApiImplicitParam(name = "Authorization", value = "授权信息：bearer token", dataType = "string", paramType = "header")
    	})   
    @ResponseBody
    @Transactional(rollbackOn = Exception.class)
	public Object importExcel(@RequestParam(value = "filename") MultipartFile file) {		
		try {
			if (file.isEmpty()) {
				return new ResObject(400, "文件为空!");
			}
			
			InputStream is = file.getInputStream();
			String fileName = file.getOriginalFilename();
			if (ExcelImportUtils.validateExcel(fileName) == false) {
				return new ResObject(400, "上传文件格式不正确");
            }
			
			List<Map<String, Object>> list = importService.getBankListByExcel(is, fileName);
			List<History> listHistory = new ArrayList<History>();
			for (Map<String, Object> s : list) {
				HistoryAddDTO historyAddDTO = new HistoryAddDTO();
				List<HistoryItems> listItems = new ArrayList<HistoryItems>();
				for (Entry<String, Object> entry : s.entrySet()) {
					HistoryItems historyItems = new HistoryItems();
					if (entry.getKey().equals("templateid")) {
						historyAddDTO.setTemplateid(Long.parseLong(entry.getValue().toString()));						
					} else if (entry.getKey().equals("hostid")) {
						historyAddDTO.setHostid(Long.parseLong(entry.getValue().toString()));
					} else {
						historyItems.setItemid(Long.parseLong(entry.getKey().toString()));
						historyItems.setValue(entry.getValue().toString());
						listItems.add(historyItems);
					}					
				}
				historyAddDTO.setListItems(listItems);
				// 保存数据
				listHistory.add(historyService.add(historyAddDTO));
			}			
			return new ResObject(200, listHistory);
			
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			return new ResObject(400, e.getMessage());
		}     	
    		
	}

    
}


