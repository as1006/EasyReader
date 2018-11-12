package com.kroraina.easyreader.modules.main.community;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.discussion.review.BookReviewActivity;
import com.kroraina.easyreader.modules.community.discussion.help.HelpDiscussionActivity;
import com.kroraina.easyreader.modules.community.discussion.topic.TopicDiscussionActivity;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.utils.ToastUtils;

@LayoutId(R.layout.item_section)
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
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {

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
            ToastUtils.show("还没完成");
        } else {
            TopicDiscussionActivity.startActivity(context,communityType);
        }

    }
}
