package com.lammyngoc.bookstore.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookId;
    private String bookName;
    private float unitPrice;
    private int imageId;
    private Publisher publisher;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Book(String bookId, String bookName, float unitPrice, int imageId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Book() {
    }

    public Book(String bookId, String bookName, float unitPrice) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.unitPrice = unitPrice;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        String msg=bookId+"\n"+bookName+"\n"+unitPrice+"(VND)";
        return msg;
    }
}

