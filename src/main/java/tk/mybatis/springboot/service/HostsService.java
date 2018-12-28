package tk.mybatis.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.springboot.mapper.HostsMapper;
import tk.mybatis.springboot.model.Hosts;
import tk.mybatis.springboot.model.Pages;
import tk.mybatis.springboot.util.BaseService;

@Service
public class HostsService extends BaseService<Hosts> {

	@Autowired
	HostsMapper hostsMapper;
	
	public List<Hosts> getTemplates() {
		return hostsMapper.selectTemplates();
	}
	
	public List<Hosts> getHosts() {
		return hostsMapper.selectHosts();
	}

	// 获取条件，并分页
	public JSONObject getAllTemplates(Pages pages) {
		if (pages.getPage() != null && pages.getRows() != null) {
			PageHelper.startPage(pages.getPage(), pages.getRows(), "hostid");
		}
		List<Hosts> list = hostsMapper.selectTemplates();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageInfo", new PageInfo<Hosts>(list));
		jsonObject.put("page", pages.getPage());
		jsonObject.put("rows", pages.getRows());
		return jsonObject;
	}

	// 获取条件，并分页
	public JSONObject getAllHosts(Pages pages) {
		if (pages.getPage() != null && pages.getRows() != null) {
			PageHelper.startPage(pages.getPage(), pages.getRows(), "hostid");
		}
		List<Hosts> list = hostsMapper.selectHosts();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageInfo", new PageInfo<Hosts>(list));
		jsonObject.put("page", pages.getPage());
		jsonObject.put("rows", pages.getRows());
		return jsonObject;
	}

}
