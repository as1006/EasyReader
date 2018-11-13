package com.kroraina.easyreader.modules.main.community;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.discussion.help.HelpDiscussionActivity;
import com.kroraina.easyreader.modules.community.discussion.review.BookReviewActivity;
import com.kroraina.easyreader.modules.community.discussion.topic.TopicDiscussionActivity;

@LayoutId(R.layout.item_community_head_entry)
public class CommunityHeaderEntryItem extends BaseItem {

    public int drawableResId;
    public String name;
    public CommunityType communityType;

    public CommunityHeaderEntryItem(Context context,int drawableResId,String name,CommunityType communityType) {
        super(context);
        this.drawableResId = drawableResId;
        this.name = name;
        this.communityType = communityType;
    }

    @Override
    public int getItemViewType() {
        return 5001;
    }

    @Override
    public void onClick() {
        if (communityType == CommunityType.HELP){
            startActivity(HelpDiscussionActivity.class);
        }else if (communityType == CommunityType.REVIEW){
            startActivity(BookReviewActivity.class);
        } else {
            TopicDiscussionActivity.startActivity(context,communityType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView iconView = viewHolder.findViewById(R.id.iv_community_head_icon);
        TextView nameView = viewHolder.findViewById(R.id.tv_community_head_name);
        iconView.setImageResource(this.drawableResId);
        nameView.setText(name);
    }
}
