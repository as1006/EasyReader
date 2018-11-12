package com.kroraina.easyreader.modules.sheetlist.detail;

import com.kroraina.easyreader.base.mvp.BaseContract;

public interface BookListDetailContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookListDetailBean bean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookListDetail(String detailId);
    }
}
