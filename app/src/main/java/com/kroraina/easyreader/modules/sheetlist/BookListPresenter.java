package com.kroraina.easyreader.modules.sheetlist;

import com.kroraina.easyreader.base.rx.RxPresenter;
import com.kroraina.easyreader.model.bean.BookListBean;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.utils.LogUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class BookListPresenter extends RxPresenter<BookListContract.View> implements BookListContract.Presenter {


    @Override
    public void refreshBookList(BookListType type, String tag, int start, int limited) {

        if (tag.equals("全本")){
            tag = "";
        }

        Disposable refreshDispo = getBookListSingle(type, tag, start, limited)
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
                            mView.showError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(refreshDispo);
    }

    @Override
    public void loadBookList(BookListType type, String tag, int start, int limited) {
        Disposable refreshDispo = getBookListSingle(type, tag, start, limited)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans)-> {
                            mView.finishLoading(beans);
                        }
                        ,
                        (e) ->{
                            mView.showLoadError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(refreshDispo);
    }

    private Single<List<BookListBean>> getBookListSingle(BookListType type, String tag, int start, int limited){
        Single<List<BookListBean>> bookListSingle = null;
        //数据类型转换
        String gender = "";
        if (tag.equals("男生")){
            gender = "male";
            tag = "";
        }
        else if (tag.equals("女生")){
            gender = "female";
            tag = "";
        }

        switch (type){
            case HOT:
                bookListSingle = RemoteRepository.getInstance()
                        .getBookLists(type.getNetName(),"collectorCount",start,limited,tag,gender);
                break;
            case NEWEST:
                bookListSingle = RemoteRepository.getInstance()
                        .getBookLists("all",type.getNetName(),start,limited,tag,gender);
                break;
            case COLLECT:
                bookListSingle = RemoteRepository.getInstance()
                        .getBookLists("all",type.getNetName(),start,limited,tag,gender);
                break;
        }
        return bookListSingle;
    }
}
