package tk.mybatis.springboot.model;

import javax.persistence.*;


import io.swagger.annotations.ApiModelProperty;

@Table(name = "history_items")
public class HistoryItems extends Pages {
    @Id
    @Column(name = "historyitemid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long historyitemid;

    @ApiModelProperty(hidden = true)
    private String sheetid;

    @ApiModelProperty(name = "itemid", example = "1006", required = true)
    private Long itemid;

    @ApiModelProperty(name = "value", example = "123")
    private String value;

    @ApiModelProperty(hidden = true)
    private Long clock;

    /**
     * @return historyitemid
     */
    public Long getHistoryitemid() {
        return historyitemid;
    }

    /**
     * @param historyitemid
     */
    public void setHistoryitemid(Long historyitemid) {
        this.historyitemid = historyitemid;
    }


    public String getSheetid() {
		return sheetid;
	}

	public void setSheetid(String sheetid) {
		this.sheetid = sheetid;
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

    /**
     * @return clock
     */
    public Long getClock() {
        return clock;
    }

    /**
     * @param clock
     */
    public void setClock(Long clock) {
        this.clock = clock;
    }
}