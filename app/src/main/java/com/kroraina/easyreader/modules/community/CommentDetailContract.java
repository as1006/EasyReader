package com.kroraina.easyreader.modules.community;

import com.kroraina.easyreader.base.mvp.BaseContract;
import com.kroraina.easyreader.model.bean.CommentBean;
import com.kroraina.easyreader.model.bean.CommentDetailBean;

import java.util.List;



public interface CommentDetailContract {

    interface View extends BaseContract.BaseView{
        //全部加载的时候显示
        void finishRefresh(CommentDetailBean commentDetail,
                           List<CommentBean> bestComments, List<CommentBean> comments);
        void finishLoad(List<CommentBean> comments);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshCommentDetail(String detailId,int start,int limit);
        void loadComment(String detailId,int start,int limit);
    }
}
