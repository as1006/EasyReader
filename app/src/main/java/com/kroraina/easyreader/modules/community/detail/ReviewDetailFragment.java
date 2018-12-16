package com.kroraina.easyreader.modules.community.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.fragment.BaseMVPFragment;
import com.kroraina.easyreader.model.bean.AuthorBean;
import com.kroraina.easyreader.model.bean.BookHelpfulBean;
import com.kroraina.easyreader.model.bean.CommentBean;
import com.kroraina.easyreader.model.bean.ReviewBookBean;
import com.kroraina.easyreader.model.bean.ReviewDetailBean;
import com.kroraina.easyreader.modules.community.widget.BookTextView;
import com.kroraina.easyreader.ui.widget.EasyRatingBar;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.kroraina.easyreader.ui.widget.transform.CircleTransform;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.StringUtils;
import com.xincubate.lego.adapter.core.BaseAdapter;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.adapter.load.LoadMoreAdapter;
import com.xincubate.lego.annotation.LegoItem;

import java.util.List;

import butterknife.BindView;



public class ReviewDetailFragment extends BaseMVPFragment<ReviewDetailContract.Presenter>
        implements ReviewDetailContract.View{
    private static final String TAG = "ReviewDetailFragment";
    private static final String EXTRA_DETAIL_ID = "extra_detail_id";
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /***********************************/
    private LoadMoreAdapter mCommentAdapter;
    private DetailHeaderItem mDetailHeader;
    /***********params****************/
    private String mDetailId;
    private int start = 0;
    private int limit = 30;

    public static Fragment newInstance(String detailId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DETAIL_ID,detailId);
        Fragment fragment = new ReviewDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected ReviewDetailContract.Presenter bindPresenter() {
        return new ReviewDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mDetailId = savedInstanceState.getString(EXTRA_DETAIL_ID);
        }else {
            mDetailId = getArguments().getString(EXTRA_DETAIL_ID);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView(){
        mCommentAdapter = new LoadMoreAdapter(getActivity());
        mDetailHeader = new DetailHeaderItem(getContext());
        mCommentAdapter.addHeaderItem(mDetailHeader);

        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mCommentAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
//        mCommentAdapter.setOnLoadMoreListener(
//                () ->   mPresenter.loadComment(mDetailId, start, limit)
//        );

        mCommentAdapter.setOnLoadMoreListener(() -> mPresenter.loadComment(mDetailId,start,limit));
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //获取数据啦
        mRefreshLayout.showLoading();
        mPresenter.refreshReviewDetail(mDetailId,start,limit);
    }

    @Override
    public void finishRefresh(ReviewDetailBean reviewDetail,
                              List<CommentBean> bestComments, List<CommentBean> comments) {
        start = comments.size();
        mDetailHeader.setCommentDetail(reviewDetail);
        mDetailHeader.setGodCommentList(bestComments);
        mCommentAdapter.refreshItems(CommentItem.initFrom(getContext(),comments,false));
    }

    @Override
    public void finishLoad(List<CommentBean> comments) {
        mCommentAdapter.addItems(CommentItem.initFrom(getContext(),comments,false));
        start += comments.size();
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void showLoadError() {
        mCommentAdapter.showLoadError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    @LegoItem
    public static class DetailHeaderItem extends BaseItem {

        BaseAdapter godCommentAdapter;
        ReviewDetailBean reviewDetailBean;
        List<CommentBean> godCommentList;

        public DetailHeaderItem(Context context) {
            super(context);
        }

        public void setCommentDetail(ReviewDetailBean bean){
            reviewDetailBean = bean;
        }

        public void setGodCommentList(List<CommentBean> beans){
            godCommentList = beans;
        }

        @Override
        public int getLayoutId() {
            return R.layout.header_disc_review_detail;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {

            ImageView ivPortrait = viewHolder.findViewById(R.id.disc_detail_iv_portrait);
            TextView tvName = viewHolder.findViewById(R.id.disc_detail_tv_name);
            TextView tvTime = viewHolder.findViewById(R.id.disc_detail_tv_time);
            TextView tvTitle = viewHolder.findViewById(R.id.disc_detail_tv_title);
            BookTextView btvContent = viewHolder.findViewById(R.id.disc_detail_btv_content);
            ImageView ivBookCover = viewHolder.findViewById(R.id.review_detail_iv_book_cover);
            TextView tvBookName = viewHolder.findViewById(R.id.review_detail_tv_book_name);
            EasyRatingBar erbBookRate = viewHolder.findViewById(R.id.review_detail_erb_rate);
            TextView tvHelpfulCount = viewHolder.findViewById(R.id.review_detail_tv_helpful_count);
            TextView tvUnhelpfulCount = viewHolder.findViewById(R.id.review_detail_tv_unhelpful_count);
            TextView tvBestComment = viewHolder.findViewById(R.id.disc_detail_tv_best_comment);
            RecyclerView rvBestComments = viewHolder.findViewById(R.id.disc_detail_rv_best_comments);
            TextView tvCommentCount = viewHolder.findViewById(R.id.disc_detail_tv_comment_count);


            //如果没有值就直接返回
            if (reviewDetailBean == null || godCommentList == null){
                return;
            }

            AuthorBean authorBean = reviewDetailBean.getAuthor();
            //头像
            Glide.with(context)
                    .load(Constant.IMG_BASE_URL+ authorBean.getAvatar())
                    .placeholder(R.drawable.ic_loadding)
                    .error(R.drawable.ic_load_error)
                    .transform(new CircleTransform(context))
                    .into(ivPortrait);
            //名字
            tvName.setText(authorBean.getNickname());
            //日期
            tvTime.setText(StringUtils.dateConvert(reviewDetailBean.getCreated(),
                    Constant.FORMAT_BOOK_DATE));
            //标题
            tvTitle.setText(reviewDetailBean.getTitle());
            //内容
            btvContent.setText(reviewDetailBean.getContent());
            //设置书籍的点击事件
            btvContent.setOnBookClickListener(
                    bookName -> {
                        Log.d(TAG, "onBindView: "+bookName);
                    }
            );
            ReviewBookBean bookBean = reviewDetailBean.getBook();
            //书籍封面
            Glide.with(context)
                    .load(Constant.IMG_BASE_URL+ bookBean.getCover())
                    .placeholder(R.drawable.ic_book_loading)
                    .error(R.drawable.ic_load_error)
                    .fitCenter()
                    .into(ivBookCover);
            //书名
            tvBookName.setText(bookBean.getTitle());
            //对书的打分
            erbBookRate.setRating(reviewDetailBean.getRating());
            //帮助度
            BookHelpfulBean bookHelpfulBean = reviewDetailBean.getHelpful();
            //有用
            tvHelpfulCount.setText(bookHelpfulBean.getYes()+"");
            //没用
            tvUnhelpfulCount.setText(bookHelpfulBean.getNo()+"");
            //设置神评论
            if (godCommentList.isEmpty()) {
                tvBestComment.setVisibility(View.GONE);
                rvBestComments.setVisibility(View.GONE);
            }
            else {
                tvBestComment.setVisibility(View.VISIBLE);
                rvBestComments.setVisibility(View.VISIBLE);
                //初始化RecyclerView
                if (godCommentAdapter == null) {
                    godCommentAdapter = new BaseAdapter(context);
                    rvBestComments.setLayoutManager(new LinearLayoutManager(context));
                    rvBestComments.addItemDecoration(new DividerItemDecoration(context));
                    rvBestComments.setAdapter(godCommentAdapter);
                }
                godCommentAdapter.refreshItems(CommentItem.initFrom(context,godCommentList,true));
            }

            //暂时注释
            //设置评论数
//            if (mCommentAdapter.getItems().isEmpty()){
//                tvCommentCount.setText(context.getResources().getString(R.string.nb_comment_empty_comment));
//            }
//            else {
//                CommentItem commentItem = (CommentItem) mCommentAdapter.getItems().get(0);
//                CommentBean firstComment = commentItem.bean;
//                tvCommentCount.setText(context.getResources()
//                        .getString(R.string.nb_comment_comment_count,firstComment.getFloor()));
//            }
        }


    }
}
