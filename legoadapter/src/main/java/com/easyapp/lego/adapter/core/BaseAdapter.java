package com.easyapp.lego.adapter.core;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyapp.lego.adapter.annotations.LayoutId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public interface OnItemClickListener{
        boolean onItemClick(BaseItem item, int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(BaseItem item, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public static class ViewItemManager{
        private SparseArray<Class<? extends BaseItem>> viewType2ItemClazz = new SparseArray<>();
        private SparseIntArray viewType2LayoutId = new SparseIntArray();
        private Map<Class<? extends BaseItem>,Integer> itemClazz2ViewType = new HashMap<>();

        public void registerViewType(BaseItem item){
            int viewType = generateViewType(item);
            viewType2ItemClazz.put(viewType,item.getClass());
            LayoutId layoutId = item.getClass().getAnnotation(LayoutId.class);
            if (layoutId != null){
                viewType2LayoutId.put(viewType,layoutId.value());
            }else {
                viewType2LayoutId.put(viewType,item.getLayoutId());
            }
        }
        private int generateViewType(BaseItem item){
            Integer viewType = itemClazz2ViewType.get(item.getClass());
            if (viewType == null){
                viewType = itemClazz2ViewType.keySet().size();
                itemClazz2ViewType.put(item.getClass(),viewType);
                return viewType;
            }else {
                return viewType;
            }
        }

        public int getViewType(Class<? extends BaseItem> itemClass){
            return itemClazz2ViewType.get(itemClass);
        }

        public int getLayoutId(int viewType){
            return viewType2LayoutId.get(viewType);
        }

        public Class<? extends BaseItem> getItemClass(int viewType){
            return viewType2ItemClazz.get(viewType);
        }
    }

    private List<BaseItem> mItems = new ArrayList<>();
    private List<BaseItem> mHeaders = new ArrayList<>();
    private List<BaseItem> mFooters = new ArrayList<>();

    private ViewItemManager mViewItemManager = new ViewItemManager();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends BaseItem> clazz = mViewItemManager.getItemClass(viewType);

        int layoutResId = mViewItemManager.getLayoutId(viewType);
        if (layoutResId == 0){
            throw new RuntimeException(clazz.getSimpleName()+" must have LayoutId Annotation or override getlayoutId");
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId,parent,false);

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
        return mViewItemManager.getViewType(getItem(position).getClass());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void addHeaderItem(BaseItem item){
        mViewItemManager.registerViewType(item);
        mHeaders.add(item);
    }

    public void addFooterItem(BaseItem item){
        mViewItemManager.registerViewType(item);
        mFooters.add(item);
    }

    public void addItem(int index , BaseItem item){
        mViewItemManager.registerViewType(item);
        mItems.add(index,item);
    }

    public void addItem(BaseItem item){
        mViewItemManager.registerViewType(item);
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
