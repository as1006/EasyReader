package com.kroraina.easyreader.modules.main.discover

import android.content.Intent
import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.annotations.FragmentUI
import com.kroraina.easyreader.base.annotations.NavigationBar
import com.kroraina.easyreader.base.fragment.BaseListFragment
import com.kroraina.easyreader.modules.category.BookCategoryActivity
import com.kroraina.easyreader.modules.rank.RankListActivity
import com.kroraina.easyreader.modules.sheetlist.BookSheetListActivity
import kotlinx.android.synthetic.main.layout_discover_head.*
import java.util.*

@NavigationBar(titleResId = R.string.nb_fragment_title_find)
@FragmentUI(layoutId = R.layout.fragment_discover)
class DiscoverFragment : BaseListFragment() {

    override fun initClick() {
        super.initClick()

        categoryEntry.setOnClickListener { startActivity(Intent(context, BookCategoryActivity::class.java)) }
        rankEntry.setOnClickListener { startActivity(Intent(context, RankListActivity::class.java)) }
        sheetEntry.setOnClickListener { startActivity(Intent(context, BookSheetListActivity::class.java)) }
    }

    override fun processLogic() {
        super.processLogic()
        val sections = ArrayList<DiscoverSectionItem>()
        for (type in DiscoverType.values()) {
            sections.add(DiscoverSectionItem(context!!, type))
        }
        mAdapter.addItems(sections)
        mAdapter.notifyDataSetChanged()
    }
}
