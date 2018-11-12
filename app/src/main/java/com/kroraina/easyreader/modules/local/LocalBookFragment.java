package com.kroraina.easyreader.modules.local;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.local.BookRepository;
import com.kroraina.easyreader.utils.media.MediaStoreHelper;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;

import butterknife.BindView;

/**
 * Created on 17-5-27.
 * 本地书籍
 */

public class LocalBookFragment extends BaseFileFragment {
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.local_book_rv_content)
    RecyclerView mRvContent;
    @Override
    protected int getContentId() {
        return R.layout.fragment_local_book;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    private void setUpAdapter(){
        mAdapter = new FileSystemAdapter(getContext());
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是已加载的文件，则点击事件无效。
                    String id = ((FileItem)mAdapter.getItem(pos)).bean.getAbsolutePath();
                    if (BookRepository.getInstance().getCollBook(id) != null){
                        return true;
                    }

                    //点击选中
                    mAdapter.setCheckedItem(pos);

                    //反馈
                    if (mListener != null){
                        mListener.onItemCheckedChange(mAdapter.getItemIsChecked(pos));
                    }
                    return true;
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        MediaStoreHelper.getAllBookFile(getActivity(),
                (files) -> {
                    if (files.isEmpty()){
                        mRlRefresh.showEmpty();
                    }
                    else {
                        mAdapter.removeBeans(files);
                        mRlRefresh.showFinish();
                        //反馈
                        if (mListener != null){
                            mListener.onCategoryChanged();
                        }
                    }
                });
    }
}
