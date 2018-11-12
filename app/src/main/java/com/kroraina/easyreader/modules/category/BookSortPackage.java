package com.kroraina.easyreader.modules.category;

import com.kroraina.easyreader.model.bean.packages.BasePackage;

import java.util.List;



public class BookSortPackage extends BasePackage {

    private List<BookSortBean> male;
    private List<BookSortBean> female;

    public List<BookSortBean> getMale() {
        return male;
    }

    public void setMale(List<BookSortBean> male) {
        this.male = male;
    }

    public List<BookSortBean> getFemale() {
        return female;
    }

    public void setFemale(List<BookSortBean> female) {
        this.female = female;
    }
}
