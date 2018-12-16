package com.kroraina.easyreader.modules.sheetlist;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.model.bean.BookTagBean;
import com.kroraina.easyreader.model.remote.RemoteRepository;
import com.kroraina.easyreader.base.activity.BaseTabActivity;
import com.kroraina.easyreader.event.BookSubSortEvent;
import com.kroraina.easyreader.event.TagEvent;
import com.kroraina.easyreader.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@NavigationBar(titleResId = R.string.nb_fragment_find_topic)
@ActivityUI(layoutId = R.layout.activity_book_list)
public class BookSheetListActivity extends BaseTabActivity {

    private static final int RANDOM_COUNT = 5;

    @BindView(R.id.book_list_rv_tag_horizon)
    RecyclerView mRvTag;
    @BindView(R.id.book_list_cb_filter)
    CheckBox mCbFilter;
    @BindView(R.id.book_list_rv_tag_filter)
    RecyclerView mRvFilter;

    /*************************************/
    private HorizontalTagAdapter mHorizonTagAdapter;
    private TagGroupAdapter mTagGroupAdapter;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    /************Params*******************/
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>(BookListType.values().length);
        for (BookListType type : BookListType.values()){
            fragments.add(BookSheetListFragment.newInstance(type));
        }
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        List<String> titles = new ArrayList<>(BookListType.values().length);
        for (BookListType type : BookListType.values()){
            titles.add(type.getTypeName());
        }
        return titles;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initTag();
    }

    private void initTag(){
        //横向的
        mHorizonTagAdapter = new HorizontalTagAdapter(this);
        LinearLayoutManager tagManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRvTag.setLayoutManager(tagManager);
        mRvTag.setAdapter(mHorizonTagAdapter);

        mTagGroupAdapter = new TagGroupAdapter(this);

        //筛选框
        int spanCount = 4;
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        layoutManager.setSpanSizeLookup(mTagGroupAdapter.new GroupSpanSizeLookup(spanCount));
        mRvFilter.setLayoutManager(layoutManager);



        mRvFilter.setAdapter(mTagGroupAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();


        //筛选
        mCbFilter.setOnCheckedChangeListener(
                (btn,checked)->{
                    if (mTopInAnim == null || mTopOutAnim == null){
                        mTopInAnim = AnimationUtils.loadAnimation(this,R.anim.slide_top_in);
                        mTopOutAnim = AnimationUtils.loadAnimation(this,R.anim.slide_top_out);
                    }

                    if (checked){
                        mRvFilter.setVisibility(View.VISIBLE);
                        mRvFilter.startAnimation(mTopInAnim);
                    }
                    else {
                        mRvFilter.startAnimation(mTopOutAnim);
                        mRvFilter.setVisibility(View.GONE);
                    }
                }
        );

        Disposable tagDisp = RxBus.getInstance()
                .toObservable(TagEvent.class)
                .subscribe(
                        event -> {
                            List<String> tags =  mHorizonTagAdapter.getBeans();
                            boolean isExist = false;
                            for (int i=0; i<tags.size(); ++i){
                                if (event.tag.equals(tags.get(i))){
                                    mHorizonTagAdapter.setCurrentSelected(i);
                                    mRvTag.getLayoutManager().scrollToPosition(i);
                                    isExist = true;
                                }
                            }
                            if (!isExist){
                                //添加到1的位置,保证全本的位置
                                mHorizonTagAdapter.addItem(1,new HorizontalTagItem(this,event.tag));
                                mHorizonTagAdapter.setCurrentSelected(1);
                                mHorizonTagAdapter.notifyDataSetChanged();
                                mRvTag.getLayoutManager().scrollToPosition(1);
                                RxBus.getInstance().post(new BookSubSortEvent(event.tag));
                            }
                            mCbFilter.setChecked(false);
                        }
                );
        addDisposable(tagDisp);
    }


    @Override
    protected void processLogic() {
        super.processLogic();
        refreshTag();
    }

    private void refreshTag(){
        Disposable refreshDispo = RemoteRepository.getInstance()
                .getBookTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (tagBeans)-> {
                            refreshHorizonTag(tagBeans);
                            refreshGroupTag(tagBeans);
                        },
                        (e) ->{
                            LogUtils.e(e);
                        }
                );
        mDisposable.add(refreshDispo);
    }

    private void refreshHorizonTag(List<BookTagBean> tagBeans){
        List<String> randomTag = new ArrayList<>(RANDOM_COUNT);
        randomTag.add("全本");
        int caculator = 0;
        //随机获取4,5个。
        final int tagBeanCount = tagBeans.size();
        for (int i=0; i<tagBeanCount; ++i){
            List<String> tags = tagBeans.get(i).getTags();
            final int tagCount = tags.size();
            for (int j=0; j<tagCount; ++j){
                if (caculator < RANDOM_COUNT){
                    randomTag.add(tags.get(j));
                    ++caculator;
                }
                else {
                    break;
                }
            }
            if (caculator >= RANDOM_COUNT){
                break;
            }
        }
        mHorizonTagAdapter.addItemByBeans(this,randomTag);
        mHorizonTagAdapter.notifyDataSetChanged();
    }

    private void refreshGroupTag(List<BookTagBean> tagBeans){
        //由于数据还有根据性别分配，所以需要加上去
        BookTagBean bean = new BookTagBean();
        bean.setName(getResources().getString(R.string.nb_tag_sex));
        bean.setTags(Arrays.asList(getResources().getString(R.string.nb_tag_boy),getResources().getString(R.string.nb_tag_girl)));
        tagBeans.add(0,bean);

        mTagGroupAdapter.refreshItems(this,tagBeans);
    }
}
