package com.kroraina.easyreader.modules.rank.detail

import com.xincubate.lego.annotation.LegoBean

@LegoBean(clazz = RankDetailItem::class)
class RankDetailBean {
    var _id: String? = null
    var title: String? = null
    var author: String? = null
    var shortIntro: String? = null
    var cover: String? = null
    var cat: String? = null
    var site: String? = null
    var banned: Int = 0
    var latelyFollower: Int = 0
    var retentionRatio: String? = null
}
