package com.kroraina.easyreader.modules.sheetlist.detail

import com.kroraina.easyreader.model.bean.AuthorBean

class BookListDetailBean {
    var _id: String? = null
    var updated: String? = null
    var title: String? = null
    var author: AuthorBean? = null
    var desc: String? = null
    var gender: String? = null
    var created: String? = null
    var stickStopTime: Any? = null
    var isIsDraft: Boolean = false
    var isDistillate: Any? = null
    var collectorCount: Int = 0
    var shareLink: String? = null
    var id: String? = null
    var total: Int = 0
    var tags: List<String>? = null
    var books: List<BooksBean>? = null


    class BooksBean {
        var book: BookBean? = null
        var comment: String? = null

        class BookBean {
            var cat: String? = null
            var _id: String? = null
            var title: String? = null
            var author: String? = null
            var longIntro: String? = null
            var majorCate: String? = null
            var minorCate: String? = null
            var cover: String? = null
            var site: String? = null
            var banned: Int = 0
            var latelyFollower: Int = 0
            var wordCount: Int = 0
            var retentionRatio: Double = 0.toDouble()
        }
    }
}
