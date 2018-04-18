package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

import java.sql.Date;

//七天记录，方便图表
public class SevenDaysReview extends DataSupport{

    private Date theDate;
    private int wordsCount;
    private int hadCount;
    private int studiedCount;
    private int studiedTime;

    public SevenDaysReview() {
    }

    public Date getTheDate() {
        return theDate;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getHadCount() {
        return hadCount;
    }

    public void setHadCount(int hadCount) {
        this.hadCount = hadCount;
    }

    public int getStudiedCount() {
        return studiedCount;
    }

    public void setStudiedCount(int studiedCount) {
        this.studiedCount = studiedCount;
    }

    public int getStudiedTime() {
        return studiedTime;
    }

    public void setStudiedTime(int studiedTime) {
        this.studiedTime = studiedTime;
    }
}
