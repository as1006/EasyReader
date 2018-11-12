package com.kroraina.easyreader.modules.book.detail;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.bean.BookDetailRecommendListBean;
import com.kroraina.easyreader.model.bean.HotCommentBean;
import com.kroraina.easyreader.model.entity.CollBookBean;

import java.util.List;



public interface BookDetailContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(BookDetailBean bean);
        void finishHotComment(List<HotCommentBean> beans);
        void finishRecommendBookList(List<BookDetailRecommendListBean> beans);

        void waitToBookShelf();
        void errorToBookShelf();
        void succeedToBookShelf();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookDetail(String bookId);
        //添加到书架上
        void addToBookShelf(CollBookBean collBook);
    }
}
