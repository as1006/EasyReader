package com.kroraina.easyreader.modules.community.discussion.review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.model.bean.BookReviewBean;
import com.kroraina.easyreader.modules.community.detail.DiscDetailActivity;
import com.kroraina.easyreader.modules.main.community.CommunityType;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_disc_review)
public class DiscReviewItem extends BaseItem {

    public static List<DiscReviewItem> initFrom(Context context, List<BookReviewBean> beans){
        List<DiscReviewItem> results = new ArrayList<>();
        for (BookReviewBean bean : beans){
            results.add(new DiscReviewItem(context,bean));
        }
        return results;
    }


    public BookReviewBean bean;
    public DiscReviewItem(Context context,BookReviewBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        DiscDetailActivity.startActivity(context, CommunityType.REVIEW, bean.get_id());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView mIvPortrait = viewHolder.findViewById(R.id.review_iv_portrait);
        TextView mTvBookName = viewHolder.findViewById(R.id.review_tv_book_name);
        TextView mTvBookType = viewHolder.findViewById(R.id.review_tv_book_type);
        TextView mTvTime = viewHolder.findViewById(R.id.review_tv_time);
        TextView mTvBrief = viewHolder.findViewById(R.id.review_tv_brief);
        TextView mTvLabelDistillate = viewHolder.findViewById(R.id.review_tv_distillate);
        TextView mTvLabelHot = viewHolder.findViewById(R.id.review_tv_hot);
        TextView mTvRecommendCount = viewHolder.findViewById(R.id.review_tv_recommend);

        //头像
        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getBook().getCover())
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait);
        //名字
        mTvBookName.setText(bean.getBook().getTitle());
        //类型
        String bookType = Constant.bookType.get(bean.getBook().getType());
        mTvBookType.setText(context.getResources().getString(R.string.nb_book_type,bookType));
        //简介
        mTvBrief.setText(bean.getTitle());
        //time
        mTvTime.setText(StringUtils.dateConvert(bean.getUpdated(),Constant.FORMAT_BOOK_DATE));
        //label
        if (bean.getState().equals(Constant.BOOK_STATE_DISTILLATE)){
            mTvLabelDistillate.setVisibility(View.VISIBLE);
        }
        else {
            mTvLabelDistillate.setVisibility(View.GONE);
        }
        //response count
        mTvRecommendCount.setText(context.getResources().getString(R.string.nb_book_recommend,bean.getHelpful().getYes()));
    }
}