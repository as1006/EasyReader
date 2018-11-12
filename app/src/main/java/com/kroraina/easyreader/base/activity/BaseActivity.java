package com.kroraina.easyreader.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.utils.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    private static final int INVALID_VAL = -1;

    protected CompositeDisposable mDisposable;
    //ButterKnife
    private Toolbar mToolbar;

    private Unbinder unbinder;
    /****************************abstract area*************************************/

    @LayoutRes
    protected int getContentId(){return 0;}

    /************************init area************************************/
    protected void addDisposable(Disposable d){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     * @param toolbar
     */
    protected void setUpToolbar(Toolbar toolbar){

    }

    protected void initData(Bundle savedInstanceState){
    }
    /**
     * 初始化零件
     */
    protected void initWidget() {

    }
    /**
     * 初始化点击事件
     */
    protected void initClick(){
    }
    /**
     * 逻辑使用区
     */
    protected void processLogic(){
    }

    /*************************lifecycle area*****************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUI activityUI = this.getClass().getAnnotation(ActivityUI.class);
        if (activityUI != null && activityUI.layoutId() != 0){
            setContentView(activityUI.layoutId());
        }else {
            setContentView(getContentId());
        }


        initData(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        initStatusBar();
        initToolbar();
        initWidget();
        initClick();
        processLogic();

    }

    private void initStatusBar(){
        StatusBarCompat.compat(this,ContextCompat.getColor(this, R.color.main_color));
    }

    private void initToolbar(){
        //更严谨是通过反射判断是否存在Toolbar
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar != null){
            processNavigationBarAnnotations();
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }

    private void processNavigationBarAnnotations(){
        NavigationBar navigationBar = getClass().getAnnotation(NavigationBar.class);
        if (navigationBar != null){
            mToolbar.setVisibility(View.VISIBLE);
            if (navigationBar.titleResId() != 0){
                mToolbar.setTitle(navigationBar.titleResId());
            }else {
                mToolbar.setTitle(navigationBar.title());
            }
        }else {
            //mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDisposable != null){
            mDisposable.dispose();
        }
    }

    /**************************used method area*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    protected ActionBar supportActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }

    protected void setStatusBarColor(int statusColor){
        StatusBarCompat.compat(this, ContextCompat.getColor(this, statusColor));
    }
}
