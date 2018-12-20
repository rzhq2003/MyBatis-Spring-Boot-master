package tk.mybatis.springboot.request;


import io.swagger.annotations.ApiModelProperty;



public class TemplatesUpdateDTO {
	
	@ApiModelProperty(name = "hostid",value = "更新必填段", example = "3", required = true, position = 1)
    private Long hostid;
    
    @ApiModelProperty(name = "host", example = "Templates POS Params", position = 2)
    private String host;

    @ApiModelProperty(name = "name", example = "收银机参数组", position = 3)
    private String name;

    @ApiModelProperty(name = "description", example = "无", position = 4)
    private String description;
    
    @ApiModelProperty(name = "groupids", value = "主机组id,不能为空", position = 5)
	private Long[] groupids;
	
    @ApiModelProperty(name = "hostids", value = "主机id,可选", position = 6)
	private Long[] hostids;


	public Long getHostid() {
		return hostid;
	}

	public void setHostid(Long hostid) {
		this.hostid = hostid;
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

	public Long[] getGroupids() {
		return groupids;
	}

	public void setGroupids(Long[] groupids) {
		this.groupids = groupids;
	}

	public Long[] getHostids() {
		return hostids;
	}

	public void setHostids(Long[] hostids) {
		this.hostids = hostids;
	}   
	
	
}
