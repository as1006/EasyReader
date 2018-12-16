package com.kroraina.easyreader.modules.rank.otherdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseMVPActivity;
import com.kroraina.easyreader.model.bean.BillBookBean;
import com.kroraina.easyreader.modules.rank.detail.BillBookContract;
import com.kroraina.easyreader.modules.rank.detail.BillBookItem;
import com.kroraina.easyreader.modules.rank.detail.BillBookPresenter;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.xincubate.lego.adapter.core.BaseAdapter;

import java.util.List;

import butterknife.BindView;



public class OtherBillBookActivity extends BaseMVPActivity<BillBookContract.Presenter>
        implements BillBookContract.View{
    private static final String EXTRA_BILL_ID = "extra_bill_id";
    private static final String EXTRA_BILL_NAME = "extra_bill_name";
    /********************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /*******************/
    private BaseAdapter mBillBookAdapter;
    /*****************/
    private String mBillId;
    private String mBillName;
    public static void startActivity(Context context,String billName, String billId){
        Intent intent = new Intent(context,OtherBillBookActivity.class);
        intent.putExtra(EXTRA_BILL_ID,billId);
        intent.putExtra(EXTRA_BILL_NAME,billName);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_refresh_list;
    }

    @Override
    protected BillBookContract.Presenter bindPresenter() {
        return new BillBookPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mBillId = savedInstanceState.getString(EXTRA_BILL_ID);
            mBillName = savedInstanceState.getString(EXTRA_BILL_NAME);
        }
        else {
            mBillId = getIntent().getStringExtra(EXTRA_BILL_ID);
            mBillName = getIntent().getStringExtra(EXTRA_BILL_NAME);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        toolbar.setTitle(mBillName);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookBrief(mBillId);
    }

    private void setUpAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new DividerItemDecoration(this));
        mBillBookAdapter = new BaseAdapter(this);
        mRvContent.setAdapter(mBillBookAdapter);
    }

    @Override
    public void finishRefresh(List<BillBookBean> beans) {
        mBillBookAdapter.refreshItems(BillBookItem.initFrom(this,beans));
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }
    /***************************************************/
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(EXTRA_BILL_ID,mBillId);
        outState.putString(EXTRA_BILL_NAME,mBillName);
    }
}
