package tk.mybatis.springboot.model;

import javax.persistence.*;


public class Items extends Pages {
    @Id
    @Column(name = "itemid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemid;

    private Long templateid;

    private String name;

    private String description;

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



    public Long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
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