package com.kroraina.easyreader.modules.sheetlist.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.ui.widget.transform.CircleTransform;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseMVPActivity;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.adapter.load.LoadMoreAdapter;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.kroraina.easyreader.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@ActivityUI(layoutId = R.layout.activity_refresh_list)
public class BookListDetailActivity extends BaseMVPActivity<BookListDetailContract.Presenter> implements BookListDetailContract.View {

    private static final String EXTRA_DETAIL_ID = "extra_detail_id";
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /****************************/
    private LoadMoreAdapter mDetailAdapter;
    private DetailHeaderItem mDetailHeader;
    private List<BookListDetailBean.BooksBean> mBooksList;
    /***********params****************/
    private String mDetailId;
    private int start = 0;
    public static final int LIMIT = 20;

    public static void startActivity(Context context,String detailId){
        Intent intent  =new Intent(context,BookListDetailActivity.class);
        intent.putExtra(EXTRA_DETAIL_ID,detailId);
        context.startActivity(intent);
    }

    @Override
    protected BookListDetailContract.Presenter bindPresenter() {
        return new BookListDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mDetailId = savedInstanceState.getString(EXTRA_DETAIL_ID);
        }else {
            mDetailId = getIntent().getStringExtra(EXTRA_DETAIL_ID);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("书单详情");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
    }

    private void setUpAdapter(){
        mDetailAdapter = new LoadMoreAdapter();
        mDetailHeader = new DetailHeaderItem(this);
        mDetailAdapter.addHeaderItem(mDetailHeader);

        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new DividerItemDecoration(this));
        mRvContent.setAdapter(mDetailAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mDetailAdapter.setOnLoadMoreListener(
                () -> new Handler().postDelayed(() -> loadBook(),500)
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookListDetail(mDetailId);
    }

    @Override
    public void finishRefresh(BookListDetailBean bean) {
        mDetailHeader.setBookListDetail(bean);
        mBooksList = bean.getBooks();
        refreshBook();
    }

    private void refreshBook(){
        start = 0;
        List<BookListDetailBean.BooksBean.BookBean> books = getBookList();
        mDetailAdapter.refreshItems(BookListInfoItem.initFrom(this,books));
        start = books.size();
    }

    private void loadBook(){
        List<BookListDetailBean.BooksBean.BookBean> books = getBookList();
        mDetailAdapter.addItems(BookListInfoItem.initFrom(this,books));
        start += books.size();
        mDetailAdapter.notifyDataSetChanged();
    }

    private List<BookListDetailBean.BooksBean.BookBean> getBookList(){
        int end = start + LIMIT;
        if (end > mBooksList.size()){
            end = mBooksList.size();
        }
        List<BookListDetailBean.BooksBean.BookBean> books = new ArrayList<>(LIMIT);
        for (int i=start; i < end; ++i){
            books.add(mBooksList.get(i).getBook());
        }
        return books;
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_DETAIL_ID, mDetailId);
    }


    @LayoutId(R.layout.header_book_list_detail)
    class DetailHeaderItem extends BaseItem{

        private BookListDetailBean detailBean;

        public DetailHeaderItem(Context context) {
            super(context);
        }

        @Override
        public int getItemViewType() {
            return 3003;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
            TextView tvTitle = viewHolder.findViewById(R.id.book_list_info_tv_title);
            TextView tvDesc = viewHolder.findViewById(R.id.book_list_detail_tv_desc);
            ImageView ivPortrait = viewHolder.findViewById(R.id.book_list_info_iv_cover);
            TextView tvCreate = viewHolder.findViewById(R.id.book_list_detail_tv_create);
            TextView tvAuthor = viewHolder.findViewById(R.id.book_list_info_tv_author);
            TextView tvShare = viewHolder.findViewById(R.id.book_list_detail_tv_share);

            if (detailBean == null){
                return;
            }
            //标题
            tvTitle.setText(detailBean.getTitle());
            //描述
            tvDesc.setText(detailBean.getDesc());
            //头像
            Glide.with(getApplicationContext())
                    .load(Constant.IMG_BASE_URL+detailBean.getAuthor().getAvatar())
                    .placeholder(R.drawable.ic_loadding)
                    .error(R.drawable.ic_load_error)
                    .transform(new CircleTransform(getApplicationContext()))
                    .into(ivPortrait);
            //作者
            tvAuthor.setText(detailBean.getAuthor().getNickname());

        }

        public void setBookListDetail(BookListDetailBean bean){
            detailBean = bean;
        }
    }
}
