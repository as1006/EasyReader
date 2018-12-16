package com.kroraina.easyreader.modules.main

import android.support.v4.app.Fragment
import android.widget.Toast

import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.activity.BaseTabActivity
import com.kroraina.easyreader.modules.main.community.CommunityFragment
import com.kroraina.easyreader.modules.main.discover.DiscoverFragment
import com.kroraina.easyreader.modules.main.shelf.BookShelfFragment
import com.kroraina.easyreader.modules.main.store.BookStoreFragment
import kotlinx.android.synthetic.main.activity_base_tab.*

import java.util.ArrayList
import java.util.Arrays

class MainActivity : BaseTabActivity() {
    private var isPrepareFinish = false

    override fun createTabFragments(): List<Fragment> {
        val fragments = ArrayList<Fragment>()
        fragments.add(BookShelfFragment())
        fragments.add(BookStoreFragment())
        fragments.add(CommunityFragment())
        fragments.add(DiscoverFragment())
        return fragments
    }

    override fun createTabTitles(): List<String> {
        val titles = resources.getStringArray(R.array.nb_fragment_title)
        return Arrays.asList(*titles)
    }

    override fun onBackPressed() {
        if (!isPrepareFinish) {
            tab_vp.postDelayed(
                    { isPrepareFinish = false }, EXIT_WAIT_INTERVAL.toLong()
            )
            isPrepareFinish = true
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        /**
         * 两次返回键，退出app
         */
        private const val EXIT_WAIT_INTERVAL = 2000
    }
}
