package com.kroraina.easyreader.model.bean.packages;

import com.kroraina.easyreader.model.bean.HotCommentBean;

import java.util.List;



public class HotCommentPackage extends BasePackage {

    private List<HotCommentBean> reviews;

    public List<HotCommentBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<HotCommentBean> reviews) {
        this.reviews = reviews;
    }
}
