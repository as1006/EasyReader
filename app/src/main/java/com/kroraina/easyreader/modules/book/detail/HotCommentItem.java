package com.kroraina.easyreader.modules.book.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.bean.HotCommentBean;
import com.kroraina.easyreader.modules.community.detail.DiscDetailActivity;
import com.kroraina.easyreader.modules.main.community.CommunityType;
import com.kroraina.easyreader.ui.widget.EasyRatingBar;
import com.kroraina.easyreader.ui.widget.transform.CircleTransform;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.ArrayList;
import java.util.List;


@LegoItem
public class HotCommentItem extends BaseItem {



    public static List<HotCommentItem> initFrom(Context context, List<HotCommentBean> beans){
        List<HotCommentItem> results = new ArrayList<>();
        for (HotCommentBean bean : beans){
            results.add(new HotCommentItem(context,bean));
        }
        return results;
    }

    public HotCommentBean bean;

    public HotCommentItem(Context context,HotCommentBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hot_comment;
    }

    @Override
    public void onClick() {
        DiscDetailActivity.startActivity(context, CommunityType.COMMENT,bean.get_id());

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        ImageView mIvPortrait = viewHolder.findViewById(R.id.hot_comment_iv_cover);
        TextView mTvAuthor = viewHolder.findViewById(R.id.hot_comment_tv_author);
        TextView mTvLv = viewHolder.findViewById(R.id.hot_comment_tv_lv);
        TextView mTvTitle = viewHolder.findViewById(R.id.hot_comment_title);
        EasyRatingBar mErbRate = viewHolder.findViewById(R.id.hot_comment_erb_rate);
        TextView mTvContent = viewHolder.findViewById(R.id.hot_comment_tv_content);
        TextView mTvHelpful = viewHolder.findViewById(R.id.hot_comment_tv_helpful);
        TextView mTvTime = viewHolder.findViewById(R.id.hot_comment_tv_time);

        //头像
        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getAuthor().getAvatar())
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .transform(new CircleTransform(context))
                .into(mIvPortrait);
        //作者
        mTvAuthor.setText(bean.getAuthor().getNickname());
        //等级
        mTvLv.setText(context.getResources().getString(R.string.nb_user_lv,bean.getAuthor().getLv()));
        //标题
        mTvTitle.setText(bean.getTitle());
        //评分
        mErbRate.setRating(bean.getRating());
        //内容
        mTvContent.setText(bean.getContent());
        //点赞数
        mTvHelpful.setText(bean.getLikeCount()+"");
        //时间
        mTvTime.setText(StringUtils.dateConvert(bean.getUpdated(),Constant.FORMAT_BOOK_DATE));
    }
}
