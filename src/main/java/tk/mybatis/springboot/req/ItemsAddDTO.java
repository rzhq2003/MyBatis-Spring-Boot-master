package tk.mybatis.springboot.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ItemsAddDTO", description = "监控项对象")
public class ItemsAddDTO {
   
	@ApiModelProperty(name = "name", value = "不能为空", example = "参数1", position = 1, required = true)
    private String name;
	
	@ApiModelProperty(example = "无", position = 2)
	private String description;

	@ApiModelProperty(name = "hostid", value = "不能为空", example = "1", position = 3, required = true)
    private Long hostid;
	
	@ApiModelProperty(position = 4)
    private Integer enable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHostid() {
		return hostid;
	}

	public void setHostid(Long hostid) {
		this.hostid = hostid;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	
  
}
