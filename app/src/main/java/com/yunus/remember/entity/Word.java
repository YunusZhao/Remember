package com.yunus.remember.entity;


/**
 * Created by yun on 2018/3/31.
 */

public class Word {

    private String spell;

    private String chineseMean;

    public Word() {

    }

    public Word(String spell, String mean) {
        this.spell = spell;
        this.chineseMean = mean;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getChineseMean() {
        return chineseMean;
    }

    public void setChineseMean(String chineseMean) {
        this.chineseMean = chineseMean;
    }
}
