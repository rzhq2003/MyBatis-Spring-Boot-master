package tk.mybatis.springboot.model;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Table(name = "users_groups")
public class UsersGroups extends Pages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;
    
    @ApiModelProperty(hidden = true)
    private Long userid;
    @ApiModelProperty(name = "usrgrpid",value = "用户组id", example = "1")
    private Long usrgrpid;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return userid
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * @return usrgrpid
     */
    public Long getUsrgrpid() {
        return usrgrpid;
    }

    /**
     * @param usrgrpid
     */
    public void setUsrgrpid(Long usrgrpid) {
        this.usrgrpid = usrgrpid;
    }
}