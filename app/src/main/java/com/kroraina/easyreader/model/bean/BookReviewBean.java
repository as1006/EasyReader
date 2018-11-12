package com.kroraina.easyreader.model.bean;

/**
 * Created on 17-4-21.
 * 书籍类别讨论
 */
public class BookReviewBean {
    /**
     * _id : 58f8f3efedaa9fe3624a87bb
     * title : 为你写一个中肯的书评，我的访客
     * book : {"_id":"530f3912651881e60d04deb3","cover":"/agent/http://img.17k.com/images/bookcover/2014/3769/18/753884-1399818238000.jpg","title":"我的26岁女房客","site":"zhuishuvip","type":"dsyn","latelyFollower":null,"retentionRatio":null}
     * helpful : {"total":1,"no":5,"yes":6}
     * likeCount : 0
     * haveImage : false
     * state : distillate
     * updated : 2017-04-21T08:20:15.991Z
     * created : 2017-04-20T17:46:23.366Z
     */
    private String _id;
    //获取Book的外键
    private String bookId;


    private String title;
    private ReviewBookBean book;
    private BookHelpfulBean helpful;
    private int likeCount;
    private boolean haveImage;
    private String state;
    private String updated;
    private String created;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isHaveImage() {
        return haveImage;
    }

    public void setHaveImage(boolean haveImage) {
        this.haveImage = haveImage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean getHaveImage() {
        return this.haveImage;
    }


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public ReviewBookBean getBook() {
        return book;
    }

    public void setBook(ReviewBookBean book) {
        this.book = book;
    }

    public BookHelpfulBean getHelpful() {
        return helpful;
    }

    public void setHelpful(BookHelpfulBean helpful) {
        this.helpful = helpful;
    }
}