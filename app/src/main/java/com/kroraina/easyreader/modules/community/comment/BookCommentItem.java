package com.kroraina.easyreader.modules.community.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.detail.DiscDetailActivity;
import com.kroraina.easyreader.modules.main.community.CommunityType;
import com.kroraina.easyreader.ui.widget.transform.CircleTransform;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;
import com.xincubate.lego.adapter.bean.BaseBeanItem;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.ArrayList;
import java.util.List;

@LegoItem
public class BookCommentItem extends BaseBeanItem<BookCommentBean> {


    public BookCommentItem(Context context, BookCommentBean bean) {
        super(context, bean);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_disc_comment;
    }

    @Override
    public void onClick() {
        String detailId = bean.get_id();
        DiscDetailActivity.startActivity(context, CommunityType.COMMENT, detailId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        ImageView mIvPortrait = viewHolder.findViewById(R.id.disc_comment_iv_portrait);
        TextView mTvName = viewHolder.findViewById(R.id.disc_comment_tv_name);
        TextView mTvLv = viewHolder.findViewById(R.id.disc_comment_tv_lv);
        TextView mTvTime = viewHolder.findViewById(R.id.disc_comment_tv_time);
        TextView mTvBrief = viewHolder.findViewById(R.id.disc_comment_tv_brief);
        TextView mTvLabelDistillate = viewHolder.findViewById(R.id.disc_comment_tv_label_distillate);
        TextView mTvLabelHot = viewHolder.findViewById(R.id.disc_comment_tv_label_hot);
        TextView mTvResponseCount = viewHolder.findViewById(R.id.disc_comment_tv_response_count);
        TextView mTvLikeCount = viewHolder.findViewById(R.id.disc_comment_tv_like_count);

        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getAuthor().getAvatar())
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .transform(new CircleTransform(context))
                .into(mIvPortrait);
        //名字
        mTvName.setText(bean.getAuthor().getNickname());
        //等级
        mTvLv.setText(context.getResources().getString(R.string.nb_user_lv,
                bean.getAuthor().getLv()));
        //简介
        mTvBrief.setText(bean.getTitle());
        //label
        if (bean.getState().equals(Constant.BOOK_STATE_DISTILLATE)){
            mTvLabelDistillate.setVisibility(View.VISIBLE);
            mTvTime.setVisibility(View.VISIBLE);
        }
        else {
            mTvLabelDistillate.setVisibility(View.GONE);
            mTvTime.setVisibility(View.GONE);
        }

        //time
        mTvTime.setText(StringUtils.dateConvert(bean.getUpdated(),Constant.FORMAT_BOOK_DATE));
        //response count
        mTvResponseCount.setText(bean.getCommentCount()+"");
        //like count
        mTvLikeCount.setText(bean.getLikeCount()+"");
    }

}
