package com.kroraina.easyreader.model.local;

import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;
import com.kroraina.easyreader.App;
import com.kroraina.easyreader.model.gen.DaoMaster;
import com.kroraina.easyreader.model.gen.DaoSession;
import com.kroraina.easyreader.utils.Constant;



public class DaoDbHelper {
    private static final String DB_NAME = "easy_reader_db";

    private static volatile DaoDbHelper sInstance;
    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;

    private DaoDbHelper(){
        //封装数据库的创建、更新、删除
        DaoMaster.DevOpenHelper openHelper = new MyOpenHelper(Utils.getApp(),Constant.DATABASE_PATH + DB_NAME,null);
        //获取数据库
        mDb = openHelper.getWritableDatabase();
        //封装数据库中表的创建、更新、删除
        mDaoMaster = new DaoMaster(mDb);  //合起来就是对数据库的操作
        //对表操作的对象。
        mSession = mDaoMaster.newSession(); //可以认为是对数据的操作
    }


    public static DaoDbHelper getInstance(){
        if (sInstance == null){
            synchronized (DaoDbHelper.class){
                if (sInstance == null){
                    sInstance = new DaoDbHelper();
                }
            }
        }
        return sInstance;
    }

    public DaoSession getSession(){
        return mSession;
    }

    public SQLiteDatabase getDatabase(){
        return mDb;
    }

    public DaoSession getNewSession(){
        return mDaoMaster.newSession();
    }
}
