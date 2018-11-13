package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.bean.BookListBean;
import com.kroraina.easyreader.modules.sheetlist.detail.BookListDetailActivity;
import com.kroraina.easyreader.utils.Constant;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_book_brief)
public class BookListItem extends BaseItem {

    public static List<BookListItem> initFrom(Context context,List<BookListBean> beans){
        List<BookListItem> results = new ArrayList<>();
        for (BookListBean bean : beans){
            results.add(new BookListItem(context,bean));
        }
        return results;
    }

    public BookListBean bean;

    public BookListItem(Context context,BookListBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        BookListDetailActivity.startActivity(context,bean.get_id());
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
        mTvBrief.setText(bean.getDesc());
        //信息
        mTvMsg.setText(context.getResources().getString(R.string.nb_fragment_book_list_message,
                bean.getBookCount(),bean.getCollectorCount()));
    }
}
