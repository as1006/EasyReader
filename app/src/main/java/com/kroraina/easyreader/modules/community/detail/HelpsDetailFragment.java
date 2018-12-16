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
import com.kroraina.easyreader.model.bean.CommentBean;
import com.kroraina.easyreader.modules.community.widget.BookTextView;
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



public class HelpsDetailFragment extends BaseMVPFragment<HelpsDetailContract.Presenter> implements HelpsDetailContract.View{
    private static final String TAG = "HelpsDetailFragment";
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
        Fragment fragment = new HelpsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected HelpsDetailContract.Presenter bindPresenter() {
        return new HelpsDetailPresenter();
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
        mCommentAdapter.setOnLoadMoreListener(
                () -> mPresenter.loadComment(mDetailId, start, limit)
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //获取数据啦
        mRefreshLayout.showLoading();
        mPresenter.refreshHelpsDetail(mDetailId,start,limit);
    }

    @Override
    public void finishRefresh(HelpsDetailBean helpsDetail,
                              List<CommentBean> bestComments,
                              List<CommentBean> comments) {
        //加载
        start = comments.size();
        mDetailHeader.setCommentDetail(helpsDetail);
        mDetailHeader.setGodCommentList(bestComments);
        mCommentAdapter.refreshItems(CommentItem.initFrom(getContext(),comments,false));
    }

    @Override
    public void finishLoad(List<CommentBean> comments) {
        start += comments.size();
        mCommentAdapter.addItems(CommentItem.initFrom(getContext(),comments,false));
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

    /***********************************************************************/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_DETAIL_ID, mDetailId);
    }

    @LegoItem
    public static class DetailHeaderItem extends BaseItem {

        BaseAdapter godCommentAdapter;
        HelpsDetailBean helpsDetailBean;
        List<CommentBean> godCommentList;

        public DetailHeaderItem(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.header_disc_detail;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
            ImageView ivPortrait = viewHolder.findViewById(R.id.disc_detail_iv_portrait);
            TextView tvName = viewHolder.findViewById(R.id.disc_detail_tv_name);
            TextView tvTime = viewHolder.findViewById(R.id.disc_detail_tv_time);
            TextView tvTitle = viewHolder.findViewById(R.id.disc_detail_tv_title);
            BookTextView btvContent = viewHolder.findViewById(R.id.disc_detail_btv_content);
            TextView tvBestComment = viewHolder.findViewById(R.id.disc_detail_tv_best_comment);
            RecyclerView rvBestComments = viewHolder.findViewById(R.id.disc_detail_rv_best_comments);
            TextView tvCommentCount = viewHolder.findViewById(R.id.disc_detail_tv_comment_count);

            if (helpsDetailBean == null || godCommentList == null){
                return;
            }
            //头像
            Glide.with(context)
                    .load(Constant.IMG_BASE_URL+ helpsDetailBean.getAuthor().getAvatar())
                    .placeholder(R.drawable.ic_loadding)
                    .error(R.drawable.ic_load_error)
                    .transform(new CircleTransform(context))
                    .into(ivPortrait);
            //名字
            tvName.setText(helpsDetailBean.getAuthor().getNickname());
            //日期
            tvTime.setText(StringUtils.dateConvert(helpsDetailBean.getCreated(),Constant.FORMAT_BOOK_DATE));
            //标题
            tvTitle.setText(helpsDetailBean.getTitle());
            //内容
            btvContent.setText(helpsDetailBean.getContent());
            //设置书籍的点击事件
            btvContent.setOnBookClickListener(
                    bookName -> {
                        Log.d(TAG, "onBindView: "+bookName);
                    }
            );
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
//            if (mCommentAdapter.getItems().isEmpty()){
//                tvCommentCount.setText(context.getResources().getString(R.string.nb_comment_empty_comment));
//            }
//            else {
//                CommentItem commentItem = (CommentItem) mCommentAdapter.getItems().get(0);
//                CommentBean firstComment = commentItem.bean;
//                //评论数
//                tvCommentCount.setText(context.getResources()
//                        .getString(R.string.nb_comment_comment_count,firstComment.getFloor()));
//            }
        }

        public void setCommentDetail(HelpsDetailBean bean){
            helpsDetailBean = bean;
        }

        public void setGodCommentList(List<CommentBean> beans){
            godCommentList = beans;
        }
    }

}


