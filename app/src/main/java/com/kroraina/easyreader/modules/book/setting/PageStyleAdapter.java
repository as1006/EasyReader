package com.kroraina.easyreader.modules.book.setting;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kroraina.easyreader.ui.widget.page.PageStyle;
import com.xincubate.lego.adapter.core.BaseAdapter;
import com.xincubate.lego.adapter.core.BaseViewHolder;


public class PageStyleAdapter extends BaseAdapter {
    public int currentChecked;

    public PageStyleAdapter(Context context) {
        super(context);
    }

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
