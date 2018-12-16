package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.event.BookSubSortEvent;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class HorizontalTagItem extends BaseItem {

    public String name;

    public HorizontalTagItem(Context context,String name) {
        super(context);
        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_horizon_tag;
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
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        TextView mTvName = viewHolder.findViewById(R.id.horizon_tag_tv_name);
        mTvName.setText(name);
        mTvName.setTextColor(context.getResources().getColor(R.color.nb_text_common_h2));
    }
}
