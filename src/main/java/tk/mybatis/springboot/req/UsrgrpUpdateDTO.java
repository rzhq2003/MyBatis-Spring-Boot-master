package tk.mybatis.springboot.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "UsrgrpUpdateDTO", description = "用户组对象")
public class UsrgrpUpdateDTO {

	@ApiModelProperty(name = "usrgrpid",required = true,value = "usrgrpid必填段", example = "1", position = 1)
	private Long usrgrpid;

	@ApiModelProperty(name = "name",example = "泉州区", position = 2)
	private String name;
	
	@ApiModelProperty(position = 3)
	private Integer enable;
	
	@ApiModelProperty(name = "userids", value = "用户组id,如:1,2,3", position = 4)
	private Long[] userids;
	
	@ApiModelProperty(name = "groupids", value = "主机组id,如:1,2,3", position = 5)
	private Long[] groupids;

	public Long getUsrgrpid() {
		return usrgrpid;
	}

	public void setUsrgrpid(Long usrgrpid) {
		this.usrgrpid = usrgrpid;
	}

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

	public Long[] getGroupids() {
		return groupids;
	}

	public void setGroupids(Long[] groupids) {
		this.groupids = groupids;
	}




}
