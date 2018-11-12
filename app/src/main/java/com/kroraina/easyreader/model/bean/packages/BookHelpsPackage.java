package com.kroraina.easyreader.model.bean.packages;

import com.kroraina.easyreader.model.bean.BookHelpsBean;

import java.util.List;



public class BookHelpsPackage extends BasePackage {

    private List<BookHelpsBean> helps;

    public List<BookHelpsBean> getHelps() {
        return helps;
    }

    public void setHelps(List<BookHelpsBean> helps) {
        this.helps = helps;
    }

}
