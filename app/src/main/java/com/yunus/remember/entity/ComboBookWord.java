package com.yunus.remember.entity;

import org.litepal.crud.DataSupport;

public class ComboBookWord extends DataSupport{

    private int bookID;
    private int wordID;

    public ComboBookWord() {
    }

    public ComboBookWord(int bookID, int wordID) {
        this.bookID = bookID;
        this.wordID = wordID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }
}
