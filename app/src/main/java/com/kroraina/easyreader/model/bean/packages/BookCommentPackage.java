package com.kroraina.easyreader.model.bean.packages;

import com.kroraina.easyreader.model.bean.BookCommentBean;

import java.util.List;


public class BookCommentPackage extends BasePackage {

    private List<BookCommentBean> posts;

    public List<BookCommentBean> getPosts() {
        return posts;
    }

    public void setPosts(List<BookCommentBean> posts) {
        this.posts = posts;
    }
}
