package com.kroraina.easyreader.modules.category.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kroraina.easyreader.model.bean.BookSubSortBean;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseTabActivity;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.modules.sheetlist.HorizontalTagAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 书籍分类详情
 */

@ActivityUI(layoutId = R.layout.activity_book_sort_list)
public class BookSortListActivity extends BaseTabActivity {
    private static final String EXTRA_GENDER = "extra_gender";
    private static final String EXTRA_SUB_SORT = "extra_sub_sort";

    /*******************/
    @BindView(R.id.book_sort_list_rv_tag)
    RecyclerView mRvTag;
    /************************************/
    private HorizontalTagAdapter mTagAdapter;
    /**********************************/
    private BookSubSortBean mSubSortBean;
    private String mGender;

    public static void startActivity(Context context, String gender, BookSubSortBean subSortBean){
        Intent intent = new Intent(context,BookSortListActivity.class);
        intent.putExtra(EXTRA_GENDER,gender);
        intent.putExtra(EXTRA_SUB_SORT, subSortBean);
        context.startActivity(intent);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mSubSortBean.getMajor());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mGender = savedInstanceState.getString(EXTRA_GENDER);
            mSubSortBean = savedInstanceState.getParcelable(EXTRA_SUB_SORT);
        } else {
            mGender = getIntent().getStringExtra(EXTRA_GENDER);
            mSubSortBean = getIntent().getParcelableExtra(EXTRA_SUB_SORT);
        }
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (BookSortListType type : BookSortListType.values()){
            fragments.add(BookSortListFragment.newInstance(mGender,mSubSortBean.getMajor(),type));
        }
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        List<String> titles = new ArrayList<>();
        for (BookSortListType type : BookSortListType.values()){
            titles.add(type.getTypeName());
        }
        return titles;
    }

    @Override
    protected void initClick() {
        super.initClick();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
    }

    private void setUpAdapter(){
        mTagAdapter = new HorizontalTagAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvTag.setLayoutManager(linearLayoutManager);
        mRvTag.setAdapter(mTagAdapter);

        mSubSortBean.getMins().add(0,"全部");
        mTagAdapter.addItemByBeans(this,mSubSortBean.getMins());
    }
    /*****************************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_GENDER, mGender);
        outState.putParcelable(EXTRA_SUB_SORT, mSubSortBean);
    }
}
