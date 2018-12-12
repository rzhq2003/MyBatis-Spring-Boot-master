package tk.mybatis.springboot.model;

import javax.persistence.*;



import io.swagger.annotations.ApiModelProperty;

@Table(name = "hosts_templates")
public class HostsTemplates extends Pages {
    @Id
    @Column(name = "hosttemplateid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long hosttemplateid;
    
    @ApiModelProperty(name = "hostid",value = "主机id", example = "1")
    private Long hostid;
    
    @ApiModelProperty(hidden = true)
    private Long templateid;

    /**
     * @return hosttemplateid
     */
    public Long getHosttemplateid() {
        return hosttemplateid;
    }

    /**
     * @param hosttemplateid
     */
    public void setHosttemplateid(Long hosttemplateid) {
        this.hosttemplateid = hosttemplateid;
    }

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
}