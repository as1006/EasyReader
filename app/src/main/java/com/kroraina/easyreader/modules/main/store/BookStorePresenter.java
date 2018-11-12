package com.kroraina.easyreader.modules.main.store;

import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.modules.sheetlist.BookListType;
import com.kroraina.easyreader.utils.LogUtils;
import com.kroraina.easyreader.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookStorePresenter extends RxPresenter<BookStoreContract.View> implements BookStoreContract.Presenter {

    @Override
    public void loadRecommendTopicBooks() {
        RemoteRepository.getInstance()
                .getSortBooks("male","hot","玄幻","",0,6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishLoadRecommendTopicBooks(beans);
                            mView.complete();
                        },
                        (e) -> {
                            //提示没有网络
                            LogUtils.e(e);
                            mView.showErrorTip(e.toString());
                            mView.complete();
                        }
                );
    }

    @Override
    public void loadRecommendHotBookSheet() {

        RemoteRepository.getInstance()
                .getBookLists(BookListType.HOT.getNetName(),"collectorCount",0,3,"","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishLoadRecommendHotBookSheet(beans);
                            mView.complete();
                        },
                        (e) -> {
                            //提示没有网络
                            LogUtils.e(e);
                            mView.showErrorTip(e.toString());
                            mView.complete();
                        }
                );
    }

    @Override
    public void loadRecommendBooks(String gender) {
        RemoteRepository.getInstance()
                .getRecommendBooks(gender)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.finishLoadRecommendBooks(beans);
                            mView.complete();
                        },
                        (e) -> {
                            //提示没有网络
                            LogUtils.e(e);
                            mView.showErrorTip(e.toString());
                            mView.complete();
                        }
                );
    }
}
