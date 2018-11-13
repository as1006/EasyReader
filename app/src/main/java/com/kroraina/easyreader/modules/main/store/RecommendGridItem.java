package com.kroraina.easyreader.modules.main.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.book.detail.BookDetailActivity;
import com.kroraina.easyreader.modules.category.detail.SortBookBean;
import com.kroraina.easyreader.utils.Constant;

import java.util.List;

@LayoutId(R.layout.item_recommend_grid)
public class RecommendGridItem extends BaseItem {

    public List<SortBookBean> mRecommendBooks;

    public RecommendGridItem(Context context, List<SortBookBean> books){
        super(context);
        this.mRecommendBooks = books;
    }

    @Override
    public int getItemViewType() {
        return 1003;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        fillBookView(mRecommendBooks.get(0),viewHolder.findViewById(R.id.layout_recommend_book_1));
        fillBookView(mRecommendBooks.get(1),viewHolder.findViewById(R.id.layout_recommend_book_2));
        fillBookView(mRecommendBooks.get(2),viewHolder.findViewById(R.id.layout_recommend_book_3));
        fillBookView(mRecommendBooks.get(3),viewHolder.findViewById(R.id.layout_recommend_book_4));
        fillBookView(mRecommendBooks.get(4),viewHolder.findViewById(R.id.layout_recommend_book_5));
        fillBookView(mRecommendBooks.get(5),viewHolder.findViewById(R.id.layout_recommend_book_6));

        viewHolder.findViewById(R.id.search_book_tv_refresh_hot).setVisibility(View.INVISIBLE);
    }

    private void fillBookView(SortBookBean bookBean,RelativeLayout bookView){

        bookView.setOnClickListener(view -> {
            BookDetailActivity.startActivity(context,bookBean.get_id());
        });

        ImageView coverView = bookView.findViewById(R.id.iv_cover);
        TextView nameView = bookView.findViewById(R.id.tv_book_name);
        TextView authorView = bookView.findViewById(R.id.tv_author_name);
        TextView summaryView = bookView.findViewById(R.id.tv_summary);

        {
            //书的图片
            Glide.with(context)
                    .load(Constant.IMG_BASE_URL+bookBean.getCover())
                    .placeholder(R.drawable.ic_book_loading)
                    .error(R.drawable.ic_load_error)
                    .fitCenter()
                    .into(coverView);
        }
        //书名
        nameView.setText(bookBean.getTitle());
        authorView.setText(bookBean.getAuthor());
        authorView.setVisibility(View.VISIBLE);
        //章节
        summaryView.setText(bookBean.getLastChapter());
    }
}
