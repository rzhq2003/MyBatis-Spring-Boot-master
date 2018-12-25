package tk.mybatis.springboot.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

@Table(name = "history_items")
public class HistoryItems extends Pages {
    @Id
    @Column(name = "historyitemid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JSONField(serialize = false)
    private Long historyitemid;

    private String sheetid;

    private Long itemid;

    private String value;

    private Integer clock;

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

    /**
     * @return sheetid
     */
    public String getsheetid() {
        return sheetid;
    }

    /**
     * @param sheetid
     */
    public void setsheetid(String sheetid) {
        this.sheetid = sheetid == null ? null : sheetid.trim();
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
    public Integer getClock() {
        return clock;
    }

    /**
     * @param clock
     */
    public void setClock(Integer clock) {
        this.clock = clock;
    }
}