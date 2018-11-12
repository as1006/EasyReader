package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.event.BookSubSortEvent;

@LayoutId(R.layout.item_horizon_tag)
public class HorizontalTagItem extends BaseItem {

    public String name;

    public HorizontalTagItem(Context context,String name) {
        super(context);
        this.name = name;
    }

    @Override
    public void onClick() {
        RxBus.getInstance().post(new BookSubSortEvent(name));
    }

    public void setIsSelected(@NonNull BaseViewHolder viewHolder){
        TextView mTvName = viewHolder.findViewById(R.id.horizon_tag_tv_name);
        mTvName.setTextColor(context.getResources().getColor(R.color.light_red));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        TextView mTvName = viewHolder.findViewById(R.id.horizon_tag_tv_name);
        mTvName.setText(name);
        mTvName.setTextColor(context.getResources().getColor(R.color.nb_text_common_h2));
    }
}
