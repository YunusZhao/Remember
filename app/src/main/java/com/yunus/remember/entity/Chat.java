package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

import java.sql.Date;

public class Chat extends DataSupport {

    public static final byte TYPE_RECEIVED = 0;
    public static final byte TYPE_SENT = 1;

    private int friendId;
    private int type;
    private String message;
    private boolean read;
    private Date time;


    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
