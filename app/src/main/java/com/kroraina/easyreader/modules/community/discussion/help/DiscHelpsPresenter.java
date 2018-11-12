package com.kroraina.easyreader.modules.community.discussion.help;

import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.bean.BookHelpsBean;
import com.kroraina.easyreader.model.flag.BookDistillate;
import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.model.remote.RemoteRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.kroraina.easyreader.utils.LogUtils.e;



public class DiscHelpsPresenter extends RxPresenter<DiscHelpsContract.View> implements DiscHelpsContract.Presenter {

    @Override
    public void firstLoading(BookSort sort, int start, int limited, BookDistillate distillate) {
        //获取数据库中的数据
        Single<List<BookHelpsBean>> remoteObserver = RemoteRepository.getInstance()
                .getBookHelps(sort.getNetName(), start, limited, distillate.getNetName());

        remoteObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) ->{
                            mView.complete();
                            mView.showErrorTip();
                            e(e);
                        }
                );
    }

    @Override
    public void refreshBookHelps(BookSort sort, int start, int limited, BookDistillate distillate) {
        Disposable refreshDispo = RemoteRepository.getInstance()
                .getBookHelps(sort.getNetName(), start, limited, distillate.getNetName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans)-> {
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) ->{
                            mView.complete();
                            mView.showErrorTip();
                            e(e);
                        }
                );
        addDisposable(refreshDispo);
    }

    @Override
    public void loadingBookHelps(BookSort sort, int start, int limited, BookDistillate distillate) {

        Single<List<BookHelpsBean>> single = RemoteRepository.getInstance()
                .getBookHelps(sort.getNetName(), start, limited, distillate.getNetName());
        loadBookHelps(single);

    }

    private void loadBookHelps(Single<List<BookHelpsBean>> observable){
        Disposable loadDispo =observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishLoading(beans);
                        }
                        ,
                        (e) -> {
                            mView.showError();
                            e(e);
                        }
                );
        addDisposable(loadDispo);
    }
}
