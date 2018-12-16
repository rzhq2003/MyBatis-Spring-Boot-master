package tk.mybatis.springboot.model;

import javax.persistence.*;



@Table(name = "hosts_groups")
public class HostsGroups extends Pages {
    @Id
    @Column(name = "hostgroupid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostgroupid;
    
    private Long groupid;
    

    private Long hostid;

    /**
     * @return hostgroupid
     */
    public Long getHostgroupid() {
        return hostgroupid;
    }

    /**
     * @param hostgroupid
     */
    public void setHostgroupid(Long hostgroupid) {
        this.hostgroupid = hostgroupid;
    }

    /**
     * @return groupid
     */
    public Long getGroupid() {
        return groupid;
    }

    /**
     * @param groupid
     */
    public void setGroupid(Long groupid) {
        this.groupid = groupid;
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
}