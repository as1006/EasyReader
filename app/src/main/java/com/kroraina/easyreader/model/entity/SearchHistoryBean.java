package com.kroraina.easyreader.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SearchHistoryBean {

    @Id
    private String searchKey;
    private long timestamp;

    @Generated(hash = 2136519499)
    public SearchHistoryBean(String searchKey, long timestamp) {
        this.searchKey = searchKey;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


}
