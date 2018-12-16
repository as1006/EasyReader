package com.xincubate.lego.adapter.core;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by loganpluo on 2018/8/4.
 */

public class AdapterDiffCallback<Item> extends DiffUtil.Callback {


    private List<Item> oldList;
    private List<Item> newList;

    public AdapterDiffCallback(List<Item> oldList, List<Item> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getClass().equals(newList.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
