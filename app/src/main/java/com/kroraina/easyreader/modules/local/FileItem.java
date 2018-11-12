package com.kroraina.easyreader.modules.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;
import com.kroraina.easyreader.model.local.BookRepository;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.FileUtils;
import com.kroraina.easyreader.utils.MD5Utils;
import com.kroraina.easyreader.utils.StringUtils;

import java.io.File;
import java.util.HashMap;


@LayoutId(R.layout.item_file)
public class FileItem extends BaseItem {

    private HashMap<File,Boolean> mSelectedMap;
    public File bean;
    public FileItem(Context context,HashMap<File, Boolean> selectedMap,File bean){
        super(context);
        this.bean = bean;
        mSelectedMap = selectedMap;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder) {
        ImageView mIvIcon = viewHolder.findViewById(R.id.file_iv_icon);
        CheckBox mCbSelect = viewHolder.findViewById(R.id.file_cb_select);
        TextView mTvName = viewHolder.findViewById(R.id.file_tv_name);
        LinearLayout mLlBrief = viewHolder.findViewById(R.id.file_ll_brief);
        TextView mTvTag = viewHolder.findViewById(R.id.file_tv_tag);
        TextView mTvSize = viewHolder.findViewById(R.id.file_tv_size);
        TextView mTvDate = viewHolder.findViewById(R.id.file_tv_date);
        TextView mTvSubCount = viewHolder.findViewById(R.id.file_tv_sub_count);

        //判断是文件还是文件夹
        if (bean.isDirectory()){
            //图片
            mIvIcon.setVisibility(View.VISIBLE);
            mCbSelect.setVisibility(View.GONE);
            mIvIcon.setImageResource(R.drawable.ic_dir);
            //名字
            mTvName.setText(bean.getName());
            //介绍
            mLlBrief.setVisibility(View.GONE);
            mTvSubCount.setVisibility(View.VISIBLE);

            mTvSubCount.setText(context.getString(R.string.nb_file_sub_count,bean.list().length));
        } else {
            //选择
            String id = MD5Utils.strToMd5By16(bean.getAbsolutePath());

            if (BookRepository.getInstance().getCollBook(id) != null){
                mIvIcon.setImageResource(R.drawable.ic_file_loaded);
                mIvIcon.setVisibility(View.VISIBLE);
                mCbSelect.setVisibility(View.GONE);
            }
            else {
                boolean isSelected = mSelectedMap.get(bean);
                mCbSelect.setChecked(isSelected);
                mIvIcon.setVisibility(View.GONE);
                mCbSelect.setVisibility(View.VISIBLE);
            }

            mLlBrief.setVisibility(View.VISIBLE);
            mTvSubCount.setVisibility(View.GONE);

            mTvName.setText(bean.getName());
            mTvSize.setText(FileUtils.getFileSize(bean.length()));
            mTvDate.setText(StringUtils.dateConvert(bean.lastModified(), Constant.FORMAT_FILE_DATE));
        }
    }
}
