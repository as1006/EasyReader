package com.kroraina.easyreader.base.activity

import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kroraina.easyreader.R
import kotlinx.android.synthetic.main.activity_base_tab.*

abstract class BaseTabActivity : BaseActivity() {

    private var mFragmentList: List<Fragment>? = null
    private var mTitleList: List<String>? = null

    /**************abstract */
    protected abstract fun createTabFragments(): List<Fragment>

    protected abstract fun createTabTitles(): List<String>

    /*****************rewrite method */

    override fun getContentId(): Int {
        return R.layout.activity_base_tab
    }

    override fun initWidget() {
        super.initWidget()
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        mFragmentList = createTabFragments()
        mTitleList = createTabTitles()

        checkParamsIsRight()

        val adapter = TabFragmentPageAdapter(supportFragmentManager)
        tab_vp!!.adapter = adapter
        tab_vp!!.offscreenPageLimit = 3
        tab_tl_indicator!!.setupWithViewPager(tab_vp)
    }

    /**
     * 检查输入的参数是否正确。即Fragment和title是成对的。
     */
    private fun checkParamsIsRight() {
        if (mFragmentList == null || mTitleList == null) {
            throw IllegalArgumentException("fragmentList or titleList doesn't have null")
        }

        if (mFragmentList!!.size != mTitleList!!.size)
            throw IllegalArgumentException("fragment and title size must equal")
    }

    /******************inner class */
    internal inner class TabFragmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragmentList!![position]
        }

        override fun getCount(): Int {
            return mFragmentList!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitleList!![position]
        }
    }
}
