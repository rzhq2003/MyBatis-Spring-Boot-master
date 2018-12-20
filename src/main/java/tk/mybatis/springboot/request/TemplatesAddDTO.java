package tk.mybatis.springboot.request;




import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "TemplatesAddDTO", description = "模板对象")
public class TemplatesAddDTO {

    @ApiModelProperty(name = "host", value = "不能为空", example = "Templates POS Params", position = 1, required = true)
    private String host;

    @ApiModelProperty(name = "name", example = "收银机参数组", position = 2)
    private String name;

    @ApiModelProperty(name = "description", example = "无", position = 3)
    private String description;
    
    @ApiModelProperty(name = "groupids", value = "主机组id,不能为空", position = 4)
	private Long[] groupids;
	
    @ApiModelProperty(name = "hostids", value = "主机id,可选", position = 5)
	private Long[] hostids;

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
