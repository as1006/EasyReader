package com.kroraina.easyreader.modules.rank.detail;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.bean.BillBookBean;

import java.util.List;



public interface BillBookContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BillBookBean> beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookBrief(String billId);
    }
}
