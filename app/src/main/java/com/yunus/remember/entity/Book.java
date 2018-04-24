package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

public class Book extends DataSupport{

    private int id;
    private String name;
    private int wordNum;
    private int studyWordNum;
    private int state;//已学完1/正在学-1/已收藏0

    public Book() {
    }

    public Book(int id, String name, int wordNum, int studyWordNum, byte state) {
        this.id = id;
        this.name = name;
        this.wordNum = wordNum;
        this.studyWordNum = studyWordNum;
        this.state = state;
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

    public int getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
