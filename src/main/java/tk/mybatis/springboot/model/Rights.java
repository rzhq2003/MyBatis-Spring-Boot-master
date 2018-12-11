package tk.mybatis.springboot.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class Rights extends Pages {
    @Id
    @Column(name = "rightid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long rightid;

    /**
     * 用户组
     */
    @JsonIgnore
    private Long groupid;

    /**
     * 主机组
     */
    @ApiModelProperty(name = "id",value = "主机组id", example = "1")
    private Long id;

    /**
     * @return rightid
     */
    
    public Long getRightid() {
        return rightid;
    }

    /**
     * @param rightid
     */
    public void setRightid(Long rightid) {
        this.rightid = rightid;
    }

    /**
     * 获取用户组
     *
     * @return groupid - 用户组
     */
    
    public Long getGroupid() {
        return groupid;
    }

    /**
     * 设置用户组
     *
     * @param groupid 用户组
     */
    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    /**
     * 获取主机组
     *
     * @return id - 主机组
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主机组
     *
     * @param id 主机组
     */
    public void setId(Long id) {
        this.id = id;
    }
}