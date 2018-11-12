package com.kroraina.easyreader.base.adapter.load;

import com.kroraina.easyreader.base.adapter.BaseAdapter;
import com.kroraina.easyreader.base.adapter.BaseItem;

import java.util.List;

public class LoadMoreAdapter extends BaseAdapter {

    private LoadMoreItem mLoadMoreItem = new LoadMoreItem(null);

    public LoadMoreAdapter(){
        addFooterItem(mLoadMoreItem);
    }

    @Override
    public void refreshItems(List<? extends BaseItem> list) {
        mLoadMoreItem.setLoadMoreStatus(LoadMoreItem.TYPE_LOAD_MORE);
        super.refreshItems(list);
    }

    @Override
    public void addItems(List<? extends BaseItem> items) {
        if (items.size() == 0){
            mLoadMoreItem.setLoadMoreStatus(LoadMoreItem.TYPE_NO_MORE);
        }
        super.addItems(items);
    }

    public void setOnLoadMoreListener(LoadMoreItem.OnLoadMoreListener listener){
        mLoadMoreItem.setOnLoadMoreListener(listener);
    }

    public void showLoadError(){
        //设置为加载错误
        mLoadMoreItem.setLoadMoreStatus(LoadMoreItem.TYPE_LOAD_ERROR);
        notifyDataSetChanged();
    }
}
