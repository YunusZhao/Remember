package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

public class Book extends DataSupport{

    private int id;
    private String name;
    private int wordNum;
    private int studyWordNum;
    private byte state;//已学完1/正在学-1/已收藏0

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public int getStudyWordNum() {
        return studyWordNum;
    }

    public void setStudyWordNum(int studyWordNum) {
        this.studyWordNum = studyWordNum;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
