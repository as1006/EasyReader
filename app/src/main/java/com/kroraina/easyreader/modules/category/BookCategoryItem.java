package com.kroraina.easyreader.modules.category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.annotations.LayoutId;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_sort)
public class BookCategoryItem extends BaseItem {

    public static List<BookCategoryItem> initFromBookSortBeans(Context context,List<BookSortBean> sortBeans){
        List<BookCategoryItem> results = new ArrayList<>();
        for (BookSortBean sortBean : sortBeans){
            results.add(new BookCategoryItem(context,sortBean));
        }
        return results;
    }

    public BookSortBean bean;

    public BookCategoryItem(Context context,BookSortBean bean){
        super(context);
        this.bean = bean;
    }

    @Override
    public int getItemViewType() {
        return 2000;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        TextView typeView = viewHolder.findViewById(R.id.sort_tv_type);
        ImageView coverView = viewHolder.findViewById(R.id.iv_cover);
        TextView countView = viewHolder.findViewById(R.id.sort_tv_count);

        typeView.setText(bean.getName());

        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getBookCover().get(0))
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(coverView);
        countView.setText(context.getResources().getString(R.string.nb_sort_book_count,bean.getBookCount()));

    }
}
