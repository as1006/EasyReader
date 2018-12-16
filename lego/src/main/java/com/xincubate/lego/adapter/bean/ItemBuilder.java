package com.xincubate.lego.adapter.bean;

/**
 * Created by loganpluo on 2018/11/27.
 */

import android.content.Context;

import com.xincubate.lego.adapter.core.BaseItem;

/**
 * Bean到Item的转化器，需要在app或者module初始化的时候，往
 */
public interface ItemBuilder<T>{
    BaseItem build(Context context, T bean);
}