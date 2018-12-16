package com.kroraina.easyreader.modules.main.shelf;

import android.content.Context;

import com.kroraina.easyreader.model.entity.CollBookBean;
import com.xincubate.lego.adapter.core.BaseAdapter;
import com.xincubate.lego.adapter.core.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class BookShelfAdapter extends BaseAdapter {

    public BookShelfAdapter(Context context) {
        super(context);
    }

    public List<CollBookBean> getBooks(){

        List<CollBookBean> results = new ArrayList<>();
        for (BaseItem baseItem : getItems()){
            if (baseItem instanceof BookShelfItem){
                results.add(((BookShelfItem) baseItem).bean);
            }
        }
        return results;
    }

    public void removeItem(CollBookBean bookBean){
        BaseItem result = null;
        for (BaseItem baseItem : getItems()){
            if (baseItem instanceof BookShelfItem){
                if (((BookShelfItem) baseItem).bean == bookBean){
                    result = baseItem;
                    break;
                }
            }
        }
        removeItem(result);
    }

}
