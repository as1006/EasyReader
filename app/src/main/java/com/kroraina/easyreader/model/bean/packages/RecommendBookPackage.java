package com.kroraina.easyreader.model.bean.packages;

import com.kroraina.easyreader.model.entity.CollBookBean;

import java.util.List;

/**
 * Created on 17-5-8.
 */

public class RecommendBookPackage extends BasePackage {

    private List<CollBookBean> books;

    public List<CollBookBean> getBooks() {
        return books;
    }

    public void setBooks(List<CollBookBean> books) {
        this.books = books;
    }
}
