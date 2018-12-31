package com.kroraina.easyreader.model.local;

import com.google.gson.Gson;
import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.model.gen.DaoSession;
import com.kroraina.easyreader.modules.rank.RankListPackage;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.LogUtils;
import com.kroraina.easyreader.utils.SharedPreUtils;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;



public class LocalRepository implements SaveDbHelper,GetDbHelper{
    private static final String TAG = "LocalRepository";
    private static final String DISTILLATE_ALL = "normal";
    private static final String DISTILLATE_BOUTIQUES = "distillate";

    private static volatile LocalRepository sInstance;
    private DaoSession mSession;
    private LocalRepository(){
        mSession = DaoDbHelper.getInstance().getSession();
    }

    public static LocalRepository getInstance(){
        if (sInstance == null){
            synchronized (LocalRepository.class){
                if (sInstance == null){
                    sInstance = new LocalRepository();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void saveBillboardPackage(RankListPackage bean) {
        String json = new Gson().toJson(bean);
        SharedPreUtils.getInstance()
                .putString(Constant.SHARED_SAVE_BILLBOARD,json);
    }

    @Override
    public void saveDownloadTask(DownloadTaskBean bean) {
        BookRepository.getInstance()
                .saveBookChaptersWithAsync(bean.getBookChapters());
        mSession.getDownloadTaskBeanDao()
                .insertOrReplace(bean);
    }

    /***************************************read data****************************************************/

    @Override
    public RankListPackage getBillboardPackage() {
        String json = SharedPreUtils.getInstance()
                .getString(Constant.SHARED_SAVE_BILLBOARD);
        if (json == null){
            return null;
        }
        else {
            return new Gson().fromJson(json, RankListPackage.class);
        }
    }

    @Override
    public List<DownloadTaskBean> getDownloadTaskList() {
        return mSession.getDownloadTaskBeanDao()
                .loadAll();
    }

    private <T> void queryOrderBy(QueryBuilder queryBuilder, Class<T> daoCls,String orderBy){
        //获取Dao中的Properties
        Class<?>[] innerCls = daoCls.getClasses();
        Class<?> propertiesCls = null;
        for (Class<?> cls : innerCls){
            if (cls.getSimpleName().equals("Properties")){
                propertiesCls = cls;
                break;
            }
        }
        //如果不存在则返回
        if (propertiesCls == null) return;

        //这里没有进行异常处理有点小问题
        try {
            Field field = propertiesCls.getField(orderBy);
            Property property = (Property) field.get(propertiesCls);
            queryBuilder.orderDesc(property);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtils.e(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtils.e(e);
        }
    }

    private <T> Single<List<T>> queryToRx(QueryBuilder<T> builder){
        return Single.create(new SingleOnSubscribe<List<T>>() {
            @Override
            public void subscribe(SingleEmitter<List<T>> e) {
                List<T> data = builder.list();
                if (data == null){
                    data = new ArrayList<T>(1);
                }
                e.onSuccess(data);
            }
        });
    }

}
