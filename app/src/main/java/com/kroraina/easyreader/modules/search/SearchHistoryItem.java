package com.kroraina.easyreader.modules.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.easyapp.lego.adapter.annotations.LayoutId;
import com.easyapp.lego.adapter.core.BaseItem;
import com.easyapp.lego.adapter.core.BaseViewHolder;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.entity.SearchHistoryBean;

@LayoutId(R.layout.item_search_history)
public class SearchHistoryItem extends BaseItem {

    public SearchHistoryBean bean;

    public SearchHistoryItem(Context context,SearchHistoryBean bean){
        super(context);
        this.bean = bean;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        TextView mKeywordView = viewHolder.findViewById(R.id.tv_keyword);
        mKeywordView.setText(bean.getSearchKey());
    }
}
