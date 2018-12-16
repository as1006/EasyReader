package com.kroraina.easyreader.modules.category.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.base.fragment.BaseMVPFragment;
import com.kroraina.easyreader.event.BookSubSortEvent;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.xincubate.lego.adapter.load.LoadMoreAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;



public class BookSortListFragment extends BaseMVPFragment<BookSortListContract.Presenter>
        implements BookSortListContract.View{
    private static final String EXTRA_GENDER = "extra_gender";
    private static final String EXTRA_TYPE = "extra_type";
    private static final String EXTRA_MAJOR = "extra_major";

    /********************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /*************************************/
    LoadMoreAdapter mBookSortListAdapter;
    /************************************/
    private String mGender;
    private String mMajor;
    private BookSortListType mType;
    private String mMinor = "";
    private int mStart = 0;
    private int mLimit = 20;

    public static Fragment newInstance(String gender,String major,BookSortListType type){
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_GENDER,gender);
        bundle.putString(EXTRA_MAJOR,major);
        bundle.putSerializable(EXTRA_TYPE,type);
        Fragment fragment = new BookSortListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected BookSortListContract.Presenter bindPresenter() {
        return new BookSortListPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mGender = savedInstanceState.getString(EXTRA_GENDER);
            mMajor = savedInstanceState.getString(EXTRA_MAJOR);
            mType = (BookSortListType) savedInstanceState.getSerializable(EXTRA_TYPE);
        }
        else {
            mGender = getArguments().getString(EXTRA_GENDER);
            mMajor = getArguments().getString(EXTRA_MAJOR);
            mType = (BookSortListType) getArguments().getSerializable(EXTRA_TYPE);
        }
    }

    @Override
    protected void initClick() {
        super.initClick();

        mBookSortListAdapter.setOnLoadMoreListener(
                () -> mPresenter.loadSortBook(mGender,mType,mMajor,mMinor,mStart,mLimit)
        );

        //子类的切换
        Disposable disposable = RxBus.getInstance()
                .toObservable(BookSubSortEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (event) -> {
                            mMinor = event.bookSubSort;
                            mRefreshLayout.showLoading();
                            mStart = 0;
                            mPresenter.refreshSortBook(mGender,mType,mMajor,mMinor,mStart,mLimit);
                        }
                );
        addDisposable(disposable);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    private void setUpAdapter(){
        mBookSortListAdapter = new LoadMoreAdapter(getActivity());

        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mBookSortListAdapter);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshSortBook(mGender,mType,mMajor,mMinor,mStart,mLimit);
    }

    @Override
    public void finishRefresh(List<SortBookBean> beans) {
        if (beans.isEmpty()){
            mRefreshLayout.showEmpty();
            return;
        }
        mBookSortListAdapter.refreshItems(CategoryBookItem.initFrom(getContext(),beans));
        mStart = beans.size();
    }

    @Override
    public void finishLoad(List<SortBookBean> beans) {
        mBookSortListAdapter.addItems(CategoryBookItem.initFrom(getContext(),beans));
        mStart += beans.size();
        mBookSortListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void showLoadError() {
        mBookSortListAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    /***************************************************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_GENDER, mGender);
        outState.putString(EXTRA_MAJOR,mMajor);
        outState.putSerializable(EXTRA_TYPE,mType);
    }
}
