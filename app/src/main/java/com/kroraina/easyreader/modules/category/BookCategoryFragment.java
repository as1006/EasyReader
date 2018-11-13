package com.kroraina.easyreader.modules.category;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easyapp.lego.adapter.core.BaseAdapter;
import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.fragment.BaseFragment;
import com.kroraina.easyreader.model.bean.BookSubSortBean;
import com.kroraina.easyreader.model.bean.packages.BookSubSortPackage;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.modules.category.detail.BookSortListActivity;
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerGridItemDecoration;
import com.kroraina.easyreader.ui.widget.refresh.RefreshLayout;
import com.kroraina.easyreader.utils.LogUtils;

import butterknife.BindView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 发现->分类
 *
 */

public class BookCategoryFragment extends BaseFragment implements BookSortContract.View{
    /*******************Constant*********************/
    public static final String KEY_IS_MALE = "KEY_IS_MALE";
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.book_sort_rl_refresh)
    RefreshLayout mRlRefresh;
    @BindView(R.id.rv_book_category)
    RecyclerView mRvBoy;

    private BaseAdapter mBookAdapter;

    private BookSubSortPackage mSubSortPackage;
    /**********************init***********************************/
    @Override
    protected int getContentId() {
        return R.layout.fragment_book_category;
    }

    private boolean mIsMale = true;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mIsMale = getArguments().getBoolean(KEY_IS_MALE);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    private void setUpAdapter(){
        mBookAdapter = new BaseAdapter();

        RecyclerView.ItemDecoration itemDecoration = new DividerGridItemDecoration(getContext(),R.drawable.shape_divider_row,R.drawable.shape_divider_col);

        mRvBoy.setLayoutManager(new GridLayoutManager(getContext(),SPAN_COUNT));
        mRvBoy.addItemDecoration(itemDecoration);
        mRvBoy.setAdapter(mBookAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBookAdapter.setOnItemClickListener(
                (view,pos) -> {
                    if (mIsMale){
                        BookSubSortBean subSortBean = mSubSortPackage.getMale().get(pos);
                        //上传
                        BookSortListActivity.startActivity(getContext(),"male",subSortBean);
                    }else {
                        BookSubSortBean subSortBean = mSubSortPackage.getFemale().get(pos);
                        //上传
                        BookSortListActivity.startActivity(getContext(),"female",subSortBean);
                    }

                    return true;
                }
        );
    }

    /*********************logic*******************************/

    @Override
    protected void processLogic() {
        super.processLogic();

        mRlRefresh.showLoading();
        refreshSortBean();
    }

    private void refreshSortBean(){
        Single<BookSortPackage> sortSingle = RemoteRepository.getInstance()
                .getBookSortPackage();
        Single<BookSubSortPackage> subSortSingle = RemoteRepository.getInstance()
                .getBookSubSortPackage();

        Single<SortPackage> zipSingle =  Single.zip(sortSingle, subSortSingle,
                (bookSortPackage, subSortPackage) -> new SortPackage(bookSortPackage,subSortPackage));

        Disposable disposable = zipSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bean) ->{
                            finishRefresh(bean.sortPackage,bean.subSortPackage);
                            complete();
                        }
                        ,
                        (e) -> {
                            showError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(disposable);
    }

    /***********************rewrite**********************************/
    @Override
    public void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage) {
        if (sortPackage == null || sortPackage.getMale().size() == 0 || sortPackage.getFemale().size() == 0){
            mRlRefresh.showEmpty();
        }
        else {
            if (mIsMale){
                mBookAdapter.refreshItems(BookCategoryItem.initFromBookSortBeans(getContext(),sortPackage.getMale()));
            }else {
                mBookAdapter.refreshItems(BookCategoryItem.initFromBookSortBeans(getContext(),sortPackage.getFemale()));
            }
        }
        mSubSortPackage = subSortPackage;
    }

    @Override
    public void showError() {
        mRlRefresh.showError();
    }

    @Override
    public void complete() {
        mRlRefresh.showFinish();
    }


    class SortPackage{
        BookSortPackage sortPackage;
        BookSubSortPackage subSortPackage;

        public SortPackage(BookSortPackage sortPackage, BookSubSortPackage subSortPackage){
            this.sortPackage = sortPackage;
            this.subSortPackage = subSortPackage;
        }
    }
}
