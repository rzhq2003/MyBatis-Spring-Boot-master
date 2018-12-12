package tk.mybatis.springboot.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;


public class Hosts extends Pages {
    @Id
    @Column(name = "hostid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long hostid;
    
    @ApiModelProperty(name = "host", example = "Templates POS Params")
    private String host;

    @JSONField(serialize = false) // 结果不被输出
    @ApiModelProperty(hidden = true)
    private Integer status;

    @ApiModelProperty(name = "name", example = "收银机参数组")
    private String name;
    
    @JSONField(serialize = false) // 结果不被输出
    @ApiModelProperty(hidden = true)
    private Long templateid;

    @ApiModelProperty(name = "description", example = "无")
    private String description;

    @ApiModelProperty(name = "enable", example = "0")
    private Integer enable;

    /**
     * @return hostid
     */
    public Long getHostid() {
        return hostid;
    }

    /**
     * @param hostid
     */
    public void setHostid(Long hostid) {
        this.hostid = hostid;
    }

    /**
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     */
    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return templateid
     */
    public Long getTemplateid() {
        return templateid;
    }

    /**
     * @param templateid
     */
    public void setTemplateid(Long templateid) {
        this.templateid = templateid;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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