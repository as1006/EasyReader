package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.event.TagEvent;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class TagChildItem extends BaseItem {

    private String bean;
    public TagChildItem(Context context,String bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tag_child;
    }

    @Override
    public void onClick() {
        RxBus.getInstance().post(new TagEvent(bean));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        TextView mTvName = viewHolder.findViewById(R.id.tag_child_btn_name);
        mTvName.setText(bean);
        mTvName.setTextColor(ContextCompat.getColor(context,R.color.nb_text_default));
    }
}
