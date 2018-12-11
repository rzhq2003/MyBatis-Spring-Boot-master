package tk.mybatis.springboot.model;

import javax.persistence.*;

public class Groups extends Pages {
	
    @Id
    @Column(name = "groupid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupid;

    private String name;

    private Integer enable;

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