package com.easyapp.lego.adapter.bean;

import com.easyapp.lego.adapter.core.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseBeanAdapter extends BaseAdapter {

    private Map<Class<? extends BaseBean>,BaseBeanItem.ItemBuilder> mItemBuilders = new HashMap<>();

    public BaseBeanAdapter registerItemBuilder(Class<? extends BaseBean> beanClass, BaseBeanItem.ItemBuilder itemBuilder){
        mItemBuilders.put(beanClass,itemBuilder);
        return this;
    }

    public void refresh(List<? extends BaseBean> beans){
        clear();
        addBeans(beans);
        notifyDataSetChanged();
    }

    public void addBeans(List<? extends BaseBean> beans){
        for (BaseBean bean : beans){
            addBean(bean);
        }
    }

    public void addBean(BaseBean bean){
        addItem(mItemBuilders.get(bean.getClass()).build(bean));
    }


}
