package com.easyapp.lego.adapter.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class BaseItem {

    protected Context context;

    public BaseItem(Context context){
        this.context = context;
    }

    public int getItemViewType(){
        return 0;
    }

    public abstract void onBindViewHolder(@NonNull BaseViewHolder viewHolder);
    public int getLayoutId(){return 0;}
    public void onClick(){}


    /***************经常用到的Utils方法***********************/
    public void startActivity(Class<? extends Activity> clazz){
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
