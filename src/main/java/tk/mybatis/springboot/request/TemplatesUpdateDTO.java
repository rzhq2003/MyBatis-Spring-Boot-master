package tk.mybatis.springboot.request;


import io.swagger.annotations.ApiModelProperty;



public class TemplatesUpdateDTO {
	
	@ApiModelProperty(name = "templateid",value = "模板id,更新必填段", example = "3", required = true, position = 1)
    private Long templateid;
    
    @ApiModelProperty(name = "name", value = "模板名称", example = "收银机参数组", position = 2)
    private String name;

    @ApiModelProperty(name = "description", example = "无", position = 3)
    private String description;
    

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

	public Long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}

 
	
	
}
