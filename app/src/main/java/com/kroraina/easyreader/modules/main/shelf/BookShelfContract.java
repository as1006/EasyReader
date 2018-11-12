package com.kroraina.easyreader.modules.main.shelf;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.entity.CollBookBean;

import java.util.List;

/**
 * Created on 17-5-8.
 */

public interface BookShelfContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<CollBookBean> collBookBeans);
        void finishUpdate();
        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshCollBooks();
        void createDownloadTask(CollBookBean collBookBean);
        void updateCollBooks(List<CollBookBean> collBookBeans);
    }
}
