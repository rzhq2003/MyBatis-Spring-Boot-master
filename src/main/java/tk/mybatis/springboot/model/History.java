package tk.mybatis.springboot.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;

public class History extends Pages {
    @Id
    @Column(name = "historyid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JSONField(serialize = false)
    private Long historyid;

    private Long hostid;

    private String sheetid;

    /**
     * @return historyid
     */
    public Long getHistoryid() {
        return historyid;
    }

    /**
     * @param historyid
     */
    public void setHistoryid(Long historyid) {
        this.historyid = historyid;
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
     * @return sheetid
     */
    public String getSheetid() {
        return sheetid;
    }

    /**
     * @param sheetid
     */
    public void setSheetid(String sheetid) {
        this.sheetid = sheetid == null ? null : sheetid.trim();
    }
}