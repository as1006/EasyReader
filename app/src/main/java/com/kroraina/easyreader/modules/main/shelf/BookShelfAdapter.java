package com.kroraina.easyreader.modules.main.shelf;

import com.easyapp.lego.adapter.core.BaseAdapter;
import com.easyapp.lego.adapter.core.BaseItem;
import com.kroraina.easyreader.model.entity.CollBookBean;

import java.util.ArrayList;
import java.util.List;

public class BookShelfAdapter extends BaseAdapter {

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
