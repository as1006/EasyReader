package com.kroraina.easyreader.modules.book.setting;

import android.support.annotation.NonNull;

import com.kroraina.easyreader.base.adapter.BaseAdapter;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.ui.widget.page.PageStyle;



public class PageStyleAdapter extends BaseAdapter {
    public int currentChecked;

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

        if (currentChecked == position){
            ((PageStyleItem)getItem(position)).setChecked(viewHolder);
        }
    }

    public void setPageStyleChecked(PageStyle pageStyle){
        currentChecked = pageStyle.ordinal();
    }
}
