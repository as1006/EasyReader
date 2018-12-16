package com.xincubate.lego.adapter.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.xincubate.lego.adapter.bridge.BridgeEntity;
import com.xincubate.lego.adapter.bridge.ItemBridge;

public abstract class BaseItem implements BridgeEntity{

    protected Context context;

    public BaseItem(Context context){
        this.context = context;
    }

    public abstract void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position);
    public abstract int getLayoutId();
    public void onClick(){}

    private ContextDataSet contextDataSet;

    void setContextDataSet(ContextDataSet contextDataSet){
        this.contextDataSet = contextDataSet;
    }

    public <T> T getContextData(String key){
        if (contextDataSet != null){
            return this.contextDataSet.getContextData(key);
        }else {
            return null;
        }
    }

    /***************Item之间通信的方法***********************/
    private ItemBridge itemBridge;

    public void setItemBridge(ItemBridge itemBridge){
        this.itemBridge = itemBridge;
    }

    public void initSubscribe(){

    }

    public void subscribe(String event){
        if (this.itemBridge != null){
            this.itemBridge.subscribe(event,this);
        }
    }

    public void publishEvent(String event,Object args){
        if (this.itemBridge != null){
            this.itemBridge.publish(this,event,args);
        }
    }

    public void onBridge(Object sender,String event,Object args){
        if (event.equals("pull_to_down")){
            //
        }
    }

    /***************经常用到的Utils方法***********************/
    public void startActivity(Class<? extends Activity> clazz){
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
