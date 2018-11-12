package com.kroraina.easyreader.modules.community.discuss;

import com.kroraina.easyreader.model.bean.packages.BasePackage;

import java.util.List;

public class BookDiscussPackage extends BasePackage {

    private List<BookDiscussBean> posts;

    private int total;
    private int today;

    public List<BookDiscussBean> getPosts() {
        return posts;
    }

    public void setPosts(List<BookDiscussBean> posts) {
        this.posts = posts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }
}
