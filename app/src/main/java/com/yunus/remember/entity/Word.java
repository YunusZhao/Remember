package com.yunus.remember.entity;


/**
 * Created by yun on 2018/3/31.
 */

public class Word {

    private String spell;

    private String mean;

    public Word() {

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
