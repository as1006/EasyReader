package com.kroraina.easyreader.modules.book.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.model.bean.BookDetailRecommendListBean;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.modules.sheetlist.detail.BookListDetailActivity;
import com.kroraina.easyreader.utils.Constant;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_book_brief)
public class BookDetailRecommendItem extends BaseItem {

    public static List<BookDetailRecommendItem> initFrom(Context context, List<BookDetailRecommendListBean> beans){
        List<BookDetailRecommendItem> results = new ArrayList<>();
        for (BookDetailRecommendListBean bean : beans){
            results.add(new BookDetailRecommendItem(context,bean));
        }
        return results;
    }

    public BookDetailRecommendListBean bean;

    public BookDetailRecommendItem(Context context,BookDetailRecommendListBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        BookListDetailActivity.startActivity(context,bean.getId());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
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
        mTvBrief.setText(bean.getDesc());
        //信息
        mTvMsg.setText(context.getResources().getString(R.string.nb_fragment_book_list_message,
                bean.getBookCount(),bean.getCollectorCount()));
    }
}
