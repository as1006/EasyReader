package com.kroraina.easyreader.modules.main.shelf;

import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.entity.CollBookBean;
import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.model.local.BookRepository;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.modules.book.detail.BookDetailBean;
import com.kroraina.easyreader.utils.LogUtils;
import com.kroraina.easyreader.utils.RxUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created on 17-5-8.
 */

public class BookShelfPresenter extends RxPresenter<BookShelfContract.View>
        implements BookShelfContract.Presenter {
    private static final String TAG = "BookStorePresenter";

    @Override
    public void refreshCollBooks() {
        List<CollBookBean> collBooks = BookRepository.getInstance().getCollBooks();
        mView.finishRefresh(collBooks);
    }

    @Override
    public void createDownloadTask(CollBookBean collBookBean) {
        DownloadTaskBean task = new DownloadTaskBean();
        task.setTaskName(collBookBean.getTitle());
        task.setBookId(collBookBean.get_id());
        task.setBookChapters(collBookBean.getBookChapters());
        task.setLastChapter(collBookBean.getBookChapters().size());

        RxBus.getInstance().post(task);
    }

    //需要修改
    @Override
    public void updateCollBooks(List<CollBookBean> collBookBeans) {
        if (collBookBeans == null || collBookBeans.isEmpty()) {
            mView.complete();
            return;
        }
        List<CollBookBean> collBooks = new ArrayList<>(collBookBeans);
        List<Single<BookDetailBean>> observables = new ArrayList<>(collBooks.size());
        Iterator<CollBookBean> it = collBooks.iterator();
        while (it.hasNext()){
            CollBookBean collBook = it.next();
            //删除本地文件
            observables.add(RemoteRepository.getInstance()
                    .getBookDetail(collBook.get_id()));

        }
        //zip可能不是一个好方法。
        Single.zip(observables, new Function<Object[], List<CollBookBean>>() {
            @Override
            public List<CollBookBean> apply(Object[] objects) {
                List<CollBookBean> newCollBooks = new ArrayList<CollBookBean>(objects.length);
                for (int i=0; i<collBooks.size(); ++i){
                    CollBookBean oldCollBook = collBooks.get(i);
                    CollBookBean newCollBook = ((BookDetailBean)objects[i]).getCollBookBean();
                    //如果是oldBook是update状态，或者newCollBook与oldBook章节数不同
                    if (oldCollBook.isUpdate() ||
                            !oldCollBook.getLastChapter().equals(newCollBook.getLastChapter())){
                        newCollBook.setUpdate(true);
                    }
                    else {
                        newCollBook.setUpdate(false);
                    }
                    newCollBook.setLastRead(oldCollBook.getLastRead());
                    newCollBooks.add(newCollBook);
                    //存储到数据库中
                    BookRepository.getInstance()
                            .saveCollBooks(newCollBooks);
                }
                return newCollBooks;
            }
        })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(new SingleObserver<List<CollBookBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<CollBookBean> value) {
                        //跟原先比较
                        mView.finishUpdate();
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //提示没有网络
                        mView.showErrorTip(e.toString());
                        mView.complete();
                        LogUtils.e(e);
                    }
                });
    }
}
