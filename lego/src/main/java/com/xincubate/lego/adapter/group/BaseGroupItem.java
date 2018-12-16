package com.xincubate.lego.adapter.group;

import android.content.Context;

import com.xincubate.lego.adapter.core.BaseItem;

import java.util.List;

public abstract class BaseGroupItem extends BaseItem {

    private List<? extends BaseItem> childItems;

    public BaseGroupItem(Context context,List<? extends BaseItem> childItems) {
        super(context);
        this.childItems = childItems;
    }

    public int getChildCount(){
        return childItems == null ? 0 : childItems.size();
    }

    public BaseItem getChild(int position){
        if (childItems != null && position >= 0 && position < getChildCount()){
            return childItems.get(position);
        }else {
            return null;
        }
    }

    public List<? extends BaseItem> getChildItems(){
        return childItems;
    }
}
