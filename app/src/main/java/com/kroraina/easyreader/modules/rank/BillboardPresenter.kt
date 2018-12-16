package com.kroraina.easyreader.modules.rank

import com.kroraina.easyreader.base.rx.RxPresenter
import com.kroraina.easyreader.model.local.LocalRepository
import com.kroraina.easyreader.model.remote.RemoteRepository

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class BillboardPresenter : RxPresenter<BillboardContract.View>(), BillboardContract.Presenter {

    override fun loadBillboardList() {
        //这个最好是设定一个默认时间采用Remote加载，如果Remote加载失败则采用数据中的数据。我这里先写死吧
        val bean = LocalRepository.getInstance()
                .billboardPackage
        if (bean == null) {
            RemoteRepository.getInstance()
                    .billboardPackage
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { value -> Schedulers.io().createWorker().schedule { LocalRepository.getInstance().saveBillboardPackage(value) } }
                    .subscribe(object : SingleObserver<BillboardPackage> {
                        override fun onSubscribe(d: Disposable) {
                            addDisposable(d)
                        }

                        override fun onSuccess(value: BillboardPackage) {
                            mView.finishRefresh(value)
                            mView.complete()
                        }

                        override fun onError(e: Throwable) {
                            mView.showError()
                        }
                    })
        } else {
            mView.finishRefresh(bean)
            mView.complete()
        }
    }
}
