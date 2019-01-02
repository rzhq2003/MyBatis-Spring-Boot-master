package tk.mybatis.springboot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by echisan on 2018/6/23
 */
@ApiModel(value = "LoginUserExample", description = "用户登录")
public class LoginUser {

	@ApiModelProperty(value = "用户帐号",name = "username", example = "admin")
    private String username;
	
	@ApiModelProperty(value = "用户密码",name = "password", example = "123456")
    private String password;
	
	@ApiModelProperty(value = "记住用户,0 忘记或1 记住",name = "rememberMe", example = "0")
    private Integer rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }
}
