package com.kroraina.easyreader.modules.rank;

import com.kroraina.easyreader.base.mvp.BaseContract;



public interface BillboardContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BillboardPackage beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadBillboardList();
    }
}
