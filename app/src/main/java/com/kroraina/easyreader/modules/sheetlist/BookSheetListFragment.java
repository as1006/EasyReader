package com.kroraina.easyreader.modules.sheetlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kroraina.easyreader.model.bean.BookListBean;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.base.adapter.load.LoadMoreAdapter;
import com.kroraina.easyreader.base.fragment.BaseMVPFragment;
import com.kroraina.easyreader.event.BookSubSortEvent;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 主题书单列表
 * 发现->主题书单
 */

public class BookSheetListFragment extends BaseMVPFragment<BookListContract.Presenter>
        implements BookListContract.View{
    private static final String EXTRA_BOOK_LIST_TYPE = "extra_book_list_type";
    private static final String BUNDLE_BOOK_TAG = "bundle_book_tag";
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /************************************/
    private LoadMoreAdapter mBookListAdapter;
    /***************************************/
    private BookListType mBookListType;
    private String mTag = "";
    private int mStart = 0;
    private int mLimit = 20;

    public static Fragment newInstance(BookListType bookListType){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOK_LIST_TYPE,bookListType);
        Fragment fragment = new BookSheetListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected BookListContract.Presenter bindPresenter() {
        return new BookListPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if(savedInstanceState != null){
            mBookListType = (BookListType) savedInstanceState.getSerializable(EXTRA_BOOK_LIST_TYPE);
            mTag = savedInstanceState.getString(BUNDLE_BOOK_TAG);
        }
        else {
            mBookListType = (BookListType) getArguments().getSerializable(EXTRA_BOOK_LIST_TYPE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }


    @Override
    protected void initClick() {
        super.initClick();
        mBookListAdapter.setOnLoadMoreListener(
                () -> {
                   mPresenter.loadBookList(mBookListType,mTag,mStart,mLimit);
                }
        );

        Disposable disposable = RxBus.getInstance()
                .toObservable(BookSubSortEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            mTag = event.bookSubSort;
                            showRefresh();
                        }
                );
        addDisposable(disposable);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        showRefresh();
    }

    private void showRefresh(){
        mStart = 0;
        mRefreshLayout.showLoading();
        mPresenter.refreshBookList(mBookListType,mTag,mStart,mLimit);
    }

    private void setUpAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mBookListAdapter = new LoadMoreAdapter();
        mRvContent.setAdapter(mBookListAdapter);
    }

    @Override
    public void finishRefresh(List<BookListBean> beans){
        mBookListAdapter.refreshItems(BookListItem.initFrom(getContext(),beans));
        mStart = beans.size();
    }

    @Override
    public void finishLoading(List<BookListBean> beans) {
        mBookListAdapter.addItems(BookListItem.initFrom(getContext(),beans));
        mStart += beans.size();
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadError() {
        mBookListAdapter.showLoadError();
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    /***********************************************************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_BOOK_LIST_TYPE, mBookListType);
        outState.putSerializable(BUNDLE_BOOK_TAG,mTag);
    }
}
