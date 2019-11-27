package com.chen.practice.itimese.model;

public class MyTicker {

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
