package com.kroraina.easyreader.base.adapter.load;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@LayoutId(R.layout.view_load_more_container)
public class LoadMoreItem extends BaseItem {

    public static final int TYPE_HIDE = 0;
    public static final int TYPE_LOAD_MORE = 1;
    public static final int TYPE_NO_MORE = 2;
    public static final int TYPE_LOAD_ERROR = 3;

    private int mStatus = TYPE_HIDE;

    private OnLoadMoreListener mListener;

    public LoadMoreItem(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType() {
        return 9999;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {

        View mLoadMoreView = viewHolder.findViewById(R.id.layout_load_more);
        View mErrorView = viewHolder.findViewById(R.id.layout_error);
        View mNoMoreView = viewHolder.findViewById(R.id.layout_no_more);

        switch (mStatus){
            case TYPE_HIDE:
            {
                mLoadMoreView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mNoMoreView.setVisibility(GONE);
            }
                break;
            case TYPE_LOAD_MORE:
            {
                mLoadMoreView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mNoMoreView.setVisibility(GONE);
                //加载
                if (mListener != null){
                    mListener.onLoadMore();
                }
            }
                break;
            case TYPE_NO_MORE:
            {
                mLoadMoreView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mNoMoreView.setVisibility(VISIBLE);
            }
                break;
            case TYPE_LOAD_ERROR:
            {
                mLoadMoreView.setVisibility(GONE);
                mErrorView.setVisibility(VISIBLE);
                mNoMoreView.setVisibility(GONE);
            }
                break;
        }
    }

    public void setLoadMoreStatus(int status){
        mStatus = status;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mListener = listener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
