package com.kroraina.easyreader.modules.main.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.modules.search.SearchActivity;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class SearchBarItem extends BaseItem {

    public SearchBarItem(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_search_bar;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        LinearLayout mSearchBar = viewHolder.findViewById(R.id.ll_search_bar);
        mSearchBar.setOnClickListener(view -> {
            startActivity(SearchActivity.class);
        });
    }
}
