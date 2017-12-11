package com.example.tom.mybookslist;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable{

    private long id;
    private String title;
    private String author;
    private String dateAdded;
    private String bookStatus;
    private String notes = "";

    public Book(){}

    public Book(int id, String title, String author
                ,String dateAdded
                ,String bookStatus
                ,String notes
                ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateAdded = dateAdded;
        this.bookStatus = bookStatus;
        this.notes = notes;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
