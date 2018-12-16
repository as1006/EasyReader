package com.kroraina.easyreader.modules.search;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseMVPActivity;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.model.entity.SearchHistoryBean;
import com.kroraina.easyreader.model.local.BookRepository;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.xincubate.lego.adapter.core.BaseAdapter;

import java.util.List;

import butterknife.BindView;
import me.gujun.android.taggroup.TagGroup;

@ActivityUI(layoutId = R.layout.activity_search)
public class SearchActivity extends BaseMVPActivity<SearchContract.Presenter> implements SearchContract.View{

    private static final int TAG_LIMIT = 8;

    @BindView(R.id.search_iv_back)
    ImageView mIvBack;
    @BindView(R.id.search_et_input)
    EditText mEtInput;
    @BindView(R.id.search_iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.search_iv_search)
    ImageView mIvSearch;
    @BindView(R.id.search_book_tv_refresh_hot)
    TextView mTvRefreshHot;
    @BindView(R.id.search_tg_hot)
    TagGroup mTgHot;

    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvSearch;

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private BaseAdapter mKeyWordAdapter;
    private BaseAdapter mSearchAdapter;
    private BaseAdapter mHistoryAdapter;

    private boolean isTag;
    private List<String> mHotTagList;
    private int mTagStart = 0;

    @Override
    protected SearchContract.Presenter bindPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
        mRlRefresh.setBackground(ContextCompat.getDrawable(this,R.color.white));
    }

    private void setUpAdapter(){
        mKeyWordAdapter = new BaseAdapter(this);
        mSearchAdapter = new BaseAdapter(this);
        mHistoryAdapter = new BaseAdapter(this);

        mRvSearch.setLayoutManager(new LinearLayoutManager(this));
        mRvSearch.addItemDecoration(new DividerItemDecoration(this));

        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
        mRvHistory.addItemDecoration(new DividerItemDecoration(this));

        mHistoryAdapter.setOnItemClickListener((item, position) -> {
            if (item instanceof SearchHistoryItem){

                mRlRefresh.setVisibility(View.VISIBLE);
                mRlRefresh.showLoading();

                String searchKey = ((SearchHistoryItem) item).bean.getSearchKey();
                getMPresenter().searchBook(searchKey);
                insertSearchHistory(searchKey);

                toggleKeyboard();
            }
            return true;
        });

        mRvHistory.setAdapter(mHistoryAdapter);

        refreshHistory();
    }

    public void refreshHistory(){
        mHistoryAdapter.clear();

        List<SearchHistoryBean> searchHistoryBeans = BookRepository.getInstance().getSearchHistory();
        if (searchHistoryBeans != null){
            for (SearchHistoryBean bean : searchHistoryBeans){
                mHistoryAdapter.addItem(new SearchHistoryItem(this,bean));
            }
        }
        mHistoryAdapter.notifyDataSetChanged();
    }

    public void insertSearchHistory(String searchKey){
        //加入搜索历史
        SearchHistoryBean bean = new SearchHistoryBean(searchKey,System.currentTimeMillis());
        BookRepository.getInstance().saveSearchHistory(bean);
        refreshHistory();
    }

    @Override
    protected void initClick() {
        super.initClick();

        //退出
        mIvBack.setOnClickListener(
                (v) -> onBackPressed()
        );

        //输入框
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")){
                    //隐藏delete按钮和关键字显示内容
                    if (mIvDelete.getVisibility() == View.VISIBLE){
                        mIvDelete.setVisibility(View.INVISIBLE);
                        mRlRefresh.setVisibility(View.INVISIBLE);
                        //删除全部视图
                        mKeyWordAdapter.clear();
                        mSearchAdapter.clear();
                        mRvSearch.removeAllViews();
                    }
                    return;
                }
                //显示delete按钮
                if (mIvDelete.getVisibility() == View.INVISIBLE){
                    mIvDelete.setVisibility(View.VISIBLE);
                    mRlRefresh.setVisibility(View.VISIBLE);
                    //默认是显示完成状态
                    mRlRefresh.showFinish();
                }
                //搜索
                String query = s.toString().trim();
                if (isTag){
                    mRlRefresh.showLoading();
                    getMPresenter().searchBook(query);
                    insertSearchHistory(query);
                    isTag = false;
                }
                else {
                    //传递
                    getMPresenter().searchKeyWord(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //键盘的搜索
        mEtInput.setOnKeyListener((v, keyCode, event) -> {
            //修改回车键功能
            if(keyCode==KeyEvent.KEYCODE_ENTER) {
                searchBook();
                return true;
            }
            return false;
        });

        //进行搜索
        mIvSearch.setOnClickListener(
                (v) -> searchBook()
        );

        //删除字
        mIvDelete.setOnClickListener(
                (v) ->  {
                    mEtInput.setText("");
                    toggleKeyboard();
                }
        );

        //点击查书
        mKeyWordAdapter.setOnItemClickListener(
                (item, pos) -> {
                    if (item instanceof KeyWordItem){
                        mRlRefresh.showLoading();
                        getMPresenter().searchBook(((KeyWordItem) item).keyword);
                        insertSearchHistory(((KeyWordItem) item).keyword);
                        toggleKeyboard();
                    }
                    return true;
                }
        );

        //Tag的点击事件
        mTgHot.setOnTagClickListener(
                tag -> {
                    isTag = true;
                    mEtInput.setText(tag);
                }
        );

        //Tag的刷新事件
        mTvRefreshHot.setOnClickListener(
                (v) -> refreshTag()
        );
    }

    private void searchBook(){
        String query = mEtInput.getText().toString().trim();
        if(!query.equals("")){
            //开始搜索
            mRlRefresh.setVisibility(View.VISIBLE);
            mRlRefresh.showLoading();
            getMPresenter().searchBook(query);
            insertSearchHistory(query);
            //显示正在加载
            mRlRefresh.showLoading();
            toggleKeyboard();
        }
    }

    private void toggleKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //默认隐藏
        mRlRefresh.setVisibility(View.GONE);
        //获取热词
        getMPresenter().searchHotWord();
    }

    @Override
    public void showError() {
    }

    @Override
    public void complete() {

    }

    @Override
    public void finishHotWords(List<String> hotWords) {
        mHotTagList = hotWords;
        refreshTag();
    }

    private void refreshTag(){
        int last = mTagStart + TAG_LIMIT;
        if (mHotTagList.size() <= last){
            mTagStart = 0;
            last = TAG_LIMIT;
        }
        List<String> tags = mHotTagList.subList(mTagStart, last);
        mTgHot.setTags(tags);
        mTagStart += TAG_LIMIT;
    }

    @Override
    public void finishKeyWords(List<String> keyWords) {
        if (keyWords.size() == 0) mRlRefresh.setVisibility(View.INVISIBLE);

        mKeyWordAdapter.refreshItems(KeyWordItem.initForStringArray(this,keyWords));
        if (mRvSearch.getAdapter() != mKeyWordAdapter){
            mRvSearch.setAdapter(mKeyWordAdapter);
        }
    }

    @Override
    public void finishBooks(List<SearchBookPackage.BooksBean> books) {
        mSearchAdapter.refreshItems(SearchBookItem.initFromBean(this,books));
        if (books.size() == 0){
            mRlRefresh.showEmpty();
        }
        else {
            //显示完成
            mRlRefresh.showFinish();
        }
        //加载
        if (mRvSearch.getAdapter() != mSearchAdapter){
            mRvSearch.setAdapter(mSearchAdapter);
        }
    }

    @Override
    public void errorBooks() {
        mRlRefresh.showEmpty();
    }

    @Override
    public void onBackPressed() {
        if (mRlRefresh.getVisibility() == View.VISIBLE){
            mEtInput.setText("");
            mRlRefresh.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
