package com.kroraina.easyreader.modules.local;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.model.local.BookRepository;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.utils.FileStack;
import com.kroraina.easyreader.utils.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;



public class FileCategoryFragment extends BaseFileFragment {
    private static final String TAG = "FileCategoryFragment";
    @BindView(R.id.file_category_tv_path)
    TextView mTvPath;
    @BindView(R.id.file_category_tv_back_last)
    TextView mTvBackLast;
    @BindView(R.id.file_category_rv_content)
    RecyclerView mRvContent;

    private FileStack mFileStack;
    @Override
    protected int getContentId() {
        return R.layout.fragment_file_category;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mFileStack = new FileStack();
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
                    FileItem item = (FileItem) mAdapter.getItem(pos);
                    File file = item.bean;
                    if (file.isDirectory()){
                        //保存当前信息。
                        FileStack.FileSnapshot snapshot = new FileStack.FileSnapshot();
                        snapshot.filePath = mTvPath.getText().toString();
                        snapshot.files = new ArrayList<File>(mAdapter.getBeans());
                        snapshot.scrollOffset = mRvContent.computeVerticalScrollOffset();
                        mFileStack.push(snapshot);
                        //切换下一个文件
                        toggleFileTree(file);
                    }
                    else {

                        //如果是已加载的文件，则点击事件无效。
                        String id = file.getAbsolutePath();
                        if (BookRepository.getInstance().getCollBook(id) != null){
                            return true;
                        }
                        //点击选中
                        mAdapter.setCheckedItem(pos);
                        //反馈
                        if (mListener != null){
                            mListener.onItemCheckedChange(mAdapter.getItemIsChecked(pos));
                        }
                    }
                    return true;
                }
        );

        mTvBackLast.setOnClickListener(
                (v) -> {
                    FileStack.FileSnapshot snapshot = mFileStack.pop();
                    int oldScrollOffset = mRvContent.computeHorizontalScrollOffset();
                    if (snapshot == null) return;
                    mTvPath.setText(snapshot.filePath);
                    mAdapter.refreshBeans(snapshot.files);
                    mRvContent.scrollBy(0,snapshot.scrollOffset - oldScrollOffset);
                    //反馈
                    if (mListener != null){
                        mListener.onCategoryChanged();
                    }
                }
        );

    }

    @Override
    protected void processLogic() {
        super.processLogic();
        File root = Environment.getExternalStorageDirectory();
        toggleFileTree(root);
    }

    private void toggleFileTree(File file){
        //路径名
        mTvPath.setText(getString(R.string.nb_file_path,file.getPath()));
        //获取数据
        File[] files = file.listFiles(new SimpleFileFilter());
        //转换成List
        List<File> rootFiles = Arrays.asList(files);
        //排序
        Collections.sort(rootFiles,new FileComparator());
        //加入
        mAdapter.refreshBeans(rootFiles);
        //反馈
        if (mListener != null){
            mListener.onCategoryChanged();
        }
    }

    public class FileComparator implements Comparator<File>{
        @Override
        public int compare(File o1, File o2){
            if (o1.isDirectory() && o2.isFile()) {
                return -1;
            }
            if (o2.isDirectory() && o1.isFile()) {
                return 1;
            }
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public class SimpleFileFilter implements FileFilter{
        @Override
        public boolean accept(File pathname) {
            if (pathname.getName().startsWith(".")){
                return false;
            }
            //文件夹内部数量为0
            if (pathname.isDirectory() && pathname.list().length == 0){
                return false;
            }

            /**
             * 现在只支持TXT文件的显示
             */
            //文件内容为空,或者不以txt为开头
            return pathname.isDirectory() ||
                    (pathname.length() != 0 && pathname.getName().endsWith(FileUtils.SUFFIX_TXT));
        }
    }
}
