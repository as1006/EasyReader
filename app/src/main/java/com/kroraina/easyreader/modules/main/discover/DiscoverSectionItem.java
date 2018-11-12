package com.kroraina.easyreader.modules.main.discover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.discussion.help.HelpDiscussionActivity;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.annotations.LayoutId;

@LayoutId(R.layout.item_section)
public class DiscoverSectionItem extends BaseItem {


    private String name;
    private int drawableId;
    private DiscoverType findType;

    public DiscoverSectionItem(Context context, String name, int drawableId, DiscoverType findType){
        super(context);
        this.name = name;
        this.drawableId = drawableId;
        this.findType = findType;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView mIconView = viewHolder.findViewById(R.id.section_iv_icon);
        TextView mNameView = viewHolder.findViewById(R.id.section_tv_name);

        mNameView.setText(name);
        mIconView.setImageResource(drawableId);
    }

    @Override
    public void onClick() {
        switch (findType){
            case HELP:
                startActivity(HelpDiscussionActivity.class);
                break;
        }
    }
}
