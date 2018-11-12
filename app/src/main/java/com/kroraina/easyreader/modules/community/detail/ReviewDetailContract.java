package com.kroraina.easyreader.modules.community.detail;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.bean.CommentBean;
import com.kroraina.easyreader.model.bean.ReviewDetailBean;

import java.util.List;



public interface ReviewDetailContract {

    interface View extends BaseContract.BaseView{
        //全部加载的时候显示
        void finishRefresh(ReviewDetailBean reviewDetail,
                           List<CommentBean> bestComments, List<CommentBean> comments);
        void finishLoad(List<CommentBean> comments);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshReviewDetail(String detailId,int start,int limit);
        void loadComment(String detailId,int start,int limit);
    }
}
