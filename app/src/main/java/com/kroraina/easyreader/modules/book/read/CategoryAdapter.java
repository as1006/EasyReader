package com.kroraina.easyreader.modules.book.read;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xincubate.lego.adapter.bean.BaseBeanAdapter;
import com.xincubate.lego.adapter.core.BaseAdapter;
import com.xincubate.lego.adapter.core.BaseViewHolder;


public class CategoryAdapter extends BaseBeanAdapter {
    private int currentSelected = 0;

    CategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        if (position == currentSelected){
            ((CategoryItem)getItem(position)).setSelectedChapter(viewHolder);
        }
    }

    public void setChapter(int pos){
        currentSelected = pos;
        notifyDataSetChanged();
    }
}
