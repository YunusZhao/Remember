package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

import java.sql.Date;

//七天记录，方便图表
public class SevenDaysReview extends DataSupport {


    private int allWordsCount;
    private int allHadCount;
    private int todayStudiedCount;
    private int studiedTime;
    private String theDate;
    public SevenDaysReview() {
    }

    public SevenDaysReview(String theDate, int allHadCount, int allWordsCount) {
        this.theDate = theDate;
        this.allHadCount = allHadCount;
        this.allWordsCount = allWordsCount;
    }

    public String getTheDate() {
        return theDate;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public int getAllWordsCount() {
        return allWordsCount;
    }

    public void setAllWordsCount(int allWordsCount) {
        this.allWordsCount = allWordsCount;
    }

    public int getAllHadCount() {
        return allHadCount;
    }

    public void setAllHadCount(int allHadCount) {
        this.allHadCount = allHadCount;
    }

    public int getTodayStudiedCount() {
        return todayStudiedCount;
    }

    public void setTodayStudiedCount(int todayStudiedCount) {
        this.todayStudiedCount = todayStudiedCount;
    }

    public int getStudiedTime() {
        return studiedTime;
    }

    public void setStudiedTime(int studiedTime) {
        this.studiedTime = studiedTime;
    }
}
