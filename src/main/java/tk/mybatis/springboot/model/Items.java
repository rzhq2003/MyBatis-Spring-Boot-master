package tk.mybatis.springboot.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

public class Items extends Pages {
    @Id
    @Column(name = "itemid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemid;

    private Long hostid;

    private String name;

    private String description;

    /**
     * 对应本表模板itemid
     */
    @JSONField(serialize = false)
    private Long templateid;

    private Integer enable;

    /**
     * @return itemid
     */
    public Long getItemid() {
        return itemid;
    }

    /**
     * @param itemid
     */
    public void setItemid(Long itemid) {
        this.itemid = itemid;
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
     * 获取对应本表模板itemid
     *
     * @return templateid - 对应本表模板itemid
     */
    public Long getTemplateid() {
        return templateid;
    }

    /**
     * 设置对应本表模板itemid
     *
     * @param templateid 对应本表模板itemid
     */
    public void setTemplateid(Long templateid) {
        this.templateid = templateid;
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