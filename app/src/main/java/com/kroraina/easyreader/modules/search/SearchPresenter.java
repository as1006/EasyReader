package com.kroraina.easyreader.modules.search;

import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.utils.LogUtils;
import com.kroraina.easyreader.utils.RxUtils;

/**
 * Created on 17-6-2.
 */

public class SearchPresenter extends RxPresenter<SearchContract.View>
        implements SearchContract.Presenter{

    @Override
    public void searchHotWord() {
        RemoteRepository.getInstance()
                .getHotWords()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> mView.finishHotWords(bean),
                        LogUtils::e
                );
    }

    @Override
    public void searchKeyWord(String query) {
        RemoteRepository.getInstance()
                .getKeyWords(query)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> mView.finishKeyWords(bean),
                        LogUtils::e
                );
    }

    @Override
    public void searchBook(String query) {
         RemoteRepository.getInstance()
                .getSearchBooks(query)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> mView.finishBooks(bean),
                        e -> {
                            LogUtils.e(e);
                            mView.errorBooks();
                        }
                );
    }
}
