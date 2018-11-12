package com.kroraina.easyreader.modules.community.discussion.topic;

import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.bean.BookCommentBean;
import com.kroraina.easyreader.model.flag.BookDistillate;
import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.modules.main.community.CommunityType;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.kroraina.easyreader.utils.LogUtils.e;



public class DiscCommentPresenter extends RxPresenter<DiscCommentContact.View> implements DiscCommentContact.Presenter{

    @Override
    public void firstLoading(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate) {
        Single<List<BookCommentBean>> remoteObserver = RemoteRepository.getInstance()
                .getBookComment(block.getNetName(), sort.getNetName(),
                        start, limited, distillate.getNetName());

        Disposable disposable =  remoteObserver
                .subscribeOn(Schedulers.io())
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
        addDisposable(disposable);
    }

    @Override
    public void refreshComment(CommunityType block, BookSort sort,
                               int start, int limited, BookDistillate distillate) {
        Disposable refreshDispo = RemoteRepository.getInstance()
                .getBookComment(block.getNetName(),sort.getNetName(),
                        start,limited,distillate.getNetName())
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
    public void loadingComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate) {
        {
            //单纯的加载数据
            Single<List<BookCommentBean>> single = RemoteRepository.getInstance()
                    .getBookComment(block.getNetName(),sort.getNetName(),
                            start,limited,distillate.getNetName());
            loadComment(single);

        }
    }

    private void loadComment(Single<List<BookCommentBean>> observable){
        Disposable loadDispo =observable.subscribeOn(Schedulers.io())
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
