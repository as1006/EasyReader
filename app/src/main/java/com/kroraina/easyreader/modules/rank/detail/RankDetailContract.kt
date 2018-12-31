package com.kroraina.easyreader.modules.rank.detail

import com.kroraina.easyreader.base.mvp.BaseContract


interface RankDetailContract {
    interface View : BaseContract.BaseView {
        fun finishRefresh(beans: List<RankDetailBean>)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun refreshBookBrief(billId: String)
    }
}
