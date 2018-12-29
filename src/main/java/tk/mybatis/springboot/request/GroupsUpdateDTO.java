package tk.mybatis.springboot.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(value = "GroupsUpdateDTO", description = "门店组对象")
public class GroupsUpdateDTO {
	
	@ApiModelProperty(name = "groupid",example = "1000", position = 1)
    private Long groupid;
	
	@ApiModelProperty(name = "name",example = "福州区", position = 2)
    private String name;
	
	@ApiModelProperty(position = 3)
    private Integer enable;
	
	@ApiModelProperty(position = 4, value = "主机组id,如:1,2,3")
	private Long[] hostids;



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

	public Long[] getHostids() {
		return hostids;
	}

	public void setHostids(Long[] hostids) {
		this.hostids = hostids;
	}

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}




}
