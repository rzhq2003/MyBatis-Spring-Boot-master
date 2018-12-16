package tk.mybatis.springboot.model;

import javax.persistence.*;



public class Hosts extends Pages {
    @Id
    @Column(name = "hostid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostid;
    

    private String host;


    private Integer status;


    private String name;
    

    private Long templateid;

    private String description;

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