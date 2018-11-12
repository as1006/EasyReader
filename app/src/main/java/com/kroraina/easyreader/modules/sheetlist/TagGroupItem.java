package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.adapter.group.BaseGroupItem;
import com.kroraina.easyreader.base.annotations.LayoutId;

import java.util.List;


@LayoutId(R.layout.item_tag_group)
public class TagGroupItem extends BaseGroupItem {

    public String bean;

    public TagGroupItem(Context context,String bean,List<TagChildItem> childItems) {
        super(context,childItems);
        this.bean = bean;
    }

    @Override
    public int getItemViewType() {
        return 4000;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        TextView mTvGroupName = viewHolder.findViewById(R.id.tag_group_name);
        mTvGroupName.setText(bean);
    }
}
