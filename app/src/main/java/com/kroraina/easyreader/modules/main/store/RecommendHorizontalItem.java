package com.kroraina.easyreader.modules.main.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.entity.CollBookBean;
import com.kroraina.easyreader.modules.book.read.ReadActivity;
import com.kroraina.easyreader.utils.Constant;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.List;

@LegoItem
public class RecommendHorizontalItem extends BaseItem {

    public List<CollBookBean> mRecommendBooks;


    public RecommendHorizontalItem(Context context, List<CollBookBean> books){
        super(context);
        this.mRecommendBooks = books;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_recommend_horizontal;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder,int position) {
        fillBookView(mRecommendBooks.get(0),viewHolder.findViewById(R.id.layout_recommend_book_1));
        fillBookView(mRecommendBooks.get(1),viewHolder.findViewById(R.id.layout_recommend_book_2));
        fillBookView(mRecommendBooks.get(2),viewHolder.findViewById(R.id.layout_recommend_book_3));
        fillBookView(mRecommendBooks.get(3),viewHolder.findViewById(R.id.layout_recommend_book_4));
    }

    private void fillBookView(CollBookBean bookBean,LinearLayout bookView){

        bookView.setOnClickListener(view -> {
            ReadActivity.startActivity(context,bookBean, false);
        });

        ImageView coverView = bookView.findViewById(R.id.iv_cover);
        TextView nameView = bookView.findViewById(R.id.tv_book_name);
        TextView summaryView = bookView.findViewById(R.id.tv_summary);

        //书的图片
        Glide.with(context)
                .load(Constant.IMG_BASE_URL + bookBean.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(coverView);

        //书名
        nameView.setText(bookBean.getTitle());
        //章节
        summaryView.setText(bookBean.getAuthor());
    }
}
