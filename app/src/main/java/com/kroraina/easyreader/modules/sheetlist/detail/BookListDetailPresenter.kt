package com.kroraina.easyreader.modules.sheetlist.detail

import com.kroraina.easyreader.model.remote.RemoteRepository
import com.kroraina.easyreader.base.rx.RxPresenter
import com.kroraina.easyreader.utils.LogUtils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created on 17-5-2.
 */

class BookListDetailPresenter : RxPresenter<BookListDetailContract.View>(), BookListDetailContract.Presenter {
    override fun refreshBookListDetail(detailId: String) {
        val refreshDispo = RemoteRepository.getInstance()
                .getBookListDetail(detailId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { beans ->
                            mView.finishRefresh(beans)
                            mView.complete()
                        },
                        { e ->
                            mView.showError()
                            LogUtils.e(e)
                        }
                )
        addDisposable(refreshDispo)
    }
}
