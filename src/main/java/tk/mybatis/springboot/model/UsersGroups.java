package tk.mybatis.springboot.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;


@Table(name = "users_groups")
public class UsersGroups extends Pages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    private Long id;
    
    @ApiModelProperty(hidden = true)
    private Long userid;
    
    @ApiModelProperty(name = "usrgrpid",example = "1", value = "必填段", required = true)
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