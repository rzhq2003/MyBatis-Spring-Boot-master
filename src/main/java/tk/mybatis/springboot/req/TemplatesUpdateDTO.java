package tk.mybatis.springboot.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.springboot.model.HostsGroups;
import tk.mybatis.springboot.model.HostsTemplates;


public class TemplatesUpdateDTO {
	
	@ApiModelProperty(name = "templateid",value = "更新必填段", example = "3", required = true, position = 1)
    private Long templateid;
    
    @ApiModelProperty(name = "host", example = "Templates POS Params", position = 2)
    private String host;

    @ApiModelProperty(name = "name", example = "收银机参数组", position = 3)
    private String name;

    @ApiModelProperty(name = "description", example = "无", position = 4)
    private String description;

    @ApiModelProperty(name = "enable", example = "0", position = 5)
    private Integer enable;
    
	private List<HostsGroups> Groups;
	
	private List<HostsTemplates> hosts;

	public Long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
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
