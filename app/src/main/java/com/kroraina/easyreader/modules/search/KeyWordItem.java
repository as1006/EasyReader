package com.kroraina.easyreader.modules.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;

import java.util.ArrayList;
import java.util.List;

@LayoutId(R.layout.item_keyword)
public class KeyWordItem extends BaseItem {

    public static List<KeyWordItem> initForStringArray(Context context,List<String> keywords){
        List<KeyWordItem> items = new ArrayList<>();
        for (String keyword : keywords){
            items.add(new KeyWordItem(context,keyword));
        }
        return items;
    }

    public String keyword;

    public KeyWordItem(Context context,String keyword){
        super(context);
        this.keyword = keyword;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        TextView mKeywordView = viewHolder.findViewById(R.id.tv_keyword);
        mKeywordView.setText(keyword);
    }
}
