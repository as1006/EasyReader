package com.kroraina.easyreader.modules.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.ArrayList;
import java.util.List;

@LegoItem
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
    public int getLayoutId() {
        return R.layout.item_keyword;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder,int position) {
        TextView mKeywordView = viewHolder.findViewById(R.id.tv_keyword);
        mKeywordView.setText(keyword);
    }
}
