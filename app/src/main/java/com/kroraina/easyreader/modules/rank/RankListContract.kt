package com.kroraina.easyreader.modules.rank

import com.kroraina.easyreader.base.mvp.BaseContract


interface RankListContract {

    interface View : BaseContract.BaseView {
        fun finishRefresh(beans: RankListPackage?)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadBillboardList()
    }
}
