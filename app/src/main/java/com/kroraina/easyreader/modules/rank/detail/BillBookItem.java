package com.kroraina.easyreader.modules.rank.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.bean.BillBookBean;
import com.kroraina.easyreader.modules.book.detail.BookDetailActivity;
import com.kroraina.easyreader.utils.Constant;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.ArrayList;
import java.util.List;

@LegoItem
public class BillBookItem extends BaseItem {

    public static List<BillBookItem> initFrom(Context context, List<BillBookBean> beans){
        List<BillBookItem> results = new ArrayList<>();
        for (BillBookBean bean : beans){
            results.add(new BillBookItem(context,bean));
        }
        return results;
    }

    public BillBookBean bean;

    public BillBookItem(Context context,BillBookBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_book_brief;
    }

    @Override
    public void onClick() {
        BookDetailActivity.startActivity(context,bean.get_id());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder,int position) {
        ImageView mIvPortrait = viewHolder.findViewById(R.id.book_brief_iv_portrait);
        TextView mTvTitle = viewHolder.findViewById(R.id.book_brief_tv_title);
        TextView mTvAuthor = viewHolder.findViewById(R.id.book_brief_tv_author);
        TextView mTvBrief = viewHolder.findViewById(R.id.book_brief_tv_brief);
        TextView mTvMsg = viewHolder.findViewById(R.id.book_brief_tv_msg);

        //头像
        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getCover())
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait);
        //书单名
        mTvTitle.setText(bean.getTitle());
        //作者
        mTvAuthor.setText(bean.getAuthor());
        //简介
        mTvBrief.setText(bean.getShortIntro());
        //信息
        mTvMsg.setText(context.getString(R.string.nb_book_message,
                bean.getLatelyFollower(),bean.getRetentionRatio()));
    }
}
