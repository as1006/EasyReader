package com.kroraina.easyreader.modules.category.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.book.detail.BookDetailActivity;
import com.kroraina.easyreader.utils.Constant;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_book_brief)
public class CategoryBookItem extends BaseItem {

    public static List<CategoryBookItem> initFrom(Context context,List<SortBookBean> beans){
        List<CategoryBookItem> results = new ArrayList<>();
        for (SortBookBean bean : beans){
            results.add(new CategoryBookItem(context,bean));
        }
        return results;
    }

    private SortBookBean bean;

    public CategoryBookItem(Context context,SortBookBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        String bookId = bean.get_id();
        BookDetailActivity.startActivity(context,bookId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView mIvPortrait = viewHolder.findViewById(R.id.book_brief_iv_portrait);
        TextView mTvTitle = viewHolder.findViewById(R.id.book_brief_tv_title);
        TextView mTvAuthor = viewHolder.findViewById(R.id.book_brief_tv_author);
        TextView mTvBrief = viewHolder.findViewById(R.id.book_brief_tv_brief);
        TextView mTvMsg = viewHolder.findViewById(R.id.book_brief_tv_msg);

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
        mTvMsg.setText(context.getResources().getString(R.string.nb_book_message,
                bean.getLatelyFollower(),bean.getRetentionRatio()));
    }
}
