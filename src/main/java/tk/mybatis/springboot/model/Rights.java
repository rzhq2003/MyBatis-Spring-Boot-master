package tk.mybatis.springboot.model;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;


public class Rights extends Pages {
    @Id
    @Column(name = "rightid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long rightid;

    /**
     * 用户组
     */
    @ApiModelProperty(hidden = true)
    private Long groupid;

    /**
     * 主机组
     */
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