package com.xincubate.lego.adapter.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xincubate.lego.adapter.bridge.ItemBridge;
import com.xincubate.lego.layoutcenter.LayoutCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> implements ContextDataSet{

    /**
     * 处理BaseAdapter中Item的点击事件，优先处理容器的点击事件，根据返回值来决定是否执行item内部的OnClick方法
     */
    public interface OnItemClickListener{
        /**
         * 处理Item的点击事件，
         * 返回值
         * true 表示处理已经结束，不再继续处理
         * false 表示处理没有接触，基础处理Item的OnClick方法
         */
        boolean onItemClick(BaseItem item, int position);
    }

    /**
     * 处理BaseAdapter中Item的长按事件，优先处理容器的点击事件，根据返回值来决定是否执行item内部的OnClick方法
     */
    public interface OnItemLongClickListener{
        boolean onItemLongClick(BaseItem item, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 主要数据
     */
    private List<BaseItem> mItems = new ArrayList<>();

    private List<BaseItem> mHeaders = new ArrayList<>();
    private List<BaseItem> mFooters = new ArrayList<>();

    /**
     *存储Item的上下文数据
     */
    private Map<String,Object> contextData = new HashMap<>();
    public void addContextData(String key,Object object){
        if (key != null && object != null){
            this.contextData.put(key,object);
        }
    }
    public void addContextData(Map<String,Object> contextData){
        if (contextData != null){
            this.contextData.putAll(contextData);
        }
    }
    public void removeContextData(String key){
        if (key != null){
            this.contextData.remove(key);
        }
    }

    /**
     * 用于Item之间的通信使用，使用者应该在
     */
    private ItemBridge mItemBridge = new ItemBridge();

    public ItemBridge getItemBridge() {
        return mItemBridge;
    }

    protected Context mContext;

    public BaseAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends BaseItem> clazz = LayoutCenter.getItemClass(viewType);

        int layoutResId = LayoutCenter.getLayoutId(viewType);
        if (layoutResId == 0){
            throw new RuntimeException(clazz.getSimpleName()+" must override getlayoutId and layoutResId must != 0");
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId,parent,false);

        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {

        Log.v("BaseAdapter","onBindViewHolder position = "+position);

        viewHolder.itemView.setOnClickListener(view -> {
            if (mOnItemClickListener == null || !mOnItemClickListener.onItemClick(getItem(position),position)){
                getItem(position).onClick();
            }
        });


        if (mOnItemLongClickListener != null){
            viewHolder.itemView.setOnLongClickListener(v -> mOnItemLongClickListener.onItemLongClick(mItems.get(position),position));
        }else {
            viewHolder.itemView.setOnLongClickListener(null);
        }


        getItem(position).onBindViewHolder(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        Log.v("BaseAdapter","getItemCount result = "+(mItems.size() + mHeaders.size() + mFooters.size()));
        return mItems.size() + mHeaders.size() + mFooters.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseItem item = getItem(position);
        int viewType = LayoutCenter.getViewType(item.getClass());
        LayoutCenter.getInstance().registerLayoutId(viewType,item.getLayoutId());
        return viewType;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void addHeaderItem(BaseItem item){
        mHeaders.add(item);
    }

    public void addFooterItem(BaseItem item){
        mFooters.add(item);
    }

    public void addItem(int index , BaseItem item){
        item.setItemBridge(mItemBridge);
        item.initSubscribe();
        item.setContextDataSet(this);
        mItems.add(index,item);
    }

    public void addItem(BaseItem item){
        item.setItemBridge(mItemBridge);
        item.initSubscribe();
        item.setContextDataSet(this);
        mItems.add(item);
    }

    public void addItems(List<? extends BaseItem> items){
        for (BaseItem item : items){
            addItem(item);
        }
    }

    public List<BaseItem> getItems(){
        return mItems;
    }

    public BaseItem getItem(int position){
        if (position < mHeaders.size()){
            return mHeaders.get(position);
        }else if (position - mHeaders.size() < mItems.size()){
            return mItems.get(position - mHeaders.size());
        }else {
            return mFooters.get(position - mHeaders.size() - mItems.size());
        }
    }

    public void clear(){
        mItems.clear();
    }

    public void removeItem(BaseItem item){
        if (mItems.remove(item)){
            notifyDataSetChanged();
        }
    }

    public void removeItem(int index){
        if (index >= 0 && index < mItems.size()){
            mItems.remove(index);
            notifyDataSetChanged();
        }
    }

    public void refreshItems(List<? extends BaseItem> list){
        mItems.clear();
        addItems(list);
        notifyDataSetChanged();
    }

    private List<BaseItem> mOldListItemsInDiff = new ArrayList<>();

    /**
     * diff 增量更新
     * 注意 BaseItem 需要 实现 equals 和 hashcode，告诉字段是否变了
     * @param list
     */
    public void refreshItemsByDiff(List<? extends BaseItem> list){

        mItems.clear();
        addItems(list);

        List<BaseItem> newListItems = new ArrayList<>();
        newListItems.addAll(mItems);
        newListItems.addAll(mHeaders);
        newListItems.addAll(mFooters);

        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new AdapterDiffCallback(mOldListItemsInDiff, newListItems));
        diffResult.dispatchUpdatesTo(this);

        mOldListItemsInDiff.clear();
        mOldListItemsInDiff.addAll(newListItems);
    }


    @Override
    public <T> T getContextData(String key) {
        Object object = this.contextData.get(key);
        return (T) object;
    }

}
