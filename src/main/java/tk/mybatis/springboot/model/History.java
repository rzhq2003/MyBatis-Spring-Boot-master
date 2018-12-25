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

    private String historysn;

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
     * @return historysn
     */
    public String getHistorysn() {
        return historysn;
    }

    /**
     * @param historysn
     */
    public void setHistorysn(String historysn) {
        this.historysn = historysn == null ? null : historysn.trim();
    }
}