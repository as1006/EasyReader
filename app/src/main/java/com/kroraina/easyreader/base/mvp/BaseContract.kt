package com.kroraina.easyreader.base.mvp

interface BaseContract {

    interface BasePresenter<T> {

        fun attachView(view: T)

        fun detachView()
    }

    interface BaseView {

        fun showError()

        fun complete()
    }
}
