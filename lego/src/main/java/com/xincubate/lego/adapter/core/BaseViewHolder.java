package com.xincubate.lego.adapter.core;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;


public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> id2View = new SparseArray<>();

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends View> T findViewById(@IdRes int viewId){
        View view = id2View.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
            if (view != null){
                id2View.put(viewId,view);
            }
        }
        return (T) view;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
        return this;
    }

}
