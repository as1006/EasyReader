package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.adapter.group.BaseGroupItem;
import com.xincubate.lego.annotation.LegoItem;

import java.util.List;


@LegoItem
public class TagGroupItem extends BaseGroupItem {

    public String bean;

    public TagGroupItem(Context context,String bean,List<TagChildItem> childItems) {
        super(context,childItems);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tag_group;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        TextView mTvGroupName = viewHolder.findViewById(R.id.tag_group_name);
        mTvGroupName.setText(bean);
    }
}
