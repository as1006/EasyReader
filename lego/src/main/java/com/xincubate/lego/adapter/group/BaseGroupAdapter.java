package com.xincubate.lego.adapter.group;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xincubate.lego.adapter.bridge.ItemBridge;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.layoutcenter.LayoutCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseGroupAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    public interface OnItemClickListener{
        boolean onGroupItemClick(BaseItem item, int groupPosition);
        boolean onChildItemClick(BaseItem item, int groupPosition, int childPosition);
    }

    public interface OnItemLongClickListener{
        boolean onGroupItemLongClick(BaseItem item, int groupPosition);
        boolean onChildItemLongClick(BaseItem item, int groupPosition, int childPosition);
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


    private List<BaseGroupItem> mGroupItems = new ArrayList<>();

    protected Context mContext;

    public BaseGroupAdapter(Context context){
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Class<? extends BaseItem> clazz = LayoutCenter.getItemClass(viewType);

        int layoutResId = LayoutCenter.getLayoutId(viewType);
        if (layoutResId == 0){
            throw new RuntimeException(clazz.getSimpleName()+" must override getlayoutId and layoutResId must != 0");
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId,parent,false);

        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {

        viewHolder.itemView.setOnClickListener(view -> {
            getItem(position).onClick();

        });

        getItem(position).onBindViewHolder(viewHolder,position);
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

    public void addGroupItems(List<? extends BaseGroupItem> groupItems){
        for (BaseGroupItem groupItem : groupItems){
            addGroupItem(groupItem);
        }
    }

    public void addGroupItem(BaseGroupItem groupItem){
        mGroupItems.add(groupItem);
    }

    public void refreshItems(List<? extends BaseGroupItem> list){
        mGroupItems.clear();
        addGroupItems(list);
        notifyDataSetChanged();
    }

    /**
     * 设置Group与child在GridLayoutManager情况下占用的格子
     */
    public class GroupSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{
        private int maxSize;
        public GroupSpanSizeLookup(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        public int getSpanSize(int position) {
            return isGroupPosition(position) ? maxSize : 1;
        }
    }
}
