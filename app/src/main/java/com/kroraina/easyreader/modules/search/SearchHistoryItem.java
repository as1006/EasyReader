package com.kroraina.easyreader.modules.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.entity.SearchHistoryBean;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class SearchHistoryItem extends BaseItem {

    public SearchHistoryBean bean;

    public SearchHistoryItem(Context context,SearchHistoryBean bean){
        super(context);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_search_history;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        TextView mKeywordView = viewHolder.findViewById(R.id.tv_keyword);
        mKeywordView.setText(bean.getSearchKey());
    }
}
