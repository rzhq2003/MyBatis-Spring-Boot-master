package tk.mybatis.springboot.request;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UsersUpdateDTO", description = "用户对象")
public class UsersUpdateDTO {
	@ApiModelProperty(name = "userid",value = "必填", example = "27", required = true, position = 1)
    private Long userid;
	
    @ApiModelProperty(name = "name",example = "张三", position = 2)
    private String name;

    @ApiModelProperty(name = "password",example = "123456", position = 3)
    @JSONField(serialize = false) // 结果不被输出
    private String password;

    @ApiModelProperty(name = "enabled",example = "0", position = 4)
    private Integer enabled;
    
    @ApiModelProperty(name = "role",example = "3", position = 5)
    private Integer role;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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
    
}
