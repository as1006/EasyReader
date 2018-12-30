package com.kroraina.easyreader.modules.main.discover

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

import com.blankj.utilcode.util.Utils
import com.kroraina.easyreader.R
import com.xincubate.lego.annotation.LegoBean

enum class DiscoverType(@StringRes typeNameId: Int, @param:DrawableRes val iconId: Int) {
    HELP(R.string.nb_fragment_community_help, R.drawable.discover_icon_help);
    //LISTEN(R.string.nb_fragment_find_listen,R.drawable.ic_section_listen);

    val typeName: String = Utils.getApp().getString(typeNameId)

}
