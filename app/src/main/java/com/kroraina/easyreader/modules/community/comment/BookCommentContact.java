package com.kroraina.easyreader.modules.community.comment;

import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.base.mvp.BaseContract;

import java.util.List;

public interface BookCommentContact {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookCommentBean> beans);
        void finishLoading(List<BookCommentBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void firstLoading(String bookId, BookSort sort, int start, int limited);
        void refreshComment(String bookId, BookSort sort, int start, int limited);
        void loadingComment(String bookId, BookSort sort, int start, int limited);
    }
}
