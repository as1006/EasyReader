package com.kroraina.easyreader.modules.local;

import android.content.Context;

import com.kroraina.easyreader.base.adapter.BaseAdapter;
import com.kroraina.easyreader.base.adapter.BaseItem;
import com.kroraina.easyreader.model.local.BookRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class FileSystemAdapter extends BaseAdapter {
    //记录item是否被选中的Map
    private HashMap<File,Boolean> mCheckMap = new HashMap<>();
    private int mCheckedCount = 0;

    private Context context;

    public FileSystemAdapter(Context context){
        this.context = context;
    }

    public List<File> getBeans(){
        List<File> results = new ArrayList<>();
        for (BaseItem item : getItems()){
            if (item instanceof FileItem){
                results.add(((FileItem) item).bean);
            }
        }
        return results;
    }

    public void refreshBeans(List<File> list) {

        List<FileItem> items = new ArrayList<>();
        mCheckMap.clear();
        for(File file : list){
            mCheckMap.put(file, false);
            items.add(new FileItem(context,mCheckMap,file));
        }
        super.refreshItems(items);
    }

    public void addBean(File value) {
        mCheckMap.put(value, false);
        super.addItem(new FileItem(context,mCheckMap,value));
    }

    public void addBean(int index, File value) {
        mCheckMap.put(value, false);
        super.addItem(index, new FileItem(context,mCheckMap,value));
    }

    public void addBeans(List<File> values) {

        List<FileItem> items = new ArrayList<>();

        for(File file : values){
            mCheckMap.put(file, false);
            items.add(new FileItem(context,mCheckMap,file));
        }
        super.addItems(items);
    }


    public void removeBean(File value) {
        BaseItem temp = null;
        mCheckMap.remove(value);
        for (BaseItem item : getItems()){
            if (item instanceof FileItem){
                if (((FileItem) item).bean == value){
                    temp = item;
                    break;
                }
            }
        }
        super.removeItem(temp);
    }

    public void removeBeans(List<File> value) {
        //删除在HashMap中的文件
        for (File file : value){
            mCheckMap.remove(file);
            //因为，能够被移除的文件，肯定是选中的
            --mCheckedCount;
            removeBean(file);
        }
    }

    //设置点击切换
    public void setCheckedItem(int pos){
        File file = ((FileItem)getItem(pos)).bean;
        if (isFileLoaded(file.getAbsolutePath())) return;

        boolean isSelected = mCheckMap.get(file);
        if (isSelected){
            mCheckMap.put(file, false);
            --mCheckedCount;
        }
        else{
            mCheckMap.put(file, true);
            ++mCheckedCount;
        }
        notifyDataSetChanged();
    }

    public void setCheckedAll(boolean isChecked){
        Set<Map.Entry<File, Boolean>> entrys = mCheckMap.entrySet();
        mCheckedCount = 0;
        for (Map.Entry<File, Boolean> entry:entrys){
            //必须是文件，必须没有被收藏
            if (entry.getKey().isFile() && !isFileLoaded(entry.getKey().getAbsolutePath())){
                entry.setValue(isChecked);
                //如果选中，则增加点击的数量
                if (isChecked){
                    ++mCheckedCount;
                }
            }
        }
        notifyDataSetChanged();
    }

    private boolean isFileLoaded(String id){
        //如果是已加载的文件，则点击事件无效。
        return BookRepository.getInstance().getCollBook(id) != null;
    }

    public int getCheckableCount(){
        List<File> files = getBeans();
        int count = 0;
        for (File file : files){
            if (!isFileLoaded(file.getAbsolutePath()) && file.isFile())
                ++count;
        }
        return count;
    }

    public boolean getItemIsChecked(int pos){
        File file = ((FileItem)getItem(pos)).bean;
        return mCheckMap.get(file);
    }

    public List<File> getCheckedFiles(){
        List<File> files = new ArrayList<>();
        Set<Map.Entry<File, Boolean>> entrys = mCheckMap.entrySet();
        for (Map.Entry<File, Boolean> entry:entrys){
            if (entry.getValue()){
                files.add(entry.getKey());
            }
        }
        return files;
    }

    public int getCheckedCount(){
        return mCheckedCount;
    }

    public HashMap<File,Boolean> getCheckMap(){
        return mCheckMap;
    }
}
