package com.kroraina.easyreader.modules.rank.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.annotations.FragmentUI
import com.kroraina.easyreader.base.fragment.BaseMVPFragment
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration
import com.xincubate.lego.adapter.bean.BaseBeanAdapter
import kotlinx.android.synthetic.main.fragment_refresh_list.*

@FragmentUI(layoutId = R.layout.fragment_refresh_list)
class RankDetailFragment : BaseMVPFragment<RankDetailContract.Presenter>(), RankDetailContract.View {

    private lateinit var mBillBookAdapter: BaseBeanAdapter
    private var mBillId: String? = null

    override fun bindPresenter(): RankDetailContract.Presenter {
        return RankDetailPresenter()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mBillId = arguments!!.getString(EXTRA_BILL_ID)
    }

    override fun initWidget(savedInstanceState: Bundle?) {
        super.initWidget(savedInstanceState)
        setUpAdapter()
    }

    override fun processLogic() {
        super.processLogic()
        refresh_layout.showLoading()
        mPresenter.refreshBookBrief(mBillId!!)
    }

    private fun setUpAdapter() {
        refresh_rv_content.layoutManager = LinearLayoutManager(context)
        refresh_rv_content.addItemDecoration(DividerItemDecoration(context!!))
        mBillBookAdapter = BaseBeanAdapter(activity)
        refresh_rv_content.adapter = mBillBookAdapter
    }

    override fun finishRefresh(beans: List<RankDetailBean>) {
        mBillBookAdapter.refreshBeans(beans)
    }

    override fun showError() {
        refresh_layout.showError()
    }

    override fun complete() {
        refresh_layout.showFinish()
    }

    companion object {
        private const val EXTRA_BILL_ID = "extra_bill_id"

        fun newInstance(billId: String?): Fragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_BILL_ID, billId)
            val fragment = RankDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
