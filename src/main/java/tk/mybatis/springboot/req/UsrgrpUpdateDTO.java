package tk.mybatis.springboot.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UsrgrpUpdateDTO", description = "用户组对象")
public class UsrgrpUpdateDTO {

	@ApiModelProperty(name = "usrgrpid",required = true,value = "usrgrpid必填段", example = "1")
	private Long usrgrpid;
	
	 private Integer enable;

	@ApiModelProperty(name = "name",example = "泉州区")
	private String name;
	
	@ApiModelProperty(name = "ids", value= "主机组id" ,example = "1,2")
	private String ids;
	
	@ApiModelProperty(name = "userids",value = "用户组id", example = "5,4")
	private String userids;
	
	
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Long getUsrgrpid() {
		return usrgrpid;
	}

	public void setUsrgrpid(Long usrgrpid) {
		this.usrgrpid = usrgrpid;
	}

	public String getName() {
		return name;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public void setName(String name) {
		this.name = name;
	}
}
