package com.kroraina.easyreader.modules.community.comment;

import com.kroraina.easyreader.modules.community.discuss.AuthorBean;
import com.xincubate.lego.annotation.LegoBean;

@LegoBean(clazz = BookCommentItem.class)
public class BookCommentBean {

    public static class HelpFulBean{
        private int total;
        private int yes;
        private int no;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getYes() {
            return yes;
        }

        public void setYes(int yes) {
            this.yes = yes;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }
    }

    private String _id;
    private int rating;
    private AuthorBean author;
    private HelpFulBean helpful;


    private int likeCount;
    private String state;
    private String updated;
    private String created;
    private int commentCount;
    private String content;
    private String title;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public HelpFulBean getHelpful() {
        return helpful;
    }

    public void setHelpful(HelpFulBean helpful) {
        this.helpful = helpful;
    }
}
