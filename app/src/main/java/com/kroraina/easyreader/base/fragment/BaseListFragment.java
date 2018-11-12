package com.kroraina.easyreader.base.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseAdapter;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;

import butterknife.BindView;

public class BaseListFragment extends BaseFragment {

    @BindView(R.id.community_rv_content)
    protected RecyclerView mRvContent;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    protected BaseAdapter mAdapter;

    @Override
    protected int getContentId() {
        return R.layout.fragment_recyclerview_base;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        mToolbar.setTitle("社区");

        NavigationBar navigationBar = getClass().getAnnotation(NavigationBar.class);
        if (navigationBar != null){
            mToolbar.setVisibility(View.VISIBLE);
            if (navigationBar.titleResId() != 0){
                mToolbar.setTitle(navigationBar.titleResId());
            }else {
                mToolbar.setTitle(navigationBar.title());
            }
        }else {
            mToolbar.setVisibility(View.GONE);
        }

        setUpAdapter();
    }

    private void setUpAdapter(){
        mAdapter = new BaseAdapter();
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
    }
}
