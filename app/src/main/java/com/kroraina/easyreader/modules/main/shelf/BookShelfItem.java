package com.kroraina.easyreader.modules.main.shelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.entity.CollBookBean;
import com.kroraina.easyreader.modules.book.read.ReadActivity;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_coll_book)
public class BookShelfItem extends BaseItem {

    public static List<BookShelfItem> initFrom(Context context,List<CollBookBean> bookBeans){
        List<BookShelfItem> results = new ArrayList<>();
        for (CollBookBean bookBean : bookBeans){
            results.add(new BookShelfItem(context,bookBean));
        }
        return results;
    }

    public CollBookBean bean;
    public BookShelfItem(Context context,CollBookBean bean){
        super(context);
        this.bean = bean;
    }

    @Override
    public void onClick() {
        ReadActivity.startActivity(context,bean, true);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView mIvCover = viewHolder.findViewById(R.id.coll_book_iv_cover);
        TextView mTvName = viewHolder.findViewById(R.id.coll_book_tv_name);
        TextView mTvChapter = viewHolder.findViewById(R.id.coll_book_tv_chapter);
        TextView mTvTime = viewHolder.findViewById(R.id.coll_book_tv_lately_update);
        CheckBox mCbSelected = viewHolder.findViewById(R.id.coll_book_cb_selected);
        ImageView mIvRedDot = viewHolder.findViewById(R.id.coll_book_iv_red_rot);
        ImageView mIvTop = viewHolder.findViewById(R.id.coll_book_iv_top);


            //书的图片
        Glide.with(context)
                .load(Constant.IMG_BASE_URL+bean.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvCover);

        //书名
        mTvName.setText(bean.getTitle());

        //时间
        mTvTime.setText(StringUtils.dateConvert(bean.getUpdated(), Constant.FORMAT_BOOK_DATE)+":");
        mTvTime.setVisibility(View.VISIBLE);

        //章节
        mTvChapter.setText(bean.getLastChapter());
        //我的想法是，在Collection中加一个字段，当追更的时候设置为true。当点击的时候设置为false。
        //当更新的时候，最新数据跟旧数据进行比较，如果更新的话，设置为true。
        if (bean.isUpdate()){
            mIvRedDot.setVisibility(View.VISIBLE);
        }
        else {
            mIvRedDot.setVisibility(View.GONE);
        }
    }
}
