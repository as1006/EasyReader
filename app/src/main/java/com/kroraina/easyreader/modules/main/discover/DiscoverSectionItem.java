package com.kroraina.easyreader.modules.main.discover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.discussion.help.HelpDiscussionActivity;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class DiscoverSectionItem extends BaseItem {

    private DiscoverType findType;

    public DiscoverSectionItem(Context context, DiscoverType findType){
        super(context);
        this.findType = findType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_section;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder,int position) {
        ImageView mIconView = viewHolder.findViewById(R.id.section_iv_icon);
        TextView mNameView = viewHolder.findViewById(R.id.section_tv_name);

        mNameView.setText(findType.getTypeName());
        mIconView.setImageResource(findType.getIconId());
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
