package com.yunus.remember.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

public class TodayWord extends DataSupport implements Parcelable {

    public static final Parcelable.Creator<TodayWord> CREATOR = new Parcelable.Creator<TodayWord>() {

        @Override
        public TodayWord createFromParcel(Parcel parcel) {
            TodayWord todayWord = new TodayWord();
            todayWord.spell = parcel.readString();
            todayWord.mean = parcel.readString();
            todayWord.phonogram = parcel.readString();
            todayWord.sentence = parcel.readString();
            todayWord.level = parcel.readInt();
            return todayWord;
        }

        @Override
        public TodayWord[] newArray(int i) {
            return new TodayWord[i];
        }
    };
    private String spell;
    private String mean;
    private String phonogram;
    private String sentence;
    private int level;
    private Word word;

    public TodayWord() {
    }

    public TodayWord(String spell, String mean, String phonogram, String sentence, int level) {
        this.spell = spell;
        this.mean = mean;
        this.phonogram = phonogram;
        this.sentence = sentence;
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(spell);
        parcel.writeString(mean);
        parcel.writeString(phonogram);
        parcel.writeString(sentence);
        parcel.writeInt(level);
    }
}
