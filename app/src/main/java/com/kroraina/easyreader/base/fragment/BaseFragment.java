package com.kroraina.easyreader.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kroraina.easyreader.base.annotations.FragmentUI;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment{

    protected CompositeDisposable mDisposable;

    private View root = null;
    private Unbinder unbinder;

    @LayoutRes
    protected int getContentId(){return 0;}

    /*******************************init area*********************************/
    protected void addDisposable(Disposable d){
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }


    protected void initData(Bundle savedInstanceState){
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

    /**
     * 初始化零件
     */
    protected void initWidget(Bundle savedInstanceState){
    }

    /******************************lifecycle area*****************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId;
        FragmentUI fragmentUI = getClass().getAnnotation(FragmentUI.class);
        if (fragmentUI != null && fragmentUI.layoutId() != 0){
            resId = fragmentUI.layoutId();
        }else {
            resId = getContentId();
        }
        root = inflater.inflate(resId,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
        unbinder = ButterKnife.bind(this,root);
        initWidget(savedInstanceState);
        initClick();
        processLogic();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        unbinder.unbind();

        if (mDisposable != null){
            mDisposable.clear();
        }
    }

    /**************************公共类*******************************************/
    public String getName(){
        return getClass().getName();
    }

    protected <VT> VT getViewById(int id){
        if (root == null){
            return  null;
        }
        return (VT) root.findViewById(id);
    }
}


