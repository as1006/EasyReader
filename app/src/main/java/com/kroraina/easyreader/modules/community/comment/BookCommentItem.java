package com.kroraina.easyreader.modules.community.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.community.detail.DiscDetailActivity;
import com.kroraina.easyreader.modules.main.community.CommunityType;
import com.kroraina.easyreader.ui.widget.transform.CircleTransform;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_disc_comment)
public class BookCommentItem extends BaseItem {

    public static List<BookCommentItem> initFrom(Context context, List<BookCommentBean> beans){
        List<BookCommentItem> results = new ArrayList<>();
        for (BookCommentBean bean : beans){
            results.add(new BookCommentItem(context,bean));
        }
        return results;
    }

    private BookCommentBean bean;

    public BookCommentItem(Context context, BookCommentBean bean){
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        String detailId = bean.get_id();
        DiscDetailActivity.startActivity(context, CommunityType.COMMENT, detailId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
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
