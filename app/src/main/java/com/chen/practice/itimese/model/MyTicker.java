package com.chen.practice.itimese.model;

import java.io.Serializable;

public class MyTicker implements Serializable {

    private static final long serialVersionUID = 2470451358670939348L;

    public String title, remark;
    public RepeatCycle repeatCycle;
    public Date date;
    public String imageUriPath;

    public MyTicker() {
        this.repeatCycle = new RepeatCycle();
        this.date = new Date();
        this.imageUriPath = "";
    }

    public void setDate(int year, int month, int day) {
        this.date.year = year;
        this.date.month = month;
        this.date.day = day;
    }
}
