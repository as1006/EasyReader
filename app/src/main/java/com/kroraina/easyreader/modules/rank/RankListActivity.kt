package com.kroraina.easyreader.modules.rank

import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.activity.BaseMVPActivity
import com.kroraina.easyreader.base.annotations.ActivityUI
import com.kroraina.easyreader.base.annotations.NavigationBar
import com.kroraina.easyreader.modules.rank.detail.RankDetailActivity
import com.kroraina.easyreader.modules.rank.detail.OtherRankDetailActivity
import kotlinx.android.synthetic.main.activity_bilboard.*
import java.util.*


@NavigationBar(titleResId = R.string.nb_fragment_find_top)
@ActivityUI(layoutId = R.layout.activity_bilboard)
class RankListActivity : BaseMVPActivity<RankListContract.Presenter>(), RankListContract.View {
    private var mBoyAdapter: RankListAdapter? = null
    private var mGirlAdapter: RankListAdapter? = null

    override fun initWidget() {
        super.initWidget()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        mBoyAdapter = RankListAdapter()
        mGirlAdapter = RankListAdapter()
        elv_boy!!.setAdapter(mBoyAdapter)
        elv_girl!!.setAdapter(mGirlAdapter)
    }

    override fun initClick() {
        super.initClick()
        rl_refresh.setOnReloadingListener { mPresenter.loadBillboardList() }
        elv_boy.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition != mBoyAdapter!!.groupCount - 1) {
                val bean = mBoyAdapter!!.getGroup(groupPosition)
                RankDetailActivity.startActivity(this, bean._id,
                        bean.monthRank, bean.totalRank)
                return@setOnGroupClickListener true
            }
            false
        }
        elv_boy.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (groupPosition == mBoyAdapter!!.groupCount - 1) {
                val bean = mBoyAdapter!!.getChild(groupPosition, childPosition)
                OtherRankDetailActivity.startActivity(this, bean!!.title, bean._id)
                return@setOnChildClickListener true
            }
            false
        }

        elv_girl.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition != mGirlAdapter!!.groupCount - 1) {
                val bean = mGirlAdapter!!.getGroup(groupPosition)
                RankDetailActivity.startActivity(this, bean._id,
                        bean.monthRank, bean.totalRank)
                return@setOnGroupClickListener true
            }
            false
        }

        elv_girl.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (groupPosition == mGirlAdapter!!.groupCount - 1) {
                val bean = mGirlAdapter!!.getChild(groupPosition, childPosition)
                OtherRankDetailActivity.startActivity(this, bean!!.title, bean._id)
                return@setOnChildClickListener true
            }
            false
        }
    }

    override fun bindPresenter(): RankListContract.Presenter {
        return RankListPresenter()
    }

    override fun processLogic() {
        super.processLogic()

        rl_refresh.showLoading()
        mPresenter.loadBillboardList()
    }

    override fun finishRefresh(beans: RankListPackage?) {
        if (beans?.male == null || beans.female == null
                || beans.male!!.isEmpty() || beans.female!!.isEmpty()) {
            rl_refresh!!.showEmpty()
            return
        }
        updateMaleBillboard(beans.male!!)
        updateFemaleBillboard(beans.female!!)
    }

    private fun updateMaleBillboard(disposes: List<RankListBean>) {
        val maleGroups = ArrayList<RankListBean>()
        val maleChildren = ArrayList<RankListBean>()
        for (bean in disposes) {
            if (bean.collapse) {
                maleChildren.add(bean)
            } else {
                maleGroups.add(bean)
            }
        }
        maleGroups.add(RankListBean("别人家的排行榜"))
        mBoyAdapter!!.addGroups(maleGroups)
        mBoyAdapter!!.addChildren(maleChildren)
    }

    private fun updateFemaleBillboard(disposes: List<RankListBean>) {
        val femaleGroups = ArrayList<RankListBean>()
        val femaleChildren = ArrayList<RankListBean>()

        for (bean in disposes) {
            if (bean.collapse) {
                femaleChildren.add(bean)
            } else {
                femaleGroups.add(bean)
            }
        }
        femaleGroups.add(RankListBean("别人家的排行榜"))
        mGirlAdapter!!.addGroups(femaleGroups)
        mGirlAdapter!!.addChildren(femaleChildren)
    }

    override fun showError() {
        rl_refresh!!.showError()
    }

    override fun complete() {
        rl_refresh!!.showFinish()
    }
}
