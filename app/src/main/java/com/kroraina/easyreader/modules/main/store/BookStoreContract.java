package com.kroraina.easyreader.modules.main.store;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.bean.BookListBean;
import com.kroraina.easyreader.model.entity.CollBookBean;
import com.kroraina.easyreader.modules.category.detail.SortBookBean;

import java.util.List;

public interface BookStoreContract {

    interface View extends BaseContract.BaseView{
        void showErrorTip(String error);

        void finishLoadRecommendTopicBooks(List<SortBookBean> bookSortBeans);
        void finishLoadRecommendHotBookSheet(List<BookListBean> bookListBeans);

        void finishLoadRecommendBooks(List<CollBookBean> collBookBeans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadRecommendTopicBooks();
        void loadRecommendHotBookSheet();
        void loadRecommendBooks(String gender);
    }
}
