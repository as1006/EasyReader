package com.kroraina.easyreader.modules.book.read;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.entity.BookChapterBean;
import com.kroraina.easyreader.ui.widget.page.TxtChapter;

import java.util.List;



public interface ReadContract extends BaseContract{
    interface View extends BaseContract.BaseView {
        void showCategory(List<BookChapterBean> bookChapterList);
        void finishChapter();
        void errorChapter();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadCategory(String bookId);
        void loadChapter(String bookId,List<TxtChapter> bookChapterList);
    }
}
