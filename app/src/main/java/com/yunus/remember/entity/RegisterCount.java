package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.sql.Date;

//打卡记录
public class RegisterCount extends DataSupport implements Serializable {
    private int dayCount;
    private String registerDate;
    private int wordNum;
    private int studyTime;

    public RegisterCount() {
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }
}
