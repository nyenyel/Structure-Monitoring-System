package com.example.structuremonitoringsystem.Item;

public class DataLogsItem {
    String time, xyzData;
    int day, week, month, year;

    public DataLogsItem(String time, String xyzData, int day, int week, int month, int year) {
        this.time = time;
        this.xyzData = xyzData;
        this.day = day;
        this.week = week;
        this.month = month;
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getXyzData() {
        return xyzData;
    }

    public void setXyzData(String xyzData) {
        this.xyzData = xyzData;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
