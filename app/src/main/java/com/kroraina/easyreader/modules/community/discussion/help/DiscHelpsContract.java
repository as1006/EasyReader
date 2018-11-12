package com.kroraina.easyreader.modules.community.discussion.help;

import com.kroraina.easyreader.model.bean.BookHelpsBean;
import com.kroraina.easyreader.model.flag.BookDistillate;
import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.base.mvp.BaseContract;

import java.util.List;

public interface DiscHelpsContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookHelpsBean> beans);
        void finishLoading(List<BookHelpsBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void firstLoading(BookSort sort, int start, int limited, BookDistillate distillate);
        void refreshBookHelps(BookSort sort, int start, int limited, BookDistillate distillate);
        void loadingBookHelps(BookSort sort, int start,int limited,BookDistillate distillate);
    }
}
