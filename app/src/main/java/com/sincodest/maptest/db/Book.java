package com.sincodest.maptest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/3/16.
 */

public class Book extends DataSupport{
    private int id;
    private String name;
    private double price;
    private int pages;

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.name = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getPages() {
        return pages;
    }
}
