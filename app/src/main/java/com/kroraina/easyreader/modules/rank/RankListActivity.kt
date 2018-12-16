package com.kroraina.easyreader.modules.rank

import android.view.View
import android.widget.ExpandableListView
import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.activity.BaseMVPActivity
import com.kroraina.easyreader.base.annotations.ActivityUI
import com.kroraina.easyreader.base.annotations.NavigationBar
import com.kroraina.easyreader.modules.rank.detail.BillBookActivity
import com.kroraina.easyreader.modules.rank.otherdetail.OtherBillBookActivity
import kotlinx.android.synthetic.main.activity_bilboard.*
import java.util.*

/**
 * 数据的初始化，Expand的配置
 * 1. 查看Api制作数据Bean，制作相应的Adapter
 * 2. 初始化Expandable
 * 3. 制作数据获取类。
 */

@NavigationBar(titleResId = R.string.nb_fragment_find_top)
@ActivityUI(layoutId = R.layout.activity_bilboard)
class RankListActivity : BaseMVPActivity<BillboardContract.Presenter>(), BillboardContract.View, ExpandableListView.OnGroupClickListener {

    private var mBoyAdapter: BillboardAdapter? = null
    private var mGirlAdapter: BillboardAdapter? = null

    override fun initWidget() {
        super.initWidget()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        mBoyAdapter = BillboardAdapter()
        mGirlAdapter = BillboardAdapter()
        elv_boy!!.setAdapter(mBoyAdapter)
        elv_girl!!.setAdapter(mGirlAdapter)
    }

    override fun initClick() {
        super.initClick()
        rl_refresh!!.setOnReloadingListener { mPresenter.loadBillboardList() }
        elv_boy!!.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition != mBoyAdapter!!.groupCount - 1) {
                val bean = mBoyAdapter!!.getGroup(groupPosition)
                BillBookActivity.startActivity(this, bean._id,
                        bean.monthRank, bean.totalRank)
                return@setOnGroupClickListener true
            }
            false
        }
        elv_boy!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (groupPosition == mBoyAdapter!!.groupCount - 1) {
                val bean = mBoyAdapter!!.getChild(groupPosition, childPosition)
                OtherBillBookActivity.startActivity(this, bean!!.title, bean._id)
                return@setOnChildClickListener true
            }
            false
        }

        elv_girl!!.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition != mGirlAdapter!!.groupCount - 1) {
                val bean = mGirlAdapter!!.getGroup(groupPosition)
                BillBookActivity.startActivity(this, bean._id,
                        bean.monthRank, bean.totalRank)
                return@setOnGroupClickListener true
            }
            false
        }

        elv_girl!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (groupPosition == mGirlAdapter!!.groupCount - 1) {
                val bean = mGirlAdapter!!.getChild(groupPosition, childPosition)
                OtherBillBookActivity.startActivity(this, bean!!.title, bean._id)
                return@setOnChildClickListener true
            }
            false
        }
    }

    override fun bindPresenter(): BillboardContract.Presenter {
        return BillboardPresenter()
    }

    override fun processLogic() {
        super.processLogic()

        rl_refresh!!.showLoading()
        mPresenter.loadBillboardList()
    }

    override fun finishRefresh(beans: BillboardPackage?) {
        if (beans == null || beans.male == null || beans.female == null
                || beans.male.size == 0 || beans.female.size == 0) {
            rl_refresh!!.showEmpty()
            return
        }
        updateMaleBillboard(beans.male)
        updateFemaleBillboard(beans.female)
    }

    private fun updateMaleBillboard(disposes: List<BillboardBean>) {
        val maleGroups = ArrayList<BillboardBean>()
        val maleChildren = ArrayList<BillboardBean>()
        for (bean in disposes) {
            if (bean.isCollapse) {
                maleChildren.add(bean)
            } else {
                maleGroups.add(bean)
            }
        }
        maleGroups.add(BillboardBean("别人家的排行榜"))
        mBoyAdapter!!.addGroups(maleGroups)
        mBoyAdapter!!.addChildren(maleChildren)
    }

    private fun updateFemaleBillboard(disposes: List<BillboardBean>) {
        val femaleGroups = ArrayList<BillboardBean>()
        val femaleChildren = ArrayList<BillboardBean>()

        for (bean in disposes) {
            if (bean.isCollapse) {
                femaleChildren.add(bean)
            } else {
                femaleGroups.add(bean)
            }
        }
        femaleGroups.add(BillboardBean("别人家的排行榜"))
        mGirlAdapter!!.addGroups(femaleGroups)
        mGirlAdapter!!.addChildren(femaleChildren)
    }

    override fun showError() {
        rl_refresh!!.showError()
    }

    override fun complete() {
        rl_refresh!!.showFinish()
    }

    override fun onGroupClick(parent: ExpandableListView, v: View, groupPosition: Int, id: Long): Boolean {
        return false
    }
}
