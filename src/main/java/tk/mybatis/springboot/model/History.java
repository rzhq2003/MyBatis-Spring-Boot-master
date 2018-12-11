package tk.mybatis.springboot.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class History extends Pages {

    @Id
    @Column(name = "historyid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long historyid;
	
    private Long itemid;

    private Integer clock;

    private String value;
    

    public Long getHistoryid() {
		return historyid;
	}

	public void setHistoryid(Long historyid) {
		this.historyid = historyid;
	}

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
     * @return clock
     */
    public Integer getClock() {
        return clock;
    }

    /**
     * @param clock
     */
    public void setClock(Integer clock) {
        this.clock = clock;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}