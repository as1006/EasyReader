package com.kroraina.easyreader.modules.rank.detail

import com.kroraina.easyreader.base.rx.RxPresenter
import com.kroraina.easyreader.model.remote.RemoteRepository
import com.kroraina.easyreader.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RankDetailPresenter : RxPresenter<RankDetailContract.View>(), RankDetailContract.Presenter {
    override fun refreshBookBrief(billId: String) {
        val remoteDisp = RemoteRepository.getInstance()
                .getBillBooks(billId)
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
        addDisposable(remoteDisp)
    }
}
