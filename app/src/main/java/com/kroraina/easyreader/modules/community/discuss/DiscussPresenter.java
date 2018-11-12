package com.kroraina.easyreader.modules.community.discuss;

import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.bean.BookCommentBean;
import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.model.remote.RemoteRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.kroraina.easyreader.utils.LogUtils.e;



public class DiscussPresenter extends RxPresenter<DiscussContact.View> implements DiscussContact.Presenter{


    @Override
    public void firstLoading(String bookId,BookSort sort, int start, int limited) {
        Single<List<BookDiscussBean>> remoteObserver = RemoteRepository.getInstance()
                .getBookDiscuss(bookId, sort.getNetName(),start, limited);

        //这里有问题，但是作者却用的好好的，可能是2.0之后的问题
        Disposable disposable =  remoteObserver
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) ->{
                            mView.finishRefresh(beans);
                        },
                        (e) ->{
                            mView.complete();
                            mView.showErrorTip();
                            e(e);
                        }
                );
        addDisposable(disposable);
    }

    @Override
    public void refreshComment(String bookId,BookSort sort, int start, int limited) {
        Disposable refreshDispo = RemoteRepository.getInstance()
                .getBookDiscuss(bookId, sort.getNetName(),start, limited)
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
    public void loadingComment(String bookId,BookSort sort, int start, int limited) {
//        if (isLocalLoad){
//            Single<List<BookCommentBean>> single = LocalRepository.getInstance()
//                    .getBookComments(block.getNetName(), sort.getDbName(),
//                            start, limited, distillate.getDbName());
//            loadComment(single);
//        }
//
//        else{
//            //单纯的加载数据
//            Single<List<BookCommentBean>> single = RemoteRepository.getInstance()
//                    .getBookComment(block.getNetName(),sort.getNetName(),
//                            start,limited,distillate.getNetName());
//            loadComment(single);
//
//        }
    }

    private void loadComment(Single<List<BookCommentBean>> observable){
//        Disposable loadDispo =observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        (beans) -> {
//                            mView.finishLoading(beans);
//                        }
//                        ,
//                        (e) -> {
//                            mView.showError();
//                            e(e);
//                        }
//                );
//        addDisposable(loadDispo);
    }
}
