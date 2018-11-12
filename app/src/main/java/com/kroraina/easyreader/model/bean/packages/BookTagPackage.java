package com.kroraina.easyreader.model.bean.packages;

import com.kroraina.easyreader.model.bean.BookTagBean;

import java.util.List;



public class BookTagPackage extends BasePackage {

    private List<BookTagBean> data;

    public List<BookTagBean> getData() {
        return data;
    }

    public void setData(List<BookTagBean> data) {
        this.data = data;
    }


}
