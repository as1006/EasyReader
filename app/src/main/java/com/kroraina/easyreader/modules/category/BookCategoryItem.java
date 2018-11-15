package com.kroraina.easyreader.modules.category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.bean.BaseBeanItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.utils.Constant;

@LayoutId(R.layout.item_sort)
public class BookCategoryItem extends BaseBeanItem<BookSortBean> {

//    public static List<BookCategoryItem> initFromBookSortBeans(Context context,List<BookSortBean> sortBeans){
//        List<BookCategoryItem> results = new ArrayList<>();
//        for (BookSortBean sortBean : sortBeans){
//            results.add(new BookCategoryItem(context,sortBean));
//        }
//        return results;
//    }

    public BookCategoryItem(Context context,BookSortBean bean){
        super(context,bean);
    }

    @Override
    public void onBindViewWithBean(@NonNull BaseViewHolder viewHolder, BookSortBean bean) {
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
