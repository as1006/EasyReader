package com.kroraina.easyreader.modules.community.discuss;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.annotations.FragmentUI;
import com.kroraina.easyreader.base.fragment.BaseMVPFragment;
import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.ScrollRefreshRecyclerView;
import com.xincubate.lego.adapter.core.BaseAdapter;

import java.util.List;

import butterknife.BindView;

@FragmentUI(layoutId = R.layout.fragment_scroll_refresh_list)
public class DiscussFragment extends BaseMVPFragment<DiscussContact.Presenter> implements DiscussContact.View {

    @BindView(R.id.scroll_refresh_rv_content)
    ScrollRefreshRecyclerView mRvContent;

    private String mBookId;

    private BaseAdapter mDiscCommentAdapter;

    private BookSort mBookSort = BookSort.DEFAULT;
    private int mStart = 0;
    private final int mLimited = 20;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mBookId = getArguments().getString("book_id");
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setUpAdapter();
    }

    private void setUpAdapter() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mDiscCommentAdapter = new BaseAdapter(getActivity());
        mRvContent.setAdapter(mDiscCommentAdapter);
    }

    /******************************init click method***********************************/

    @Override
    protected void initClick() {
        //下滑刷新
        mRvContent.setOnRefreshListener(() -> refreshData());


        //上滑加载
//        mDiscCommentAdapter.setOnLoadMoreListener(
//                () -> mPresenter.loadingComment(mBlock, mBookSort, mStart, mLimited, mDistillate)
//        );
    }

    @Override
    protected DiscussPresenter bindPresenter() {
        return new DiscussPresenter();
    }

    /*******************************logic********************************/
    @Override
    protected void processLogic() {
        super.processLogic();
        //首次加载数据
        mRvContent.startRefresh();
        mPresenter.firstLoading(mBookId,mBookSort, mStart, mLimited);
    }

    private void refreshData() {
        mStart = 0;
        mRvContent.startRefresh();
        mPresenter.refreshComment(mBookId,mBookSort, mStart, mLimited);
    }


    /********************************rewrite method****************************************/

    @Override
    public void finishRefresh(List<BookDiscussBean> beans) {
        mDiscCommentAdapter.refreshItems(BookDiscussItem.initFrom(getContext(),beans));
        mStart = beans.size();
        mRvContent.finishRefresh();
    }

    @Override
    public void finishLoading(List<BookDiscussBean> beans) {
        mDiscCommentAdapter.addItems(BookDiscussItem.initFrom(getContext(),beans));
        mStart += beans.size();
    }

    @Override
    public void showErrorTip() {
        mRvContent.showTip();
    }

    @Override
    public void showError() {
        //mDiscCommentAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRvContent.finishRefresh();
    }

    /****************************save*************************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
