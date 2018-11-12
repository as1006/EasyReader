package com.kroraina.easyreader.modules.community.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.model.bean.CommentBean;
import com.kroraina.easyreader.model.bean.ReplyToBean;
import com.kroraina.easyreader.ui.widget.transform.CircleTransform;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_comment)
public class CommentItem extends BaseItem {


    public static List<CommentItem> initFrom(Context context,List<CommentBean> beans,boolean isBestComment){
        List<CommentItem> results = new ArrayList<>();
        for (CommentBean bean : beans){
            results.add(new CommentItem(context,bean,isBestComment));
        }
        return results;
    }

    public CommentBean bean;
    public boolean isBestComment;

    public CommentItem(Context context,CommentBean bean,boolean isBestComment) {
        super(context);
        this.bean = bean;
        this.isBestComment = isBestComment;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {

        ImageView ivPortrait = viewHolder.findViewById(R.id.comment_iv_portrait);
        TextView tvFloor = viewHolder.findViewById(R.id.comment_tv_floor);
        TextView tvName = viewHolder.findViewById(R.id.comment_tv_name);
        TextView tvLv = viewHolder.findViewById(R.id.comment_tv_lv);
        TextView tvTime = viewHolder.findViewById(R.id.comment_tv_time);
        TextView tvLikeCount = viewHolder.findViewById(R.id.comment_tv_like_count);
        TextView tvContent = viewHolder.findViewById(R.id.comment_tv_content);
        TextView tvReplyName = viewHolder.findViewById(R.id.comment_tv_reply_name);
        TextView tvReplyFloor = viewHolder.findViewById(R.id.comment_tv_reply_floor);

//头像
        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getAuthor().getAvatar())
                .placeholder(R.drawable.ic_loadding)
                .error(R.drawable.ic_load_error)
                .transform(new CircleTransform(context))
                .into(ivPortrait);
        //名字
        tvName.setText(bean.getAuthor().getNickname());
        //等级
        tvLv.setText(context.getResources().getString(R.string.nb_user_lv,bean.getAuthor().getLv()));
        //楼层
        tvFloor.setText(context.getResources().getString(R.string.nb_comment_floor,bean.getFloor()));
        if (isBestComment){
            //点赞数
            tvTime.setVisibility(View.GONE);
            tvLikeCount.setVisibility(View.VISIBLE);
            tvLikeCount.setText(context.getResources().getString(R.string.nb_comment_like_count,bean.getLikeCount()));
        }
        else
            {
            //时间
            tvTime.setVisibility(View.VISIBLE);
            tvLikeCount.setVisibility(View.GONE);
            tvTime.setText(StringUtils.dateConvert(bean.getCreated(),Constant.FORMAT_BOOK_DATE));
        }
        //内容
        tvContent.setText(bean.getContent());
        //回复的人名
        ReplyToBean replyToBean = bean.getReplyTo();
        if (replyToBean != null){
            tvReplyName.setVisibility(View.VISIBLE);
            tvReplyFloor.setVisibility(View.VISIBLE);
            tvReplyName.setText(context.getResources().getString(R.string.nb_comment_reply_nickname,
                    replyToBean.getAuthor().getNickname()));
            //回复的楼层
            tvReplyFloor.setText(context.getResources().getString(R.string.nb_comment_reply_floor,
                    replyToBean.getFloor()));
        }
        else {
            tvReplyName.setVisibility(View.GONE);
            tvReplyFloor.setVisibility(View.GONE);
        }
    }
}
