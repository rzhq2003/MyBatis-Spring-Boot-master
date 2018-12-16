package tk.mybatis.springboot.req;

import java.util.List;



import io.swagger.annotations.ApiModel;
import tk.mybatis.springboot.model.Hosts;
import tk.mybatis.springboot.model.HostsGroups;
import tk.mybatis.springboot.model.HostsTemplates;

@ApiModel(value = "TemplatesAddDTO", description = "模板对象")
public class TemplatesAddDTO {

	private Hosts templates;
	
	private List<HostsGroups> Groups;
	
	private List<HostsTemplates> hosts;
	
	
	public Hosts getTemplates() {
		return templates;
	}
	public void setTemplates(Hosts templates) {
		this.templates = templates;
	}
	public List<HostsGroups> getGroups() {
		return Groups;
	}
	public void setGroups(List<HostsGroups> groups) {
		Groups = groups;
	}
	public List<HostsTemplates> getHosts() {
		return hosts;
	}
	public void setHosts(List<HostsTemplates> hosts) {
		this.hosts = hosts;
	}
	


}
