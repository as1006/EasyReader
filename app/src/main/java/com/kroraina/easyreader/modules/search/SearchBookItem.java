package com.kroraina.easyreader.modules.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.book.detail.BookDetailActivity;
import com.kroraina.easyreader.utils.Constant;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.ArrayList;
import java.util.List;

@LegoItem
public class SearchBookItem extends BaseItem {

    public static List<SearchBookItem> initFromBean(Context context,List<SearchBookPackage.BooksBean> booksBeans){
        List<SearchBookItem> items = new ArrayList<>();
        for (SearchBookPackage.BooksBean booksBean : booksBeans){
            items.add(new SearchBookItem(context,booksBean));
        }
        return items;
    }

    public SearchBookPackage.BooksBean bean;

    public SearchBookItem(Context context,SearchBookPackage.BooksBean bean){
        super(context);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_search_book;
    }

    @Override
    public void onClick() {
        BookDetailActivity.startActivity(context,this.bean.get_id());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        ImageView mIvCover = viewHolder.findViewById(R.id.search_book_iv_cover);
        TextView mTvName = viewHolder.findViewById(R.id.search_book_tv_name);
        TextView mTvBrief = viewHolder.findViewById(R.id.search_book_tv_brief);

        //显示图片
        Glide.with(context)
                .load(Constant.IMG_BASE_URL + bean.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .into(mIvCover);

        mTvName.setText(bean.getTitle());

        mTvBrief.setText(context.getString(R.string.nb_search_book_brief,
                bean.getLatelyFollower(),bean.getRetentionRatio(),bean.getAuthor()));
    }
}
