package com.kroraina.easyreader.modules.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.utils.FileUtils;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

import java.util.ArrayList;
import java.util.List;



@LegoItem
public class DownloadItem extends BaseItem {

    public static List<DownloadItem> initFrom(Context context, List<DownloadTaskBean> beans){
        List<DownloadItem> results = new ArrayList<>();
        for (DownloadTaskBean bean : beans){
            results.add(new DownloadItem(context,bean));
        }
        return results;
    }

    public DownloadTaskBean bean;
    public DownloadItem(Context context,DownloadTaskBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_download;
    }

    private void changeBtnStyle(TextView textView, ImageView imageView, int strRes, int colorRes, int drawableRes){
        //按钮状态
        if (!textView.getText().equals(
                context.getResources().getString(strRes))){
            textView.setText(context.getResources().getString(strRes));
            textView.setTextColor(context.getResources().getColor(colorRes));
            imageView.setImageResource(drawableRes);
        }
    }

    private void setProgressMax(ProgressBar progressBar,DownloadTaskBean value){
        if (progressBar.getMax() != value.getBookChapters().size()){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(value.getBookChapters().size());
        }
    }

    //提示
    private void setTip(TextView textView,int strRes){
        if (!textView.getText().equals(context.getResources().getString(strRes))){
            textView.setText(context.getResources().getString(strRes));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        TextView mTvTitle = viewHolder.findViewById(R.id.download_tv_title);
        TextView mTvMsg = viewHolder.findViewById(R.id.download_tv_msg);
        TextView mTvTip = viewHolder.findViewById(R.id.download_tv_tip);
        ProgressBar mPbShow = viewHolder.findViewById(R.id.download_pb_show);
        RelativeLayout mRlToggle = viewHolder.findViewById(R.id.download_rl_toggle);
        ImageView mIvStatus = viewHolder.findViewById(R.id.download_iv_status);
        TextView mTvStatus = viewHolder.findViewById(R.id.download_tv_status);

        if (!mTvTitle.getText().equals(bean.getTaskName())){
            mTvTitle.setText(bean.getTaskName());
        }

        switch (bean.getStatus()){
            case DownloadTaskBean.STATUS_LOADING:
                changeBtnStyle(mTvStatus,mIvStatus,R.string.nb_download_pause,
                        R.color.nb_download_pause,R.drawable.ic_download_pause);

                //进度状态
                setProgressMax(mPbShow,bean);
                mPbShow.setProgress(bean.getCurrentChapter());

                setTip(mTvTip,R.string.nb_download_loading);

                mTvMsg.setText(context.getResources().getString(R.string.nb_download_progress,
                        bean.getCurrentChapter(),bean.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_PAUSE:
                //按钮状态
                changeBtnStyle(mTvStatus,mIvStatus,R.string.nb_download_start,
                        R.color.nb_download_loading,R.drawable.ic_download_loading);

                //进度状态
                setProgressMax(mPbShow,bean);
                setTip(mTvTip,R.string.nb_download_pausing);

                mPbShow.setProgress(bean.getCurrentChapter());
                mTvMsg.setText(context.getResources().getString(R.string.nb_download_progress,
                        bean.getCurrentChapter(),bean.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_WAIT:
                //按钮状态
                changeBtnStyle(mTvStatus,mIvStatus,R.string.nb_download_wait,
                        R.color.nb_download_wait,R.drawable.ic_download_wait);

                //进度状态
                setProgressMax(mPbShow,bean);
                setTip(mTvTip,R.string.nb_download_waiting);

                mPbShow.setProgress(bean.getCurrentChapter());
                mTvMsg.setText(context.getResources().getString(R.string.nb_download_progress,
                        bean.getCurrentChapter(),bean.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_ERROR:
                //按钮状态
                changeBtnStyle(mTvStatus,mIvStatus,R.string.nb_download_error,
                        R.color.nb_download_error,R.drawable.ic_download_error);
                setTip(mTvTip,R.string.nb_download_source_error);
                mPbShow.setVisibility(View.INVISIBLE);
                mTvMsg.setVisibility(View.GONE);
                break;
            case DownloadTaskBean.STATUS_FINISH:
                //按钮状态
                changeBtnStyle(mTvStatus,mIvStatus,R.string.nb_download_finish,
                        R.color.nb_download_finish,R.drawable.ic_download_complete);
                setTip(mTvTip,R.string.nb_download_complete);
                mPbShow.setVisibility(View.INVISIBLE);

                //设置文件大小
                mTvMsg.setText(FileUtils.getFileSize(bean.getSize()));
                break;
        }
    }
}
