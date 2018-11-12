package com.kroraina.easyreader.modules.community.discuss;

public class AuthorBean {
    private String _id;
    private String avatar;
    private String nickname;
    private String activityAvatar;
    private String type;
    private int lv;
    private String gender;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getActivityAvatar() {
        return activityAvatar;
    }

    public void setActivityAvatar(String activityAvatar) {
        this.activityAvatar = activityAvatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
