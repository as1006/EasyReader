package com.kroraina.easyreader.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kroraina.easyreader.base.annotations.LayoutId;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public interface OnItemClickListener{
        boolean onItemClick(BaseItem item, int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(BaseItem item, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    private List<BaseItem> mItems = new ArrayList<>();

    private List<BaseItem> mHeaders = new ArrayList<>();
    private List<BaseItem> mFooters = new ArrayList<>();

    private SparseArray<Class<? extends BaseItem>> viewType2ItemClazz = new SparseArray<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends BaseItem> clazz = viewType2ItemClazz.get(viewType);

        LayoutId layoutId = clazz.getAnnotation(LayoutId.class);
        if (layoutId == null){
            throw new RuntimeException(clazz.getSimpleName()+" must have LayoutId Annotation");
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId.value(),parent,false);

        BaseViewHolder baseViewHolder = new BaseViewHolder(view);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {

        viewHolder.itemView.setOnClickListener(view -> {
            if (mOnItemClickListener == null || !mOnItemClickListener.onItemClick(getItem(position),position)){
                getItem(position).onClick();
            }
        });


        if (mOnItemLongClickListener != null){
            viewHolder.itemView.setOnLongClickListener(v -> {
                return mOnItemLongClickListener.onItemLongClick(mItems.get(position),position);
            });
        }else {
            viewHolder.itemView.setOnLongClickListener(null);
        }


        getItem(position).onBindViewHolder(viewHolder);
    }

    @Override
    public int getItemCount() {
        return mItems.size() + mHeaders.size() + mFooters.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemViewType();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void addHeaderItem(BaseItem item){
        viewType2ItemClazz.put(item.getItemViewType(),item.getClass());
        mHeaders.add(item);
    }

    public void addFooterItem(BaseItem item){
        viewType2ItemClazz.put(item.getItemViewType(),item.getClass());
        mFooters.add(item);
    }

    public void addItem(int index , BaseItem item){
        viewType2ItemClazz.put(item.getItemViewType(),item.getClass());
        mItems.add(index,item);
    }

    public void addItem(BaseItem item){
        viewType2ItemClazz.put(item.getItemViewType(),item.getClass());
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

    public void refreshItems(List<? extends BaseItem> list){
        mItems.clear();
        addItems(list);
        notifyDataSetChanged();
    }

}
