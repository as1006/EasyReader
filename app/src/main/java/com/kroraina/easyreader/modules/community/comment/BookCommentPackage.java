package com.kroraina.easyreader.modules.community.comment;

import com.kroraina.easyreader.model.bean.packages.BasePackage;

import java.util.List;

public class BookCommentPackage extends BasePackage {

    private List<BookCommentBean> reviews;

    private int total;
    private int today;

    public List<BookCommentBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookCommentBean> posts) {
        this.reviews = posts;
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
