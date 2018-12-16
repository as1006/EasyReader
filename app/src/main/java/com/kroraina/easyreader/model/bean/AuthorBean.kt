package com.kroraina.easyreader.model.bean

class AuthorBean {
    var _id: String? = null
    var avatar: String? = null
    var nickname: String? = null
    var activityAvatar: String? = null
    var type: String? = null
    var lv: Int = 0
    var gender: String? = null

    override fun toString(): String {
        return "AuthorBean{" +
                "_id='" + _id + '\''.toString() +
                ", avatar='" + avatar + '\''.toString() +
                ", nickname='" + nickname + '\''.toString() +
                ", activityAvatar='" + activityAvatar + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", lv=" + lv +
                ", gender='" + gender + '\''.toString() +
                '}'.toString()
    }
}