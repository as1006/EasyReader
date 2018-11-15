package com.easyapp.lego.adapter.bean;

import android.content.Context;
import android.support.annotation.NonNull;

import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;

public abstract class BaseBeanItem<B extends BaseBean> extends BaseItem {

    protected B bean;

    public BaseBeanItem(Context context,B bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        onBindViewWithBean(viewHolder,bean);
    }

    public abstract void onBindViewWithBean(@NonNull BaseViewHolder viewHolder,B bean);

    public interface ItemBuilder<T extends BaseBeanItem,S extends BaseBean>{
        T build(S bean);
    }

}
