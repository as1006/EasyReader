package com.kroraina.easyreader.modules.rank

import com.kroraina.easyreader.base.mvp.BaseContract


interface BillboardContract {

    interface View : BaseContract.BaseView {
        fun finishRefresh(beans: BillboardPackage?)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun loadBillboardList()
    }
}
