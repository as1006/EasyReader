package com.kroraina.easyreader.model.bean.packages;

import com.kroraina.easyreader.model.bean.BookReviewBean;

import java.util.List;



public class BookReviewPackage extends BasePackage {

    private List<BookReviewBean> reviews;

    public List<BookReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReviewBean> reviews) {
        this.reviews = reviews;
    }
}
