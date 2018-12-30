package com.kroraina.easyreader.modules.sheetlist;

import android.support.annotation.StringRes;

import com.blankj.utilcode.util.Utils;
import com.kroraina.easyreader.App;
import com.kroraina.easyreader.R;


public enum  BookListType{
    HOT(R.string.nb_fragment_book_list_hot,"last-seven-days"),
    NEWEST(R.string.nb_fragment_book_list_newest,"created"),
    COLLECT(R.string.nb_fragment_book_list_collect,"collectorCount")
    ;
    private String typeName;
    private String netName;

    BookListType(@StringRes int typeName, String netName){
        this.typeName = Utils.getApp().getString(typeName);
        this.netName = netName;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getNetName(){
        return netName;
    }
}
