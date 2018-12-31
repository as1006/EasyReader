package com.kroraina.easyreader.modules.rank.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar

import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.activity.BaseMVPActivity
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration
import com.xincubate.lego.adapter.bean.BaseBeanAdapter

import kotlinx.android.synthetic.main.activity_refresh_list.*


class OtherRankDetailActivity : BaseMVPActivity<RankDetailContract.Presenter>(), RankDetailContract.View {

    /** */
    private var mBillBookAdapter: BaseBeanAdapter? = null
    /** */
    private var mBillId: String? = null
    private var mBillName: String? = null

    override fun getContentId(): Int {
        return R.layout.activity_refresh_list
    }

    override fun bindPresenter(): RankDetailContract.Presenter {
        return RankDetailPresenter()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        if (savedInstanceState != null) {
            mBillId = savedInstanceState.getString(EXTRA_BILL_ID)
            mBillName = savedInstanceState.getString(EXTRA_BILL_NAME)
        } else {
            mBillId = intent.getStringExtra(EXTRA_BILL_ID)
            mBillName = intent.getStringExtra(EXTRA_BILL_NAME)
        }
    }

    override fun setUpToolbar(toolbar: Toolbar) {
        super.setUpToolbar(toolbar)
        toolbar.title = mBillName
    }

    override fun initWidget() {
        super.initWidget()
        setUpAdapter()
    }

    override fun processLogic() {
        super.processLogic()
        refresh_layout.showLoading()
        mPresenter.refreshBookBrief(mBillId!!)
    }

    private fun setUpAdapter() {
        refresh_rv_content.layoutManager = LinearLayoutManager(this)
        refresh_rv_content.addItemDecoration(DividerItemDecoration(this))
        mBillBookAdapter = BaseBeanAdapter(this)
        refresh_rv_content.adapter = mBillBookAdapter
    }

    override fun finishRefresh(beans: List<RankDetailBean>) {
        mBillBookAdapter!!.refreshBeans(beans)
    }

    override fun showError() {
        refresh_layout.showError()
    }

    override fun complete() {
        refresh_layout.showFinish()
    }

    /** */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(EXTRA_BILL_ID, mBillId)
        outState.putString(EXTRA_BILL_NAME, mBillName)
    }

    companion object {
        private val EXTRA_BILL_ID = "extra_bill_id"
        private val EXTRA_BILL_NAME = "extra_bill_name"
        fun startActivity(context: Context, billName: String?, billId: String?) {
            val intent = Intent(context, OtherRankDetailActivity::class.java)
            intent.putExtra(EXTRA_BILL_ID, billId)
            intent.putExtra(EXTRA_BILL_NAME, billName)
            context.startActivity(intent)
        }
    }
}
