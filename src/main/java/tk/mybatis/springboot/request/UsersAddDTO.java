package tk.mybatis.springboot.request;


import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.springboot.model.UsersGroups;

@ApiModel(value = "UsersAddDTO", description = "用户对象")
public class UsersAddDTO {

    @ApiModelProperty(name = "username",example = "80006877", position = 1)
    private String username;
    
    @ApiModelProperty(name = "name",example = "张三", position = 2)
    private String name;

    @ApiModelProperty(name = "password",example = "123456", position = 3)
    @JSONField(serialize = false) // 结果不被输出
    private String password;

    @ApiModelProperty(name = "enabled",example = "0", position = 4)
    private Integer enabled;
    
    @ApiModelProperty(name = "role",example = "3", position = 5)
    private Integer role;
    
    @ApiModelProperty(position = 6)
    private List<UsersGroups> usrgrps;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public List<UsersGroups> getUsrgrps() {
		return usrgrps;
	}

	public void setUsrgrps(List<UsersGroups> usrgrps) {
		this.usrgrps = usrgrps;
	}


    
    
}
