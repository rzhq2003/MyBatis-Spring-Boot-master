package tk.mybatis.springboot.request;




import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "TemplatesAddDTO", description = "模板对象")
public class TemplatesAddDTO {
    
    @ApiModelProperty(name = "host", example = "Templates POS Params", position = 1)
    private String host;

    @ApiModelProperty(name = "name", example = "收银机参数组", position = 2)
    private String name;

    @ApiModelProperty(name = "description", example = "无", position = 3)
    private String description;
    
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Integer status = 3;
    

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


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	

	
    
    


}
