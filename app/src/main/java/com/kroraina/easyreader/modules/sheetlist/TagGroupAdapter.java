package com.kroraina.easyreader.modules.sheetlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.easyapp.lego.adapter.group.BaseGroupAdapter;
import com.kroraina.easyreader.model.bean.BookTagBean;

import java.util.ArrayList;
import java.util.List;

public class TagGroupAdapter extends BaseGroupAdapter {

    public TagGroupAdapter(RecyclerView recyclerView, int spanSize) {
        super(recyclerView, spanSize);
    }

    public void refreshItems(Context context,List<BookTagBean> bookTags){
        List<TagGroupItem> groupItems = new ArrayList<>();
        for (BookTagBean bookTagBean : bookTags){
            List<TagChildItem> childItems = new ArrayList<>();
            for (String child : bookTagBean.getTags()){
                childItems.add(new TagChildItem(context,child));
            }
            groupItems.add(new TagGroupItem(context,bookTagBean.getName(),childItems));
        }

        super.refreshItems(groupItems);
    }

}
