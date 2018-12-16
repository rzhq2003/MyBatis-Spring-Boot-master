package tk.mybatis.springboot.model;

import javax.persistence.*;


@Table(name = "hosts_templates")
public class HostsTemplates extends Pages {
    @Id
    @Column(name = "hosttemplateid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hosttemplateid;
    

    private Long hostid;
    

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