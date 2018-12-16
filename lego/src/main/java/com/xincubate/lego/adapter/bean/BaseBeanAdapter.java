package com.xincubate.lego.adapter.bean;

import android.content.Context;

import com.xincubate.lego.adapter.core.BaseAdapter;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.layoutcenter.LayoutCenter;

import java.util.List;

public class BaseBeanAdapter extends BaseAdapter {

    public BaseBeanAdapter(Context context){
        super(context);
    }

    public void refreshBeans(List<?> beans){
        clear();
        addBeans(beans);
        notifyDataSetChanged();
    }

    public void addBeans(List<?> beans){
        for (Object bean : beans){
            addBean(bean);
        }
    }

    public void addBean(int index,Object bean){
        addItem(index,LayoutCenter.buildItem(mContext,bean));
    }

    public void addBean(Object bean){
        addItem(LayoutCenter.buildItem(mContext, bean));
    }

    public void removeBean(Object bean){
        BaseItem tempItem = null;
        for (BaseItem item : getItems()){
            if (item instanceof BaseBeanItem){
                if (((BaseBeanItem) item).bean == bean){
                    tempItem = item;
                    break;
                }
            }
        }
        if (tempItem != null){
            removeItem(tempItem);
        }
    }

}
