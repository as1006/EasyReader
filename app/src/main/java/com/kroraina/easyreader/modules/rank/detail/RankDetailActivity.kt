package com.kroraina.easyreader.modules.rank.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.activity.BaseTabActivity
import com.kroraina.easyreader.base.annotations.ActivityUI
import com.kroraina.easyreader.base.annotations.NavigationBar

import java.util.ArrayList
import java.util.Arrays


@NavigationBar(title = "追书最热榜")
@ActivityUI(layoutId = R.layout.activity_tab_top)
class RankDetailActivity : BaseTabActivity() {

    private var mWeekId: String? = null
    private var mMonthId: String? = null
    private var mTotalId: String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        if (savedInstanceState != null) {
            mWeekId = savedInstanceState.getString(EXTRA_WEEK_ID)
            mMonthId = savedInstanceState.getString(EXTRA_MONTH_ID)
            mTotalId = savedInstanceState.getString(EXTRA_TOTAL_ID)
        } else {
            mWeekId = intent.getStringExtra(EXTRA_WEEK_ID)
            mMonthId = intent.getStringExtra(EXTRA_MONTH_ID)
            mTotalId = intent.getStringExtra(EXTRA_TOTAL_ID)
        }
    }

    override fun createTabFragments(): List<Fragment> {
        val fragments = ArrayList<Fragment>()
        fragments.add(RankDetailFragment.newInstance(mWeekId))
        fragments.add(RankDetailFragment.newInstance(mMonthId))
        fragments.add(RankDetailFragment.newInstance(mTotalId))
        return fragments
    }

    override fun createTabTitles(): List<String> {
        val title = resources.getStringArray(R.array.nb_fragment_bill_book)
        return Arrays.asList(*title)
    }

    /** */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_WEEK_ID, mWeekId)
        outState.putString(EXTRA_MONTH_ID, mMonthId)
        outState.putString(EXTRA_TOTAL_ID, mTotalId)
    }

    companion object {
        private const val EXTRA_WEEK_ID = "extra_week_id"
        private const val EXTRA_MONTH_ID = "extra_month_id"
        private const val EXTRA_TOTAL_ID = "extra_total_id"
        fun startActivity(context: Context, weekId: String?, monthId: String?, totalId: String?) {
            val intent = Intent(context, RankDetailActivity::class.java)
            intent.putExtra(EXTRA_WEEK_ID, weekId)
            intent.putExtra(EXTRA_MONTH_ID, monthId)
            intent.putExtra(EXTRA_TOTAL_ID, totalId)

            context.startActivity(intent)
        }
    }
}
