package tk.mybatis.springboot.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

// @ApiModel(value = "UsersExample", description = "用户对象")
public class Users extends Pages {

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "userid", hidden = true)
    private Long userid;
    
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
    
    @ApiModelProperty(name = "name",example = "张三")
    private String name;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username",example = "80006877")
    private String username;

    /**
     * 密码
     */

    @ApiModelProperty(name = "password",example = "123456")
    @JSONField(serialize = false) // 结果不被输出
    private String password;


    /**
     * 是否可用
     */
    @ApiModelProperty(name = "enabled",example = "0")
    private Integer enabled;
    
    @ApiModelProperty(name = "role",example = "3")
    private Integer role;


    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */

    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }


    /**
     * 获取是否可用
     *
     * @return enabled - 是否可用
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用
     *
     * @param enabled 是否可用
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * @return role
     */
    public Integer getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(Integer role) {
        this.role = role;
    }
    
}