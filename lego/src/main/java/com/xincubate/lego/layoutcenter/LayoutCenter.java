package com.xincubate.lego.layoutcenter;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.xincubate.lego.adapter.bean.ItemBuilder;
import com.xincubate.lego.adapter.core.BaseItem;

import java.util.HashMap;
import java.util.Map;

public class LayoutCenter {

    public static class LayoutCenterHolder {
        public static LayoutCenter HOLDER = new LayoutCenter();
    }

    public static LayoutCenter getInstance(){
        return LayoutCenterHolder.HOLDER;
    }

    private LayoutCenter(){

    }



    private SparseArray<Class<? extends BaseItem>> viewType2ItemClazz = new SparseArray<>();
    private SparseIntArray viewType2LayoutId = new SparseIntArray();
    private Map<Class<? extends BaseItem>,Integer> itemClazz2ViewType = new HashMap<>();

    public static Class<? extends BaseItem> getItemClass(int viewType){
        return getInstance().viewType2ItemClazz.get(viewType);
    }

    public static int getLayoutId(int viewType){
        return getInstance().viewType2LayoutId.get(viewType);
    }

    public static int getViewType(Class<? extends BaseItem> itemClass){
        return getInstance().itemClazz2ViewType.get(itemClass);
    }

    /**
     * 根据Bean的类型去找到相应的ItemBuilder
     * @param context
     * @param bean
     * @return
     */
    public static BaseItem buildItem(Context context, Object bean){
        return buildItem(context,bean,bean.getClass());
    }

    /**
     * 根据Bean的类型去找到相应的ItemBuilder
     * @param context
     * @param bean
     * @param beanClass
     * @return
     */
    public static BaseItem buildItem(Context context, Object bean, Class<?> beanClass){
        ItemBuilder itemBuilder = getInstance().mItemBuilders.get(beanClass);
        if (itemBuilder != null){
            BaseItem beanItem = itemBuilder.build(context, bean);
            getInstance().registerViewType(beanItem.getClass());
            return beanItem;
        }else {
            Class<?> superClass = beanClass.getSuperclass();
            if (superClass != null && !superClass.equals(Object.class)){
                return buildItem(context,bean,superClass);
            }else {
                return null;
            }
        }
    }


    public void registerViewType(Class<? extends BaseItem> itemClass){
        int viewType = generateViewType(itemClass);
        viewType2ItemClazz.put(viewType,itemClass);
    }

    public void registerLayoutId(int viewType,int layoutId){
        viewType2LayoutId.put(viewType,layoutId);
    }

    private int generateViewType(Class<? extends BaseItem> itemClass){

        Integer viewType = itemClazz2ViewType.get(itemClass);
        if (viewType == null){
            viewType = itemClazz2ViewType.keySet().size();
            itemClazz2ViewType.put(itemClass,viewType);
            return viewType;
        }else {
            return viewType;
        }


    }

    private Map<Class<?>,ItemBuilder> mItemBuilders = new HashMap<>();
    public <T> void registerItemBuilder(Class<T> entityClass, ItemBuilder<T> itemBuilder){
        mItemBuilders.put(entityClass,itemBuilder);
    }



}
