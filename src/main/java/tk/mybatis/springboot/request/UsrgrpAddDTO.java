package tk.mybatis.springboot.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(value = "UsrgrpAddDTO", description = "用户组对象")
public class UsrgrpAddDTO {
	
	@ApiModelProperty(name = "name",example = "福泫区", position = 1)
    private String name;
	
	@ApiModelProperty(position = 2)
    private Integer enable;
	
	@ApiModelProperty(position = 3, value = "用户组id,如:1,2,3")
	private Long[] userids;
	
	@ApiModelProperty(position = 4, value = "主机组id,如:1,2,3")
	private Long[] ids;



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

	public Long[] getUserids() {
		return userids;
	}

	public void setUserids(Long[] userids) {
		this.userids = userids;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}


}
