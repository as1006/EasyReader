package com.kroraina.easyreader.modules.main.discover

import android.content.Context
import android.widget.ImageView
import android.widget.TextView

import com.kroraina.easyreader.R
import com.kroraina.easyreader.modules.community.discussion.help.HelpDiscussionActivity
import com.xincubate.lego.adapter.core.BaseItem
import com.xincubate.lego.adapter.core.BaseViewHolder
import com.xincubate.lego.annotation.LegoItem

@LegoItem
class DiscoverSectionItem(context: Context, private val findType: DiscoverType) : BaseItem(context) {

    override fun getLayoutId(): Int {
        return R.layout.item_section
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val mIconView = viewHolder.findViewById<ImageView>(R.id.section_iv_icon)
        val mNameView = viewHolder.findViewById<TextView>(R.id.section_tv_name)

        mNameView!!.text = findType.typeName
        mIconView!!.setImageResource(findType.iconId)
    }

    override fun onClick() {
        when (findType) {
            DiscoverType.HELP -> startActivity(HelpDiscussionActivity::class.java)
        }
    }
}
