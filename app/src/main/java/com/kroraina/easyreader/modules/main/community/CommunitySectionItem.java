package com.kroraina.easyreader.modules.main.community;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.discussion.help.HelpDiscussionActivity;
import com.kroraina.easyreader.modules.community.discussion.review.BookReviewActivity;
import com.kroraina.easyreader.modules.community.discussion.topic.TopicDiscussionActivity;

import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class CommunitySectionItem extends BaseItem {

    private String name;
    private int drawableId;
    private CommunityType communityType;

    public CommunitySectionItem(Context context,String name,int drawableId,CommunityType communityType){
        super(context);
        this.name = name;
        this.drawableId = drawableId;
        this.communityType = communityType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_section;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder,int position) {

        ImageView mIconView = viewHolder.findViewById(R.id.section_iv_icon);
        TextView mNameView = viewHolder.findViewById(R.id.section_tv_name);

        mNameView.setText(name);
        mIconView.setImageResource(drawableId);
    }

    @Override
    public void onClick() {
        if (communityType == CommunityType.HELP){
            startActivity(HelpDiscussionActivity.class);
        } else if (communityType == CommunityType.REVIEW){
            startActivity(BookReviewActivity.class);
        } else if (communityType == CommunityType.HOT){
            ToastUtils.showShort("还没完成");
        } else {
            TopicDiscussionActivity.startActivity(context,communityType);
        }

    }
}
