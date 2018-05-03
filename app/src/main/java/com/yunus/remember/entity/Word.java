package com.yunus.remember.entity;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yun on 2018/3/31.
 */

public class Word extends DataSupport {

    List<Book> bookList = new ArrayList<>();
    List<Friend> friendList = new ArrayList<>();

    private int id;

    private String spell;

    private String mean;

    private String phonogram;

    private String sentence;

    private int level;

    private int importance;

    public Word() {

    }

    public Word(int id, String spell, String mean, String phonogram, String sentence) {
        this.id = id;
        this.spell = spell;
        this.mean = mean;
        this.phonogram = phonogram;
        this.sentence = sentence;
    }

    public Word(String spell, String mean) {
        this.spell = spell;
        this.mean = mean;
    }

    public Word(String spell, String mean, String phonogram) {
        this.spell = spell;
        this.mean = mean;
        this.phonogram = phonogram;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhonogram() {
        return phonogram;
    }

    public void setPhonogram(String phonogram) {
        this.phonogram = phonogram;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }
}
