package tk.mybatis.springboot.model;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;


public class Usrgrp extends Pages {
    @Id
    @Column(name = "usrgrpid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "usrgrpid", hidden = true)
    private Long usrgrpid;
    

    private String name;

    private Integer enable;

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
     * @return enable
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * @param enable
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}