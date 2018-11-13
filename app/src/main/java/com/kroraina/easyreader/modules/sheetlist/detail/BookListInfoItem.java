package com.kroraina.easyreader.modules.sheetlist.detail;

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

@LayoutId(R.layout.item_book_list_info)
public class BookListInfoItem extends BaseItem {

    public static List<BookListInfoItem> initFrom(Context context, List<BookListDetailBean.BooksBean.BookBean> beans){
        List<BookListInfoItem> results = new ArrayList<>();
        for (BookListDetailBean.BooksBean.BookBean bean : beans){
            results.add(new BookListInfoItem(context,bean));
        }
        return results;
    }

    public BookListDetailBean.BooksBean.BookBean bean;

    public BookListInfoItem(Context context,BookListDetailBean.BooksBean.BookBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        BookDetailActivity.startActivity(context, bean.get_id());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView mIvPortrait = viewHolder.findViewById(R.id.book_list_info_iv_cover);
        TextView mTvTitle = viewHolder.findViewById(R.id.book_list_info_tv_title);
        TextView mTvAuthor = viewHolder.findViewById(R.id.book_list_info_tv_author);
        TextView mTvContent = viewHolder.findViewById(R.id.book_list_info_tv_content);
        TextView mTvMsg = viewHolder.findViewById(R.id.book_list_info_tv_msg);
        TextView mTvWord = viewHolder.findViewById(R.id.book_list_info_tv_word);

        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait);
        //书单名
        mTvTitle.setText(bean.getTitle());
        //作者
        mTvAuthor.setText(bean.getAuthor());
        //简介
        mTvContent.setText(bean.getLongIntro());
        //信息
        mTvMsg.setText(context.getResources().getString(R.string.nb_book_message,
                bean.getLatelyFollower(),bean.getRetentionRatio()));
        //书籍字数
        mTvWord.setText(context.getResources().getString(R.string.nb_book_word,bean.getWordCount()/10000));
    }
}
