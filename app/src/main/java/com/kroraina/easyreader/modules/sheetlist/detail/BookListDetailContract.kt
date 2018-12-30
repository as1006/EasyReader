package com.kroraina.easyreader.modules.sheetlist.detail

import com.kroraina.easyreader.base.mvp.BaseContract

interface BookListDetailContract {

    interface View : BaseContract.BaseView {
        fun finishRefresh(bean: BookListDetailBean)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun refreshBookListDetail(detailId: String)
    }
}
