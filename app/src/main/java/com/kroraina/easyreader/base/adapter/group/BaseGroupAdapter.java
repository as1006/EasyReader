package com.kroraina.easyreader.base.adapter.group;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.base.adapter.BaseViewHolder;
import com.kroraina.easyreader.base.annotations.LayoutId;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGroupAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    public interface OnItemClickListener{
        boolean onGroupItemClick(BaseItem item, int groupPosition);
        boolean onChildItemClick(BaseItem item,int groupPosition,int childPosition);
    }

    public interface OnItemLongClickListener{
        boolean onGroupItemLongClick(BaseItem item, int groupPosition);
        boolean onChildItemLongClick(BaseItem item,int groupPosition,int childPosition);
    }

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    public int getGroupCount(){
        return mGroupItems.size();
    }
    public int getChildCount(int groupPosition){
        return mGroupItems.get(groupPosition).getChildCount();
    }

    public BaseGroupItem getGroupItem(int groupPosition){
        return mGroupItems.get(groupPosition);
    }
    public BaseItem getChildItem(int groupPosition,int childPosition){
        return getGroupItem(groupPosition).getChild(childPosition);
    }

    private List<BaseGroupItem> mGroupItems = new ArrayList<>();
    private SparseArray<Class<? extends BaseItem>> viewType2ItemClazz = new SparseArray<>();

    public BaseGroupAdapter(RecyclerView recyclerView, int spanSize){
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(),spanSize);
        manager.setSpanSizeLookup(new GroupSpanSizeLookup(spanSize));
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Class<? extends BaseItem> clazz = viewType2ItemClazz.get(viewType);
        LayoutId layoutId = clazz.getAnnotation(LayoutId.class);
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId.value(),parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {

        viewHolder.itemView.setOnClickListener(view -> {
            getItem(position).onClick();

        });

        getItem(position).onBindViewHolder(viewHolder);
    }

    public BaseItem getItem(int position){
        if (position == 0){
            return getGroupItem(0);
        }

        int total = 1;

        for (int i=0; i<getGroupCount(); ++i){
            int childCount = getChildCount(i);
            if (position < total + childCount){
                return getChildItem(i,position - total);
            }else if (position == total + childCount){
                return getGroupItem(i + 1);
            }
            total += childCount;
            total += 1;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        int groupCount = getGroupCount();
        //因为Group需要有头部
        int totalCount = groupCount;
        for (int i=0; i<groupCount; ++i){
            totalCount += getChildCount(i);
        }
        return totalCount;
    }


    public boolean isGroupPosition(int position){
        if (position == 0){
            return true;
        }

        int total = 1;

        for (int i=0; i<getGroupCount(); ++i){
            int childCount = getChildCount(i);
            if (position < total + childCount){
                return false;
            }else if (position == total + childCount){
                return true;
            }
            total += childCount;
            total += 1;
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemViewType();
    }

    /**
     * 设置Group与child在GridLayoutManager情况下占用的格子
     */
    class GroupSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{
        private int maxSize;
        public GroupSpanSizeLookup(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        public int getSpanSize(int position) {
            return isGroupPosition(position) ? maxSize : 1;
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void addGroupItems(List<? extends BaseGroupItem> groupItems){
        for (BaseGroupItem groupItem : groupItems){
            addGroupItem(groupItem);
        }
    }

    public void addGroupItem(BaseGroupItem groupItem){
        viewType2ItemClazz.put(groupItem.getItemViewType(),groupItem.getClass());
        for (BaseItem childItem : groupItem.getChildItems()){
            viewType2ItemClazz.put(childItem.getItemViewType(),childItem.getClass());
        }
        mGroupItems.add(groupItem);

    }

    public void refreshItems(List<? extends BaseGroupItem> list){
        mGroupItems.clear();
        addGroupItems(list);
        notifyDataSetChanged();
    }
}
