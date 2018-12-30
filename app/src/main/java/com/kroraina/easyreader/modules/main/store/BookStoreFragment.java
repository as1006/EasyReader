package com.kroraina.easyreader.modules.main.store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseActivity;
import com.kroraina.easyreader.base.fragment.BaseMVPFragment;
import com.kroraina.easyreader.model.bean.BookListBean;
import com.kroraina.easyreader.model.entity.CollBookBean;
import com.kroraina.easyreader.modules.category.detail.SortBookBean;
import com.kroraina.easyreader.modules.local.DownloadActivity;
import com.kroraina.easyreader.modules.local.FileSystemActivity;
import com.kroraina.easyreader.modules.search.SearchActivity;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.ScrollRefreshRecyclerView;
import com.kroraina.easyreader.utils.PermissionsChecker;
import com.xincubate.lego.adapter.core.BaseAdapter;

import java.util.List;

import butterknife.BindView;

public class BookStoreFragment extends BaseMVPFragment<BookStoreContract.Presenter> implements BookStoreContract.View {

    @BindView(R.id.book_shelf_rv_content)
    ScrollRefreshRecyclerView mRvContent;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    /************************************/
    private BaseAdapter mCollBookAdapter;

    @Override
    protected int getContentId() {
        return R.layout.fragment_bookstore;
    }

    @Override
    protected BookStoreContract.Presenter bindPresenter() {
        return new BookStorePresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setHasOptionsMenu(true);
        mToolbar.setTitle("书城");
        ((BaseActivity)getActivity()).setSupportActionBar(mToolbar);
        setUpAdapter();

        mRvContent.setOnRefreshListener(() -> mRvContent.finishRefresh());
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadRecommendTopicBooks();
        mPresenter.loadRecommendHotBookSheet();
        mPresenter.loadRecommendBooks("male");
    }

    private void setUpAdapter() {
        //添加Footer
        mCollBookAdapter = new BaseAdapter(getActivity());
        mCollBookAdapter.addItem(new SearchBarItem(getContext()));

        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mCollBookAdapter);
        mCollBookAdapter.notifyDataSetChanged();
    }

    /*******************************************************************8*/
    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        if (mRvContent.isRefreshing()) {
            mRvContent.finishRefresh();
        }
    }

    @Override
    public void finishLoadRecommendTopicBooks(List<SortBookBean> bookSortBeans) {
        mCollBookAdapter.addItem(new RecommendGridItem(getContext(),bookSortBeans));
        mCollBookAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishLoadRecommendHotBookSheet(List<BookListBean> bookListBeans) {
        mCollBookAdapter.addItem(new RecommendVerticalItem(getContext(),bookListBeans));
        mCollBookAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishLoadRecommendBooks(List<CollBookBean> collBookBeans) {
        mCollBookAdapter.addItem(new RecommendBookGridItem(getContext(),collBookBeans));
        mCollBookAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorTip(String error) {
        mRvContent.setTip(error);
        mRvContent.showTip();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PermissionsChecker mPermissionsChecker;
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_store,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class<?> activityCls = null;
        switch (id) {
            case R.id.action_search:
                activityCls = SearchActivity.class;
                break;
            case R.id.action_login:
                break;
            case R.id.action_my_message:
                break;
            case R.id.action_download:
                activityCls = DownloadActivity.class;
                break;
            case R.id.action_sync_bookshelf:
                break;
            case R.id.action_scan_local_book:

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){

                    if (mPermissionsChecker == null){
                        mPermissionsChecker = new PermissionsChecker(getContext());
                    }

                    //获取读取和写入SD卡的权限
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)){
                        //请求权限
                        requestPermissions(PERMISSIONS,PERMISSIONS_REQUEST_STORAGE);
                        return super.onOptionsItemSelected(item);
                    }
                }

                activityCls = FileSystemActivity.class;
                break;
            case R.id.action_wifi_book:
                break;
            case R.id.action_feedback:
                break;
            case R.id.action_night_mode:
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        if (activityCls != null){
            Intent intent = new Intent(getActivity(), activityCls);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST_STORAGE: {
                // 如果取消权限，则返回的值为0
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //跳转到 FileSystemActivity
                    Intent intent = new Intent(getActivity(), FileSystemActivity.class);
                    startActivity(intent);

                } else {
                    ToastUtils.showShort("用户拒绝开启读写权限");
                }
                return;
            }
        }
    }
}
