package tk.mybatis.springboot.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ItemsUpdateDTO", description = "监控项对象")
public class ItemsUpdateDTO {
   
	@ApiModelProperty(name = "itemid", value = "不能为空", example = "3", position = 1, required = true)
    private Long itemid;
	
	@ApiModelProperty(name = "name", value = "参数名称", example = "参数1", position = 2)
    private String name;
	
	@ApiModelProperty(example = "无", position = 3)
	private String description;

	@ApiModelProperty(position = 4)
    private Integer enable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
    
	
	
  
}
