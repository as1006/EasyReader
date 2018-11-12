package com.kroraina.easyreader.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.kroraina.easyreader.App;
import com.kroraina.easyreader.model.gen.BookChapterBeanDao;
import com.kroraina.easyreader.model.gen.CollBookBeanDao;
import com.kroraina.easyreader.model.gen.DaoSession;
import com.kroraina.easyreader.utils.StringUtils;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created on 17-5-8.
 * 收藏的书籍
 */
@Entity
public class CollBookBean implements Parcelable{

    @Id
    private String _id;
    private String title;
    private String author;
    private String shortIntro;
    private String cover;
    private int latelyFollower;
    private double retentionRatio;
    //最新更新日期
    private String updated;
    //最新阅读日期
    private String lastRead;
    private int chaptersCount;
    private String lastChapter;
    //是否更新或未阅读
    private boolean isUpdate = true;

    @ToMany(referencedJoinProperty = "bookId")
    private List<BookChapterBean> bookChapterList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1552163441)
    private transient CollBookBeanDao myDao;

    @Generated(hash = 1474291485)
    public CollBookBean(String _id, String title, String author, String shortIntro, String cover, int latelyFollower, double retentionRatio, String updated, String lastRead,
            int chaptersCount, String lastChapter, boolean isUpdate) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.shortIntro = shortIntro;
        this.cover = cover;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
        this.updated = updated;
        this.lastRead = lastRead;
        this.chaptersCount = chaptersCount;
        this.lastChapter = lastChapter;
        this.isUpdate = isUpdate;
    }

    public CollBookBean() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return StringUtils.convertCC(title, App.getContext());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return StringUtils.convertCC(author, App.getContext());
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortIntro() {
        return StringUtils.convertCC(shortIntro, App.getContext());
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return StringUtils.convertCC(cover, App.getContext());
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public double getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return StringUtils.convertCC(updated, App.getContext());
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getLastChapter() {
        return StringUtils.convertCC(lastChapter, App.getContext());
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean getIsUpdate() {
        return this.isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getLastRead() {
        return StringUtils.convertCC(lastRead, App.getContext());
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public void setBookChapters(List<BookChapterBean> beans){
        bookChapterList = beans;
        for (BookChapterBean bean : bookChapterList){
            bean.setBookId(get_id());
        }
    }

    public List<BookChapterBean> getBookChapters(){
        if (daoSession == null){
            return bookChapterList;
        }
        else {
            return getBookChapterList();
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 711740787)
    public List<BookChapterBean> getBookChapterList() {
        if (bookChapterList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookChapterBeanDao targetDao = daoSession.getBookChapterBeanDao();
            List<BookChapterBean> bookChapterListNew = targetDao
                    ._queryCollBookBean_BookChapterList(_id);
            synchronized (this) {
                if (bookChapterList == null) {
                    bookChapterList = bookChapterListNew;
                }
            }
        }
        return bookChapterList;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1077762221)
    public synchronized void resetBookChapterList() {
        bookChapterList = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.shortIntro);
        dest.writeString(this.cover);
        dest.writeInt(this.latelyFollower);
        dest.writeDouble(this.retentionRatio);
        dest.writeString(this.updated);
        dest.writeString(this.lastRead);
        dest.writeInt(this.chaptersCount);
        dest.writeString(this.lastChapter);
        dest.writeByte(this.isUpdate ? (byte) 1 : (byte) 0);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 159260324)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCollBookBeanDao() : null;
    }

    protected CollBookBean(Parcel in) {
        this._id = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.shortIntro = in.readString();
        this.cover = in.readString();
        this.latelyFollower = in.readInt();
        this.retentionRatio = in.readDouble();
        this.updated = in.readString();
        this.lastRead = in.readString();
        this.chaptersCount = in.readInt();
        this.lastChapter = in.readString();
        this.isUpdate = in.readByte() != 0;
    }

    public static final Creator<CollBookBean> CREATOR = new Creator<CollBookBean>() {
        @Override
        public CollBookBean createFromParcel(Parcel source) {
            return new CollBookBean(source);
        }

        @Override
        public CollBookBean[] newArray(int size) {
            return new CollBookBean[size];
        }
    };
}