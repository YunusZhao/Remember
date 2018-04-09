package com.yunus.remember.entity;


import org.litepal.crud.DataSupport;

/**
 * Created by yun on 2018/3/31.
 */

public class Word extends DataSupport{

    private int id;

    private String spell;

    private String mean;

    private String phonogram;

    private String sentence;

    private int level ;

    public Word() {

    }

    public Word(int id, String spell, String mean, String phonogram, String sentence) {
        this.id = id;
        this.spell = spell;
        this.mean = mean;
        this.phonogram = phonogram;
        this.sentence = sentence;
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

    public Word(String spell, String mean) {
        this.spell = spell;
        this.mean = mean;
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
}
